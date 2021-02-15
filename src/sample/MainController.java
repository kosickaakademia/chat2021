package sample;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sk.kosickaakademia.onofrej.chat.entity.User;

public class MainController {
    public Label lbl_login;
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
}
