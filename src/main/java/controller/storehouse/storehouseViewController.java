package controller.storehouse;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import controller.instrument.editInstrumentViewController;
import dbUtil.dbSqlite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.fxModel.instrumentFxModel;
import model.fxModel.storehouseFxModel;
import model.instrumentModel;
import model.registerModel;
import model.storehouseModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class storehouseViewController {
    public  storehouseViewController() {System.out.println("Siemanko jestem konstruktorem klasy  storehouseViewController.");}




    @FXML
    private Button addNewInstrumentButton;
    @FXML
    private TextField fullNameSearchTextField;
    @FXML
    private TableView<storehouseFxModel> storehouseTableView;
    @FXML
    private TableColumn<storehouseFxModel, String> idInstrumentColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentNameColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentTypeColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentProducerColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentSerialNumberColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentIdentificationNumberColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentRangeColumn;
    @FXML
    private TableColumn<storehouseFxModel, String> instrumentClientColumn;
    @FXML
    private TableColumn<storehouseFxModel, Date> addDateColumn;
    @FXML
    private TableColumn<storehouseFxModel, Date> calibrationDateColumn;
    @FXML
    private TableColumn<storehouseFxModel, Date> leftDateColumn;
    @FXML
    private TextField shortNameTextField;
    @FXML
    private VBox mainVBox;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private Button leftInstrumentButton;
    @FXML
    private Button calibrateInstrumentButton;
    @FXML
    private Button editInstrumentButton;
    @FXML
    private TextField streetTextField;

    @FXML
    private DatePicker datePicker;
    // Converter
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
    private void initialize(){
        System.out.println("Siemanko jestem funkcją initialize klasy storehouseViewController.");
        getStorehouseList();
        initializeTableView();
        datePicker.setConverter(converter);
    }

    private storehouseFxModel editedStorehouseElementFromList;

    public void setEditedStorehouseElementFromList(storehouseFxModel editedStorehouseElementFromList) {
        this.editedStorehouseElementFromList = editedStorehouseElementFromList;
    }

    private newInstrumentViewController newInstrumentController;

    private List<storehouseModel> storehouseModelList = new ArrayList<storehouseModel>();
    private ObservableList<storehouseFxModel> storehouseFxObservableList = FXCollections.observableArrayList();
    private editInstrumentViewController editedInstrumentController;


    public void addInstrumentToStorehouse(String dataczegos){
        try {
            Date dNow = new Date();
            Dao<storehouseModel, Integer> storehouseDao = DaoManager.createDao(dbSqlite.getConnectionSource(),storehouseModel.class);
            storehouseModel magazynek = new storehouseModel(0,new instrumentModel(),dataczegos, dataczegos,dataczegos);
            magazynek.getInstrument().setIdInstrument(1);
            storehouseDao.create(magazynek);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getStorehouseList(){
        try {
            storehouseFxObservableList.clear();
            Dao<storehouseModel, Integer> storehouseDao = DaoManager.createDao(dbSqlite.getConnectionSource(),storehouseModel.class);
            storehouseModelList = storehouseDao.queryForAll();
            Integer indeks = 0;
            for (storehouseModel storehouseElement : storehouseModelList) {
                storehouseFxObservableList.add(new storehouseFxModel(indeks, storehouseElement.getIdStorehouse(),
                        storehouseElement.getInstrument().getInstrumentName().getInstrumentName(), storehouseElement.getInstrument().getInstrumentType().getInstrumentType(),
                        storehouseElement.getInstrument().getInstrumentProducer().getInstrumentProducer(), storehouseElement.getInstrument().getSerialNumber(),
                        storehouseElement.getInstrument().getIdentificationNumber(), storehouseElement.getInstrument().getInstrumentRange().getInstrumentRange(),
                        storehouseElement.getInstrument().getClient().getShortName(), storehouseElement.getAddDate(), storehouseElement.getCalibrationDate(),storehouseElement.getLeftDate()));
                indeks++;
            }
            dbSqlite.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initializeTableView(){
        idInstrumentColumn.setCellValueFactory(new PropertyValueFactory<>("idInstrument"));
        instrumentNameColumn.setCellValueFactory(new PropertyValueFactory<>("instrumentName"));
        instrumentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("instrumentType"));
        instrumentProducerColumn.setCellValueFactory(new PropertyValueFactory<>("instrumentProducer"));
        instrumentSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        instrumentIdentificationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        instrumentRangeColumn.setCellValueFactory(new PropertyValueFactory<>("instrumentRange"));
        instrumentClientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        addDateColumn.setCellValueFactory(new PropertyValueFactory<>("addDate"));
        calibrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("calibrationDate"));
        leftDateColumn.setCellValueFactory(new PropertyValueFactory<>("leftDate"));
        storehouseTableView.setItems(storehouseFxObservableList);
        storehouseTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setEditedStorehouseElementFromList(newValue);
            //    showInformation(newValue);
        });
    }
    @FXML
    private void editInstrument(){
        if(editedStorehouseElementFromList!=null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/instrument/editInstrumentView.fxml"));
                VBox vBox = loader.load();
                editedInstrumentController = loader.getController();
                if (editedInstrumentController != null){
                    editedInstrumentController.setStorehouseMainController(this);
                    editedInstrumentController.setEditedInstrument(storehouseModelList.get(editedStorehouseElementFromList.getIndexOfStorehouseModelList()).getInstrument());
                     setEditedInstrumentValues();
                }
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Edytuj wybrany przyrząd");
                Scene scene = new Scene(vBox);
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setEditedInstrumentValues(){
        editedInstrumentController.setInstrumentNameComboBox(editedStorehouseElementFromList.getInstrumentName());
        editedInstrumentController.setInstrumentTypeComboBox(editedStorehouseElementFromList.getInstrumentType());
        editedInstrumentController.setInstrumentProducerComboBox(editedStorehouseElementFromList.getInstrumentProducer());
        editedInstrumentController.setSerialNumberTextField(editedStorehouseElementFromList.getSerialNumber());
        editedInstrumentController.setIdentificationNumberTextField(editedStorehouseElementFromList.getIdentificationNumber());
        editedInstrumentController.setInstrumentRangeComboBox(editedStorehouseElementFromList.getInstrumentRange());
        editedInstrumentController.setInstrumentClientComboBox2(editedStorehouseElementFromList.getClient());
    }
    @FXML
    private void addNewInstrument(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/storehouse/newInstrumentView.fxml"));
                VBox vBox = loader.load();
                newInstrumentController = loader.getController();
                if (newInstrumentController != null){
                    newInstrumentController.setStorehouseMainController(this);
                 //   newInstrumentController.setNewInstrumentModel(storehouseModelList.get(editedStorehouseElementFromList.getIndexOfStorehouseModelList()).getInstrument());
                }
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Dodaj przyrząd do magazynu");
                Scene scene = new Scene(vBox);
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    @FXML
    public void calibrateInstrument(){
        if(editedStorehouseElementFromList!=null) {
            try {
                Dao<registerModel, Integer> registerDao= DaoManager.createDao(dbSqlite.getConnectionSource(), registerModel.class);
                registerModel calibrateInstrument = new registerModel(0,0,storehouseModelList.get(editedStorehouseElementFromList.getIndexOfStorehouseModelList()).getIdStorehouse(),
                        "",storehouseModelList.get(editedStorehouseElementFromList.getIndexOfStorehouseModelList()).getCalibrationDate(),
                        storehouseModelList.get(editedStorehouseElementFromList.getIndexOfStorehouseModelList()).getInstrument(),"");
                registerDao.create(calibrateInstrument);
                Dao<registerModel, Integer> registerDao2= DaoManager.createDao(dbSqlite.getConnectionSource(), registerModel.class);
                QueryBuilder<registerModel, Integer> registerQueryBuilder = registerDao2.queryBuilder();
                registerQueryBuilder.where().eq("idRegister", calibrateInstrument.getIdRegister()-1);
                PreparedQuery<registerModel> prepare = registerQueryBuilder.prepare();
                List<registerModel> result = registerDao2.query(prepare);
                if(result.isEmpty()){ //znaczy się ze pierwszy wpis :)
                    calibrateInstrument.setIdRegisterByYear(1);
                    calibrateInstrument.setCardNumber("1-2018");
                    registerDao.update(calibrateInstrument);
                }else{ //nie jest to pierwszy wpis
                    calibrateInstrument.setIdRegisterByYear(result.get(0).getIdRegisterByYear()+1);
                    calibrateInstrument.setCardNumber(result.get(0).getIdRegisterByYear()+1+"2018");
                    registerDao.update(calibrateInstrument);
                }

                System.out.println();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void showDate(){
        System.out.println(datePicker.getValue());
        String dataczegostam=datePicker.getValue().toString();

        addInstrumentToStorehouse(dataczegostam);
        getStorehouseList();
    }
}