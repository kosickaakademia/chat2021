package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sk.kosickaakademia.onofrej.chat.database.Database;
import sk.kosickaakademia.onofrej.chat.entity.Message;
import sk.kosickaakademia.onofrej.chat.entity.User;

import java.util.Date;
import java.util.List;

public class MainController {
    public Label lbl_login;
    public TextField txt_newmwssage;
    public TextField txt_receiver;
    public ListView lbx_messages;
    private User user;

    public MainController(){
        System.out.println("I am contruktor!");
    }

    public void logout(MouseEvent mouseEvent) {
    }

    public void setUser(User user){
        this.user=user;
    }

    public void initLoginName() {
        lbl_login.setText(user.getLogin());
    }

    public void send_click(ActionEvent actionEvent) {
        String receiver= txt_receiver.getText().trim();
        String newMessage = txt_newmwssage.getText().trim();
        if(receiver.length()<1 || newMessage.length()<1)
            return ;
        boolean result = new Database().sendMessage(user.getId(), receiver,newMessage);
        txt_receiver.setText("");
        txt_newmwssage.setText("");
    }


    public void click_refresh(ActionEvent actionEvent) {

        List<Message> list = new Database().getMyMessages(user.getLogin());
        if(list.isEmpty()){
            // ziadna nova sprava
            return;
        }
        // idem nacitat a vypisat spravy
        for(Message m:list){
            String sender = m.getFrom();
            Date dt = m.getDt();
            String text = m.getText();
            lbx_messages.getItems().add(0, sender+" "+dt);
            lbx_messages.getItems().add(1," > "+text);
        }

    }
}
