package controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import dbUtil.dbSqlite;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.userModel;
import util.Close;
import util.showAlert;

import java.sql.SQLException;

/**
 * Klasa kontrolera odpowiedzialnego za obsługę okną edycji użytkowników
 */
public class dialogUserViewController {
    public dialogUserViewController() {}

    //Wstrzyknięcie pól z widoku, żeby można było na nich operować
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ComboBox<String> permissionComboBox;
    @FXML
    private Label userLabel;
    @FXML
    private VBox mainVBox;


    //Potrzebny do przekazania id miedzy oknami dla nowego usera bedzie tutaj null dla edytowanego bedzie idUser z tablicy USERS
    private Integer idEditedUser;
    private userViewController mainUserController;
    //Settery
    public void setMainUserController(userViewController mainUserController) {
        this.mainUserController = mainUserController;
    }
    public void setIdEditedUser(Integer idEditedUser) {
        this.idEditedUser = idEditedUser;
    }
    //Settery textField i comboBox
    public void setFirstNameTextField(String firstNameTextField) {
        this.firstNameTextField.setText(firstNameTextField);
    }
    public void setLastNameTextField(String lastNameTextField) {
        this.lastNameTextField.setText(lastNameTextField);
    }
    public void setLoginTextField(String loginTextField) {
        this.loginTextField.setText(loginTextField);
    }
    public void setPasswordTextField(String passwordTextField) {
        this.passwordTextField.setText(passwordTextField);
    }
    public void setPermissionComboBox(String permissionComboBox) {
        this.permissionComboBox.setValue(permissionComboBox);
    }
    public void setUserLabel(String userLabel) {
        this.userLabel.setText(userLabel);
    }

    //Funkcja initialize wykonuje się po konstruktorze
    @FXML
    private void initialize(){
        permissionComboBox.getItems().addAll("admin","user");
    }
    @FXML
    private void saveUser(){
        if(idEditedUser !=null){
            editUser();
        }else{
            addNewUser();
        }
    }
    @FXML
    private void cancelSaveUser(){
        Close.closeVBoxWindow(mainVBox);
    }
    private void addNewUser(){
        if(isValidUserData()){
            try {
                Dao<userModel, Integer> userDao = DaoManager.createDao(dbSqlite.getConnectionSource(),userModel.class);
                userDao.create(getUser());
                Close.closeVBoxWindow(mainVBox);
                mainUserController.getUsers();
                dbSqlite.closeConnection();
            } catch (SQLException e) {
                showAlert.display(e.getMessage());
            }
        }
    }
    private void editUser(){
        if(isValidUserData()){
            try {
                Dao<userModel, Integer> userDao = DaoManager.createDao(dbSqlite.getConnectionSource(),userModel.class);
                userDao.update(getUser());
                Close.closeVBoxWindow(mainVBox);
                mainUserController.getUsers();
                dbSqlite.closeConnection();
            } catch (SQLException e) {
                showAlert.display(e.getMessage());
            }
        }
    }
    private boolean isValidUserData() {
        String errorMessage = "";
        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) {
            errorMessage += "Nie wprowadziłeś prawidłowo imienia ! \n";
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) {
            errorMessage += "Nie wprowadziłeś prawidłowo nazwiska ! \n";
        }
        if (loginTextField.getText() == null || loginTextField.getText().length() == 0) {
            errorMessage += "Nie wprowadziłeś prawidłowo loginu ! \n";
        }
        if (passwordTextField.getText() == null || passwordTextField.getText().length() == 0) {
            errorMessage += "Nie wprowadziłeś prawidłowo hasła ! \n";
        }
        if (permissionComboBox.getValue() == null ) {
            errorMessage += "Nie wybrałeś poziomu dostępu ! \n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nieprawidłowe dane");
            alert.setHeaderText("Proszę wprowadzić prawidłowe dane dla podanych niżej pól");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    private userModel getUser(){
        userModel user=new userModel(idEditedUser,firstNameTextField.getText(),lastNameTextField.getText(),loginTextField.getText(),passwordTextField.getText(),permissionComboBox.getValue());
        return user;
    }
}
