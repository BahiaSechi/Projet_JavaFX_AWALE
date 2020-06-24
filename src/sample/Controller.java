package sample;

import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Allows to manage the main view of the game.
 *
 * Address :
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 * Note :
 * This file is owned by an ENSICAEN student.  No portion of this
 * document may be reproduced, copied  or revised without written
 * permission of the authors.
 *
 * @author BURON Manfred manfred.buron@ecole.ensicaen.fr
 * @author Bahia SECHI bahia.sechi@ecole.ensicaen.fr
 * @version 1.0
 */

public class Controller implements Initializable {

    public List<Integer> gameState;
    public List<Integer> gameStatePreviousPlay;
    public List<Integer> gameStateTampon;
    public int grainesJ1_value, grainesJ2_value;
    public int grainesJ1_value_previous, grainesJ2_value_previous;

    //Pour savoir qui joue : true est le J1, false est le J2;
    public boolean whoPlay, partieEnCours;
    String log1, log2, log3, log4, log5, log6;

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
    public CheckMenuItem musicCheck, boardChecked, hoverChecked, effectCheck, commentCheck;

    @FXML
    public RadioButton debutant, moyen;

    @FXML
    public TextField number1, number2, number3, number4, number5, number6, number7, number8, number9, number10, number11, number12;

    // Music variables
    public MediaPlayer mediaPlayer, effectPlayer, pavard, zinedine, airhorn;

    // create random object
    Random random = new Random();


    /**
     * Initializing the game, especially the board.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialisation des listes
        this.gameState = new ArrayList<>(12);
        this.gameStatePreviousPlay = new ArrayList<>(12);
        this.gameStateTampon = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            gameState.add(4);
            gameStatePreviousPlay.add(4);
            gameStateTampon.add(4);
        }

        //Initialisation du Journal
        logs.setEditable(false);
        logs.setFocusTraversable(false);

        //Selection du mode de jeu à l'initialisation
        debutant.setSelected(true);

        //Chargement des fichiers audios : musique, bruitage et commentaires
        String musicFile = "src/sound/winter.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

        String effectFile = "src/sound/marble.wav";
        Media sound2 = new Media(new File(effectFile).toURI().toString());
        effectPlayer = new MediaPlayer(sound2);

        String pavardFile = "src/sound/Pavard.mp3";
        Media sound3 = new Media(new File(pavardFile).toURI().toString());
        pavard = new MediaPlayer(sound3);

        String zinedineFile = "src/sound/Zinedine.mp3";
        Media sound5 = new Media(new File(zinedineFile).toURI().toString());
        zinedine = new MediaPlayer(sound5);

        String airhornFile = "src/sound/AirHorn.mp3";
        Media sound4 = new Media(new File(airhornFile).toURI().toString());
        airhorn = new MediaPlayer(sound4);

        //Chargement d'une nouvelle partie
        try {
            newGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // FUNCTIONS : MENU FICHIER

    /**
     * Create a new game.
     */
    public void newGame() throws IOException {
        //Nettoyage du journal
        clearLogs();
        addLogMessage("Nouvelle partie !");

        //Initialisation du nombre de graine
        for (int i = 0; i < 12; i++) {
            gameState.set(i, 4);
            gameStatePreviousPlay.set(i, 4);
            gameStateTampon.set(i, 4);
        }

        //On éteint les commentaires
        pavard.stop();
        zinedine.stop();
        airhorn.stop();

        //On set les scores à 0
        grainesJ1_value = 0;
        grainesJ2_value = 0;
        grainesJ1_value_previous = 0;
        grainesJ2_value_previous = 0;
        partieEnCours = true;

        //On détermine un premier joueur aléatoirement
        whoPlay = random.nextBoolean();
        if (whoPlay) {
            addLogMessage("Le joueur 1 commence.");
        } else {
            addLogMessage("Le joueur 2 commence.");
        }

        //On nettoie la mémoire du coup précédent et on met à jour la vue
        savePreviousPlay();
        updateView();
    }

