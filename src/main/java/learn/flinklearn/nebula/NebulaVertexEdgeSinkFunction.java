package learn.flinklearn.nebula;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/26 2:09 下午
 */
public class NebulaVertexEdgeSinkFunction<T> extends RichSinkFunction<T> implements CheckpointedFunction {

    private final NebulaVertexEdgeBatchOutputFormat<T> outPutFormat;

    private final AtomicReference<Throwable> failureThrowable = new AtomicReference<>();

    public NebulaVertexEdgeSinkFunction(NebulaVertexEdgeBatchOutputFormat<T> outPutFormat) {
        super();
        this.outPutFormat = outPutFormat;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        RuntimeContext ctx = getRuntimeContext();
        outPutFormat.setRuntimeContext(ctx);
        try {
            outPutFormat.open(ctx.getIndexOfThisSubtask(), ctx.getNumberOfParallelSubtasks());
        } catch (IOException e) {
            failureThrowable.compareAndSet(null, e);
        }
    }

    @Override
    public void close() throws Exception {
        outPutFormat.close();
    }

    @Override
    public void invoke(T value, Context context) throws Exception {
        checkErrorAndRethrow();
        outPutFormat.writeRecord(value);
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        flush();
        checkErrorAndRethrow();
    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {

    }

    private void checkErrorAndRethrow() {
        Throwable cause = failureThrowable.get();
        if (cause != null) {
            throw new RuntimeException("An error occurred in NebulaSink.", cause);
        }
    }

    private void flush() throws IOException {
        outPutFormat.flush();
    }
}
