package ru.saptex.controller;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.saptex.Service.UserService;
import ru.saptex.model.User;

public class UserController {
    private final UserService userService;
    private final ObservableList<User> users = FXCollections.observableArrayList();


    public UserController() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        userService = new UserService(factory);
    }


    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, Integer> ageColumn;

    @FXML
    public TextField textFieldFirstName;

    @FXML
    public TextField textFieldLastName;

    @FXML
    public TextField textFieldAge;

    @FXML
    public Label labelId;


    @FXML
    private void initialize() {

        initData();

        idColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getId()));
        firstNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLastName()));
        ageColumn.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getAge()));
        userTableView.setItems(users);

        listenerTabUserDetails(null);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            listenerTabUserDetails(newValue);
        });


    }

    private void initData() {
        users.addAll(userService.findAll());
    }

    @FXML
    public void buttonUpdate(ActionEvent actionEvent) {
        int selectedIndex = userTableView.getSelectionModel().getSelectedIndex();
        final User user = userTableView.getSelectionModel().getSelectedItem();
        user.setFirstName(textFieldFirstName.getText());
        user.setLastName(textFieldLastName.getText());
        user.setAge(Integer.parseInt(textFieldAge.getText()));
        userTableView.getItems().set(selectedIndex, user);
        userService.update(user);
        cleanTextField();

    }

    @FXML
    public void deleteButton(ActionEvent actionEvent) {
        final User user = userTableView.getSelectionModel().getSelectedItem();
        userService.delete(user);
        userTableView.getItems().remove(user);
        cleanTextField();
        System.out.println("Delete user: " + user);

    }

    @FXML
    public void buttonSave(ActionEvent actionEvent) {
        //Создаем пользователя, в качестве значений принимаем данные с текстовых полей.
        User user = new User(textFieldFirstName.getText(), textFieldLastName.getText(), Integer.parseInt(textFieldAge.getText()));
        //Сохраняем данные в базу данных
        userService.save(user);
        //Добавляем в коллекцию для моментального обновления
        userTableView.getItems().add(user);
        cleanTextField();

    }

    private void listenerTabUserDetails(User user) {
        if (user != null) {
            labelId.setText(String.valueOf(user.getId()));
            textFieldFirstName.setText(user.getFirstName());
            textFieldLastName.setText(user.getLastName());
            textFieldAge.setText(String.valueOf(user.getAge()));
        } else {
            labelId.setText("");
            textFieldFirstName.setText("");
            textFieldLastName.setText("");
            textFieldAge.setText("");
        }
    }


    private void cleanTextField() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldAge.clear();
        labelId.setText("");

    }
}