    /**
     * Load a saved game.
     * @throws IOException if cannot load the game.
     */
    public void loadGame() throws IOException {
        //Selection du fichier
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open a HTML file");
        File file = chooser.showOpenDialog(new Stage());

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st = br.readLine();
        List<String> stringList = new ArrayList<>(Arrays.asList(st.split(",")));

        //Mise-à-jour des valeurs du plateau
        for (int i = 0; i < 12 ; i++) {
                gameState.set(i, Integer.parseInt(stringList.get(i)));
        }

        grainesJ1_value = Integer.parseInt(br.readLine());
        grainesJ2_value = Integer.parseInt(br.readLine());
        grainesJ1_value_previous = Integer.parseInt(br.readLine());
        grainesJ2_value_previous = Integer.parseInt(br.readLine());

        String st2 = br.readLine();
        List<String> stringList2 = new ArrayList<>(Arrays.asList(st2.split(",")));

        for (int i = 0; i < 12 ; i++) {
            gameStatePreviousPlay.set(i, Integer.parseInt(stringList2.get(i)));
        }

        updateView();
    }

    /**
     * Function to save the game.
     * @throws IOException if I/O exception occurred
     */
    public void saveGame() throws IOException {
        //Selection du fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName("nameYourFile");
        File savedFile = fileChooser.showSaveDialog(new Stage());

        //Ecriture de l'état du plateau, du coup précédent et des scores
        try (FileWriter writtenFile = new FileWriter(savedFile, false)) {
            for (Integer nb_seeds : gameState.subList(0,12)) {
                writtenFile.write(nb_seeds.toString());
                writtenFile.write(",");
            }
            writtenFile.write("\n");
            writtenFile.write(String.valueOf(grainesJ1_value));
            writtenFile.write("\n");
            writtenFile.write(String.valueOf(grainesJ2_value));
            writtenFile.write("\n");
            writtenFile.write(String.valueOf(grainesJ1_value_previous));
            writtenFile.write("\n");
            writtenFile.write(String.valueOf(grainesJ2_value_previous));
            writtenFile.write("\n");
            for (Integer nb_previous_seeds : gameStatePreviousPlay.subList(0,12)) {
                writtenFile.write(nb_previous_seeds.toString());
                writtenFile.write(",");
            }
        }
    }

    /**
     * If a player hits the button surrender, the other player immediately wins.
     * Then another game is loaded.
     */
    public void surrender() throws IOException {
        displayGagnant();
        partieEnCours = false;
        addLogMessage("");
        addLogMessage("");
        addLogMessage("Si vous souhaitez rejouer, lancez une nouvelle partie.");
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
        return totalGraines < 6 && (grainesJ1_value <= 24 && grainesJ2_value <= 24);
    }

