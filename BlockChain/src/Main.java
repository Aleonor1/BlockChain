
 
public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        blockchain.createBlock();
        blockchain.createBlock();
        blockchain.createBlock();
        blockchain.createBlock();
        blockchain.createBlock();
 
        System.out.println(blockchain);
    }
}