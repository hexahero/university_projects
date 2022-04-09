import java.util.Iterator;

class Node<T> implements Comparable<Node<T>> {

    private T data;
    private Node<T> nextNode;

    Node(T data) {

        this.data = data;
        nextNode = null;

    }

    public T get_data() { return data; }

    public void set_data(T newData) { data = newData; }

    public Node<T> get_next_node() { return nextNode; }

    public void set_next_node(Node<T> next) { nextNode = next; }

    @Override
    public int compareTo(Node<T> anotherNode) {

        T toCompare = anotherNode.get_data();

        if (toCompare instanceof String) {

            String dataThis = (String)data;
            String anotherData = (String)toCompare;

            for (int i = 0; i < dataThis.length() && i < anotherData.length(); i++) {

                if ((int)dataThis.charAt(i) == (int)anotherData.charAt(i)) continue;
                else return (int)dataThis.charAt(i) - (int)anotherData.charAt(i);

            }

            if (dataThis.length() < anotherData.length())
                return (dataThis.length() - anotherData.length());

            else if (dataThis.length() > anotherData.length())
                return (dataThis.length() - anotherData.length());

            else return 0;

        }
        else if(toCompare instanceof Integer) {

            return (Integer)data > (Integer)toCompare ? 1 : (Integer)data == (Integer)toCompare ? 0 : -1;
        }
        else if(toCompare instanceof Float) {

            return (Float)data > (Float)toCompare ? 1 : (Float)data == (Float)toCompare ? 0 : -1;
        }
        else throw new Error("Unsupported data type");

    }

}

public class List<T> implements Iterable<T> {

    private int listSize;
    private Node<T> head;
    private Node<T> tail;

    public List() {

        listSize = 0;
        head = null;
        tail = null;

    }

    public Node<T> push_back(T data) {

        Node<T> newNode = new Node<T>(data);

        if (head == null) tail = head = newNode;
        else tail.set_next_node(newNode);

        tail = newNode;
        ++listSize;

        return newNode;
    }

    public void push_ordered(T data) {

        push_back(data);
        sort();

    }

    public T pop_back() {

        if (listSize == 1) {

            T temp = head.get_data();
            remove(0);
            return temp;
        }

        Node<T> iter = head;
        Node<T> popped;

        for (int i = 0; i != (listSize - 2); ++i) iter = iter.get_next_node();

        popped = iter.get_next_node();
        iter.set_next_node(null);
        tail = iter;
        --listSize;

        return popped.get_data();
    }

    public T get_element_data(int index) {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index);

        Node<T> iter = head;

        for (int i = 0; i != index; ++i) iter = iter.get_next_node();

        return iter.get_data();
    }

    public void set_element_data(int index, T data) {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index);

        Node<T> iter = head;

        for (int i = 0; i != index; ++i) iter = iter.get_next_node();

        iter.set_data(data);

    }

    public void insert(int index, T data) {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index);

        Node<T> iter = head;
        Node<T> newNode = new Node<T>(data);

        for (int i = 0; i != index; ++i) iter = iter.get_next_node();

        newNode.set_next_node(iter.get_next_node());
        iter.set_next_node(newNode);
        ++listSize;

    }

    public void remove(int index) {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index);

        Node<T> iter = head;
        Node<T> temp;

        if (index == 0) {

            head = head.get_next_node();
            --listSize;
            return;
        }

        for (int i = 0; i != index - 1; ++i) iter = iter.get_next_node();

        if (index == listSize) {

            tail = iter;
            --listSize;
            return;
        }

        temp = iter.get_next_node();
        iter.set_next_node(temp.get_next_node());
        --listSize;

    }

    private void swap(Node<T> first, Node<T> second) {

        T toCompare = second.get_data();
        second.set_data(first.get_data());
        first.set_data(toCompare);

    }

    public void sort() {

        boolean sorted = false;
        Node<T> first = null;

        while(!sorted) {

            sorted = true;
            first = head;

            for (int i = 0; i < listSize - 1; i++) {

                Node<T> second = first.get_next_node();

                if (first.compareTo(second) >= 1) {

                    swap(first, second);
                    sorted = false;

                }

                first = second;

            }

        }

    }

    public void print() {

        if (head == null) {

            System.out.println("No elements in the list");
            return;
        }

        Node<T> iter = head;

        for (int i = 0; i != listSize; ++i) {

            System.out.println(iter.get_data());
            iter = iter.get_next_node();

        }

    }

    public Node<T> get_head() { return head; }

    public Node<T> get_tail() { return tail; }

    public int get_size() { return listSize; }

    public Iterator<T> iterator() { return new ListIterator<T>(this); }

}

class ListIterator<T> implements Iterator<T> {

    Node<T> currentNode;

    public ListIterator(List<T> list) { currentNode = list.get_head(); }

    public boolean hasNext() { return currentNode != null; }

    public T next() {

        T data = currentNode.get_data();
        currentNode = currentNode.get_next_node();

        return data;
    }

}