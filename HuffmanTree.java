import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Huffman Tree
 *
 * A program used to encode English characters,
 * which read an input file and output encoding outcome in output file
 *
 * @author Nhan Truong
 *
 * @version July 02, 2020
 *
 */
public class HuffmanTree {
    private static HashMap<Character, Integer> dict;
    private static HashMap<Character, String> dictEncode;
    private static int inBits;
    private static int outBits;
    private PriorityQueue<HuffmanNode> pqueue;

    public HuffmanTree(String inputFile) {
        this.dict = new HashMap<>();
        this.dictEncode = new HashMap<>();
        this.pqueue = new PriorityQueue<>();
        try {
            this.makeMap(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //create HashMap that store character and its frequency in the input file
    public void makeMap (String inputFile) {
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
                if (dict.containsKey(current)) {
                    dict.put(current, dict.get(current) + 1);
                }
                dict.putIfAbsent(current, 1);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //create priority queue based on the HashMap
    public void makePQueue() {
        for (Map.Entry<Character, Integer> entry : dict.entrySet()) {
            HuffmanNode hn = new HuffmanNode(entry.getKey(), entry.getValue());
            hn.setLeft(null);
            hn.setRight(null);
            pqueue.add(hn);
        }
    }

    //create binary tree based on the Priority Queue
    public HuffmanNode makeTree() {
        HuffmanNode root = null;
        while (pqueue.size() > 1) {
            HuffmanNode letter1 = pqueue.poll();
            HuffmanNode letter2 = pqueue.poll();
            HuffmanNode newLetter = new HuffmanNode('-', letter1.getFrequency() + letter2.getFrequency());
            newLetter.setLeft(letter1);
            newLetter.setRight(letter2);
            root = newLetter;
            pqueue.add(newLetter);
        }
        return root;
    }

    //traverse the binary tree and encode each node
    public void traverseAndEncode(HuffmanNode root) {
        if (root == null)
            return;
        if(root.getLeft() == null && root.getRight() == null) {
            dictEncode.put(root.getChar(), root.getEncoding());
        }
        if(root.getLeft() != null)
        {
            HuffmanNode left = root.getLeft();
            left.setEncoding(root.getEncoding() + "0");
            traverseAndEncode(root.getLeft());
        }
        if(root.getRight() != null)
        {
            HuffmanNode right = root.getRight();
            right.setEncoding(root.getEncoding() + "1");
            traverseAndEncode(root.getRight());
        }

    }

    //read input file and print encoded string in output file, also print additional information (details below)
    public static String huffmanCoder(String inputFileName, String outputFileName) {
        HuffmanTree huffTree = new HuffmanTree(inputFileName);
        huffTree.makePQueue();
        HuffmanNode root = huffTree.makeTree();
        huffTree.traverseAndEncode(root);

        try {
            FileOutputStream fos = new FileOutputStream(outputFileName, false);
            PrintWriter pw = new PrintWriter(fos);
            FileInputStream fis = new FileInputStream(inputFileName);
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
                inBits += 8;
                for (Map.Entry<Character, String> entry : dictEncode.entrySet()) {
                    if (entry != null && current == entry.getKey()) {
                        outBits += entry.getValue().length();
                        pw.print(entry.getValue());
                    }
                }
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return printOthers(dict, dictEncode);
    }

    //additional information
    public static String printOthers(HashMap<Character, Integer> dict, HashMap<Character, String> dictEncode) {
        String str = "";

        //print letter: frequency: encode
        for (Map.Entry<Character, Integer> entry : dict.entrySet()) {
            for (Map.Entry<Character, String> entry1 : dictEncode.entrySet()) {
                if (entry.getKey() == entry1.getKey())
                    str = str + "Letter: " + entry.getKey() + ": " + entry.getValue() + ": " + entry1.getValue() + "\n";
            }
        }

        //print number of char
        str += "\nThere are " + dict.size() + " characters in the input file.";

        //print space savings
        double ratio = (double)(inBits)/(double)(outBits);
        str = str + "\nThe input file contains " + inBits + " bits.\n" +
                "The output file contains " + outBits + " bits.\n" +
                "The encoded output file is " + ratio + " times smaller than the input file.";
        return str;
    }
}
