import list._
import util.Random

object Main {

  def main(args: Array[String]): Unit = {

    var listOfStrings = new List[String]()

    val rand = new Random()
    val alphNum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes()
  
    def gen_random_str(chars: Array[Byte], length: Int): String = {

      val bytes = new Array[Byte](length)
      for (i <- 0 until length) bytes(i) = chars(rand.nextInt(chars.length))
      
      return new String(bytes)
    }

    for (i <- 0 until 6) listOfStrings.push_back(gen_random_str(alphNum, 6))

    println("=====Randomly generated list=====")
    listOfStrings.print()

    println("\n=====push_ordered()=====")
    listOfStrings.push_ordered("somestr1")
    listOfStrings.print()

    println("\n=====pop_back()=====")
    listOfStrings.pop_back()
    listOfStrings.print()

    println("\n=====get_element_data(3)=====")
    println(listOfStrings.get_element_data(3))

    println("\n=====set_element_data(3, \"somestr2\")=====")
    listOfStrings.set_element_data(3, "somestr2")
    listOfStrings.print()

    println("\n=====insert(3, \"somestr3\")=====")
    listOfStrings.insert(3, "somestr3")
    listOfStrings.print()

    println("\n=====remove(3)=====")
    listOfStrings.remove(3)
    listOfStrings.print()

    println("\n=====get_size()=====")
    println(listOfStrings.get_size())

    println("\n=====sort()=====")
    listOfStrings.merge_sort()
    listOfStrings.print()

    println("\n=====.foreach=====")
    listOfStrings.foreach { println }

  }
  
}