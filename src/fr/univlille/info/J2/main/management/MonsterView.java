/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management;

import java.util.logging.Logger;

import fr.univlille.info.J2.main.application.cells.Cell;
import fr.univlille.info.J2.main.application.cells.CellWithText;
import fr.univlille.info.J2.main.application.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.menuConception.Generators;
import fr.univlille.info.J2.main.utils.menuConception.ImageLoader;
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
	 * Hauteur de la fenêtre, par défaut 500
	 */
	private double window_height;
	/**
	 * Largeur de la fenêtre, par défaut 500
	 */
	private double window_width;
	/**
	 * Position horizontal, par défaut 0
	 */
	private int gap_X;
	/**
	 * Position vertical, par défaut 0
	 */
	private int gap_Y;
	/**
	 * Zoom, par défaut 30
	 */
	private int zoom;
	/**
	 * Couleur des murs
	 */
	private Color colorOfWalls;
	/**
	 * Couleur des sols
	 */
	private Color colorOfFloors;
	/**
	 * Couleur du brouillard
	 */
	private Color colorOfFog;
	/**
	 * boolean indiquant si le jeu doit afficher des carrés de couleurs ou des images
	 */
	private boolean isWithImages;
	

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
	 * Groupe pour la gestion de la scène.
	 */
	private Group group_stage;

	private BorderPane bp;

	private Text turnIndication;
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
	 * @param colorOfWalls  	Couleur des murs.
	 * @param colorOfFloors		Couleur des sols.
	 * @param maze				Instance du labyrinthe associée à cette vue.
	 */
	public MonsterView(double window_height, double window_width, int gap_X, int gap_Y, int zoom,
			Color colorOfWalls, Color colorOfFloors, Color colorOfFog, Maze maze,  String monsterName, boolean isWithImages) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_Y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.colorOfFog = colorOfFog;
		this.isWithImages=isWithImages;
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

		this.scene=new Scene(bp,this.window_width,this.window_height,Color.BLACK);
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
		this.sprite_shot.setCoord(this.maze.getMonster().getCoord());
		this.sprite_shot.setVisible(true);
		
		this.selection.setVisible(false);
		this.sprite_shot.getImgv().setVisible(true);
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
					if(this.isWithImages) {
						cell.setFill(Color.TRANSPARENT);
					}else {
						if(this.maze.isFloor(cell.getCoord())) {
							cell.setFill(colorOfFloors);
						}else {
							cell.setFill(colorOfWalls);
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
		this.sprite_monster=new CellWithText(this.maze.getMonster().getCoord(), this.zoom, Color.TRANSPARENT, this.gap_X, this.gap_Y, "Monster");
		if(this.isWithImages) {
			this.sprite_monster.setImage(ImageLoader.monster_ocean);
		}else {
			this.sprite_monster.setFill(MONSTER_COLOR);
		}
		this.addMouseEvents(sprite_monster);

		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=new CellWithText(this.maze.getHunter().getCoord(), this.zoom, Color.TRANSPARENT, Color.TRANSPARENT, 3, this.gap_X, this.gap_Y, "Hunter");
		if(this.isWithImages) {
			this.sprite_shot.setImage(ImageLoader.scope);
		}else {
			this.sprite_shot.setStroke(Color.YELLOW);
		}
		
		this.addMouseEvents(sprite_shot);
		this.sprite_shot.setVisible(false);

		//Initialisation du sprite de la sortie
		this.sprite_exit=new CellWithText(this.maze.getExit().getCoord(), this.zoom, Color.TRANSPARENT, this.gap_X, this.gap_Y, "Exit");
		if(this.isWithImages) {
			this.sprite_exit.setImage(ImageLoader.exit_dungeon);
		}else {
			this.sprite_exit.setFill(EXIT_COLOR);
		}
		this.addMouseEvents(sprite_exit);

		//initialisation du rectangle de sélection
		this.selection=new CellWithText(0,0, this.zoom, Color.TRANSPARENT, Color.RED, 3, this.gap_X, this.gap_Y, "Selection");
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
		this.sprite_shot.getImgv().setVisible(false);


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
				Cell r = new Cell(l, h, this.zoom, Color.TRANSPARENT, this.gap_X, this.gap_Y);
				//Codage des rectangles
				if(!this.maze.getWalls()[h][l]) {
					r.setImage(ImageLoader.wall_dungeon);
					r.setFill(colorOfWalls);
				}else {
					r.setImage(ImageLoader.floor_dungeon);
					r.setFill(colorOfFloors);
				}
				if(this.maze.getVisionRange()!=-1) {
					r.setFill(this.colorOfFog);
					r.setStroke(this.colorOfWalls);
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
	 * Obtient la hauteur de la fenêtre de jeu.
	 *
	 * @return La hauteur de la fenêtre.
	 */
	public double getWindow_height() {
		return window_height;
	}

	/**
	 * Définit la hauteur de la fenêtre de jeu.
	 *
	 * @param window_height La nouvelle hauteur de la fenêtre.
	 */
	public void setWindow_height(double window_height) {
		this.window_height = window_height;

	}

	/**
	 * Obtient la largeur de la fenêtre de jeu.
	 *
	 * @return La largeur de la fenêtre.
	 */
	public double getWindow_width() {
		return window_width;
	}

	/**
	 * Définit la largeur de la fenêtre de jeu.
	 *
	 * @param window_width La nouvelle largeur de la fenêtre.
	 */
	public void setWindow_width(double window_width) {
		this.window_width = window_width;
	}

	/**
	 * Obtient la position X.
	 * @return La postion X.
	 */
	public int getGap_X() {
		return gap_X;
	}

	/**
	 * Définit la position X.
	 * @param gap_X La nouvelle positon X.
	 */
	public void setGap_X(int gap_X) {
		this.gap_X = gap_X;
	}

	/**
	 * Obtient la position Y.
	 * @return La postion Y.
	 */
	public int getGap_y() {
		return gap_Y;
	}

	/**
	 * Définit la position Y.
	 * @param gap_Y La nouvelle positon Y.
	 */
	public void setGap_y(int gap_Y) {
		this.gap_Y = gap_Y;
	}

	/**
	 * Obtient le zoom.
	 * @return La valeur du zoom.
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * Définit la valeur du zoom.
	 * @param zoom La nouvelle valeur du zoom.
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * Obtient la couleur du mur.
	 * @return La couleur du mur
	 */
	public Color getColorOfWalls() {
		return colorOfWalls;
	}

	/**
	 * Définit la couleur du mur.
	 * @param colorOfWalls La nouvelle couleur du mur.
	 */
	public void setColorOfWalls(Color colorOfWalls) {
		this.colorOfWalls = colorOfWalls;
	}

	/**
	 * Obtient la couleur du sol.
	 * @return la couleur du sol.
	 */
	public Color getColorOfFloors() {
		return colorOfFloors;
	}

	/**
	 * Définit la couleur du sol.
	 * @param colorOfFloors La nouvelle couleur du sol.
	 */
	public void setColorOfFloors(Color colorOfFloors) {
		this.colorOfFloors = colorOfFloors;
	}

	/**
     * Calcule la position de dessin X en fonction d'une coordonnée.

	 * @param x La coordonnée X.
	 * @return  La position de dessin X calculée.
	 */
	public int calculDrawX(int x) {
		return x*zoom+gap_X;
	}

	/**
     * Calcule la position de dessin Y en fonction d'une coordonnée.
     *
	 * @param y La coordonnée Y.
	 * @return  La position de dessin Y calculée.
	 */
	public int calculDrawY(int y) {
		return y*zoom+gap_Y;
	}

	/**
     * Calcule la coordonnée X en fonction d'un rectangle.
     *
	 * @param r Le rectangle.
	 * @return La coordonnée X calculée.
	 */
	public int calculCoordX(Rectangle r) {
		return (int)((r.getX()-gap_X)/zoom);
	}

	/**
     * Calcule la coordonnée Y en fonction d'un rectangle.
     *
	 * @param r Le rectangle.
	 * @return La coordonnée Y calculée.
	 */
	public int calculCoordY(Rectangle r) {
		return (int)((r.getY()-gap_Y)/zoom);
	}
}