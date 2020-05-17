package blockchain;
 
import java.io.Serializable;
import java.util.List;
 
public class MessageList implements Serializable {
    private final List<Message> messages;
 
    public MessageList(List<Message> messages) {
        this.messages = messages;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Message message: messages) {
            sb.append(message);
            sb.append("\n");
        }
        return sb.toString();
    }
}