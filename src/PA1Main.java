/**
 * Brandon Leiva
 * PA1
 * The main program for creating and testing 2-3 Trees
 * @author Srini Badri
 */
public class PA1Main {
    /**
     * The entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {

// create an empty 2-3 Tree to store Integer keys

        TwoThreeTree<Integer> tree = new TwoThreeTree<Integer>();

// iterate through a for loop and insert data into the tree

        for(int i = 1; i <= 5; i++) {

// data to be inserted

            int x = i*10;

// data insertion operation

            tree.insert(x);

// data to be inserted

            x = 100-i*10;

// data insertion operation

            tree.insert(x);
        }
        System.out.println("In-order Traversal");

// perform in-order traversl on the 2-3 tree and print its result

        String s = tree.inorder();

        System.out.println(s);

// create a Scanner object to stream console input

        java.util.Scanner scan = new java.util.Scanner(System.in);

// prompt the user for data input to search for

        while (true) {
            System.out.print("Enter an integer value to search for: ");

// read the next console input as an integer value
            int y = scan.nextInt();

// search the tree for the integer value

            boolean found = tree.contains(y);

// print the search result to the console

            if (found) {
                System.out.println("Data " + y + " is present in the tree");
            }
            else {
                System.out.println("Data " + y + " is NOT present in the tree");
            }
        }
    }
}
