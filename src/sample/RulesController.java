package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * Allows to manage the rules view of the game.
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

public class RulesController implements Initializable {

    @FXML
    public TextArea rulesArea;

    /**
     * Initializing the rules layout.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rulesArea.setEditable(false);
        rulesArea.setText("Règle 1 : Seulement deux joueurs peuvent s'affronter.\n" +
                "Règle 2 : On répartit quarante-huit graines dans les douze trous à raison de quatre graines par trou.\n" +
                "Règle 3 : Chaque joueur joue à tour de rôle, celui qui joue en premier est tiré au hasard. \n Le joueur va " +
                "prendre l'ensemble des graines dans l'un des trous de son territoire et les distribuer, un par trou,\n dans le " +
                "sens inverse des aiguilles d'une montre.\n" +
                "Règle 4 : Si sa dernière graine tombe dans un trou du camp adverse et qu'il y a maintenant deux ou trois\n" +
                "graines dans ce trou, le joueur récupère ces deux ou trois graines et les met de côté. \nEnsuite il regarde la " +
                "case précédente : si elle est dans le camp adverse et contient deux ou trois graines, il récupère ces graines, " +
                "\net ainsi de suite jusqu'à ce qu'il arrive à son camp ou jusqu'à ce qu'il y ait un nombre de graines différent " +
                "de deux ou trois.\n" +
                "Règle 5 : On ne saute pas de case lorsqu'on égrène sauf lorsqu'on a douze graines ou plus, c'est-à-dire " +
                "qu'on fait un tour complet : \non ne remplit pas la case dans laquelle on vient de prendre les graines.\n" +
                "Règle 6 : Il faut « nourrir » l'adversaire, c'est-à-dire que, quand celui-ci n'a plus de graines, \nil faut " +
                "absolument jouer un coup qui lui permette de rejouer ensuite. \nSi ce n'est pas possible, la partie s'arrête et " +
                "le joueur qui allait jouer capture les graines restantes.\n" +
                "Règle 7 : Si un coup devait prendre toutes les graines adverses, alors le coup peut être joué, \nmais aucune " +
                "capture n'est faite : il ne faut pas « affamer » l'adversaire.\n" +
                "Règle 8 : La partie s'arrête quand :\n" +
                "- Un des joueurs a capturé au moins 25 graines, et est désigné gagnant (mode moyen)\n" +
                "- Ou qu'il ne reste qu'au plus 6 graines en jeu et que l’un des joueurs n’a plus de graines dans son " +
                "camp \net que son adversaire n’a pas eu l’opportunité de lui en redonner (mode débutant)\n" +
                "Règle 9 : Quand il ne reste qu'au plus 10 graines sur le plateau, le joueur qui a la main peut proposer " +
                "l'abandon de la partie. \nSi il est accepté les deux joueurs se partagent les graines restantes. " +
                "\nSi le total des graines du plateau est inférieur à 6, sans qu'aucun des joueurs n'a un total de graines " +
                "supérieur à 24. La partie est nulle.");
    }
}
