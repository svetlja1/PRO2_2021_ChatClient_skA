package czk.uhk.pro2.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToFileChatClient implements ChatClient{
    private String loggedUser;
    private List<Message> messages;
    private List<String> loggedUsers;

    private List<ActionListener> listenersLoggedUsersChanged = new ArrayList<>();
    //todo dalsi listener na chat


    Gson gson = null;

    private static final String MESSAGES_PATH = "./messages.json";
//predavat budto csv nebo json  v konmstruktoru (rozhrani
    public ToFileChatClient() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        messages = new ArrayList<>();
        loggedUsers = new ArrayList<>();
    }
    @Override
    public boolean isAuthenticated() {
        return loggedUser!=null;
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(userName);
        addMessage(new Message(Message.USER_LOGGED_IN, userName));
        raiseEventLoggedUsersChanged();
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        addMessage(new Message(Message.USER_LOGGED_IN, loggedUser));
        loggedUser = null;
        raiseEventLoggedUsersChanged();
    }

    @Override
    public void sendMessage(String text) {
        addMessage(new Message(loggedUser,text));
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addListenerLoggedUsersChanged(ActionListener toAdd) {
        listenersLoggedUsersChanged.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged(){
        for (ActionListener al : listenersLoggedUsersChanged){
            al.actionPerformed(new ActionEvent(this, 1, "listenersLoggedUsersChanged"));
        }
    }

    private void addMessage(Message message){
        messages.add(message);
        writeMessagesToFile();
    }

    private void writeMessagesToFile(){
       String jsonText = gson.toJson(messages);
        try{
            FileWriter writer = new FileWriter(MESSAGES_PATH);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
//chatfilewriter zapsat, nacist pro CSV
    private void readMessagesFromFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MESSAGES_PATH));
            String jsonText = "";
            String line;
            while((line = reader.readLine())!= null){
                jsonText += line;
            }
            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();
            messages = gson.fromJson(jsonText,targetType);
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }


}
