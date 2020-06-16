package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Address :
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 * Note :
 * This file is owned by an ENSICAEN student.  No portion of this
 * document may be reproduced, copied  or revised without written
 * permission of the authors.
 *
 * @author BURON Manfred <manfred.buron@ecole.ensicaen.fr>
 * @author Bahia SECHI <bahia.sechi@ecole.ensicaen.fr>
 * @version 1.0
 */

public class Controller implements Initializable{

    public boolean isGameLaunched;
    public List<Integer> gameState;
    public List<Integer> gameStatePreviousPlay;
    public int grainesJ1_value, grainesJ2_value;

    @FXML
    public Label grainesJ1, grainesJ2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameState = new ArrayList<>(14);
        this.gameStatePreviousPlay = new ArrayList<>(14);

        for(int i = 0; i < 12; i++){
            gameState.add(4);
        }
        gameState.add(0);
        gameState.add(0);

        gameStatePreviousPlay = gameState;
    }

    // FUNCTIONS : MENU FICHIER

    /**
     * Create a new game.
     *
     */
    public void newGame(ActionEvent actionEvent) {
        for (int i = 0; i < 12; i++) {
            gameState.set(i, 4);
        }

        grainesJ1_value = 0;
        grainesJ2_value = 0;

        gameStatePreviousPlay = gameState;

        this.updateView();
    }

    /**
     *
     * @param actionEvent
     */
    public void loadGame(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent
     */
    public void saveGame(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent
     */
    public void surrender(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent
     */
    public void stopGame(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
    }

    /**
     * Quits the game.
     */
    public void quit() {
        Platform.exit();
    }

    // FUNCTIONS : MENU SYSTEME

    /**
     * Information about the projects.
     */
    public void about() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setContentText("This project is part of the programming course (ENSICAEN - Engineering School). \n" +
                "Authors : BURON Manfred & SECHI Bahia \n" +
                "Date : June 2020 \n" +
                "Version : 1.0");
        about.setTitle("AWALE - About");
        about.show();
    }

    // FUNCTIONS : UTILITARIES

    public void updateView() {
        grainesJ1.setText("Graines : " + grainesJ1_value);
        grainesJ2.setText("Graines : " + grainesJ2_value);
    }
}
