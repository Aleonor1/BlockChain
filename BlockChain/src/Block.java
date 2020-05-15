
 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class Block {
 
    private static MessageDigest digest;
 
    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
 
    private long id;
    private long ts;
    private String prevHash;
    private String hash;
 
    public Block(long id, long ts, String prevHash) {
        this.id = id;
        this.ts = ts;
        this.prevHash = prevHash;
        this.hash = applySha256(id + "" + ts + prevHash);
    }
 
    public long getId() {
        return id;
    }
 
    public long getTs() {
        return ts;
    }
 
    public String getPrevHash() {
        return prevHash;
    }
 
    public String getHash() {
        return hash;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Block:").append("\n");
        sb.append("Id:").append(id).append("\n");
        sb.append("Timestamp: ").append(ts).append("\n");
        sb.append("Hash of the previous block:").append("\n");
        sb.append(prevHash).append("\n");
        sb.append("Hash of the block:").append("\n");
        sb.append(hash);
 
        return sb.toString();
    }
 
    public String applySha256(String input) {
        try {
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}