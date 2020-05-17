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
<<<<<<< Upstream, based on origin/master
        var sb = new StringBuilder();
        for (var message: messages) {
=======
        StringBuilder sb = new StringBuilder();
        for (Message message: messages) {
>>>>>>> 0073c33 executor multi-threading add chatter bot add
            sb.append(message);
            sb.append("\n");
        }
        return sb.toString();
    }
}