package blockchain;
 
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
 
import static java.util.stream.Collectors.toList;
 
public class BlockChain implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    public static BlockChain load(String fileName) throws IOException, ClassNotFoundException {
        BlockChain blockChain;
        if (Files.exists(Paths.get(fileName))) {
<<<<<<< Upstream, based on origin/master
            try (var file = new FileInputStream(fileName);
                 var buffer = new BufferedInputStream(file);
                 var stream = new ObjectInputStream(buffer)) {
=======
            try (FileInputStream file = new FileInputStream(fileName);
                 BufferedInputStream buffer = new BufferedInputStream(file);
                 ObjectInputStream stream = new ObjectInputStream(buffer)) {
>>>>>>> 0073c33 executor multi-threading add chatter bot add
 
                blockChain = (BlockChain) stream.readObject();
                blockChain.validate();
            }
        } else {
            blockChain = new BlockChain(fileName);
        }
        return blockChain;
    }
 
    private String fileName;
    private final Stack<Block> blocks = new Stack<>();
    private long lastBlockTime = System.currentTimeMillis();
    private int proofLength = 0;
    private int previousProofLength = 0;
    private Queue<Message> pendingMessages = new ArrayDeque<>();
 
    public BlockChain() {
    }
 
    public BlockChain(String fileName) {
        this();
        this.fileName = fileName;
    }
 
    private Hash getLastBlockHash() {
        synchronized (blocks) {
            return blocks.size() == 0 ? null : blocks.peek().getHash();
        }
    }
 
    private boolean put(Block block) throws IOException {
        synchronized (blocks) {
            if (block.getId() == getNextBlockId()) {
                blocks.add(block);
                save();
                adjustProofLength();
                return true;
            }
            return false;
        }
    }
 
    private int getNextBlockId() {
        synchronized (blocks) {
            return blocks.size();
        }
    }
 
    private void adjustProofLength() {
<<<<<<< Upstream, based on origin/master
        var idleTime = Duration.ofMillis(System.currentTimeMillis() - lastBlockTime);
        lastBlockTime = System.currentTimeMillis();
        if (idleTime.toSeconds() < 1) {
            previousProofLength = proofLength;
            proofLength++;
        } else if (idleTime.toMinutes() > 1) {
            previousProofLength = proofLength;
            proofLength--;
        }
    }
 
    private void save() throws IOException {
        synchronized (blocks) {
            synchronized (pendingMessages) {
                if (fileName == null) {
                    return;
                }
                var path = Paths.get(fileName);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
                try (var file = new FileOutputStream(fileName);
                     var buffer = new BufferedOutputStream(file);
                     var stream = new ObjectOutputStream(buffer)) {
                    stream.writeObject(this);
                }
            }
        }
    }
 
    private void validate() {
        synchronized (blocks) {
            if (!blocks.empty()) {
                blocks.get(0).validate(null);
                for (int i = 1; i < blocks.size(); i++) {
                    var block = blocks.get(i);
                    block.validate(blocks.get(i - 1).getHash());
                }
            }
        }
    }
 
    public Iterable<Block> blocks() {
        synchronized (blocks) {
            return blocks;
        }
    }
 
    public void queueMessage(Message message) {
        synchronized (pendingMessages) {
            pendingMessages.add(message);
        }
    }
 
    public boolean tryPutNewBlock(int minerId) throws IOException {
        var blockId = getNextBlockId();
        var previous = getLastBlockHash();
        synchronized (pendingMessages) {
            var block = new Block(
                    minerId,
                    blockId,
                    proofLength,
                    previous,
                    Integer.compare(proofLength, previousProofLength),
                    pendingMessages.isEmpty()
                            ? null
                            : new MessageList(pendingMessages.stream().collect(toList())));
            var result = put(block);
=======
        Duration idleTime = Duration.ofMillis(System.currentTimeMillis() - lastBlockTime);
        lastBlockTime = System.currentTimeMillis();
        if (idleTime.toSeconds() < 1) {
            previousProofLength = proofLength;
            proofLength++;
        } else if (idleTime.toMinutes() > 1) {
            previousProofLength = proofLength;
            proofLength--;
        }
    }
 
    private void save() throws IOException {
        synchronized (blocks) {
            synchronized (pendingMessages) {
                if (fileName == null) {
                    return;
                }
                var path = Paths.get(fileName);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
                try (FileOutputStream file = new FileOutputStream(fileName);
                     BufferedOutputStream buffer = new BufferedOutputStream(file);
                     ObjectOutputStream stream = new ObjectOutputStream(buffer)) {
                    stream.writeObject(this);
                }
            }
        }
    }
 
    private void validate() {
        synchronized (blocks) {
            if (!blocks.empty()) {
                blocks.get(0).validate(null);
                for (int i = 1; i < blocks.size(); i++) {
                    Block block = blocks.get(i);
                    block.validate(blocks.get(i - 1).getHash());
                }
            }
        }
    }
 
    public Iterable<Block> blocks() {
        synchronized (blocks) {
            return blocks;
        }
    }
 
    public void queueMessage(Message message) {
        synchronized (pendingMessages) {
            pendingMessages.add(message);
        }
    }
 
    public boolean tryPutNewBlock(int minerId) throws IOException {
        int blockId = getNextBlockId();
        Hash previous = getLastBlockHash();
        synchronized (pendingMessages) {
            Block block = new Block(
                    minerId,
                    blockId,
                    proofLength,
                    previous,
                    Integer.compare(proofLength, previousProofLength),
                    pendingMessages.isEmpty()
                            ? null
                            : new MessageList(pendingMessages.stream().collect(toList())));
            boolean result = put(block);
>>>>>>> 0073c33 executor multi-threading add chatter bot add
            if (result) {
                pendingMessages.clear();
            }
            return result;
        }
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}