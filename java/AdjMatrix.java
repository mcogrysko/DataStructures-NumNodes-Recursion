/** AdjMatrix
  * Reads adjacency matrices from text files and displays/prints paths for all nodes in
  * each matrix
  *
  * @author Mike Ogrysko
  *
  */


import java.util.*;
import java.io.*;

public class AdjMatrix {
   public static void main(String[] args) throws IOException { // Start main
      if (args.length != 2) {
         throw new IllegalArgumentException("\n\nOne or both file args are missing. \n\nPlease provide them in quotes - e.g., java AdjMatrix \"input.txt\" \"output.txt\"\n");
      }
      FileReader inFile = null;
      FileWriter outFile = null;
      inFile = new FileReader(args[0]);
      File ifile = new File(args[0]);
      Scanner input = new Scanner(inFile);
      outFile = new FileWriter(args[1]);
      File ofile = new File(args[1]);
      displayHeaders(ifile,ofile,outFile);
      // Read the matrix and putting it into an array
      processAdjMatrix(input, outFile);
   }


   /** displayHeaders()
    * Displays and prints headers for output
    *
    * Pre-Conditions: ifile and ofile are of File type; outFile is FileWriter type
    *
    * Post-Conditions: message is printed on screen and in file
    *
    * @param ifile File
    * @param ofile File
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   public static void displayHeaders(File ifile, File ofile, FileWriter outFile) { // start displayHeaders()
      try {
   	   // Output heading messages
         System.out.println("\nMike Ogrysko");
         outFile.write("Mike Ogrysko\n");
         System.out.println("\njava AdjMatrix \"" + ifile.getName() + "\" \"" + ofile.getName() + "\"");
         outFile.write("java AdjMatrix \"" + ifile.getName() + "\" \"" + ofile.getName() + "\"\n");
         System.out.println("\nReading \"" + ifile.getName() + "\" and writing to \"" + ofile.getName() + "\"");
         outFile.write("Reading \"" + ifile.getName() + "\" and writing to \"" + ofile.getName() + "\"\n");
         System.out.println("\nAdjacency List Paths: ");
         outFile.write("\nAdjacency List Paths: \n\n");
      }
      catch (Exception e) {
         System.out.println("\nError writing headers.");
       }
   } // end displayHeaders()


   /** processAdjMatrix()
    * Runs through the file, creates the array and removes loops; runs arrayIteration
    *
    * Pre-Conditions: input and outFile are of Scanner and FileWriter types
    *
    * Post-Conditions: message is printed on screen and in file
    *
    * @param input File Scanner
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   public static void processAdjMatrix(Scanner input,
                              FileWriter outFile) throws IOException { // start processAdjMatrix()
      int adjMatrixSize = 0;
      int adjMatrixValue;
      int[][] adjMatrixArray = new int[0][0];
      boolean[] resultsArray = new boolean[0];
      while (input.hasNextLine()) {
         // Read the first line as the size
         adjMatrixSize = input.nextInt();
         // Print matrix size provided
         System.out.println("\n" + adjMatrixSize);
         try {
            outFile.write("\n" + adjMatrixSize + "\n");
         }
         catch (Exception e) {
            System.out.println("\nError writing matrix size to file.");
         }
         // Initialize adjMatrixArray
         adjMatrixArray = new int[adjMatrixSize][adjMatrixSize];
         // Iterate through matrix and put values in array, printing as we go
         for (int i = 0; i < adjMatrixSize; i++) {
            for (int j = 0; j < adjMatrixSize; j++) {
               adjMatrixValue = input.nextInt();
               adjMatrixArray[i][j] = adjMatrixValue;
               System.out.print(adjMatrixArray[i][j] + " ");
               try {
                 outFile.write(adjMatrixArray[i][j] + " ");
               }
               catch (Exception e) {
                  System.out.println("\nError writing matrix to file.");
               }
            }
            System.out.println();
            try {
               outFile.write("\n");
            }
            catch (Exception e) {
               System.out.println("\nError adding space to file.");
            }
         }
         System.out.println();
         try {
            outFile.write("\n");
         }
         catch (Exception e) {
            System.out.println("\nError adding space to file.");
         }
         // Initialize visitArray to capture visits during recursive call
         boolean[] visitArray = new boolean[adjMatrixArray.length];
         // Iterate through array to remove loops - setting the value to 0
         for (int i = 0; i < adjMatrixSize; i++) {
            for (int j = 0; j < adjMatrixSize; j++) {
               if ((adjMatrixArray[i][j] == 1) && (i == j)) {
                  adjMatrixArray[i][j] = 0;
               }
            }
         }
         arrayIteration(adjMatrixArray, resultsArray, visitArray, outFile);
      }
      input.close();
      outFile.close();
   } // end processAdjMatrix()


   /** arrayIteration()
    * Reads adjMatrixArray runs displayPaths() for each set of nodes;
    * if not, print a message saying no path found
    *
    * Pre-Conditions: adjMatrixArray is a 2d int array, resultsArray and visitArray
    * are boolean arrays and outFile is of FileWriter type
    *
    * Post-Conditions: na
    *
    * @param adjMatrixArray int[][]
    * @param resultsArray boolean[]
    * @param visitArray boolean[]
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   private static void arrayIteration(int[][] adjMatrixArray, boolean[] resultsArray,
                              boolean[] visitArray, FileWriter outFile) { // start arrayIteration()
      // Iterate through array to print headers and paths
      for (int i = 0; i < adjMatrixArray.length; i++) {
         for (int j = 0; j < adjMatrixArray.length; j++) {
            // Define resultsArray and set all values to false
            resultsArray = new boolean[adjMatrixArray.length];
            for (int x = 0; x < resultsArray.length; x++) {
               resultsArray[x] = false;
            }
            System.out.println("\nPaths from " + (i + 1) + " to " + (j + 1) + ":");
            try {
               outFile.write("\nPaths from " + (i + 1) + " to " + (j + 1) + ":\n");
               displayPaths(i,j,visitArray,adjMatrixArray,resultsArray,outFile);
               noPathMessage(resultsArray,outFile);
            }
            catch (Exception e) {
               System.out.println("\nError writing file.");
            }
         }
      }
   } // end arrayIteration()


   /** noPathMessage()
    * Reads resultsArray and determines if there was a path between 2 nodes;
    * if not, print a message saying no path found
    *
    * Pre-Conditions: resultsArray is a boolean array and outFile is of FileWriter type
    *
    * Post-Conditions: message is printed on screen and in file
    *
    * @param resultsArray boolean[]
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   private static void noPathMessage(boolean[] resultsArray,FileWriter outFile) throws IOException { // start noPathMessage()
      // Initialize and set x and count equal to 0
      int x = 0;
      int count = 0;
      // Iterate through to find a true value - we're looking for a row full of falses to print the message
      while (resultsArray.length-1 >= x) {
         if (resultsArray[x] == true) {
            count--;
            x++;
         }
         else {
            count++;
            x++;
         }
      }
      if (count == resultsArray.length) {
         System.out.println("No Path Found");
         outFile.write("No Path Found\n");
      }
   } // end noPathMessage()


   /** rowEmpty()
    * Reads adjMatrixArray and the currentNode to determine if there are any from nodes
    *
    * Pre-Conditions: adjMatrixArray is a 2d array, currentNode is an int
    *
    * Post-Conditions: boolean empty is returned
    *
    * @param adjMatrixArray int[][]
    * @param currentNode int
    *
    * @return empty boolean
    *
    * @author Mike Ogrysko
    */
   private static boolean rowEmpty(int[][] adjMatrixArray, int currentNode) { // start rowEmpty()
      // Initialize and set empty equal to false
      boolean empty = false;
      // Set i equal to the length of the array less one
      int i = adjMatrixArray.length-1;
      // Iterate to determine if the row is empty
      while (i >= 0) {
         if (adjMatrixArray[currentNode][i] == 1) {
            empty = false;
            return empty;
         }
         else {
            empty = true;
            i--;
         }
      }
      return empty;
   } // end rowEmpty()


