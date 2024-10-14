package com.example.carla_delafuentebernardino_crudcoches;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InicioController {
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
    private ComboBox<?> cb_tipo;

    @FXML
    private TableColumn<?, ?> tc_marca;

    @FXML
    private TableColumn<?, ?> tc_matricula;

    @FXML
    private TableColumn<?, ?> tc_modelo;

    @FXML
    private TableColumn<?, ?> tc_tipo;

    @FXML
    private TableView<?> tv_coches;

    @FXML
    private TextField txt_marca;

    @FXML
    private TextField txt_matricula;

    @FXML
    private TextField txt_modelo;

    @FXML
    void OnCancelarClick(ActionEvent event) {

    }

    @FXML
    void OnEliminarClick(ActionEvent event) {

    }

    @FXML
    void OnGuardarClick(ActionEvent event) {

    }

    @FXML
    void OnModificarClick(ActionEvent event) {

    }

    @FXML
    void OnNuevoClick(ActionEvent event) {

    }

}