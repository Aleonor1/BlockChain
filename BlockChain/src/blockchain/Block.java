package blockchain;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class Block {
    private long timeStamp;
    private int id;
    private String previousHash;
    private int hash;
    private long finalTime;
    private int magicNumber;
    private int numberOfZeroes;

    @Override
    public String toString() {
        return "Block: " +
                "\nId: " + id +
                "\nTimeStamp: " + timeStamp +
                "\nMagicNumber: " + magicNumber +
                "\nHash of the previous block:\n" + previousHash +
                "\nHash of the block: \n" + getHash() +
                "\nBlock was generating for " + ((finalTime-timeStamp)%1000) + " seconds\n";
    }


    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        int hash = hashCode();
        Random rand = new Random();
        String hashToReturn = applySha256(Integer.toString(hash));
        while(!validateHash(hashToReturn)){
            this.magicNumber=rand.nextInt(10000000);
            hash = hashCode();
            hashToReturn = applySha256(Integer.toString(hash));
        }
        finalTime = new Date().getTime();
        return hashToReturn;
    }

    public boolean validateHash(String hashToReturn) {
        for (int i = 0; i < numberOfZeroes; i++) {
            if (hashToReturn.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hash;
        result = prime * result + id;
        result = prime * result + magicNumber;
        result = prime * result + ((previousHash == null) ? 0 : previousHash.hashCode());
        result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Block other = (Block) obj;
        if (hash != other.hash)
            return false;
        if (id != other.id)
            return false;
        if (previousHash == null) {
            if (other.previousHash != null)
                return false;
        } else if (!previousHash.equals(other.previousHash))
            return false;
        if (timeStamp != other.timeStamp)
            return false;
        return true;
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
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

    public Block(int id, String string, int numberOfZeroes) {
        super();
        this.timeStamp = new Date().getTime();
        this.id = id;
        this.previousHash = string;
        this.hash = this.hashCode();
        this.numberOfZeroes = numberOfZeroes;
        Random rand = new Random();
        this.magicNumber=rand.nextInt(10000000);
        getHash();

    }
}
