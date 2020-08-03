import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * HuffmanMain
 *
 * The main method to run the Huffman Compressor
 *
 * @author Nhan Truong
 *
 * @version May 02, 2020
 *
 */public class HuffmanMain {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("Encodings_and_SpaceSaving.txt",false);
            PrintWriter pw = new PrintWriter(fos);
            String s = HuffmanTree.huffmanCoder("inputFileName.txt", "outputFileName.txt");
            pw.print(s);
            pw.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Cannot find file.");
        }
    }
}
