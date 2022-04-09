package list
import util.control.Breaks._


class Node[T](
    
    private var data: T, 
    private var nextNode: Node[T] = null
    
){

    def get_data(): T = data

    def set_data(data: T): Unit = { this.data = data }

    def get_next_node(): Node[T] = nextNode
    
    def set_next_node(nextNode: Node[T]): Unit = { this.nextNode = nextNode }

}

class List[T](
    
    private var listSize: Int = 0, 
    private var _head: Node[T] = null, 
    private var _tail: Node[T] = null
    
) extends Iterable[T] {

    def push_back(data: T): Node[T] = {

        var newNode = new Node[T](data)

        if (_head == null) {
            
            _head = newNode
            _tail = _head

        }
        else _tail.set_next_node(newNode)

        _tail = newNode
        listSize += 1

        return newNode
    }

    def push_ordered(data: T): Unit = {

        push_back(data)
        merge_sort()
    }

    def pop_back(): T = {

        if (listSize == 1) {

            var temp: T = _head.get_data()
            remove(0)
            return temp
        }

        var iter: Node[T] = _head
        var popped: Node[T] = null
        var i = 0

        while (i != (listSize - 2)) {

            iter = iter.get_next_node()
            i += 1;

        }
        
        popped = iter.get_next_node()
        iter.set_next_node(null)
        _tail = iter
        listSize -= 1

        return popped.get_data()
    }

    def get_element_data(index: Int): T = {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index)

        var iter: Node[T] = _head
        var i = 0

        while (i != index) { 
            
            iter = iter.get_next_node() 
            i += 1

        }

        return iter.get_data()
    }

    def set_element_data(index: Int, data: T): Unit = {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index)

        var iter: Node[T] = _head
        var i = 0

        while (i != index) { 

            iter = iter.get_next_node()
            i += 1

        } 

        iter.set_data(data)

    }

    def insert(index: Int, data: T): Unit = {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index)

        var iter: Node[T] = _head
        var newNode = new Node[T](data)
        var i = 0

        while (i != index) {
            
            iter = iter.get_next_node()
            i += 1

        }

        newNode.set_next_node(iter.get_next_node())
        iter.set_next_node(newNode)
        listSize += 1

    }

    def remove(index: Int): Unit = {

        if (index < 0 || index > listSize) throw new ArrayIndexOutOfBoundsException(index);

        if (index == 0) {

            _head = _head.get_next_node()
            listSize -= 1

        }
        else {

            var i = 0
            var iter: Node[T] = _head

            while (i != index - 1) { 

                iter = iter.get_next_node()
                i += 1

            }

            if (index == listSize) {

                _tail = iter
                listSize -= 1
                
            }
            else {

                var temp: Node[T] = iter.get_next_node()
                iter.set_next_node(temp.get_next_node())
                listSize -= 1

            }
            
        }

    }

    def iterator = new Iterator[T] {

        var currentNode: Node[T] = _head
        var iter = 0

        def hasNext: Boolean = { 

            iter += 1
            return iter != (listSize + 1) 
        }

        def next(): T = {

            var data = currentNode.get_data()
            currentNode = currentNode.get_next_node()

            return data
        }

    }

    private def merge[T](list: List[T], list1: List[T], list2: List[T]): Unit = {

        var leftSize = list1.get_size()
        var rightSize = list2.get_size()

        var i = 0
        var j = 0
        var k = 0

        if (list.get_element_data(0).isInstanceOf[Int]) {

            while (i < leftSize && j < rightSize) {

                if (list1.get_element_data(i).asInstanceOf[Int] <= list2.get_element_data(j).asInstanceOf[Int]) {

                    list.set_element_data(k, list1.get_element_data(i))
                    i += 1

                }
                else {

                    list.set_element_data(k, list2.get_element_data(j))
                    j += 1

                }

                k += 1

            }

        }
        else if (list.get_element_data(0).isInstanceOf[Float]) {

          while (i < leftSize && j < rightSize) {

                if (list1.get_element_data(i).asInstanceOf[Float] <= list2.get_element_data(j).asInstanceOf[Float]) {

                    list.set_element_data(k, list1.get_element_data(i))
                    i += 1

                }
                else {

                    list.set_element_data(k, list2.get_element_data(j))
                    j += 1

                }

                k += 1

            }

        }
        else if (list.get_element_data(0).isInstanceOf[String]) {

            while (i < leftSize && j < rightSize) {

                if (list1.get_element_data(i).asInstanceOf[String] <= list2.get_element_data(j).asInstanceOf[String]) {

                    list.set_element_data(k, list1.get_element_data(i))
                    i += 1

                }
                else {

                    list.set_element_data(k, list2.get_element_data(j))
                    j += 1

                }

                k += 1

            }

        }
        else throw new Error("Unsupported data type")

        while (i < leftSize) {

            list.set_element_data(k, list1.get_element_data(i))
            i += 1
            k += 1

        }

        while (j < rightSize) {

            list.set_element_data(k, list2.get_element_data(j))
            j += 1
            k += 1

        }

    }

    def msort[T](list: List[T]): Unit = {

        var listSize = list.get_size()
  
        if (listSize < 2) return
        else {
              
            var list1 = new List[T]
            var list2 = new List[T]

            var pivot = listSize / 2
    
            for (i <- 0 until pivot) {
            
              list1.push_back(list.get_element_data(i))
            }
    
            for (i <- pivot until listSize) {
            
              list2.push_back(list.get_element_data(i))
            }
    
            msort(list1)
            msort(list2)
    
            merge(list, list1, list2)

        }
        
    }

    def merge_sort(): Unit = {

        msort(this)
    }

    def print(): Unit = {

        if (_head == null) {

            println("No elements in the list")
            return
        }

        var iter: Node[T] = _head
        
        for (i <- 0 until listSize) {

            println(iter.get_data())
            iter = iter.get_next_node()

        }

    }

    def get_head(): Node[T] = { return _head; }

    def get_tail(): Node[T] = { return _tail; }

    def get_size(): Int = { return listSize; }

}