
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
public class Blockchain {
 
    private List<Block> blocks = new ArrayList<>();
 
    public void createBlock() {
        String prevHash = blocks.isEmpty() ? "0" : blocks.get(blocks.size() - 1).getHash();
        Block newBlock = new Block(blocks.size() + 1, System.currentTimeMillis(), prevHash);
        blocks.add(newBlock);
    }
 
    @Override
    public String toString() {
        return blocks.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n\n"));
    }
}