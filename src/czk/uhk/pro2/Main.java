package czk.uhk.pro2;

import czk.uhk.pro2.gui.MainFrame;
import czk.uhk.pro2.models.ChatClient;
import czk.uhk.pro2.models.InMemoryChatClient;
import czk.uhk.pro2.models.Message;
import czk.uhk.pro2.models.ToFileChatClient;

public class Main {

    public static void main(String[] args) {

        ChatClient chatClient = new ToFileChatClient();
       MainFrame mainFrame = new MainFrame(800,600, chatClient);
       mainFrame.setVisible(true);
        }

        private static void TestChat() {
            ChatClient chatClient = new InMemoryChatClient();
            System.out.println("Loging in");
            chatClient.login("svetlja");
            System.out.println("IS logged" + chatClient.isAuthenticated());
            System.out.println("Logged users");
            for (String user : chatClient.getLoggedUsers()) {
                System.out.println(user);
            }
            System.out.println("sending msg1");
            chatClient.sendMessage("čus");
            System.out.println("sending msg2");
            chatClient.sendMessage("zdar");
            System.out.println("loging out");
            chatClient.logout();
            System.out.println("IS logged" + chatClient.isAuthenticated());
            for (Message msg : chatClient.getMessages()) {
                System.out.println(msg);

            }
        }
}
