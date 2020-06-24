# **Projet d’Awalé** [READ ME WIP - English coming when I have time]


## **2019-2020                                    ENSICAEN                                    S. Bahia B. Manfred**



---


**Manuel de déploiement**

Pour jouer à notre jeu d’Awalé, vous pouvez simplement lancer l’exécutable fourni dans le rendu de cette manière :


```
> java -jar out\artifacts\Projet_JavaFX_AWALE_jar\Projet_JavaFX_AWALE.jar
```


Autrement, vous pouvez ajouter les sources dans un IDE gérant le javafx et lancer le projet ainsi. Pour cloner le repository GitHub :


```
> git clone https://github.com/BahiaSechi/Projet_JavaFX_AWALE.git
```


**Manuel d’utilisation**


Le plateau du joueur 1 est celui du bas, le plateau du joueur 2 est celui du haut. Les scores des deux joueurs s’affichent à gauche et à droite de l’écran. Le journal, en bas de l’écran, contient plus d’informations sur les derniers coups joués.

Pour jouer un coup, cliquez sur une case du plateau. Vous pouvez régler la difficulté avec dans la partie “Difficulté” à gauche du plateau. Vous pouvez régler la difficulté sur “Débutant” ou “Moyen”.

Voici le détail des boutons du menu “Fichiers” :

*   “Nouveau” permet de relancer une partie.
*   “Charger” permet de charger une partie à partir d’un fichier.
*   “Sauvegarder” permet de sauvegarder votre partie dans un fichier.
*   “Abandonner” permet d’abandonner la partie.
*   “Arrêt partie” permet de proposer l’arrêt de la partie lorsqu’il reste moins de 10 graines sur le plateau.  
*   “Annuler coup” permet de revenir un coup en arrière.
*   “Quitter jeu” permet de quitter le jeu. 

Depuis le bouton “Afficher règles” vous pouvez afficher les règles du jeu.

Voici le détail des boutons du menu “Options” :

*   “Voir graines” permet d’afficher le nombre de graines dans une case lorsque l’on passe la souris dessus.
*   “Voir plateau” permet d’afficher le nombre de graines dans chaque case du plateau. Le jeu n’affiche pas plus de 10 graines par case, il est donc conseillé de jouer avec cette option activée pour savoir exactement combien il y a de graines dans les cases.
*   “Bruitages” permet d’activer le bruit de bille à chaque coup.
*   “Commentaires” permet d’activer des commentaires que nous pouvons entendre en fonction de différentes situations possiblement rencontrées dans le jeu. 
*   “Musique” permet de lancer une musique lorsque l’option est activée. La musique choisie est l’hiver des 4 saisons de Vivaldi, c’est normal si le son est faible au début.

Depuis le bouton “A propos” vous pouvez afficher les informations sur le jeu. 

Maintenant, à votre tour de jouer et de découvrir toutes les possibilités que renferme le jeu !

### **Gestion de projet**

### **Conception :**

Nous avons d’abord étudié le cahier des charges puis établi les besoins fonctionnels de notre projet. Le jeu étant assez simple, cette première partie fut assez rapide. 

Ensuite nous avons fait le plateau de jeu et les images de billes afin d’avoir un support à partir duquel nous avons pu faire le reste du design de la fenêtre de jeu.

**Documentation :**

Nous avons généré la javadoc permettant de visualiser aisément les fonctions de notre projet. Vous pouvez y accéder dans le **dossier doc**, et ouvrir le fichier** index.html.

### **Répartition du travail :**

Nous n’avions pas fait de planning à l’avance. On toujours travaillé ensemble en vocal et jamais seuls de notre côté. Donc, on se tient au courant des avancées sur le code en temps réel en s’aidant au besoin. 

Au final le travail s’est réparti de cette manière :

**Bahia : **

*   Création des éléments graphiques. 
*   Gestion de la musique et des bruitages. 
*   Création du front end. 
*   Gestion des fichiers (sauvegarder, charger, top scores). 
*   Création de l’encart avec les logs et fonction d’envoi d’alerte.

**Manfred :** 

*   Créations des fonctionnalités permettant de jouer : 
    *   déplacer les billes
    *   ramasser les billes gagnées
    *   déterminer quand un coup est jouable
    *   s’assurer de ne pas affamer son adversaire
*   Gestion des différentes conditions de victoires.

Bien entendu, nous avons tout au long du projet travaillé sur GitHub. Voici un bref aperçu de nos 80 commits : 