    /**
     * When there is less than 10 marbles on the board, the player can ask to stop the game.
     * If the other player accepts, the two players share the remaining marbles.
     */
    public void stopGame() throws IOException {

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

                displayGagnant();
                partieEnCours = false;

                addLogMessage("");
                addLogMessage("");
                addLogMessage("Si vous souhaitez rejouer, lancez une nouvelle partie.");
            }
        }
    }

    /**
     * Cancel the previous move.
     */
    public void cancel() {
        for(int i = 0; i < 12; i++){
            gameState.set(i, gameStatePreviousPlay.get(i));
        }
        grainesJ1_value = grainesJ1_value_previous;
        grainesJ2_value = grainesJ2_value_previous;
        updateView();
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
    public void boardDisplay () {
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
     * Returns a boolean if the effect checkbox is ticked.
     * @return true if the effect checkbox is ticked.
     */
    public boolean isEffectTicked() {
        if (effectCheck.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Play a music when the checkbox is ticked.
     */
    public void musicPlay() {
        if (musicCheck.isSelected()) {
            mediaPlayer.play();
            mediaPlayer.setVolume(0.2);
        } else {
            mediaPlayer.stop();
        }
    }

    /**
     * Displays a tooltip when hovering an image.
     * @param mouseEvent detects if you entered the image.
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
            stage.getIcons().add(new Image("/img/icon.png"));
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
            srcImage = "img/bille_10.png";
        } else {
            srcImage = "img/bille_"+ nombreDeBilles +".png";
        }

        tempImage = new Image(srcImage);
        return tempImage;
    }

    /**
     * Update the view of the game.
     */
    public void updateView() {

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
        log3 = log4;
        log4 = log5;
        log5 = log6;
        log6 = message;
        logs.setText("> " + log1 + "\n" + "> " + log2 + "\n" + "> " + log3 + "\n" + "> " + log4 + "\n" + "> " + log5 + "\n" + "> " + log6);
    }

    /**
     * Clear the logs on the TextArea as well as the logs variables.
     */
    public void clearLogs () {
        log1 = "";
        log2 = "";
        log3 = "";
        log4 = "";
        log5 = "";
        log6 = "";
        logs.setText("");
    }

    /**
     * Distribue les billes d'une case sélectionnée vers les cases suivantes
     * @param caseNumber : correspond à la case joué
     * @return : l'indice de la dernière case atteinte en distribuant les billes
     */
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

    /**
     * Fonction similire à la précédente mais qui sert cette fois-ci à prévisualiser le coup du joueur.
     * Cette méthode est utile pour savoir si un coup peut potentiellement affamer son adversaire
     *
     * @param caseNumber correspond à la case joué.
     * @return caseTemp l'indice de la dernière case atteinte en distribuant les billes.
     */
    public int distribuerBilleTampon(int caseNumber){
        int caseTemp = caseNumber;
        int nombreBilles = gameStateTampon.get(caseNumber);
        gameStateTampon.set(caseNumber, 0);

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

    /**
     * Fonction qui permet de ramasser les billes dans les cases en ayant que 2 ou 3.
     * @param caseTemp : correspond à la case à partir de laquelle il faut ramasser les billes.
     * @return : Le nombre de billes ramassée par le coup.
     */
    public int ramasseBilles(int caseTemp) {
        int res = 0;

        if (whoPlay && caseTemp >= 0 && caseTemp <= 5) {
            while (caseTemp != 6) {
                if (gameState.get(caseTemp) == 2 || gameState.get(caseTemp) == 3) {
                    addLogMessage("Joueur 1 récupère " + gameState.get(caseTemp) + " billes dans une case ");
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
                    addLogMessage("Joueur 2 récupère " + gameState.get(caseTemp) + " billes dans une case ");
                    res += gameState.get(caseTemp);
                    gameState.set(caseTemp, 0);
                    caseTemp--;
                } else {
                    caseTemp = 5;
                }
            }
        }
        
        return res;
    }

    /**
     * Fonction similaire à la précédente mais qui sert encore une fois à constater les effets d'un coup.
     * Cette fonction sert à ne pas affamer son adversaire.
     * @param caseTemp Correspond à la case à partir de laquelle il faut ramasser les billes.
     * @return res Le nombre de billes ramassées par le coup.
     */
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

    /**
     * Fonction principale pour jouer
     * @param event : Nous permet de savoir quelle case est jouée
     * @throws InterruptedException if a thread is waiting, sleeping, or otherwise occupied
     */
    public void makeAMove(MouseEvent event) throws InterruptedException, IOException {
        //On récupère l'indice de la case jouée
        final Node source = (Node) event.getSource();
        String id = source.getId();
        int caseNumber = Integer.parseInt(id) - 1;

        //Initialisation de quelques variables.
        int nombreBilles = gameState.get(caseNumber);
        int caseTemp, newPoints;
        int billesRestanteAdvAvantCoup;

        //Joue un bruitage
        if (isEffectTicked()) {
            effectPlayer.play();
            effectPlayer.stop();
        }

        //On vérifie : Que la partie est en cours, que le joueur clique bien sur sa moitié du terrain et que la case n'est pas vide.
        if (!partieEnCours){
            sendAlertInfo("Vous devez relancer une partie pour jouer.", "Partie finie");
        } else if (whoPlay && (caseNumber >= 0 && caseNumber <= 5)) {
            sendAlertInfo("C'est le tour du joueur 1, pas le votre.", "Impossible !");

        } else if (!whoPlay &&(caseNumber >= 6 && caseNumber <= 11)) {
            sendAlertInfo("C'est le tour du joueur 2, pas le votre.", "Impossible !");

        } else if (nombreBilles == 0) {
            sendAlertInfo("Vous devez jouer une case contenant des graines.", "Impossible !");

        } else {
            //On regarde d'abord si il existe bien un coup permettant de nourrir son adversaire dans le cas ou ce dernier n'a plus de bille
            if(nourrirAdverPossible()){
                //On calcul à l'avance l'impact du coup joué
                chargerTampon();
                billesRestanteAdvAvantCoup = billesRestanteAdversaire();
                caseTemp = distribuerBilleTampon(caseNumber);
                newPoints = ramasseBillesTampon(caseTemp);

                //On s'assure que le joueur nourrit son adversaire si besoin ou ne l'affame pas
                if(billesRestanteAdvAvantCoup == 0 && billesRestanteAdversaire() == 0){
                    addLogMessage("Vous devez nourrir votre adversaire !");

                }else if(billesRestanteAdvAvantCoup == newPoints -  nombreBilles && newPoints != 0){
                    addLogMessage("Coup effectué mais vous ne ramassez aucune bille pour ne pas affamer votre adversaire.");
                    savePreviousPlay();
                    caseTemp = distribuerBille(caseNumber);
                    whoPlay = !whoPlay;

                }else {
                    //Si le coup est jouable, on le joue
                    savePreviousPlay();

                    caseTemp = distribuerBille(caseNumber);
                    newPoints = ramasseBilles(caseTemp);

                    //Mise à jour des scores
                    if (whoPlay) {
                        grainesJ1_value_previous = grainesJ1_value;
                        grainesJ1_value += newPoints;
                    } else {
                        grainesJ2_value_previous = grainesJ2_value;
                        grainesJ2_value += newPoints;
                    }

                    jouerUnCommentaire(newPoints);

                    //On regarde si la partie est finie ou si on la continue
                    if(finDePartie()){
                        displayGagnant();
                    }else{
                        whoPlay = !whoPlay;
                    }
                }
            }
            //Si la partie est finie, on affiche le gagnant
            if(finDePartie()){
                displayGagnant();
            }

        }
        updateView();
    }

    /**
     * Fonction permettant de jouer un commentaire audio dans certains cas déterminé à partir des scores
     * @param pointFait : Le nombre de point marqué par un coup
     */
    public void jouerUnCommentaire(int pointFait){


        if(grainesJ1_value > 10 && grainesJ1_value == grainesJ2_value){
            pavard.play();
        }else if(pointFait == 0){
            if(whoPlay && grainesJ2_value - grainesJ1_value > 15){
                zinedine.play();
            }else if(!whoPlay && grainesJ1_value - grainesJ2_value > 15){
                zinedine.play();
            }
        }
    }

    /**
     * Fonction qui détermine s'il existe un coup permettant de nourrir son adversaire quand ce dernier n'a plus de bille
     * @return true si on peut nourrir l'adversaire.
     */
    public boolean nourrirAdverPossible(){
        boolean res = true;

        if(billesRestanteAdversaire() == 0){
            res = false;
            if(whoPlay){
                for(int i = 6; i < 12; i++){
                    if(gameState.get(i) > 11-i){
                        System.out.println(i);
                        res = true;
                    }
                }
            }else{
                for(int i = 0; i < 6; i++){
                    if(gameState.get(i) > i){
                        res = true;
                    }
                }
            }
        }

        return res;
    }

    /**
     * Fonction qui retourne le nombre de billes restantes à l'adversaire.
     * @return nombre de billes restantes à l'adversaire.
     */
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

    /**
     * Fonction pour afficher le gagnant de la partie.
     */
    public void displayGagnant() throws IOException {
        String gagnant = "Joueur 1";

        if (egalite()) {
            sendAlertInfo("La partie est finie ! " +
                    " Match nul !"
                    , "Fin de la partie.");
        }else {
            if(grainesJ2_value > grainesJ1_value){
                gagnant = "Joueur 2";
            }

            sendAlertInfo("La partie est finie ! " +
                    " Le " + gagnant + " gagne !"
                    , "Fin de la partie.");
        }

        if (grainesJ1_value >= grainesJ2_value){
            enregistrerTop100(grainesJ1_value);
        }else{
            enregistrerTop100(grainesJ2_value);
        }

        afficheTop100();
        airhorn.play();
    }

    /**
     * Fonction qui détermine si la partie est finie selon différent critères.
     * @return true si la partie est finie.
     */
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
        }

        if (egalite()) {
            partieEnCours = false;
            res = true;

        } else if(!nourrirAdverPossible()){
            partieEnCours = false;
            res = true;


            if (whoPlay) {
                for(int i = 6; i < 12; i++){
                    grainesJ1_value += gameState.get(i);
                    gameState.set(i, 0);
                }
            } else {
                for(int i = 0; i < 6; i++){
                    grainesJ2_value += gameState.get(i);
                    gameState.set(i, 0);
                }
            }
            updateView();
        }
        return res;
    }

    /**
     * Fonction qui détermine si la partie est finie selon la règle pour joueur débutant.
     * @return true si la partie est finie ; difficulte debutant.
     */
    public boolean finPartieDebutant(){
        boolean res = false;
        int resteJ1 = getNbBillePlateauJ1();
        int resteJ2 = getNbBillePlateauJ2();

        if(resteJ1 + resteJ2 <= 6 && !nourrirAdverPossible()){
            res = true;
        }

        return res;
    }

    /**
     * Fonction qui détermine si la partie est finie selon la règle pour joueur moyen
     * @return true si la partie est finie ; difficulte moyen.
     */
    public boolean finPartieMoyen(){
        boolean res = false;

        if(grainesJ1_value >=25 || grainesJ2_value >= 25){
            res = true;
        }
        return res;
    }

    /**
     * Fonction qui retourne le nombre de billes sur le plateau du joueur 1
     * @return nombre de billes du plateau du J1.
     */
    public int getNbBillePlateauJ1(){
        int res = 0;
        for (int i = 0; i < 6; i ++) {
            res += gameState.get(i);
        }
        return res;
    }

    /**
     * Fonction qui retourne le nombre de billes sur le plateau du joueur 2
     * @return nombre de billes du plateau du J2.
     */
    public int getNbBillePlateauJ2(){
        int res = 0;

        for(int i = 6; i < 12; i ++){
            res += gameState.get(i);
        }

        return res;
    }

    /**
     * Fonction qui enregistre l'état du plateau actuel afin de pouvoir faire "annuler coup" plus tard, si besoin
     */
    public void savePreviousPlay(){
        for(int i = 0; i < 12; i ++){
            gameStatePreviousPlay.set(i, gameState.get(i));
        }
    }

    /**
     * Fonction qui charge dans la liste tampon l'état du plateau afin de faire des calculs à l'avance sur un coup joué
     */
    public void chargerTampon(){
        for(int i = 0; i < 12; i ++){
            gameStateTampon.set(i, gameState.get(i));
        }
    }

    /**
     * Fonction qui regarde si le joueur à sa place dans le top 100
     */
    public boolean nouveauRecord(int score) throws IOException {
        boolean res = false;

        File scoreFile = new File("scoreTop100.txt");

        BufferedReader br = new BufferedReader(new FileReader(scoreFile));
        String st;
        List<String> stringList;

        for (int i = 0; i < 100 ; i ++) {
            st = br.readLine();
            stringList = new ArrayList<>(Arrays.asList(st.split(",")));
            if(Integer.parseInt(stringList.get(1)) < score){
                res = true;
            }
        }

        return res;
    }

    /**
     * Fonction qui enregistre le joueur si il le mérite
     */
    public void enregistrerTop100(int score) throws IOException {
        String nomGagnant = "Jean";
        boolean enregistre = false;
        List<String> top100 = new ArrayList<String>(200);

        //On récupère le nom du gagnant et on l'affiche
        TextInputDialog dialog = new TextInputDialog("Smith");
        dialog.setTitle("Victoire !");
        dialog.setHeaderText("Votre entrée dans le top 100 (peut-être)");
        dialog.setContentText("Veuillez indiquer votre nom :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            nomGagnant = result.get();
        }

        //On regarde la liste des scores
        File scoreFile = new File("src/scoreTop100.txt");

        BufferedReader br = new BufferedReader(new FileReader(scoreFile));
        String st;
        List<String> stringList;

        for (int i = 0; i < 100 ; i ++) {
            st = br.readLine();
            stringList = new ArrayList<>(Arrays.asList(st.split(",")));

            if(Integer.parseInt(stringList.get(1)) < score && !enregistre){
                top100.add(nomGagnant);
                top100.add(Integer.toString(score));
                i++;
                enregistre = true;
            }

            top100.add(stringList.get(0));
            top100.add(stringList.get(1));
        }

        //Ecriture des scores à jour dans le fichier
        try (FileWriter writtenFile = new FileWriter(scoreFile, false)) {
            for(int i = 0; i < 200; i++){
                writtenFile.write(top100.get(i));
                writtenFile.write(",");
                if(i%2 == 1){
                    writtenFile.write("\n");
                }
            }
        }

    }

    /**
     * Fonction qui affiche le top 100 des joueurs
     */
    public void afficheTop100() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./Top100View.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("AWALE - Top 100");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.getIcons().add(new Image("img/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
