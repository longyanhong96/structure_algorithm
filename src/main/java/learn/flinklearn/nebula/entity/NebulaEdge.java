package learn.flinklearn.nebula.entity;

import lombok.Data;

import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/27 2:42 下午
 */
@Data
public class NebulaEdge {
    private String edgeName;
    private int srcIndex;
    private int dstIndex;
    private int rankIndex;
    private List<String> fieldNameList;
    private List<Integer> positionList;
    private int batchSize;
    private List<String> rowFieldNameList;
}
