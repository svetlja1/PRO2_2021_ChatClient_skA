package czk.uhk.pro2.models;

import java.time.LocalDateTime;
public class Message {
    private String author;
    private String text;
    private LocalDateTime created;

    public static final int USER_LOGGED_IN = 1;
    public static final int USER_LOGGED_OUT = 2;

    private static final String AUTHOR_SYSTEM = "System";

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
        created = LocalDateTime.now();
    }

    public Message(int type, String userName){
        author = AUTHOR_SYSTEM;
        created = LocalDateTime.now();
        if(type == USER_LOGGED_IN){
            text = userName + "has entered the chat";
        }
        else if(type == USER_LOGGED_OUT){
            text = userName + "has left the chat";
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        if (author == AUTHOR_SYSTEM){
            return text + "\n";
        }
        String msg = author + "["+created.toString()+"]\n";
        msg+= text + "\n";
        return msg;
    }
}
