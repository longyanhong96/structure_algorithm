package leetcode.addtwonum_002;

import javax.xml.soap.Node;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/2/28 12:27
 */
public class AddTwoNum {

    public static void main(String[] args) {
        ListNode node3 = new ListNode(9);
        ListNode node2 = new ListNode(9, node3);
        ListNode node1 = new ListNode(9, node2);

        ListNode Node3 = new ListNode(9);
        ListNode Node2 = new ListNode(9, Node3);
        ListNode Node1 = new ListNode(9, Node2);

        ListNode listNode = addTwoNumbers1(node1, Node1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode listNode = null;
        ListNode nextListNode = null;

        int advanced = 0;
        while (l1 != null || l2 != null || advanced != 0) {
            int l1Val = 0;
            int l2Val = 0;
            if (l1 != null) {
                l1Val = l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                l2Val = l2.val;
                l2 = l2.next;
            }

            int sumVal = l1Val + l2Val + advanced;
            if (sumVal >= 10) {
                sumVal -= 10;
                advanced = 1;
            } else {
                advanced = 0;
            }

            if (listNode == null) {
                listNode = new ListNode(sumVal);
            } else {
                ListNode tmpListNode = new ListNode(sumVal);
                nextListNode = listNode;
                while (nextListNode.next != null) {
                    nextListNode = nextListNode.next;
                }
                nextListNode.setNext(tmpListNode);
            }
        }
        return listNode;
    }
}
