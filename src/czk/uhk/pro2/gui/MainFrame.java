package czk.uhk.pro2.gui;

import czk.uhk.pro2.models.ChatClient;
import czk.uhk.pro2.models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextField txtInputName, txtInputMessage;
    JButton btnLogin, btnSend;
    JTable tblLoggedUsers;
    JTextArea txtAreaChat;

    LoggedUsersDataTable loggedUsersDataTable;

    ChatClient chatClient;

    public MainFrame(int width, int height, ChatClient chatClient){
        super("PRO2 Client Chat");
        this.chatClient = chatClient;
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelChat = new JPanel();
        JPanel panelLoggedUsers = new JPanel();
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLogin.add(new JLabel("Jméno"));
        txtInputName = new JTextField("",30);

        initLoginpanel(panelLogin);
        initChatPanel(panelChat);
        initLoggedUsersPanel(panelLoggedUsers);
        initFooterPanel(panelFooter);



        panelFooter.add(btnSend);


        panelMain.add(panelLogin, BorderLayout.NORTH);
        panelMain.add(panelChat, BorderLayout.CENTER);
        panelMain.add(panelLoggedUsers, BorderLayout.EAST);
        panelMain.add(panelFooter, BorderLayout.SOUTH);
        add(panelMain);
    }
    private void initLoginpanel(JPanel panel){
        panel.add(txtInputName);
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chatClient.isAuthenticated()){
                   chatClient.logout();
                   btnLogin.setText("Login");
                   txtInputName.setEnabled(true);
                   txtAreaChat.setEnabled(false);
                   txtInputMessage.setEnabled(false);
                }
                else { //user clicked on login
                    String userName = txtInputName.getText();
                    if(userName.length()<1) {
                        JOptionPane.showMessageDialog(null,"Enter your name", "Name is missing",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    chatClient.login(userName);
                    btnLogin.setText("Logout");
                    txtInputName.setEnabled(false);
                    txtAreaChat.setEnabled(true);
                    txtInputMessage.setEnabled(true);
                }
                refreshMessages();
            }
        });
        panel.add(btnLogin);
    }

    private void initChatPanel(JPanel panel){
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        txtAreaChat = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtAreaChat);
        panel.add(scrollPane);
        txtAreaChat.setAutoscrolls(true);
        txtAreaChat.setEditable(false);
        /*for (int i = 0; i <50;i++){
            txtAreaChat.append("Hey"+i+"\n");
        }*/

    }

    private void initFooterPanel(JPanel panel){
        txtInputMessage = new JTextField("", 50);
        panel.add(txtInputMessage);
        txtInputMessage.setEnabled(false);
       // txtInputMessage.setEditable(false);
        btnSend = new JButton("Odeslat");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = txtInputMessage.getText();
                if(msg.length()<1){
                    return;
                }
                if(!chatClient.isAuthenticated()){
                    return;
                }
                chatClient.sendMessage(msg);
                txtInputMessage.setText("");
                refreshMessages();
            }
        });
    }

    private void initLoggedUsersPanel(JPanel panel){
       /* tblLoggedUsers = new JTable(
                new Object[][]{{"11", "12"},{"21","22"}},
                new String[]{"Col1","Col2"});
       */

        loggedUsersDataTable = new LoggedUsersDataTable(chatClient);
        tblLoggedUsers = new JTable();
        tblLoggedUsers.setModel(loggedUsersDataTable);

        chatClient.addListenerLoggedUsersChanged(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedUsersDataTable.fireTableDataChanged();
            }
        });



        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        panel.add(scrollPane);
    }

    private void refreshMessages(){
        txtAreaChat.setText("");
        for (Message msg : chatClient.getMessages()){
            txtAreaChat.append(msg.toString());
            txtAreaChat.append("\n");
        }
    }

}
