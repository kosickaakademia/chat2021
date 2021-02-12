package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.kosickaakademia.onofrej.chat.database.Database;
import sk.kosickaakademia.onofrej.chat.entity.User;

public class Controller {

    @FXML
    private TextField txt_login;
    public PasswordField txt_password;
    public Label lbl_error;
    public Button btn_login;

    public void btn_click(ActionEvent actionEvent) {
        System.out.println("Hello! It works!");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database db = new Database();
            User user = db.loginUser(login,password);
            if(user==null){
                lbl_error.setVisible(true);
            }else{
                System.out.println("Success! You are logged!");
            }
        }
    }
}
