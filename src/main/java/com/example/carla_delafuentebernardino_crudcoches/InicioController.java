package com.example.carla_delafuentebernardino_crudcoches;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InicioController implements Initializable {

    @FXML
    private Button bt_cancelar;

    @FXML
    private Button bt_eliminar;

    @FXML
    private Button bt_guardar;

    @FXML
    private Button bt_modificar;

    @FXML
    private Button bt_nuevo;

    @FXML
    private ComboBox<String> cb_tipo;

    @FXML
    private TableColumn<Coche, String> tc_marca;

    @FXML
    private TableColumn<Coche, String> tc_matricula;

    @FXML
    private TableColumn<Coche, String> tc_modelo;

    @FXML
    private TableColumn<Coche, String> tc_tipo;

    @FXML
    private TableView<Coche> tv_coches;

    @FXML
    private TextField txt_marca;

    @FXML
    private TextField txt_matricula;

    @FXML
    private TextField txt_modelo;

    List<Coche> coches;

    private CocheDAO cocheDAO;

    private Coche coche;

    private Coche coche_seleccionado;



    @FXML
    void OnCancelarClick(ActionEvent event) {
        limpiarCampos();
        desactivarCampos();
    }

    @FXML
    void OnEliminarClick(ActionEvent event) {
        if (coche_seleccionado == null) {
            Alerta.mensajeError("Seleccione un coche de la tabla para poder eliminarlo.");
        } else {
            cocheDAO.eliminarCoche(coche_seleccionado.getMatricula());
            cargarCoches();
            desactivarCampos();
        }

    }

    @FXML
    void OnGuardarClick(ActionEvent event) {
        if(txt_matricula.getText().isEmpty() || txt_marca.getText().isEmpty() || txt_modelo.getText().isEmpty() || cb_tipo.getValue() == null){
            Alerta.mensajeError("Complete todos los campos, por favor.");
        } else {
            Coche cocheNuevo = new Coche(txt_matricula.getText(), txt_marca.getText(), txt_modelo.getText(), cb_tipo.getValue());
            cocheDAO.insertarCoche(cocheNuevo);

            cargarCoches();
            desactivarCampos();
            limpiarCampos();
        }
    }

    @FXML
    void OnModificarClick(ActionEvent event) {
        if (coche_seleccionado == null) {
            Alerta.mensajeError("Seleccione un coche de la tabla para poder modificarlo.");
        } else {
            coche_seleccionado.setMarca(txt_marca.getText());
            coche_seleccionado.setModelo(txt_modelo.getText());
            coche_seleccionado.setTipo(cb_tipo.getValue());

            cocheDAO.modificarCoche(coche_seleccionado);

            cargarCoches();
            activarCampos();
        }
    }

    @FXML
    void OnNuevoClick(ActionEvent event) {
        Alerta.mensajeInfo("INFO", "Complete los campos para crear un nuevo coche.");
        activarCampos();
    }

    @FXML
    void OnCocheClick(MouseEvent event) {
        coche_seleccionado = tv_coches.getSelectionModel().getSelectedItem();
        if (coche_seleccionado != null) {
            // Llenar los campos de texto con los datos del coche seleccionado
            txt_matricula.setText(coche_seleccionado.getMatricula());
            txt_marca.setText(coche_seleccionado.getMarca());
            txt_modelo.setText(coche_seleccionado.getModelo());
            cb_tipo.setValue(coche_seleccionado.getTipo());

            activarCampos(); // Si deseas que los campos sean editables
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cocheDAO = new CocheDAO();
        cocheDAO.crearBD();

        String[] tipos = new String[]{"Familiar", "Monovolumen", "Deportivo", "SUV"};
        cb_tipo.setItems(FXCollections.observableArrayList(tipos));

        tc_matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tc_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tc_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        desactivarCampos();
        cargarCoches();

        tv_coches.setOnMouseClicked(this::OnCocheClick);

    }

    public void cargarCoches(){
        coches = cocheDAO.obtenerCoches();
        tv_coches.getItems().setAll(coches);
    }


    public void limpiarCampos() {
        txt_matricula.clear();
        txt_marca.clear();
        txt_modelo.clear();
        cb_tipo.setValue(null);
    }

    public void activarCampos(){
        txt_matricula.setEditable(true);
        txt_marca.setEditable(true);
        txt_modelo.setEditable(true);
        cb_tipo.setDisable(false);
    }

    public void desactivarCampos(){
        txt_matricula.setEditable(false);
        txt_marca.setEditable(false);
        txt_modelo.setEditable(false);
        cb_tipo.setDisable(true);
    }
}