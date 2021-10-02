package learn.flinklearn.nebula.entity;

import lombok.Data;

import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/27 2:32 下午
 */
@Data
public class NebulaTag {
    private String tagName;
    private int idIndex;
    private List<String> fieldNameList;
    private List<Integer> positionList;
    private int batchSize;
    private List<String> rowFieldNameList;
}
