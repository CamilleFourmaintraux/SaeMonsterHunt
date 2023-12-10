/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management;

import java.util.logging.Logger;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.management.cells.CellWithText;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;
import fr.univlille.info.J2.main.utils.menuConception.Generators;
import fr.univlille.info.J2.main.utils.menuConception.Theme;
import fr.univlille.info.J2.main.utils.patrons.Observer;
import fr.univlille.info.J2.main.utils.patrons.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * La classe MonsterView représente la vue du Monstre.
 * Elle affiche la vue du Monstre et gère son interaction avec le jeu.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class MonsterView implements Observer{
	private static final Logger logger = Logger.getLogger(MonsterView.class.getName());
	/**
	 * Constante couleur du Monstre. Ce n'est plus utilisé pour le moment
	 */
	private static final Color MONSTER_COLOR = Color.CRIMSON;
	/**
	 * Constante couleur de la sortie du labyrinthe. Ce n'est plus utilisé pour le moment
	 */
	private static final Color EXIT_COLOR = Color.VIOLET;
	/**
	 * Constante couleur de la sortie du labyrinthe. Ce n'est plus utilisé pour le moment
	 */
	private static final Color HUNTER_COLOR = Color.YELLOW;
	
	/**
	 * Contient toute les informations sur comment doit s'afficher la fenetre et le jeu
	 */
	DisplayValues display;
	
	/**
	 * Contient toute les informations à propos de la manière dont doit s'afficher le jeu
	 */
	private Theme theme;

	/**
	 * Nom du joueur incarnant le monstre
	 */
	private String monsterName;

	/**
	 * Sujet (pour le modèle observé)
	 */
	private Maze maze;

	//Sprites (Rectangles pour le moment)
	private CellWithText sprite_monster;
	private CellWithText  sprite_shot;
	private CellWithText  sprite_exit;
	private CellWithText  selection;

	/**
	 * Groupe pour la gestion des images.
	 */
	private Group group_img_map;

	/**
	 * Groupe pour la gestion des images des sprites.
	 */
	private Group group_img_sprite;

	/**
	 * Groupe pour la gestion de la map.
	 */
	private Group group_map;

	/**
	 * Groupe pour la gestion des sprites (rectangle).
	 */
	private Group group_sprite;

	/**
	 * Groupe englobant toutes les Cell représentant le jeu
	 */
	private Group group_stage;
	/**
	 * BorderPane pour la gestion de la scène.
	 */
	private BorderPane bp;

	/**
	 * Text indiquant le tour actuel
	 */
	private Text turnIndication;
	/**
	 * Text permettant de transmettre des informations aux joueurs (ici le monstre).
	 */
	private Text notification;

	/**
	 * Scène pour l'affichage.
	 */
	protected Scene scene;

	/**
     * Constructeur de la classe MonsterView, crée la vue du Monstre.
	 *
	 * @param window_height 	Hauteur de la fenêtre.
	 * @param window_width 		Largeur de la fenêtre.
	 * @param gap_X 			Position horizontal.
	 * @param gap_Y				Position vertical.
	 * @param zoom 				Niveau de zoom.
	 * @param theme.getWallColor()  	Couleur des murs.
	 * @param theme.getFloorColor()		Couleur des sols.
	 * @param maze				Instance du labyrinthe associée à cette vue.
	 */
	public MonsterView(DisplayValues display, Maze maze,  String monsterName, Theme theme) {
		this.display=display;
		this.theme=theme;
		this.monsterName=monsterName;

		this.maze = maze;
		this.maze.attach(this);

		this.group_sprite=new Group();
		this.group_img_sprite=new Group();

		this.initiateSprites();

		this.group_map=new Group();
		this.group_img_map=new Group();
		this.group_stage=new Group();

		this.group_stage.getChildren().add(this.group_img_map);
		this.group_stage.getChildren().add(this.group_img_sprite);
		this.group_stage.getChildren().add(this.group_map);
		this.group_stage.getChildren().add(this.group_sprite);

		this.turnIndication = new Text("Turn n°1");
		this.notification = new Text("Welcome to Monster Hunter - THE GAME");
	
		Button b_option = Generators.generateButton("-> Option", 0, 0,Color.WHITE, Color.BLACK);
		b_option.setOnAction(e-> Management.showOption(this.maze, notification) );
		
		VBox vbox = new VBox();
		Label player_name = new Label(this.monsterName);
		player_name.setTextFill(Color.WHITE);
		this.turnIndication.setFill(Color.WHITE);
		this.notification.setFill(Color.WHITE);
		vbox.getChildren().addAll(player_name, this.turnIndication, this.notification, b_option);
		this.bp=new BorderPane(group_stage);
		this.bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.bp.setTop(vbox);

		this.scene=new Scene(bp,this.display.getWindowWidth(),this.display.getWindowHeight(),Color.BLACK);
		this.draw();
	}

	/**
     * Méthode de mise à jour appelée lorsqu'un changement est notifié par le sujet (Maze).
     *
     * @param s Le sujet notifiant le changement.
	 */
	@Override
	public void update(Subject s) {
		this.actualize();
	}

	/**
	 * Méthode de mise à jour appelée lorsqu'un changement est notifié par le sujet (Maze)
     * avec un objet spécifique.
     *
     * @param s Le sujet notifiant le changement.
     * @param o L'objet spécifique notifiant le changement.
     */
	@Override
	public void update(Subject s, Object o) {
		this.actualize();
	}

	public void actualize() {
		this.sprite_monster.setXY(this.calculDrawX(this.maze.getMonster().getCol()),this.calculDrawY(this.maze.getMonster().getRow()));
		this.sprite_monster.setCoord(this.maze.getMonster().getCoord());
		this.sprite_monster.setVisible(true);
		
		this.sprite_shot.setXY(this.calculDrawX(this.maze.getHunter().getCol()),this.calculDrawY(this.maze.getHunter().getRow()));
		this.sprite_shot.setCoord(this.maze.getHunter().getCoord());
		this.sprite_shot.setVisible(true);
		this.sprite_shot.getImgv().setVisible(true);
		
		this.selection.setVisible(false);
		this.turnIndication.setText("Turn n°"+this.maze.getTurn());
		if(this.maze.isSpotted()) {
			this.notification.setText("WARNING - You have crossed a square previously discovered\nby the hunter and he has been warned.");
		}else {
			this.notification.setText("");
		}
		for(Node n:this.group_map.getChildren()) {
			try {
				Cell cell = (Cell)n;
				if(this.maze.isExplored(cell.getCoord())) {
					cell.setStroke(Color.TRANSPARENT);
					if(this.theme.isWithImages()) {
						cell.setFill(Color.TRANSPARENT);
					}else {
						if(this.maze.isFloor(cell.getCoord())) {
							cell.setFill(this.theme.getFloorColor());
						}else {
							cell.setFill(this.theme.getWallColor());
						}
					}
				}
			}catch(Exception e) {
				logger.info("Error - in class MonsterView -> method actualize");
			}
		}
	}

	/**
	 * Initialisation des sprites.
	 */
	private void initiateSprites() {
		//Initialisation du sprite du monstre
		this.sprite_monster=new CellWithText(this.maze.getMonster().getCoord(), this.display.getZoom(), Color.TRANSPARENT, this.display.getGapX(), this.display.getGapY(), "Monster");
		if(this.theme.isWithImages()) {
			this.sprite_monster.setImage(this.theme.getMonsterImg());
		}else {
			this.sprite_monster.setFill(MONSTER_COLOR);
		}
		this.addMouseEvents(sprite_monster);

		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=new CellWithText(this.maze.getHunter().getCoord(), this.display.getZoom(), Color.TRANSPARENT, Color.TRANSPARENT, 3, this.display.getGapX(), this.display.getGapY(), "Hunter");
		if(this.theme.isWithImages()) {
			this.sprite_shot.setImage(this.theme.getHunterImg());
		}else {
			this.sprite_shot.setStroke(HUNTER_COLOR);
		}
		this.addMouseEvents(sprite_shot);
		this.sprite_shot.setVisible(true);
		this.sprite_shot.getImgv().setVisible(true);

		//Initialisation du sprite de la sortie
		this.sprite_exit=new CellWithText(this.maze.getExit().getCoord(), this.display.getZoom(), Color.TRANSPARENT, this.display.getGapX(), this.display.getGapY(), "Exit");
		if(this.theme.isWithImages()) {
			this.sprite_exit.setImage(this.theme.getExitImg());
		}else {
			this.sprite_exit.setFill(EXIT_COLOR);
		}
		this.addMouseEvents(sprite_exit);

		//initialisation du rectangle de sélection
		this.selection=new CellWithText(0,0, this.display.getZoom(), Color.TRANSPARENT, Color.RED, 3, this.display.getGapX(), this.display.getGapY(), "Selection");
		this.selection.setVisible(false);
		this.addMouseEvents(selection);

		this.group_sprite.getChildren().add(this.selection);
		this.group_img_sprite.getChildren().add(this.selection.getImgv());

		this.group_sprite.getChildren().add(this.sprite_monster);
		this.group_img_sprite.getChildren().add(this.sprite_monster.getImgv());

		this.group_sprite.getChildren().add(this.sprite_exit);
		this.group_img_sprite.getChildren().add(this.sprite_exit.getImgv());

		this.group_sprite.getChildren().add(this.sprite_shot);
		this.group_img_sprite.getChildren().add(this.sprite_shot.getImgv());
	}

	public void addMouseEvents(Cell r) {
		r.setOnMouseEntered(e->{
			this.select(e, r.getCoord());
			this.scene.setOnMouseClicked( event-> this.selectionLocked(r) );
		});
	}

	/**
     * Dessine le labyrinthe et ses éléments.
	 *
	 * @return Un groupe contenant les éléments graphiques du labyrinthe.
	 */
	public void draw() {
		for(int h=0; h<this.maze.getWalls().length; h++) {
			for(int l=0; l<this.maze.getWalls()[h].length; l++) {
				Cell r = new Cell(l, h, this.display.getZoom(), Color.TRANSPARENT, this.display.getGapX(), this.display.getGapY());
				//Codage des rectangles
				if(!this.maze.getWalls()[h][l]) {
					r.setImage(this.theme.getWallImg());
					r.setFill(theme.getWallColor());
				}else {
					r.setImage(this.theme.getFloorImg());
					r.setFill(theme.getFloorColor());
				}
				if(this.maze.getVisionRange()!=-1) {
					r.setFill(this.theme.getFogColor());
					r.setStroke(this.theme.getWallColor());
					r.setStrokeWidth(1);
				}
				this.addMouseEvents(r);

				this.group_map.getChildren().add(r);
				this.group_img_map.getChildren().add(r.getImgv());
			}
		}
	}

	/**
     * Méthode pour sélectionner une cellule du labyrinthe.
	 *
	 * @param c La coordonnée sélectionné.
	 * @param e L'événement de la souris associé à la sélection.
	 */
	public void select(MouseEvent e, ICoordinate c) {
		if(this.maze.getMonsterIa().equals("Player")) {
			int row=c.getRow();
			int col=c.getCol();
			this.selection.setY(this.calculDrawY(row));
			this.selection.setX(this.calculDrawX(col));
			this.selection.setCoord(c);
			this.selection.setVisible(true);
			this.selection.toFront();
			if(this.maze.canMonsterMoveAt(c)) {
				this.validSelection();
			}else{
				this.invalidSelection();

			}
		}
	}

	public void selectionLocked(Cell cell) {
		if(this.maze.getMonsterIa().equals("Player")) {
			int y = this.calculCoordY(cell);
			int x = this.calculCoordX(cell);
			ICoordinate c = new Coordinate(y,x);
			this.maze.move(c);
		}else {
			this.notification.setText("No selection possible: "+this.monsterName+" is an AI.");
		}
	}


	/**
     * Invalide la sélection en cours en changeant la couleur de la sélection.
	 */
	public void invalidSelection() {
		this.selection.setStroke(Color.DARKRED);
		this.selection.setStrokeWidth(1);
	}

	/**
     * Met en évidence la sélection en cours en changeant la couleur de la sélection.
	 */
	public void validSelection() {
		this.selection.setStroke(Color.RED);
		this.selection.setStrokeWidth(3);
	}

	/**
     * Calcule la position de dessin X en fonction d'une coordonnée.

	 * @param x La coordonnée X.
	 * @return  La position de dessin X calculée.
	 */
	public int calculDrawX(int x) {
		return (int)(x*this.display.getZoom()+this.display.getGapX());
	}

	/**
     * Calcule la position de dessin Y en fonction d'une coordonnée.
     *
	 * @param y La coordonnée Y.
	 * @return  La position de dessin Y calculée.
	 */
	public int calculDrawY(int y) {
		return (int)(y*this.display.getZoom()+this.display.getGapY());
	}

	/**
     * Calcule la coordonnée X en fonction d'un rectangle.
     *
	 * @param r Le rectangle.
	 * @return La coordonnée X calculée.
	 */
	public int calculCoordX(Rectangle r) {
		return (int)((r.getX()-this.display.getGapX())/this.display.getZoom());
	}

	/**
     * Calcule la coordonnée Y en fonction d'un rectangle.
     *
	 * @param r Le rectangle.
	 * @return La coordonnée Y calculée.
	 */
	public int calculCoordY(Rectangle r) {
		return (int)((r.getY()-this.display.getGapY())/this.display.getZoom());
	}
}