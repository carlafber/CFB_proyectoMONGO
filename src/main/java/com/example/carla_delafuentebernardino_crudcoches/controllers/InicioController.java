package com.example.carla_delafuentebernardino_crudcoches.controllers;

import com.example.carla_delafuentebernardino_crudcoches.CRUD.CocheCRUD;
import com.example.carla_delafuentebernardino_crudcoches.classes.Coche;
import com.example.carla_delafuentebernardino_crudcoches.util.Alerta;
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
    private Button bt_insertar;

    @FXML
    private Button bt_modificar;

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

    private CocheCRUD cocheCRUD;

    private Coche coche_seleccionado;



    @FXML
    void OnCancelarClick(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void OnEliminarClick(ActionEvent event) {
        if (coche_seleccionado == null) {
            Alerta.mensajeError("Seleccione un coche de la tabla para poder eliminarlo.");
        } else {
            cocheCRUD.eliminarCoche(coche_seleccionado.getMatricula());
            cargarCoches();
            Alerta.mensajeInfo("ÉXITO", "Coche eliminado correctamente.");
            limpiarCampos();
        }

    }

    @FXML
    void OnInsertarClick(ActionEvent event) {
        if(txt_matricula.getText().isEmpty() || txt_marca.getText().isEmpty() || txt_modelo.getText().isEmpty() || cb_tipo.getValue() == null){
            Alerta.mensajeError("Complete todos los campos, por favor.");
        } else {
            Coche cocheNuevo = new Coche(txt_matricula.getText(), txt_marca.getText(), txt_modelo.getText(), cb_tipo.getValue());
            cocheCRUD.insertarCoche(cocheNuevo);

            cargarCoches();
            Alerta.mensajeInfo("ÉXITO", "Coche insertado correctamente.");
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

            cocheCRUD.modificarCoche(coche_seleccionado);

            cargarCoches();
            Alerta.mensajeInfo("ÉXITO", "Coche modificado correctamente.");
        }
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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cocheCRUD = new CocheCRUD();
        cocheCRUD.crearBD();

        String[] tipos = new String[]{"Familiar", "Monovolumen", "Deportivo", "SUV"};
        cb_tipo.setItems(FXCollections.observableArrayList(tipos));

        tc_matricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tc_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tc_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        cargarCoches();

        tv_coches.setOnMouseClicked(this::OnCocheClick);
    }

    public void cargarCoches(){
        coches = cocheCRUD.obtenerCoches();
        tv_coches.getItems().setAll(coches);
    }

    public void limpiarCampos() {
        txt_matricula.clear();
        txt_marca.clear();
        txt_modelo.clear();
        cb_tipo.setValue(null);
    }
}