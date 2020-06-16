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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
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

    //Pour savoir qui joue : true est le J1, false est le J2;
    public boolean whoPlay;
    String log1, log2, log3;


    @FXML
    public Label grainesJ1, grainesJ2;

    @FXML
    public ImageView case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12;

    @FXML
    public AnchorPane plateau_de_jeu;

    @FXML
    public TextArea logs;

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
        whoPlay = true;

        clearLogs();

        logs.setEditable(false);
        playerRound();
        logs.setFocusTraversable(false);

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

        clearLogs();
        this.updateView();
        addLogMessage("Nouvelle partie !");
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
     * If a player hits the button surrender, the other player immediately wins.
     * Then another game is loaded.
     */
    public void surrender() {
        // Message dans les logs ou popup
        sendAlert("Abandon du joueur X. Joueur X a gagné !", "Résultat de la partie");
        newGame();
    }

    /**
     *
     * Si le total des graines du plateau est inférieur à 6, sans qu'aucun des joueurs n'a un total de graines
     * supérieur à 24. La partie est nulle.
     *
     * @return A boolean ; if it's true, there is an equality between the two players.
     */
    public boolean egalite() {
        int totalGraines = 48 - grainesJ1_value - grainesJ2_value;
        return totalGraines < 6 && (grainesJ1_value <= 24 || grainesJ2_value <= 24);
    }

    /**
     * Quand il ne reste qu'au plus 10 graines sur le plateau, le joueur qui a la main peut proposer
     * l'abandon de la partie. S'il est accepté, les deux joueurs se partagent les graines restantes.
     * TODO function pas finie
     */
    public void stopGame(ActionEvent actionEvent) {
        /**
         * Règle 9 :
         * boolean whoPlayed
         * true : joueur 1
         * false : joueur 2
         * 48 - scoreJoueur1 - scoreJoueur2;
         *
         * Partage en deux
         */
        int totalGraines = 48 - grainesJ1_value - grainesJ2_value;

        if (totalGraines <= 10) {
            //TODO LES CONDITIONS D'ARRET ET ACCEPTATION
            // Si le joueur accepte alors :
            grainesJ1_value += totalGraines/2;
            grainesJ2_value += totalGraines/2;
        }
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
            stage.setResizable(false);
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

    public void sendAlert (String message, String title) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setContentText(message);
        about.setTitle(title);
        about.show();
    }

    /**
     *
     * @param message
     */
    public void addLogMessage (String message){
        log1 = log2;
        log2 = log3;
        log3 = message;
        logs.setText(log1 + "\n" + log2 + "\n" + log3);
    }

    public void clearLogs () {
        log1 = "";
        log2 = "";
        log3 = "";
        logs.setText("");
    }

    /**
     * Displays on the log field text who has to play.
     */
    private void playerRound() {
        if (whoPlay) {
            addLogMessage("C'est le tour du Joueur 1");
        } else {
            addLogMessage("C'est le tour du Joueur 2");
        }
    }

    public int distribuerBille(int caseNumber){
        int caseTemp = caseNumber;
        int nombreBilles = gameState.get(caseNumber);
        gameState.set(caseTemp, 0);

        while(nombreBilles > 0){
            if(caseTemp >= 6 && caseTemp < 11){
                caseTemp ++;
            }else if(caseTemp == 11){
                caseTemp = 5;
            }else if(caseTemp > 0 && caseTemp <= 5){
                caseTemp --;
            }else if(caseTemp == 0){
                caseTemp = 6;
            }else{
                System.out.println("Erreur distribuerBilles");
            }

            gameState.set(caseTemp, gameState.get(caseTemp)+1);

            nombreBilles --;
        }

        return caseTemp;
    }

    public int ramasseBilles(int caseTemp) {
        int res = 0;

        if (whoPlay && caseTemp >= 0 && caseTemp <= 5) {
            while (caseTemp != 6) {
                if (gameState.get(caseTemp) == 2 || gameState.get(caseTemp) == 3) {
                    addLogMessage("Joueur 1 récupère " + gameState.get(caseTemp) + " billes à la case " + (caseTemp+1));
                    res += gameState.get(caseTemp);
                    gameState.set(caseTemp, 0);
                    caseTemp++;
                } else {
                    caseTemp = 6;
                }
            }

        } else if (!whoPlay && caseTemp >= 6 && caseTemp <= 11) {
            while (caseTemp != 5) {
                if (gameState.get(caseTemp) == 2 || gameState.get(caseTemp) == 3) {
                    addLogMessage("Joueur 2 récupère " + gameState.get(caseTemp) + " billes à la case " + (caseTemp+1));
                    res += gameState.get(caseTemp);
                    gameState.set(caseTemp, 0);
                    caseTemp--;
                } else {
                    caseTemp = 5;
                }
            }

        }else{
            addLogMessage("Pas de point ce tour ci");
        }

        return res;
    }

    public void makeAMove(MouseEvent event){
        final Node source = (Node) event.getSource();
        String id = source.getId();
        int caseNumber = Integer.parseInt(id) - 1;

        int nombreBilles = gameState.get(caseNumber);
        int caseTemp, newPoints = 0;

        if(whoPlay && (caseNumber >= 0 && caseNumber <= 5)){
            sendAlert("C'est le tour du joueur 1, pas le votre", "Impossible !");

        }else if(!whoPlay &&(caseNumber >= 6 && caseNumber <= 11)){
            sendAlert("C'est le tour du joueur 2, pas le votre", "Impossible !");

        }else if(nombreBilles == 0){
            sendAlert("Vous devez jouer une case ", "Impossible !");

        }else{
            caseTemp = distribuerBille(caseNumber);
            newPoints = ramasseBilles(caseTemp);

            if(whoPlay){
                grainesJ1_value += newPoints;
            }else{
                grainesJ2_value += newPoints;
            }

            whoPlay = !whoPlay;
        }

        updateView();
        playerRound();
    }
}
