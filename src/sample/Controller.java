package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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

public class Controller implements Initializable {

    public boolean isGameLaunched;
    public List<Integer> gameState;
    public List<Integer> gameStatePreviousPlay;
    public int grainesJ1_value, grainesJ2_value;

    @FXML
    public Label grainesJ1, grainesJ2;

    @FXML
    public ImageView case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12;

    @FXML
    public AnchorPane plateau_de_jeu;

    /**
     * Initializing the game, especially the board.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameState = new ArrayList<>(12);
        this.gameStatePreviousPlay = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            gameState.add(4);
        }

        grainesJ1_value = 0;
        grainesJ2_value = 0;

        updateView();
    }

    // FUNCTIONS : MENU FICHIER

    /**
     * Create a new game.
     *
     */
    public void newGame() {
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

    // FUNCTIONS : MENU REGLES

    /**
     * Displays the rules of the game in a new window.
     *
     * @throws IOException Throws an exception if opening a new windows is not possible.
     */
    public void rules() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./RulesView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("AWALE - Règles");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // FUNCTIONS : MENU SYSTEME

    /**
     * Information about the project and its authors.
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

    /**
     * Update the image displaying the number of marbles (billes).
     *
     * @param nombreDeBilles The number of marbles in the hole.
     * @return tempImage The correct image to display.
     */
    public Image getNewImage(int nombreDeBilles){

        String srcImage;
        Image tempImage;

        if (nombreDeBilles >= 10) {
            srcImage = "@./../img/bille_10.png";
        } else {
            srcImage = "@./../img/bille_"+ nombreDeBilles +".png";
        }

        tempImage = new Image(srcImage);
        return tempImage;
    }

    /**
     * Update the view of the game.
     */
    public void updateView() {
        gameStatePreviousPlay = gameState;

        case1.setImage(getNewImage(gameState.get(0)));
        case2.setImage(getNewImage(gameState.get(1)));
        case3.setImage(getNewImage(gameState.get(2)));
        case4.setImage(getNewImage(gameState.get(3)));
        case5.setImage(getNewImage(gameState.get(4)));
        case6.setImage(getNewImage(gameState.get(5)));

        case7.setImage(getNewImage(gameState.get(6)));
        case8.setImage(getNewImage(gameState.get(7)));
        case9.setImage(getNewImage(gameState.get(8)));
        case10.setImage(getNewImage(gameState.get(9)));
        case11.setImage(getNewImage(gameState.get(10)));
        case12.setImage(getNewImage(gameState.get(11)));

        grainesJ1.setText("Graines : " + grainesJ1_value);
        grainesJ2.setText("Graines : " + grainesJ2_value);
    }


}