   /** displayPaths()
    * Takes startNode, endNode, visitArray, adjMatrixArray, resultsArray and calls findPaths
    * to find and display all paths between startNode and endNode
    *
    * Pre-Conditions: startNode and endNode are integers, visitArray is a boolean array,
    * adjMatrixArray is a 2d int array, resultsArray is a boolean array, and outFile is of FileWriter type
    *
    * Post-Conditions: paths or no path found is printed
    *
    * @param startNode int
    * @param endNode int
    * @param visitArray boolean[]
    * @param adjMatrixArray int[][]
    * @param resultsArray boolean[]
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   public static void displayPaths(int startNode, int endNode, boolean[] visitArray,
                                    int[][] adjMatrixArray, boolean[] resultsArray,
                                    FileWriter outFile) throws IOException { // start displayPaths()
      // Initialize pathStack
      LinkedStack pathStack = new LinkedStack();
      // Initialize and set firstPass equal to true - used in recursive method
      boolean firstPass = true;
      // If the row is completely empty, don't bother with recursion, just say no path found
      if (rowEmpty(adjMatrixArray,startNode)) {
         // Set resultsArray equal to true for current node to avoid conflicting message
         resultsArray[startNode] = true;
         System.out.println("No Path Found");
         outFile.write("No Path Found\n");
         return;
      }
      else {
         // Add startValue to path
         pathStack.push(startNode);
         // Method will run recursively to build path
         findPaths(startNode, endNode, startNode, visitArray, adjMatrixArray, pathStack, firstPass, resultsArray, outFile);
      }
   } // end displayPaths()


   /** findPaths()
    * Recursive method that takes currentNode, endNode, origStartNode, visitArray, adjMatrixArray,
    * localPathStack, firstPass, resultsArray, and outFile to find and display all
    * paths between startNode and endNode
    *
    * Pre-Conditions: currentNode and endNode are integers, visitArray is a boolean array,
    * adjMatrixArray is a 2d int array, localPathStack is LinkedStack, firstPass is boolean,
    * resultsArray is a boolean array, and outFile is of FileWriter type
    *
    * Post-Conditions: If found, paths are printed
    *
    * @param currentNode int
    * @param endNode int
    * @param origStartNode int
    * @param visitArray boolean[]
    * @param adjMatrixArray int[][]
    * @param localPathStack LinkedStack
    * @param firstPass boolean
    * @param resultsArray boolean[]
    * @param outFile FileWriter
    *
    * @author Mike Ogrysko
    */
   private static void findPaths(int currentNode, int endNode, int origStartNode, boolean[] visitArray,
                                 int[][] adjMatrixArray, LinkedStack localPathStack,
                                 boolean firstPass, boolean[] resultsArray, FileWriter outFile) { // start findPaths()
      // Stopping case - Display the path if currentNode is equal to endNode and this is not the first pass through
      if ((currentNode == endNode) && (firstPass == false)) {
         resultsArray[currentNode] = true;
         localPathStack.display();
         localPathStack.printFile(outFile);
      }
      // Use this so that the start node is not repeated in the path
      if ((firstPass == true) && (origStartNode != endNode)) {
         visitArray[currentNode] = true;
      }
      /* Set currentNode visited if it this is not the first pass through,
         use this for cases where the start and end nodes are the same and a path might exist */
      if (firstPass == false) {
         visitArray[currentNode] = true;
      }
      // Set firstPass equal to false
      firstPass = false;
      // Iterate through the values in each row, continue if not visited yet and value is equal to 1
         for (int i = 0; i < adjMatrixArray.length; i++) {
            if ((! visitArray[i]) && (adjMatrixArray[currentNode][i]) == 1) {
               // Push value to stack
               localPathStack.push(i);
               // Recursive call
               findPaths(i, endNode, origStartNode, visitArray, adjMatrixArray, localPathStack, firstPass, resultsArray, outFile);
               // Pop the last value once complete
               localPathStack.pop();
            }
         }
         // Set currentNode visited equal to false
         visitArray[currentNode] = false;
    } // end findPaths()
}
