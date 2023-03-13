package bean;

/**
 * @Author: longyh
 * @Description:
 * @Date: Created in 15:34 2023/2/10
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
