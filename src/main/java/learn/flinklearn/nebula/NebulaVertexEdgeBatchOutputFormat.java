package learn.flinklearn.nebula;

import com.facebook.thrift.TException;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.Session;
import com.vesoft.nebula.client.meta.MetaClient;
import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.nebula.connection.NebulaGraphConnectionProvider;
import org.apache.flink.connector.nebula.connection.NebulaMetaConnectionProvider;
import org.apache.flink.connector.nebula.sink.NebulaBatchExecutor;
import org.apache.flink.connector.nebula.sink.NebulaBatchOutputFormat;
import org.apache.flink.connector.nebula.statement.ExecutionOptions;
import org.apache.flink.connector.nebula.utils.VidTypeEnum;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Flushable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/26 2:13 下午
 */
public class NebulaVertexEdgeBatchOutputFormat<T> extends RichOutputFormat<T> implements Flushable {
    private static final Logger LOG = LoggerFactory.getLogger(NebulaVertexEdgeBatchOutputFormat.class);
    private static final long serialVersionUID = 8846672119763512586L;

    //    private volatile AtomicLong numPendingRow;
    private volatile Map<String, AtomicLong> numPendingRowMap;
    private Session session;
    private MetaClient metaClient;
    //    private NebulaBatchExecutor nebulaBatchExecutor;
    private NebulaGraphConnectionProvider graphProvider;
    private NebulaMetaConnectionProvider metaProvider;

    private List<ExecutionOptions> executionOptionsList;

    // key: label value: NebulaVertexEdgeBatchExecutor
    private Map<String, NebulaVertexEdgeBatchExecutor> nebulaBatchExecutorMap;

    private List<String> errorBuffer = new ArrayList<>();

    public NebulaVertexEdgeBatchOutputFormat(NebulaGraphConnectionProvider graphProvider,
                                             NebulaMetaConnectionProvider metaProvider) {
        this.graphProvider = graphProvider;
        this.metaProvider = metaProvider;
    }


    @Override
    public void configure(Configuration configuration) {
    }

    @Override
    public void open(int i, int i1) throws IOException {
        try {
            session = graphProvider.getSession();
        } catch (NotValidConnectionException | IOErrorException | AuthFailedException e) {
            LOG.error("failed to get graph session, ", e);
            throw new IOException("get graph session error, ", e);
        }
        try {
            metaClient = metaProvider.getMetaClient();
        } catch (TException e) {
            LOG.error("failed to get meta client, ", e);
            throw new IOException("get metaClient error, ", e);
        }

        numPendingRowMap = new HashMap<>();
        nebulaBatchExecutorMap = new HashMap<>();

        parseExecutionOptionsList();

    }

    private void parseExecutionOptionsList() {
        executionOptionsList.forEach(executionOptions -> {
            VidTypeEnum vidType = metaProvider.getVidType(metaClient, executionOptions.getGraphSpace());
            boolean isVertex = executionOptions.getDataType().isVertex();
            Map<String, Integer> schema;
            if (isVertex) {
                schema = metaProvider.getTagSchema(metaClient, executionOptions.getGraphSpace(),
                        executionOptions.getLabel());
            } else {
                schema = metaProvider.getEdgeSchema(metaClient, executionOptions.getGraphSpace(),
                        executionOptions.getLabel());
            }
            NebulaVertexEdgeBatchExecutor nebulaBatchExecutor = new NebulaVertexEdgeBatchExecutor(executionOptions, isVertex, vidType, schema);

            AtomicLong atomicLong = new AtomicLong(0);

            numPendingRowMap.put(executionOptions.getLabel(), atomicLong);
            nebulaBatchExecutorMap.put(executionOptions.getLabel(), nebulaBatchExecutor);
        });
    }

    @Override
    public void writeRecord(T t) throws IOException {
        if (t instanceof Tuple2) {
            String label = ((Tuple2<String, Row>) t).f0;
            Row value = ((Tuple2<String, Row>) t).f1;
            NebulaVertexEdgeBatchExecutor nebulaVertexEdgeBatchExecutor = nebulaBatchExecutorMap.get(label);
            nebulaVertexEdgeBatchExecutor.addToBatch(value);

            AtomicLong atomicLong = numPendingRowMap.get(label);
            ExecutionOptions executionOptions = nebulaVertexEdgeBatchExecutor.getExecutionOptions();

            if (atomicLong.incrementAndGet() >= executionOptions.getBatch()) {
                commit(nebulaVertexEdgeBatchExecutor, executionOptions, atomicLong);
            }
        }


    }

    @Override
    public void close() throws IOException {
        Iterator<Map.Entry<String, NebulaVertexEdgeBatchExecutor>> entryIterator = nebulaBatchExecutorMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, NebulaVertexEdgeBatchExecutor> next = entryIterator.next();
            String label = next.getKey();
            NebulaVertexEdgeBatchExecutor batchExecutor = next.getValue();

            AtomicLong atomicLong = numPendingRowMap.get(label);
            if (atomicLong.get() > 0) {
                commit(batchExecutor, batchExecutor.getExecutionOptions(), atomicLong);
            }
        }

        if (!errorBuffer.isEmpty()) {
            LOG.error("insert error statements: ", errorBuffer);
        }
        session.release();
    }

    @Override
    public void flush() throws IOException {
        Iterator<Map.Entry<String, NebulaVertexEdgeBatchExecutor>> entryIterator = nebulaBatchExecutorMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, NebulaVertexEdgeBatchExecutor> next = entryIterator.next();
            String label = next.getKey();
            NebulaVertexEdgeBatchExecutor batchExecutor = next.getValue();

            AtomicLong atomicLong = numPendingRowMap.get(label);
            if (atomicLong.get() != 0) {
                commit(batchExecutor, batchExecutor.getExecutionOptions(), atomicLong);
            }
        }
    }


    /**
     * commit batch insert statements
     */
    private synchronized void commit(NebulaVertexEdgeBatchExecutor nebulaVertexEdgeBatchExecutor
            , ExecutionOptions executionOptions, AtomicLong numPendingRow) throws IOException {
        ResultSet resultSet;
        try {
            resultSet = session.execute("USE " + executionOptions.getGraphSpace());
        } catch (IOErrorException e) {
            LOG.error("switch space error, ", e);
            throw new IOException("switch space error,", e);
        }

        if (resultSet.isSucceeded()) {
            String errorExec = nebulaVertexEdgeBatchExecutor.executeBatch(session);
            if (errorExec != null) {
                errorBuffer.add(errorExec);
            }
            long pendingRow = numPendingRow.get();
            numPendingRow.compareAndSet(pendingRow, 0);
        } else {
            LOG.error("switch space failed, ", resultSet.getErrorMessage());
        }
    }

    public NebulaVertexEdgeBatchOutputFormat<T> setExecutionOptions(List<ExecutionOptions> executionOptionsList) {
        this.executionOptionsList = executionOptionsList;
        return this;
    }
}
