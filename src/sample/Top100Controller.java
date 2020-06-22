package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * Display Top 100 players.
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

public class Top100Controller implements Initializable {

    @FXML
    TextArea listArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listArea.setEditable(false);

        File scoreFile = new File("./src/scoreTop100.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(scoreFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String st = null;
        List<String> stringList;

        for (int i = 0; i < 100 ; i ++) {
            try {
                st = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringList = new ArrayList<>(Arrays.asList(st.split(",")));

            listArea.appendText((i + 1) + ". " + stringList.get(0) + " : " + stringList.get(1) + " graines\n");
            //System.out.println(i + " : " + stringList.get(0) + " " + stringList.get(1) + " graines");
        }

    }
}