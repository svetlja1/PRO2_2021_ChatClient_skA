package czk.uhk.pro2.models;

import java.util.List;

public interface ChatDataFileOperations {
    List<Message> readMessages();
    void writeMessages(List<Message> messages);

    List<String> readUsers();
    void writeUsers(List<String> users);
}
