# Saé3.02 : Chasse au Monstre

Le monstre évolue dans un labyrinthe, une grille découpée en cases, avec lequel le chasseur interagit sans pour autant s’y situer.

Le monstre **connaît l’entrée** et **la sortie** et a une connaissance partielle ou totale des obstacles du labyrinthe.

Dans un premier temps, on peut supposer qu’un labyrinthe est une grille carrée avec des obstacles (mais ce n’est pas très important, ça pourrait être une grille rectangulaire, une île aux formes mal définies, un tore, pourquoi pas des étages, des pièges, des passages secrets, ...)  et que **le monstre connaît l’emplacement des obstacles**.

Les **obstacles** occupent une case du labyrinthe qui **ne peut être traversée**. Dans un second temps, on pourra supposer que le monstre a une connaissance limitée à quelques cases autours de lui et à la sortie.

Pour cette version des règles, le monstre ne pourra pas trouver un chemin à l’avance dans le labyrinthe mais devra y progresser à l’aveugle, en étant parfois obligé de rebrousser chemin. Le chasseur connaît la forme et la dimension de la grille, mais il ne connaît rien d’autre : ni l’emplacement de l’entrée ou de la sortie, ni l’emplacement des obstacles.

Le **but du jeu** pour le chasseur est de **trouver le monstre**, pour le monstre de **réussir à atteindre la sortie du labyrinthe** depuis l’entrée sans se faire attraper par le chasseur. Un tour de jeu consiste en **un coup du monstre suivi d’un coup du chasseur**.

---

## Démarrer l'application

Il faudra ouvrir un terminal, se déplacer dans le dossier application et entrer cette commande :

```bash
java --module-path ./javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml -jar MonsterHunt.jar
```

ou vous pouvez aussi la démarrer en cliquant sur le fichier launch.sh.

---
## Utilisations

Lorsque le jeu se lance vous atterissez sur le **menu principal**, depuis vous pouvez choisir le **nom des joueurs** pour chacun des deux rôles ainsi que de choisir si **l'un des joueurs ou les deux joueurs soit/sont un/des IA avec leur niveau de difficulté**. Vous avez aussi la possibilité de **personnaliser votre expérience** de jeu en allant de le menu ***"modify settings"***, ici vous pourrez modifier les **paramètres du jeu** tel que choisir de jouer en écran partagé ou scindé**, choisir la **taille du labyrinthe**, définir un **taux d'apparition des murs** ou encore un **thème pour le jeu**.

Pour jouer il suffit d'appuyer sur le bouton ***"PLAY"***, le jeu se lance alors, pour **sélectionner une case du labyrinthe** il suffit d'appuyer sur **clic gauche** sur la case.


---
## Documentation

Vous pouvez retrouvez **toute la [documentation](doc/index.html)** (javadoc) en cliquant **[ici](doc/index.html)** ou sur documentation.

## Personnalisation de l'expérience de jeu

### Diffèrent mode de jeu :

Il existe **deux mode de jeu** dans ***MonsterHunt***, nous pouvons jouer en **écran partagé** ou en **écran scindé**.

Exemple de partie en **écran partagé** (sameScreen) : 

![Partie de MonsterHunt en écran partagé](res/readMe/sameScreenExample.gif)

Exemple de partie en **écran scindé** (separateScreen) :

![Partie de MonsterHunt en écran scindé](res/readMe/separateScreenExample.gif)


### Thèmes :

Nous avons conçu **diffèrent thèmes illustrés** pour le jeu, les voici :

- Donjon
- Cave
- Forêt
- Océan
- Prairie

Le **"mode image"** est activé par défaut au lancement, toute fois si les illustrations des thèmes ne vous conviennent pas il existe un **"mode couleur"** uniquement aux couleurs du thèmes.

**Thème Forêt** : 

![Partie de MonsterHunt avec le thème forêt](res/forestTheme.jpg)

**Thème Océan** :

![Partie de MonsterHunt avec le thème océan](res/oceanTheme.jpg)

---
### Génération, Importation & Création de labyrinthe.

Nous avons ajouté la fonctionnalité de pouvoir **personnaliser la génération aléatoire du labyrinthe** avec les critères suivants :

- la hauteur du labyrinthe.
- la largeur du labyrinthe.
- le taux de génération de mur dans le labyrinthe.

![Menu de génération de labyrinthe](res/readMe/mazeGenerationSettings.jpg)

Cependant, nous proposons aussi au joueur de **jouer sur ses propres labyrinthes** grâce à l'option **Imported** qui permet d'importer des labyrinthe sous un **fichier .DAT** :

![Menu d'importation de labyrinthe](res/readMe/mazeGenerationImportedSettings.jpg)

Enfin, nous proposons aussi au joueur de pouvoir **exprimer leur créativité** en présentant cet ***éditeur de labyrinthe*** qui vous permet de **créer de A à Z votre labyrinthe idéal et de le sauvegarder**, il permet aussi d'**éditer un labyrinthe déjà existant** grâce à l'importation.

![Editeur de labyrinthe](res/readMe/mazeEditor.jpg)

---
### Bonus du Monstre & du Chasseur.

Nous avons fait en sorte de pouvoir **personnaliser l'expérience de jeu du chasseur & du monstre** en ayant offrant la possibilité de leur octroyer des **bonus** à chacun. 

Du côté du Monstre, il est possible :

- **Augmenter sa portée de déplacement** (qui est par défaut de 1).
- **Activer la vision limité du labyrinthe** (brouillard) et si activé la portée de la vision.

![Parmètres du Monster](res/readMe/monsterSettings.jpg)

Du côté du Chasseur, il est possible :

- **Augmenter la portée de la vision** autour de la case sur laquelle il a tiré.

![Parmètres du Chasseur](res/readMe/hunterSettings.jpg)


---
## Auteurs

Fourmaintraux Camille - camille.fourmaintraux.etu@univ-lille.fr  
Arthur Debacq - arthur.debacq.etu@univ-lille.fr  
Jessy Top - jessy.top.etu@univ-lille.fr  
Théo Franos - theo.franos.etu@univ-lille.fr  