### **Fonctionnalités implémentées :**

Il est possible de jouer à deux joueurs en suivant toutes les règles précisées dans l’énoncé en partie locale sur le même écran, en tour par tour. Toutes les options de menu spécifié dans l’énoncé sont implémentées également, à savoir, toutes ces dernières : 

**Menu Awalé **

o Nouveau: commence une nouvelle partie 

o Charger : recharge en mémoire une partie sauvegardée. 

o Sauvegarder : sauvegarder l'état de la partie. 

    o Abandonner : termine la partie immédiatement (l'adversaire capture les graines restantes) 

o Arrêt Partie: arrête la partie en cours.

 o Annuler dernier coup : Revient en arrière d'un tour de jeu 

** Menu Règles **

o Voir la règle : Visualise la règle.  

**Menu Options **


    o Voir nombre de graines : Si cette option est active, le nombre de graines présentes dans le trou que vous pointez avec la souris s'affiche.


     o Voir état plateau : Si cette option est active, le nombre de graines contenues dans chaque trou est affiché. 


    o Bruitages : Si cette option est active, le jeu est bruité. 


    **_o Commentaires: Si cette option est active, le jeu est commenté. (bonus)_**


    o Musiques : Si cette option est active, une musique se fait entendre durant le jeu. 


    ** Menu système **


    o Ouvre la boîte d'information donnant la version d'Awalé et les noms des réalisateurs.


## **Structure du projet :**

    **Projet_JavaFX_AWALE**
      │
      └─── .idea   	→  configuration Intellij IDEA
      │
      └─── doc →  documentation Javadoc
        └─── index-files
        └─── sample
      └─── out             	→  sorties générées par le projet
        └─── artifacts
          |   | Projet_JavaFX_AWALE.jar              	→  exécutable généré 
        └─── production
      └─── src		→  dans ce dossier se trouve le principal du projet
        └─── img
        └─── sample
          |   | Controller.java              	→  fonctions liées à la  vue principale du jeu.
          |   | Main.java                   	         	→  classe permettant de lancer le jeu.
          |   | RulesController.java              	→  fonctions liées à vue des règles du jeu.
          |   | RulesView.fxml              	→  vue des règles.
          |   | sample.fxml                         	→  vue principale du jeu.
        └─── scoreTop100.txt
        └─── sound
          |   | AirHorn.mp3              	→  commentaire.
          |   | marble.wav                   	→  bruitage.
          |   | Pavard.mp3                        	→  commentaire.
          |   | winter.mp3                        	→  musique.
          |   | Zinedine.mp3                         	→  commentaire


### **Particularité de notre version :**

Nous avons également implémenté un système de journal (logs) donnant des informations pour informer les joueurs de ce qu’il se passe pendant la partie. 

Enfin, pour nous amuser et apporter une touche d’originalité, nous avons également ajouté des commentaires, comme on peut en avoir pour le sport, qui se déclenchent lors de certaines situations précises. Cette fonctionnalité est activable dans les options, de la même manière que la musique et le bruitage. Voici les déclencheurs des commentaires :

*   Lorsque les scores des deux joueurs sont au dessus de 10 et que l’un d’eux égalise.
*   Lorsque un joueur ne marque pas de point et qu’il a au moins 15 points de de retard avec son adversaire.
*   Lorsque la partie est finie.

Évidemment on vous laisse découvrir par vous même ce que les commentateurs ont à dire du match que vous jouez. Précisons également que chaque commentaire ne se joue qu’une fois par partie.

### **Choix ergonomiques :**

*   Les logs sont une manière d’afficher des informations utiles sans surcharger le joueur de pop-ups, ce qui peut être très désagréable en pleine partie ; nous voulions minimiser ces dernières au strict nécessaire.

### **Ce que nous aurions aimé faire :**

Nous aurions aimé rajouter plus de commentaires audio pour mettre les joueurs dans l’ambiance mais cela demande du temps de trouver les meilleurs passages.

### **Améliorations possibles :**

Nous pourrions organiser un peu mieux notre code (un peu de répartition en plusieurs fichiers et un peu de factorisation) car de beaux commentaires ne suffisent pas toujours pour s’y retrouver.

Nous mettre d’accord sur une langue pour écrire les fonctions et surtout la documentation. Pour l’instant, mieux vaut être bilingue franglais. 

#### Copyright (C) 2020 - Manfred BURON - Bahia SECHI
