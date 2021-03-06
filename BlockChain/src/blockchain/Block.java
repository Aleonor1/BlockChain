package blockchain;
 
import java.io.Serializable;
import java.time.Duration;
 
public class Block implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    private final int minerId;
    private final int id;
    private final int proofLength;
    private final int proofLengthState;
    private final long timestamp;
    private final Hash current;
    private final Hash previous;
    private int magicNumber;
    private transient long generatingTime;
    private Serializable data;
 
    public Block(int minerId, int id, int proofLength, Hash hashOfPreviousBlock, int proofLengthState, Serializable data) {
        this.minerId = minerId;
        this.id = id;
        this.proofLength = proofLength;
        this.proofLengthState = proofLengthState;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.previous = hashOfPreviousBlock;
        this.current = hash();
    }
 
    private Hash hash() {
        long start = System.currentTimeMillis();
        try {
            while (true) {
                magicNumber = (int) (Math.random() * Integer.MAX_VALUE);
                Hash hash = new Hash(getValues());
                if (hash.validate(proofLength, getValues())) {
                    return hash;
                }
            }
        } finally {
            long end = System.currentTimeMillis();
            generatingTime = Duration.ofMillis(end - start).toSeconds();
        }
    }
 
    private String getValues() {
        return Integer.toString(magicNumber) +
                minerId +
                id +
                timestamp +
                data +
                (previous != null ? previous : 0);
    }
 
    public Hash getHash() {
        return current;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block:");
        sb.append("\n");
        sb.append("Created by miner # ");
        sb.append(minerId);
        sb.append("\n");
        sb.append("Id: ");
        sb.append(id);
        sb.append("\n");
        sb.append("Timestamp: ");
        sb.append(timestamp);
        sb.append("\n");
        sb.append("Magic number: ");
        sb.append(magicNumber);
        sb.append("\n");
        sb.append("Hash of the previous block:");
        sb.append("\n");
        sb.append(previous == null ? 0 : previous);
        sb.append("\n");
        sb.append("Hash of the block:");
        sb.append("\n");
        sb.append(current);
        sb.append("\n");
        sb.append("Block data:");
        if (data == null) {
            sb.append(" no messages");
            sb.append("\n");
        } else {
            sb.append("\n");
            sb.append(data);
        }
        sb.append("Block was generating for ");
        sb.append(generatingTime);
        sb.append(" seconds");
        sb.append("\n");
        sb.append(proofLengthState == 0
                ? "N stays the same"
                : proofLengthState < 0
                ? "N decreased to " + proofLength
                : "N increased to " + proofLength);
        sb.append("\n");
        return sb.toString();
    }
 
    public int getId() {
        return id;
    }
 
    public void validate(Hash previous) {
        boolean isValid = true;
 
        isValid &= (this.previous == null && previous == null) || this.previous.equals(previous);
        isValid &= !current.validate(proofLength, getValues());
 
        if (!isValid) {
            throw new IllegalArgumentException(String.format("Block %s is not valid!", id));
        }
    }
 
    public <T> T getData() {
        return (T)data;
    }
}