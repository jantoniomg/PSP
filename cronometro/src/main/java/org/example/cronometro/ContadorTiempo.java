package org.example.cronometro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class ContadorTiempo extends Application {

    private TextField inputField;
    private Label tiempoLabel;
    private Button iniciarButton;
    private Button cancelarButton;
    private Button guardarTiempoButton;
    private Button cargarTiempoButton;
    private Button temaOscuroButton;
    private Button temaClaroButton;
    private Scene scene;
    private int tiempoTotal;
    private int tiempoActual;
    private boolean contando;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Contador de Tiempo");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label instruccionLabel = new Label("Introduce el tiempo en segundos:");
        inputField = new TextField();
        tiempoLabel = new Label("Tiempo: 00:00:00");
        iniciarButton = new Button("Iniciar");
        cancelarButton = new Button("Cancelar");
        cancelarButton.setDisable(true);

        // Botones para las funcionalidades adicionales
        guardarTiempoButton = new Button("Guardar Tiempo");
        cargarTiempoButton = new Button("Cargar Tiempo");
        temaOscuroButton = new Button("Tema Oscuro");
        temaClaroButton = new Button("Tema Claro");

        root.getChildren().addAll(instruccionLabel, inputField, tiempoLabel,
                iniciarButton, cancelarButton, guardarTiempoButton, cargarTiempoButton, temaOscuroButton, temaClaroButton);

        iniciarButton.setOnAction(e -> iniciarContador());
        cancelarButton.setOnAction(e -> cancelarContador());
        guardarTiempoButton.setOnAction(e -> guardarTiempoPredefinido());
        cargarTiempoButton.setOnAction(e -> cargarTiempoPredefinido());
        temaOscuroButton.setOnAction(e -> aplicarTemaOscuro());
        temaClaroButton.setOnAction(e -> aplicarTemaClaro());

        scene = new Scene(root, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void iniciarContador() {
        try {
            tiempoTotal = Integer.parseInt(inputField.getText());
            if (tiempoTotal <= 0) {
                throw new NumberFormatException();
            }
            tiempoActual = 0;
            contando = true;
            iniciarButton.setDisable(true);
            cancelarButton.setDisable(false);
            inputField.setDisable(true);

            new Thread(() -> {
                while (contando && tiempoActual < tiempoTotal) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        break;
                    }
                    tiempoActual++;
                    Platform.runLater(this::actualizarUI);
                }
                if (tiempoActual >= tiempoTotal) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tiempo completado");
                        alert.setHeaderText(null);
                        alert.setContentText("¡El tiempo ha finalizado!");
                        alert.showAndWait();
                        reiniciarUI();
                    });
                }
            }).start();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, introduce un número válido mayor que cero.");
            alert.showAndWait();
        }
    }

    private void cancelarContador() {
        contando = false;
        reiniciarUI();
    }

    private void actualizarUI() {
        int horas = tiempoActual / 3600;
        int minutos = (tiempoActual % 3600) / 60;
        int segundos = tiempoActual % 60;

        String tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        tiempoLabel.setText("Tiempo: " + tiempoFormateado);
    }

    private void reiniciarUI() {
        iniciarButton.setDisable(false);
        cancelarButton.setDisable(true);
        inputField.setDisable(false);
        tiempoLabel.setText("Tiempo: 00:00:00");
    }

    private void guardarTiempoPredefinido() {
        try (FileWriter writer = new FileWriter("tiempo_predefinido.txt")) {
            writer.write(inputField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tiempo guardado correctamente.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarTiempoPredefinido() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tiempo_predefinido.txt"))) {
            String tiempoGuardado = reader.readLine();
            if (tiempoGuardado != null) {
                inputField.setText(tiempoGuardado);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tiempo cargado correctamente.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aplicarTemaOscuro() {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("tema_oscuro.css").toExternalForm());
    }

    private void aplicarTemaClaro() {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("tema_claro.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

