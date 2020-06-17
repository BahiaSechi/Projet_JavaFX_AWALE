package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public boolean whoPlay, partieEnCours;
    String log1, log2, log3;

    Tooltip mousePositionToolTip = new Tooltip("");

    @FXML
    public Label grainesJ1, grainesJ2;

    @FXML
    public ImageView case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12;

    @FXML
    public AnchorPane plateau_de_jeu;

    @FXML
    public TextArea logs;

    @FXML
    public CheckMenuItem musicCheck, boardChecked, hoverChecked;

    @FXML
    public RadioButton debutant, moyen;

    @FXML
    public TextField number1, number2, number3, number4, number5, number6, number7, number8, number9, number10, number11, number12;

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
        partieEnCours = true;

        clearLogs();

        logs.setEditable(false);
        playerRound();
        logs.setFocusTraversable(false);

        debutant.setSelected(true);

        updateView();
    }

    // FUNCTIONS : MENU FICHIER

    /**
     * Create a new game.
     */
    public void newGame() {
        for (int i = 0; i < 12; i++) {
            gameState.set(i, 4);
        }

        grainesJ1_value = 0;
        grainesJ2_value = 0;
        partieEnCours = true;

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
        sendAlertInfo("Abandon du joueur X. Joueur X a gagné !", "Résultat de la partie");
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
     *
     *          * Règle 9 :
     *          * boolean whoPlayed
     *          * true : joueur 1
     *          * false : joueur 2
     *          * 48 - scoreJoueur1 - scoreJoueur2;
     *          *
     *          * Partage en deux
     *
     * TODO function pas finie
     */
    public void stopGame(ActionEvent actionEvent) {

        int totalGraines = 48 - grainesJ1_value - grainesJ2_value;

        if (totalGraines <= 10) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Arrêt partie");
            alert.setContentText("Voulez-vous arrêter la partie ?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                 grainesJ1_value += totalGraines / 2;
                 grainesJ2_value += totalGraines / 2;
                 updateView();

                // TODO Appeler la fonction qui donne le gagnant en fonction de la difficulte choisie

                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Nouvelle partie");
                alert2.setContentText("Voulez-vous recommencer une partie ?");

                Optional<ButtonType> option2 = alert2.showAndWait();

                if (option2.get() == ButtonType.OK) {
                    newGame();
                }
            }
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

    // FUNCTIONS : MENU OPTIONS

    /**
     * If the checkbox is checked, it displays several TextFields where the player can see the number of seeds in each hole.
     */
    public void boardDisplay() {
        boolean visible = boardChecked.isSelected();
        number1.setVisible(visible);
        number2.setVisible(visible);
        number3.setVisible(visible);
        number4.setVisible(visible);
        number5.setVisible(visible);
        number6.setVisible(visible);
        number7.setVisible(visible);
        number8.setVisible(visible);
        number9.setVisible(visible);
        number10.setVisible(visible);
        number11.setVisible(visible);
        number12.setVisible(visible);
    }

    /**
     * Play a music when the checkbox is ticked.
     * TODO A faire fonctionner
     */
    public void musicPlay() {
        String musicFile = "src\\test.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if (musicCheck.isSelected()) {
            mediaPlayer.play();
            mediaPlayer.setVolume(1.0);
        } else {
            mediaPlayer.pause();
        }
    }

    /**
     * Displays a tooltip when hovering an image.
     * @param mouseEvent
     */
    public void hoverImage(MouseEvent mouseEvent) {
        final Node source = (Node) mouseEvent.getSource();
        String id = source.getId();
        int caseNumber = Integer.parseInt(id) - 1;
        boolean visible = hoverChecked.isSelected();
        if (visible) {
            mousePositionToolTip.setText(gameState.get(caseNumber).toString());
            mousePositionToolTip.show(source, mouseEvent.getScreenX() + 20, mouseEvent.getScreenY());
        }
    }

    /**
     * Hides the little tooltip when not hovering an image.
     */
    public void notHoverImage() {
        mousePositionToolTip.hide();
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

        number1.setText(gameState.get(0).toString());
        number2.setText(gameState.get(1).toString());
        number3.setText(gameState.get(2).toString());
        number4.setText(gameState.get(3).toString());
        number5.setText(gameState.get(4).toString());
        number6.setText(gameState.get(5).toString());
        number7.setText(gameState.get(6).toString());
        number8.setText(gameState.get(7).toString());
        number9.setText(gameState.get(8).toString());
        number10.setText(gameState.get(9).toString());
        number11.setText(gameState.get(10).toString());
        number12.setText(gameState.get(11).toString());

        grainesJ1.setText("Graines : " + grainesJ1_value);
        grainesJ2.setText("Graines : " + grainesJ2_value);
    }

    /**
     * Displays an information pop-up.
     *
     * @param message The message contained in the pop-up.
     * @param title The title of the window.
     */
    public void sendAlertInfo(String message, String title) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setContentText(message);
        about.setTitle(title);
        about.show();
    }

    /**
     * Add a new message to the two previous logs displayed in the TextArea.
     *
     * @param message The new message to display.
     */
    public void addLogMessage (String message){
        log1 = log2;
        log2 = log3;
        log3 = message;
        logs.setText(log1 + "\n" + log2 + "\n" + log3);
    }

    /**
     * Clear the logs on the TextArea as well as the logs variables.
     */
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
        } else {
            addLogMessage("Pas de point ce tour ci");
        }
        return res;
    }

    public void makeAMove(MouseEvent event){
        final Node source = (Node) event.getSource();
        String id = source.getId();
        int caseNumber = Integer.parseInt(id) - 1;

        int nombreBilles = gameState.get(caseNumber);
        int caseTemp, newPoints;

        if (!partieEnCours){
            sendAlertInfo("Vous devez relancer une partie pour jouer", "Partie finie");

        } else if (whoPlay && (caseNumber >= 0 && caseNumber <= 5)) {
            sendAlertInfo("C'est le tour du joueur 1, pas le votre", "Impossible !");

        } else if (!whoPlay &&(caseNumber >= 6 && caseNumber <= 11)) {
            sendAlertInfo("C'est le tour du joueur 2, pas le votre", "Impossible !");

        } else if (nombreBilles == 0) {
            sendAlertInfo("Vous devez jouer une case ", "Impossible !");

        } else {
            caseTemp = distribuerBille(caseNumber);
            newPoints = ramasseBilles(caseTemp);

            if (whoPlay) {
                grainesJ1_value += newPoints;
            } else {
                grainesJ2_value += newPoints;
            }

            if(finDePartie()){
                displayGagnant();
            }else{
                whoPlay = !whoPlay;
                playerRound();
            }

        }
        updateView();
    }

    public void displayGagnant(){
        String gagnant = "Joueur 1";

        if(egalite()){
            sendAlertInfo("La partie est finie ! " +
                    " MATCH NUL"
                    , "FIN");
        }else{
            if(grainesJ2_value > grainesJ1_value){
                gagnant = "Joueur 2";
            }

            sendAlertInfo("La partie est finie ! " +
                    " Le " + gagnant + " gagne !"
                    , "FIN");
        }
    }

    public boolean finDePartie(){
        boolean res = false;

        if(debutant.isSelected()){
            if(finPartieDebutant()){
                partieEnCours = false;
                res = true;
            }
        }else if(moyen.isSelected()){
            if(finPartieMoyen()){
                partieEnCours = false;
                res = true;
            }
        }else if(egalite()){
            partieEnCours = false;
            res = true;
        }
        return res;
    }

    public boolean finPartieDebutant(){
        boolean res = false;
        int resteJ1 = getNbBillePlateauJ1();
        int resteJ2 = getNbBillePlateauJ2();

        if(resteJ1 + resteJ2 <= 6 && (resteJ1 == 0) || (resteJ2 == 0)){
            res = true;
        }

        return res;
    }

    public boolean finPartieMoyen(){
        boolean res = false;

        if(grainesJ1_value >=25 || grainesJ2_value >= 25){
            res = true;
        }

        return res;
    }

    public int getNbBillePlateauJ1(){
        int res = 0;

        for(int i = 0; i < 6; i ++){
            res += gameState.get(i);
        }

        return res;
    }

    public int getNbBillePlateauJ2(){
        int res = 0;

        for(int i = 6; i < 12; i ++){
            res += gameState.get(i);
        }

        return res;
    }

}
