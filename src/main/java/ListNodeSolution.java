import bean.ListNode;

/**
 * @Author: longyh
 * @Description:
 * @Date: Created in 15:41 2023/2/10
 */
public class ListNodeSolution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }

        ListNode headNode = head;
        ListNode curNode = head;

        int count = 0;
        while (curNode.next != null) {
            count++;
            if (count % k == 0) {
                for (int i = 0; i < k; i++) {
                    ListNode tmp = headNode.next;
                    head.next = curNode;
                }
            }
            curNode = curNode.next;
        }
        return head;
    }
}
