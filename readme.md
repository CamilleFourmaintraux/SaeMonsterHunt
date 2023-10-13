# Saé3.02 : Chasse au Monstre

Le monstre évolue dans un labyrinthe, une grille découpée en cases, avec lequel le chasseur interagit sans pour autant s’y situer.

Le monstre connaît l’entrée et la sortie et a une connaissance partielle ou totale des obstacles du labyrinthe.

Dans un premier temps, on peut supposer qu’un labyrinthe est une grille carrée avec des obstacles (mais ce n’est pas très important, ça pourrait être une grille rectangulaire, une île aux formes mal définies, un tore, pourquoi pas des étages, des pièges, des passages secrets, ...)  et que le monstre connaît l’emplacement
des obstacles.

Les obstacles occupent une case du labyrinthe qui ne peut être
traversée. Dans un second temps, on pourra supposer que le monstre a une connaissance limitée à quelques cases autours de lui et à la sortie.

Pour cette version des règles, le monstre ne pourra pas trouver un chemin à l’avance dans le labyrinthe mais devra y progresser à l’aveugle, en étant parfois obligé de rebrousser chemin. Le chasseur connaît la forme et la dimension de la grille, mais il ne connaît rien d’autre : ni l’emplacement de l’entrée ou de la sortie, ni l’emplacement des obstacles.

Le but du jeu pour le chasseur est de trouver le monstre, pour le monstre de réussir à atteindre la sortie du labyrinthe depuis l’entrée sans se faire attraper par le chasseur. Un tour de jeu consiste en un coup du monstre suivi d’un coup du chasseur.
## Démarrer l'application


PLUS TARD

Il faudra ouvrir un terminal, se déplacer dans le dossier application et entrer cette commande :

```bash
  java --module-path ./javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml -jar MonsterHunt.jar
```

ou vous pouvez aussi la démarrer en cliquant sur le fichier launch.sh.

## Utilisations

L’outil  fournit ces fonctionnalités :

0. Charger un fichier CSV suivant un format bien précis et qui contient les données d’entrée.
1. visualiser l’ensemble des appariements,
2. visualiser les détails sur un appariement (noms, prénoms des adolescents et informations associées),
3. visualiser les contraintes rédhibitoires qui ne sont pas respectées,
4. ajuster les pondérations des différents critères,
5. fixer au préalable des affectations,
6. éviter d’affecter des adolescents

Ce outil permet de calculer des affectations pour des séjours à l'étranger entre des étudiants données dans un fichier CSV.


## Documentation

[Documentation](https://fr.wikipedia.org)


## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)


## Auteurs

Fourmaintraux Camille [camille.fourmaintraux.etu@univ-lille.fr]

Arthur Debacq

Jessy Top

Théo Franos
