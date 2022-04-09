import java.security.SecureRandom;
import java.math.BigInteger;

public class Main {

     public static void main(String[] args) {

          List<String> listOfStrings = new List<>();

          SecureRandom random = new SecureRandom();

          for (int i = 0; i != 6; ++i) listOfStrings.push_back(new BigInteger(50, random).toString(32));

          System.out.println("=====Randomly generated list=====");
          listOfStrings.print();

          System.out.println("\n=====push_ordered()=====");
          listOfStrings.push_ordered("somestr__1");
          listOfStrings.print();

          System.out.println("\n=====pop_back()=====");
          listOfStrings.pop_back();
          listOfStrings.print();

          System.out.println("\n=====get_element_data(3)=====");
          System.out.println(listOfStrings.get_element_data(3));

          System.out.println("\n=====set_element_data(3, \"somestr__2\")=====");
          listOfStrings.set_element_data(3, "somestr__2");
          listOfStrings.print();

          System.out.println("\n=====insert(3, \"somestr__3\")=====");
          listOfStrings.insert(3, "somestr__3");
          listOfStrings.print();

          System.out.println("\n=====remove(3)=====");
          listOfStrings.remove(3);
          listOfStrings.print();

          System.out.println("\n=====get_size()=====");
          System.out.println(listOfStrings.get_size());

          System.out.println("\n=====sort()=====");
          listOfStrings.sort();
          listOfStrings.print();

          System.out.println("\n=====forEach()=====");
          for (String string : listOfStrings) System.out.println(string);

     }

}