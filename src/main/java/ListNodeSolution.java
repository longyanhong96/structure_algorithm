import bean.ListNode;

/**
 * @Author: longyh
 * @Description: 2、21 都是先创建一个空的链表pre，数字往后加，返回 pre.next
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

    // 2
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;

        int stage = 0;
        while (l1 != null || l2 != null) {
            int l1Num = l1 == null ? 0 : l1.val;
            int l2Num = l2 == null ? 0 : l2.val;
            int sum = l1Num + l2Num + stage;

            stage = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (stage > 0) {
            cur.next = new ListNode(stage);
        }

        return pre.next;
    }

    // 21
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                cur.next = new ListNode(list2.val);
                list2 = list2.next;
            } else {
                cur.next = new ListNode(list1.val);
                list1 = list1.next;
            }
            cur = cur.next;
        }

        if (list1 != null) {
            cur.next = list1;
        }
        if (list2 != null) {
            cur.next = list2;
        }

        return pre.next;
    }

    //输入：head = [1,1,2,3,3] 输出：[1,2,3]
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null) {
            if (fast.val == slow.val) {
                fast = fast.next;
            } else {
                slow.next = fast;
                slow = slow.next;
            }
        }
        slow.next = null;
        return head;
    }
}
