/**
 * FileName: MyLinkedList.java
 * CreatedOn: July 8, 2022
 * @author ZacInman
 * @version 4.0.081722
 */

/**
 * Processes a generic list of elements by implementing a Linked List.
 */
@SuppressWarnings("unused")
public class MyLinkedList<Type extends Comparable<Type>> {
    /**
     * A reference to the first node in the list.
     * Is null if the list is empty.
     */
    protected Node first;

    /**
     * A reference to the last node in the list.
     * Is null if the list is empty.
     */
    protected Node last;

    /**
     * A reference to the current node in the list.
     * Initialized to null.
     * When this node is null, the current node has fallen off the end of the list.
     */
    protected Node current;

    /**
     * A reference to the previous node in the list.
     * Is null if current is equal to first.
     */
    protected Node previous;

    /**
     * The number of elements stored in the list.
     */
    protected int size;

    /**
     * The total number of comparisons made with the list.
     */
    public long comparisons;

    /**
     * Default constructor.
     * Initializes first, current, and previous to null.
     * Initializes size to zero.
     * Initializes comparisons to zero.
     */
    public MyLinkedList() {

        first       = null;
        last        = null;
        current     = null;
        previous    = null;
        size        = 0;
        comparisons = 0;

    }

    /**
     * Inserts the passed item before the current node.
     * Runtime: O(1).
     * @param item the element to be inserted.
     */
    public void addBefore(Type item) {

        // Try to get two cases
        // 1: previous != null
        // else
        if (first == null) {            //  Case is only true once

            first       = new Node(item, current);
            last        = first;
            previous    = first;

        } else if (current == first) {

            first       = new Node(item, current);

        } else if (current == null) {

            last.next = new Node(item, null);
            last = last.next;
            previous = last;

        } else {

            Node temp   = previous;

            previous    = new Node(item, current);
            temp.next   = previous;

        }

        size++;

    }

    /**
     * Inserts the passed item after the current node.
     * Does nothing if the current node is null.
     * Runtime: O(1).
     * @param item the element to be inserted.
     */
    public void addAfter(Type item) {

        if (current == null) return;

        current.next    = new Node(item, current.next);

        if (current == last) last = current.next;

        size++;

    }

    /**
     * Adds the item to the front of the list.
     * Runtime: O(1)
     * @param item the element to be added to the front of the list.
     */
    public void addToFront(Type item) {

        if (first == null || current == first) {

            first = new Node(item, first);
            last = first;
            previous = first;

        } else first = new Node(item, first);

        size++;
    }

    /**
     * Adds the item to the end of the list.
     * Runtime: O(1)
     * @param item the element to be added to the end of the list.
     */
    public void addToEnd(Type item) {

        if (first == null) {

            first = new Node(item, null);
            last = first;
            previous = first;

        } else {

            last.next = new Node(item, null);
            last = last.next;

            if (current == null) previous = last;

        }

        size++;
    }

    /**
     * Returns the item stored in the current node.
     * Returns null if the current node is null.
     * Runtime: O(1).
     * @return the item in the current node.
     */
    public Type current() {

        if (current == null) { return null; }

        return current.item;

    }

    /**
     * Sets the current node to be the first node.
     * Returns null if the first node is null.
     * Runtime: O(1).
     * @return the item stored in the current node after the update.
     */
    public Type first() {

        if (first == null) { return null; }

        current = first;
        previous = null;

        return current.item;

    }

    /**
     * Sets the previous node to be the current node and then sets the current node
     * to be the next node in the list.
     * Returns null if the current node is null.
     * Runtime: O(1).
     * @return the item stored in the current node after the update.
     */
    public Type next() {

        if (current == null) { return null; }

        previous    = current;
        current     = current.next;

        if (current == null) { return null; }

        return current.item;

    }

    /**
     * Removes the current node.
     * If current is null, does nothing but return null.
     * Runtime: O(1).
     * @return the item removed.
     */
    public Type remove() {

        if (current == null) return null;

        Node temp       = current;

        if (current == first) {

            first = first.next;

        } else {

            previous.next   = current.next;

        }

        if (current == last) last = previous;

        current         = current.next;
        size--;

        return temp.item;

    }

    /**
     * Backwards compatible Bubble Sort.
     * Calls sort(descending) by passing
     * false value as boolean.
     * Runtime: 0(n^2)
     */
    public void sort() {    sort(false);    }

    /**
     * Bubble Sort Algorithm.
     * Allows sorting the list in
     * ascending or descending order.
     * Runtime: O(n^2)
     * @param descending if true, sort order is descending;
     * otherwise, sort order is ascending.
     */
    public void sort(boolean descending) {

        Node current = first;

        while (current != null ) {

            Node next = current.next;

            while(next != null ) {

                if (descending) {

                    if (current.item.compareTo(next.item) < 0) {

                        Type temp = current.item;

                        current.item = next.item;
                        next.item = temp;

                    }

                } else {

                    if (current.item.compareTo(next.item) > 0) {

                        Type temp = current.item;

                        current.item = next.item;
                        next.item = temp;

                    }
                }

                next = next.next;

            }

            current = current.next;
        }
    }

    /**
     * Moves the current node to the front of the list.
     * Runtime: O(1)
     * @return the item inside the node moved.
     */
    public Type moveToFront() {

        if (current == null) return null;

        addToFront(remove());

        current = first;

        return current.item;

    }

    /**
     * Swaps the current node with the previous node.
     * Runtime: O(1)
     * @return the item swapped closer to the front of the list.
     */
    public Type swap() {

        if (current == null) return null;
        else if (current == first) return current.item;

        Type temp = current.item;
        current.item = previous.item;
        previous.item = temp;

        return temp;

    }

    /**
     * Searches the nodes for the passed item.
     * Runtime: O(n).
     * @param item the element to be searched for.
     * @return true if item is found, false otherwise.
     */
    public boolean contains(Type item) {

        Node temp       = first;

        while (temp != null) {

            comparisons++;

            if (temp.item.compareTo(item) == 0) { return true; }

            temp = temp.next;

        }

        return false;

    }

    /**
     * Returns the number of elements in the list.
     * Runtime: O(1).
     * @return the number of elements in the list.
     */
    public int size() { return size; }

    /**
     * Returns true if size is zero.
     * Runtime: O(1).
     * @return true if size is zero, false otherwise.
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Displays the contents of the list.
     * Runtime: O(n).
     * @return the string representation of the list of items.
     */
    public String toString() {

        if (isEmpty()) { return "[]"; }         // Only true in one situation

        Node temp       = first;

        StringBuilder listString = new StringBuilder("[" + temp.toString());

        for (int i = 1; i < size; i++) {        // while loops are usually more efficient

            temp = temp.next;

            listString.append(", ").append(temp.toString());

        }

        listString.append("]");

        return listString.toString();

    }

    protected class Node {

        /**
         * The item stored in this node.
         */
        Type item;

        /**
         * A reference to the next node in the list.
         * Is null if there is no next node.
         */
        Node next;

        /**
         * Default Constructor.
         * Instantiates item to the passed element.
         * Instantiates the next node reference to the passed node.
         * @param item the element to be stored.
         * @param next the reference to the next node in the list.
         */
        public Node(Type item, Node next) {

            this.item = item;
            this.next = next;

        }

        /**
         * Returns the toString of item.
         * @return the toString of item.
         */
        public String toString() {

            return item.toString();

        }
    }
}
