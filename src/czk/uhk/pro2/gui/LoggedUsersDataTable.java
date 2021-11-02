package czk.uhk.pro2.gui;

import czk.uhk.pro2.models.ChatClient;

import javax.swing.table.AbstractTableModel;

public class LoggedUsersDataTable extends AbstractTableModel {
    private ChatClient chatClient;

    public LoggedUsersDataTable(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "User name";
            default:
                return null;
        }

    }

    @Override
    public int getRowCount() {
        return chatClient.getLoggedUsers().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return chatClient.getLoggedUsers().get(rowIndex);
    }

}
