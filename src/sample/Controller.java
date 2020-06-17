package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
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
    public List<Integer> gameStateTampon;
    public int grainesJ1_value, grainesJ2_value;
    public int modeDeJeu; //0 = débutant, 1 = moyen

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

    @FXML
    public CheckMenuItem musicCheck;

    @FXML
    public RadioButton debutant, moyen;

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
        this.gameStateTampon = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            gameState.add(2);
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

    public int distribuerBilleTampon(int caseNumber){
        int caseTemp = caseNumber;
        int nombreBilles = gameStateTampon.get(caseNumber);
        System.out.println("pas tampon : " + gameState.get(caseNumber));
        System.out.println("tampon : " + gameStateTampon.get(caseNumber));
        gameStateTampon.set(caseNumber, 0);
        System.out.println("pas tampon : " + gameState.get(caseNumber));
        System.out.println("tampon : " + gameStateTampon.get(caseNumber));

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

            gameStateTampon.set(caseTemp, gameStateTampon.get(caseTemp)+1);

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

    public int ramasseBillesTampon(int caseTemp) {
        int res = 0;

        if (whoPlay && caseTemp >= 0 && caseTemp <= 5) {
            while (caseTemp != 6) {
                if (gameStateTampon.get(caseTemp) == 2 || gameStateTampon.get(caseTemp) == 3) {
                    res += gameStateTampon.get(caseTemp);
                    gameStateTampon.set(caseTemp, 0);
                    caseTemp++;
                } else {
                    caseTemp = 6;
                }
            }

        } else if (!whoPlay && caseTemp >= 6 && caseTemp <= 11) {
            while (caseTemp != 5) {
                if (gameStateTampon.get(caseTemp) == 2 || gameStateTampon.get(caseTemp) == 3) {
                    res += gameStateTampon.get(caseTemp);
                    gameStateTampon.set(caseTemp, 0);
                    caseTemp--;
                } else {
                    caseTemp = 5;
                }
            }

        }

        return res;
    }

    public void makeAMove(MouseEvent event){
        final Node source = (Node) event.getSource();
        String id = source.getId();
        int caseNumber = Integer.parseInt(id) - 1;

        int nombreBilles = gameState.get(caseNumber);
        int caseTemp, newPoints;
        int billesRestanteAdvAvantCoup;

        if (whoPlay && (caseNumber >= 0 && caseNumber <= 5)) {
            sendAlert("C'est le tour du joueur 1, pas le votre", "Impossible !");

        } else if (!whoPlay &&(caseNumber >= 6 && caseNumber <= 11)) {
            sendAlert("C'est le tour du joueur 2, pas le votre", "Impossible !");


        }else if(nombreBilles == 0){
            sendAlert("Vous devez jouer une case avec des billes", "Impossible !");

        }else{
            gameStateTampon = gameState;
            billesRestanteAdvAvantCoup = billesRestanteAdversaire();
            caseTemp = distribuerBilleTampon(caseNumber);
            System.out.println("coucou 0 bis: " + gameState.get(caseNumber));
            newPoints = ramasseBillesTampon(caseTemp);

            System.out.println("coucou 0: " + newPoints + " ; " + caseTemp);
            System.out.println("coucou 0 bis: " + gameState.get(caseNumber));

            if(billesRestanteAdvAvantCoup == 0 && billesRestanteAdversaire() == 0){
                addLogMessage("Vous devez nourrir votre adversaire !");

            }else if(billesRestanteAdvAvantCoup == newPoints && newPoints != 0){
                addLogMessage("Coup effectué mais vous ne ramassez aucune bille pour ne pas affamer votre adversaire");
                caseTemp = distribuerBille(caseNumber);
                whoPlay = !whoPlay;

            }else{

                System.out.println("coucou 1 : " + caseNumber + " ; " + caseTemp);
                System.out.println("coucou 1 bis: " + gameState.get(caseNumber));
                caseTemp = distribuerBille(caseNumber);
                System.out.println("coucou 2 : " + caseNumber + " ; " + caseTemp);
                newPoints = ramasseBilles(caseTemp);

                System.out.println("coucou 3 : " + newPoints + " ; " + caseTemp);

                if(whoPlay){
                    grainesJ1_value += newPoints;
                }else{
                    grainesJ2_value += newPoints;
                }
                whoPlay = !whoPlay;
            }
        }
        updateView();
        playerRound();
    }


    public int billesRestanteAdversaire(){
        int res = 0;
        if(whoPlay){
            for(int i = 0; i < 6; i++){
                res += gameStateTampon.get(i);
            }
        }else{
            for(int i = 6; i < 12; i++){
                res += gameStateTampon.get(i);
            }
        }

        return res;
    }

    public boolean finPartieDebutant(){
        boolean res = false;




        return res;
    }

    public boolean finPartieMoyen(){
        boolean res = false;

        if(grainesJ1_value >=25 || grainesJ2_value >= 25){
            res = true;
        }

        return res;
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
}
