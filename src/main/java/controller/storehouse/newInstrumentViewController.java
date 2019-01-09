package controller.storehouse;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import controller.clientViewController;
import controller.instrument.*;
import dbUtil.dbSqlite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class newInstrumentViewController {
    public newInstrumentViewController() {System.out.println("Halo świry jestem kontruktorem klasy newInstrumentViewController");
    }

    private newInstrumentNameViewController newInstrumentName;
    private newInstrumentTypeViewController newInstrumentType;
    private newInstrumentProducerViewController newInstrumentProducer;
    private newInstrumentRangeViewController newInstrumentRange;
    private clientViewController clientMainController;

    //Listy do ComboBoxów trochę przerost formy nad tręścią ale w sumie pożyteczna sprawa
    private ObservableList<String> instrumentNameObservableList = FXCollections.observableArrayList();
    private FilteredList<String> filteredNames = new FilteredList<String>(instrumentNameObservableList, p -> true);
    private ObservableList<String> instrumentTypeObservableList = FXCollections.observableArrayList();
    private FilteredList<String> filteredTypes = new FilteredList<String>(instrumentTypeObservableList, p -> true);
    private ObservableList<String> instrumentProducerObservableList = FXCollections.observableArrayList();
    private FilteredList<String> filteredProducers = new FilteredList<String>(instrumentProducerObservableList, p -> true);
    private ObservableList<String> instrumentRangeObservableList = FXCollections.observableArrayList();
    private FilteredList<String> filteredRange = new FilteredList<String>(instrumentRangeObservableList, p -> true);
    //Listy obiektów z tabel
    private List<instrumentNameModel> instrumentNameList;
    private List<instrumentTypeModel> instrumentTypeList;
    private List<instrumentProducerModel> instrumentProducerList;
    private List<instrumentRangeModel> instrumentRangeList;

    private storehouseViewController storehouseMainController;

    public void setStorehouseMainController(storehouseViewController storehouseMainController) {
        this.storehouseMainController = storehouseMainController;
    }

    private instrumentModel newInstrumentModel;

    public void setNewInstrumentModel(instrumentModel newInstrumentModel) {
        this.newInstrumentModel = newInstrumentModel;
    }

    //Obiekty które stworzą nowy przyrząd
    private clientModel clientInstrument;
    public void setClientInstrument(clientModel clientInstrument) {
        this.clientInstrument = clientInstrument;
    }



    //Wstrzyknięcia elementów z FXMLA
    @FXML
    private ComboBox<String> instrumentNameComboBox;
    @FXML
    private ComboBox<String> instrumentTypeComboBox;
    @FXML
    private ComboBox<String> instrumentProducerComboBox;
    @FXML
    private ComboBox<String> instrumentRangeComboBox;
    @FXML
    private ComboBox<String> instrumentClientComboBox;
    @FXML
    private TextField serialNumberTextField;
    @FXML
    private TextField identificationNumberTextField;
    @FXML
    private DatePicker addDateDatePicker;
    @FXML
    private TextArea newInstrumentTextArea;

    public void setInstrumentNameComboBox(String instrumentNameComboBox) {
        this.instrumentNameComboBox.setValue(instrumentNameComboBox);
    }

    public void setInstrumentTypeComboBox(String instrumentTypeComboBox) {
        this.instrumentTypeComboBox.setValue(instrumentTypeComboBox);
    }

    public void setInstrumentProducerComboBox(String instrumentProducerComboBox) {
        this.instrumentProducerComboBox.setValue(instrumentProducerComboBox);
    }

    public void setInstrumentRangeComboBox(String instrumentRangeComboBox) {
        this.instrumentRangeComboBox.setValue(instrumentRangeComboBox);
    }

    public void setInstrumentClientComboBox2(String instrumentClientComboBox) {
        this.instrumentClientComboBox.setValue(instrumentClientComboBox);
    }

    public void setSerialNumberTextField(String serialNumberTextField) {
        this.serialNumberTextField.setText(serialNumberTextField);
    }

    public void setIdentificationNumberTextField(String identificationNumberTextField) {
        this.identificationNumberTextField.setText(identificationNumberTextField);
    }


    @FXML
    private Button addNewInstrumentNameButton;
    @FXML
    private Button addNewInstrumentTypeButton;
    @FXML
    private Button addNewInstrumentRangeButton;
    @FXML
    private Button addNewInstrumentProducerButton;
    public void setInstrumentClientComboBox(String instrumentClientComboBox) {
        this.instrumentClientComboBox.setValue(instrumentClientComboBox);
    }

    @FXML
    private void initialize(){
        System.out.println("Halo świry jestem funkcją initialize klasy newInstrumentViewController");
        getInstrumentNameList();
        initComboBox(instrumentNameComboBox,filteredNames);
        getInstrumentTypeList();
        initComboBox(instrumentTypeComboBox,filteredTypes);
        getInstrumentProducerList();
        initComboBox(instrumentProducerComboBox,filteredProducers);
        getInstrumentRangeList();
        initComboBox(instrumentRangeComboBox,filteredRange);
    }

    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");
        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };
    @FXML
    private void addNewInstrumentName(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/newInstrumentNameView.fxml"));
            VBox vBox = loader.load();
            newInstrumentName=loader.getController();
            if (newInstrumentName != null){
                newInstrumentName.setNewInstrumentMainController(this);
            }
            Stage window = new Stage();
            window.setTitle("Nazwa");
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addNewInstrumentType(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/newInstrumentTypeView.fxml"));
            VBox vBox = loader.load();
            newInstrumentType=loader.getController();
            if (newInstrumentType != null){
                newInstrumentType.setNewInstrumentMainController(this);
            }
            Stage window = new Stage();
            window.setTitle("Typ");
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addNewInstrumentProducer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/newInstrumentProducerView.fxml"));
            VBox vBox = loader.load();
            newInstrumentProducer=loader.getController();
            if (newInstrumentProducer != null){
                newInstrumentProducer.setNewInstrumentMainController(this);
            }
            Stage window = new Stage();
            window.setTitle("Typ");
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addNewInstrumentRange(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/newInstrumentRangeView.fxml"));
            VBox vBox = loader.load();
            newInstrumentRange=loader.getController();
            if (newInstrumentRange != null){
                newInstrumentRange.setNewInstrumentMainController(this);
            }
            Stage window = new Stage();
            window.setTitle("Typ");
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addClientInstrument(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/clientView.fxml"));
            VBox vBox = loader.load();
            clientMainController=loader.getController();
            if (clientMainController != null){
                clientMainController.setNewInstrumentMainController(this);
            }
            Stage window = new Stage();
            window.setTitle("Zleceniodawcy");
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getInstrumentNameList() {
        try {
            instrumentNameObservableList.clear();
            Dao<instrumentNameModel, Integer> instrumentNameDao = DaoManager.createDao(dbSqlite.getConnectionSource(), instrumentNameModel.class);
            instrumentNameList = instrumentNameDao.queryForAll();
            instrumentNameList.forEach(instrumentName -> {
                instrumentNameObservableList.add(instrumentName.getInstrumentName());
            });
            dbSqlite.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public instrumentNameModel getName(String instrumentName){
        for (instrumentNameModel name : instrumentNameList) {
            if (name.getInstrumentName().equals(instrumentName)) {
                return name;
            }
        }
        return null;
    }
    public instrumentTypeModel getType(String instrumentType){
        for (instrumentTypeModel type : instrumentTypeList) {
            if (type.getInstrumentType().equals(instrumentType)) {
                return type;
            }
        }
        return null;
    }
    public instrumentProducerModel getProducer(String instrumentProducer){
        for (instrumentProducerModel producer : instrumentProducerList) {
            if (producer.getInstrumentProducer().equals(instrumentProducer)) {
                return producer;
            }
        }
        return null;
    }
    public instrumentRangeModel getRange(String instrumentRange){
        for (instrumentRangeModel range : instrumentRangeList) {
            if (range.getInstrumentRange().equals(instrumentRange)) {
                return range;
            }
        }
        return null;
    }
    public void getInstrumentTypeList(){
        try {
            instrumentTypeObservableList.clear();
            Dao<instrumentTypeModel, Integer> instrumentTypeDao = DaoManager.createDao(dbSqlite.getConnectionSource(),instrumentTypeModel.class);
            instrumentTypeList = instrumentTypeDao.queryForAll();
            instrumentTypeList.forEach(instrumentType ->{
                instrumentTypeObservableList.add(instrumentType.getInstrumentType());
            });
            dbSqlite.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void getInstrumentProducerList(){
        try {
            instrumentProducerObservableList.clear();
            Dao<instrumentProducerModel, Integer> instrumentProducerDao = DaoManager.createDao(dbSqlite.getConnectionSource(),instrumentProducerModel.class);
            instrumentProducerList = instrumentProducerDao.queryForAll();
            instrumentProducerList.forEach(instrumentProducer ->{
                instrumentProducerObservableList.add(instrumentProducer.getInstrumentProducer());
            });
            dbSqlite.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getInstrumentRangeList() {
        try {
            instrumentRangeObservableList.clear();
            Dao<instrumentRangeModel, Integer> instrumentRangeDao = DaoManager.createDao(dbSqlite.getConnectionSource(), instrumentRangeModel.class);
            instrumentRangeList = instrumentRangeDao.queryForAll();
            instrumentRangeList.forEach(instrumentRange -> {
                instrumentRangeObservableList.add(instrumentRange.getInstrumentRange());
            });
            dbSqlite.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addNewInstrument(){
        if(isValidInstrumentData()){
            setAddNewInstrumentName();
        }
    }

    private void setAddNewInstrumentName(){
        try {
            Dao<instrumentModel, Integer> instrumentDao= DaoManager.createDao(dbSqlite.getConnectionSource(), instrumentModel.class);
            QueryBuilder<instrumentModel, Integer> instrumentQueryBuilder = instrumentDao.queryBuilder();
            if(!serialNumberTextField.getText().isEmpty() && !identificationNumberTextField.getText().isEmpty()) {
                instrumentQueryBuilder.where().eq("serialNumber", serialNumberTextField.getText()).or().eq("identificationNumber", identificationNumberTextField.getText());
            }else if(!serialNumberTextField.getText().isEmpty() && identificationNumberTextField.getText().isEmpty()){
                instrumentQueryBuilder.where().eq("serialNumber", serialNumberTextField.getText());
            }else if(serialNumberTextField.getText().isEmpty() && !identificationNumberTextField.getText().isEmpty()){
                instrumentQueryBuilder.where().eq("identificationNumber", identificationNumberTextField.getText());
            }
            instrumentModel instrument = new instrumentModel(0, getName(instrumentNameComboBox.getValue()), getType(instrumentTypeComboBox.getValue()), getProducer(instrumentProducerComboBox.getValue()), serialNumberTextField.getText(), identificationNumberTextField.getText(), getRange(instrumentRangeComboBox.getValue()), clientInstrument);
            PreparedQuery<instrumentModel> prepare = instrumentQueryBuilder.prepare();
            List<instrumentModel> result = instrumentDao.query(prepare);
            storehouseModel storehouse = new storehouseModel(0,instrument,addDateDatePicker.getValue().toString(),"","");
            Dao<storehouseModel, Integer> storehouseDao= DaoManager.createDao(dbSqlite.getConnectionSource(), storehouseModel.class);
            if(result.isEmpty()) {
                instrumentDao.create(instrument);
                storehouseDao.create(new storehouseModel(0,instrument,addDateDatePicker.getValue().toString(),"",""));
                System.out.println("Nowy tu i tu!!");

            }else{
                instrument=result.get(0);
                System.out.println("Już jest taki przyrząd ćwoku");
                storehouseDao.create(new storehouseModel(0,instrument,addDateDatePicker.getValue().toString(),"",""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void editInstrument(){
        try {
            Dao<instrumentModel, Integer> instrumentDao= DaoManager.createDao(dbSqlite.getConnectionSource(), instrumentModel.class);
            QueryBuilder<instrumentModel, Integer> instrumentQueryBuilder = instrumentDao.queryBuilder();
            if(!serialNumberTextField.getText().isEmpty() && !identificationNumberTextField.getText().isEmpty()) {
                instrumentQueryBuilder.where().eq("serialNumber", serialNumberTextField.getText()).or().eq("identificationNumber", identificationNumberTextField.getText());
            }else if(!serialNumberTextField.getText().isEmpty() && identificationNumberTextField.getText().isEmpty()){
                instrumentQueryBuilder.where().eq("serialNumber", serialNumberTextField.getText());
            }else if(serialNumberTextField.getText().isEmpty() && !identificationNumberTextField.getText().isEmpty()){
                instrumentQueryBuilder.where().eq("identificationNumber", identificationNumberTextField.getText());
            }
            instrumentModel instrument = new instrumentModel(0, getName(instrumentNameComboBox.getValue()), getType(instrumentTypeComboBox.getValue()), getProducer(instrumentProducerComboBox.getValue()), serialNumberTextField.getText(), identificationNumberTextField.getText(), getRange(instrumentRangeComboBox.getValue()), clientInstrument);
            PreparedQuery<instrumentModel> prepare = instrumentQueryBuilder.prepare();
            List<instrumentModel> result = instrumentDao.query(prepare);
            if(result.size()>1) {
                instrumentDao.create(instrument);
                if (instrument.getClient()==null){
                    System.out.println("Ale bieda !!");
                }

            }else{
                System.out.println("Już jest taki przyrząd ćwoku");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initComboBox(ComboBox<String> comboBox, FilteredList<String> filteredList){
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((v, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final String selected = comboBox.getSelectionModel().getSelectedItem();
            if (selected == null || !selected.equals(editor.getText())) {
                filteredList.setPredicate(item -> {
                    if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        });
        comboBox.setItems(filteredList);
    }
    private boolean isValidInstrumentData() {
        String errorMessage = "";
        if (instrumentNameComboBox.getValue() == null) {
            errorMessage += "Nie wybrałeś nazwy urządzenia ! \n";
        }
        if (instrumentTypeComboBox.getValue() == null) {
            errorMessage += "Nie wybrałeś typu urządzenia ! \n";
        }
        if (instrumentProducerComboBox.getValue() == null) {
            errorMessage += "Nie wybrałeś producenta urządzenia ! \n";
        }
        if ((serialNumberTextField.getText().isEmpty())&&(identificationNumberTextField.getText().isEmpty())) {
            errorMessage += "Przyrząd musi posiadać numer fabryczny lub numer identyfikacyjny ! \n";
        }
        if (instrumentRangeComboBox.getValue() == null) {
            errorMessage += "Nie wybrałeś zakresu urządzenia ! \n";
        }
        if (instrumentClientComboBox.getValue() == null ) {
            errorMessage += "Nie wybrałeś zleceniodawcy ! \n";
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
}