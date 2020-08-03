/**
 * HuffmanNode
 *
 * The Node to form Huffman Tree
 *
 * @author Nhan Truong
 *
 * @version May 02, 2020
 *
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
    private char inChar;
    private int frequency;
    private String encoding;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(char inChar, int frequency) {
        this.inChar = inChar;
        this.frequency = frequency;
        this.encoding = "";
    }

    public char getChar() {
        return inChar;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getEncoding() {
        return encoding;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    //compare two HuffmanNodes based on their char's frequency
    @Override
    public int compareTo(HuffmanNode o1) {
        if(this.getFrequency() > o1.getFrequency()) {
            return 1;
        } else if (this.getFrequency() < o1.getFrequency()) {
            return -1;
        } else {
            return 0;
        }
    }
}