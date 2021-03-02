package leetcode.addtwonum_002;

import lombok.Data;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/2/28 12:28
 */
@Data
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}