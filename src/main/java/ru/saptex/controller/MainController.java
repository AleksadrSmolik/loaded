package ru.saptex.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.saptex.Service.UserService;
import ru.saptex.model.User;

import java.io.IOException;
import java.util.Objects;


public class MainController {
        private final UserService userService;

   @FXML
    private Label alertText;

   @FXML
   private TextField firstNameText;

   @FXML
   private TextField lastNameText;

   @FXML
   private TextField ageText;

    public MainController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        this.userService = new UserService(factory);
    }

    public void addedButton(ActionEvent actionEvent) {
        userService.save(new User(
                firstNameText.getText(),
                lastNameText.getText(),
                Integer.parseInt(ageText.getText())
        ));
        alertText.setTextFill(Color.GREEN);
        alertText.setText("Вы успешно добавили пользователя");
    }

    public void exitButton(ActionEvent actionEvent) {
        final Button source = (Button) actionEvent.getSource();
        source.getScene().getWindow().hide();
    }

    public void inputButton(ActionEvent actionEvent) {
        Button input = (Button)actionEvent.getSource();
        input.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/findUser.fxml")));
            Stage stage = new Stage();
            stage.setTitle("List users");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}