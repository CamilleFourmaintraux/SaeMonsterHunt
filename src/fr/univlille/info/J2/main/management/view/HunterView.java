/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management.view;

import fr.univlille.info.J2.main.management.Management;
import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.cells.CellWithText;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;
import fr.univlille.info.J2.main.utils.menuConception.Generators;
import fr.univlille.info.J2.main.utils.menuConception.Theme;
import fr.univlille.info.J2.main.utils.patrons.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**
 * La classe HunterView représente la vue du chasseur.
 * Elle affiche la vue du chasseur et gère son interaction avec le jeu.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class HunterView extends View{
	/**
	 * Constante couleur de la sortie du labyrinthe. Ce n'est plus utilisé pour le moment
	 */
	private static final Color SHOT_COLOR = Color.YELLOW;
	/**
	 * Constante de la largeur du contour des cases brouillard
	 */
	private static final int SHOT_STROKE_THICKNESS=5;
	/**
	 * Constante couleur de la sélection
	 */
	private static final Color SELECTION_COLOR = Color.RED;
	/**
	 * Constante de la largeur du contour des cases brouillard
	 */
	private static final int SELECTION_STROKE_THICKNESS=3;
	/**
	 * Constante couleur du contour des cases brouillard
	 */
	private static final Color FOG_STROKE_COLOR = Color.DARKGREY;
	/**
	 * Constante de la largeur du contour des cases brouillard
	 */
	private static final int FOG_STROKE_THICKNESS=1;
	
	


	/**
	 * Sprite représentant le tir.
	 */
	private CellWithText sprite_shot;
	/**
	 * Sprite de sélection.
	 */
	private CellWithText selection;

	/**
	 * Groupe pour la gestion de la scène.
	 */
	private Group group_stage;
	/**
	 * Groupe pour la gestion des sprites (rectangle).
	 */
	private Group group_sprite;
	/**
	 * Groupe pour la gestion de la map.
	 */
	private Group group_map;

	/**
	 * Groupe pour la gestion des images la map.
	 */
	private Group group_img_map;

	/**
	 * Groupe pour la gestion des textes.
	 */
	private Group group_texts;
	/**
	 * BorderPane englobant le tout pour la gestion de la scène
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
	 * Constructeur de la classe HunterView, crée la vue du chasseur.
	 *
	 * @param window_height 	Hauteur de la fenêtre.
	 * @param window_width 		Largeur de la fenêtre.
	 * @param gap_X 			Position horizontal.
	 * @param gap_y				Position vertical.
	 * @param zoom 				Niveau de zoom.
	 * @param colorOfWalls  	Couleur des murs.
	 * @param colorOfFloors		Couleur des sols.
	 * @param colorOfFog		Couleur du brouillard.
	 * @param maze				Instance du labyrinthe associée à cette vue.
	 */
	public HunterView(DisplayValues display, Maze maze, String hunterName, Theme theme) {

		//Initiation de la fenetre
		this.display=display;
		this.theme=theme;
		this.playerName=hunterName;

		this.maze = maze;
		this.maze.attach(this);

		//Initiation des groupes
		this.group_img_map = new Group();
		this.group_sprite=new Group();
		this.group_texts=new Group();
		this.group_map = new Group();
		this.group_stage=new Group();

		this.group_stage.getChildren().add(group_img_map);
		this.group_stage.getChildren().add(group_map);
		this.group_stage.getChildren().add(group_texts);
		this.group_stage.getChildren().add(group_sprite);

		this.turnIndication = new Text("Turn n°1");
		this.notification = new Text("Welcome to Monster Hunter - THE GAME");

		Button b_option = Generators.generateButton("-> Option", this.theme.getTextColor(), this.theme.getBackgroundColor());
		b_option.setOnAction(e-> Management.showOption(this.maze,notification) );
		
		VBox vbox = new VBox();
		Label player_name = new Label(this.playerName);
		player_name.setTextFill(this.theme.getTextColor());
		this.turnIndication.setFill(this.theme.getTextColor());
		this.notification.setFill(this.theme.getTextColor());
		vbox.getChildren().addAll(player_name, this.turnIndication, this.notification, b_option);
		this.bp=new BorderPane(group_stage);
		this.bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.bp.setTop(vbox);


		//Scene
		this.scene=new Scene(bp, this.display.getWindowWidth(),this.display.getWindowHeight(),this.theme.getBackgroundColor());

		this.initiateSprites();
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
		int x = calculDrawX(this.maze.getHunter().getCol());
		int y = calculDrawY(this.maze.getHunter().getRow());
		this.sprite_shot.setXY(x,y);
		this.sprite_shot.setVisible(true);
		this.sprite_shot.getImgv().setVisible(true);
		this.turnIndication.setText("Turn n°"+this.maze.getTurn());
		if(this.maze.isSpotted()) {
			this.notification.setText("WARNING - The monster has been detected in one of your squares\nalready discovered during a previous turn!");
		}else {
			this.notification.setText("");
		}
		this.actualizeCells(this.maze.getHunter().getRow(),this.maze.getHunter().getCol());
	}

	
	
	
	/**
     * Méthode pour dessiner le labyrinthe et les éléments associés.
	 */
	public void draw() {
		for(int h=0; h<this.maze.getHunter().getTraces().length; h++) {
			for(int l=0; l<this.maze.getHunter().getTraces()[h].length; l++) {
				//Codage des rectangles permettant le contrôle
				CellWithText cell = new CellWithText(l, h, this.display.getZoom(), this.theme.getFogColor(),FOG_STROKE_COLOR,FOG_STROKE_THICKNESS,this.display.getGapX(),this.display.getGapY(),new Text(""));
				if(!this.maze.getWalls()[h][l]) {
					cell.setImage(this.theme.getWallImg());
				}else {
					cell.setImage(this.theme.getFloorImg());
				}
				cell.setFocusTraversable(false);
				cell.setOnMouseEntered(event -> {
					this.select(cell);
					this.scene.setOnMouseClicked(e -> this.selectionLocked(cell) );
				});
				this.group_map.getChildren().add(cell);
				this.group_img_map.getChildren().add(cell.getImgv());
				this.group_texts.getChildren().add(cell.getText());
				}
			}
	}

	/**
	 * Initialisation des sprites.
	 */
	protected void initiateSprites() {
		//initialisation du sprite de selection
		this.selection =  new CellWithText(0,0, this.display.getZoom(), Color.TRANSPARENT, SELECTION_COLOR, SELECTION_STROKE_THICKNESS, this.display.getGapX(),this.display.getGapY(), "Shot");
		this.selection.setVisible(false);

		//initialisation du sprite du tir
		this.sprite_shot=new CellWithText(this.maze.getHunter().getCoord(), this.display.getZoom(), Color.TRANSPARENT, SHOT_COLOR, SHOT_STROKE_THICKNESS, this.display.getGapX(),this.display.getGapY(), "Shot");
		this.sprite_shot.setVisible(false);
		this.sprite_shot.setOnMouseEntered(event -> {
			this.select(this.sprite_shot);
			this.scene.setOnMouseClicked(e -> this.selectionLocked(this.sprite_shot) );
		});

		//On ajoute les sprites au groupe associé.
		this.group_sprite.getChildren().add(this.sprite_shot);
		this.group_sprite.getChildren().add(this.selection);
	}

	/**
     * Méthode pour sélectionner une cellule du labyrinthe.
	 *
	 * @param r La cellule à sélectionner.
	 * @param e L'événement de la souris associé à la sélection.
	 */
	public void select(CellWithText r) {
		if(this.maze.getHunterIA().equals("Player")) {
			this.selection.setY(r.getY());
			this.selection.setX(r.getX());
			this.selection.toFront();
			this.selection.setVisible(true);
		}
	}

	public void selectionLocked(CellWithText cell) {
		if(this.maze.getHunterIA().equals("Player")) {
			ICoordinate c = new Coordinate(cell.getRow(),cell.getCol());
			this.maze.shoot(c);
		}else {
			this.notification.setText("No selection possible : "+this.playerName+" is an AI.");
		}
	}

	/**
	 * Révèle une cellule donnee en modifiant sa couleur en fonction de si c'est un mur ou un sol.
	 *
	 * @param cwt 			La cellule avec texte que l'on veut révéler
	 * @param colorOfWalls 	La couleur associé au mur, future couleur de la cellule si la cellule se révèle être un mur.
	 * @param colorOfFloors La couleur associé au sol, future couleur de la cellule si la cellule se révèle être un sol.
	 */
	public void revealCell(CellWithText cwt, Color colorOfWalls, Color colorOfFloors) {
		if(this.theme.isWithImages()) {
			cwt.setFill(Color.TRANSPARENT);
		}else {
			if(this.maze.getWalls()[cwt.getRow()][cwt.getCol()]) {
				cwt.setFill(colorOfFloors);
			}else {
				cwt.setFill(colorOfWalls);
			}
		}
		
		cwt.setStroke(Color.TRANSPARENT);
		if(this.maze.isFloor(cwt.getCoord())) {
			int trace = this.maze.getHunter().getTraces()[cwt.getRow()][cwt.getCol()];
			if(trace>0) {
				cwt.setText(""+trace);
			}
		}
	}


	public CellWithText searchSprite(Group group, ICoordinate c) {
		for(Node e:group.getChildren()) {
			if(e.getClass()==CellWithText.class) {
				CellWithText s = (CellWithText) e;
				if(s.getCoord().equals(c)) {
					return (CellWithText)e;
				}
			}
		}
		return null;
	}
	public void actualizeCells(int cRow, int cCol) {
		CellWithText cwt;
		for(int iRow =cRow-this.maze.getBonusRange(); iRow<cRow+(this.maze.getBonusRange()+1); iRow++) {
			for(int iCol =cCol-this.maze.getBonusRange(); iCol<cCol+(this.maze.getBonusRange()+1); iCol++) {
				try {
					cwt = searchSprite(this.group_map, new Coordinate(iRow,iCol));
					if(cwt!=null) {
						this.revealCell(cwt,this.theme.getWallColor(),this.theme.getFloorColor());
					}else {
						LOGGER.info("Error in HunterView at method : actualizeCell => Aucun Rectangle Correspondant !");
					}	
				}catch(Exception e) {
					LOGGER.info("("+iRow+","+iCol+") Out of bounds in actualizeCell -> Its normal dont worry");
				}
					
			}
		}

	}
	
}