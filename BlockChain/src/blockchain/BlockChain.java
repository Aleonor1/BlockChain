package blockchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class BlockChain {

    private List<Block> blocks = new ArrayList<>();
    private int numberOfZeros;

    public BlockChain(int numberOfZeros) {
        this.numberOfZeros = numberOfZeros;
    }

    public void createBlock() {
        String prevHash = blocks.isEmpty() ? "0" : blocks.get(blocks.size() - 1).getHash();
        Block newBlock = new Block(blocks.size() + 1, prevHash, this.numberOfZeros);
        blocks.add(newBlock);
    }

    public void print(){
        for (Block block:blocks){
            System.out.println(block);
        }
    }

    @Override
    public String toString() {
        return blocks.toString();
    }
}
