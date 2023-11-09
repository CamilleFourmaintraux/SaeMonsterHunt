/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package main.maze;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.maze.cells.Cell;
import main.maze.cells.CellWithText;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.utils.Observer;
import main.utils.Subject;

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
	
	/**
	 * Constante couleur du Monstre.
	 */
	final Color MONSTER_COLOR = Color.CRIMSON;
	/**
	 * Constante couleur de la sortie du labyrinthe.
	 */
	final Color EXIT_COLOR = Color.VIOLET;
	/**
	 * Hauteur de la fenêtre, par défaut 500
	 */
	public int window_height;
	/**
	 * Largeur de la fenêtre, par défaut 500
	 */
	public int window_width;
	/**
	 * Position horizontal, par défaut 0
	 */
	public int gap_X;
	/**
	 * Position vertical, par défaut 0
	 */
	public int gap_Y;
	/**
	 * Zoom, par défaut 30
	 */
	public int zoom;
	/**
	 * Couleur des murs
	 */
	public Color colorOfWalls=Color.DARKGRAY;
	/**
	 * Couleur des sols
	 */
	public Color colorOfFloors=Color.LIGHTGRAY;
	/**
	 * Sujet (pour le modèle observé)
	 */
	Maze maze;
	
	//Sprites (Rectangles pour le moment)
	CellWithText sprite_monster;
	CellWithText  sprite_shot;
	CellWithText  sprite_exit;
	CellWithText  selection;
	
	/**
	 * Groupe pour la gestion de la scène.
	 */
	Group group_stage;
	/**
	 * Groupe pour la gestion des sprites (rectangle).
	 */
	Group group_sprite;
	/**
	 * Groupe pour la gestion de la map.
	 */
	Group group_map;
	/**
	 * Scène pour l'affichage.
	 */
	Scene scene;
	
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
	public MonsterView(int window_height, int window_width, int gap_X, int gap_Y, int zoom, Color colorOfWalls, Color colorOfFloors, Maze maze) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_Y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.maze = maze;
		this.maze.attach(this);
		this.group_sprite=new Group();
		this.initiateSprites();
		this.group_map=this.draw();
		this.group_stage=new Group();
		this.group_stage.getChildren().add(this.group_map);
		this.group_stage.getChildren().add(this.group_sprite);
		this.scene=new Scene(this.group_stage,this.window_height,this.window_width);
	}

	/**
     * Méthode de mise à jour appelée lorsqu'un changement est notifié par le sujet (Maze).
     *
     * @param s Le sujet notifiant le changement.
	 */
	@Override
	public void update(Subject s) {
		this.sprite_monster.setX(this.calculDrawX(this.maze.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.maze.monster.getRow()));
		this.sprite_monster.setCoord(this.maze.monster.coord);
		this.sprite_shot.setX(this.calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setCoord(this.maze.hunter.getCoord());
		this.sprite_shot.setVisible(true);
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
		this.sprite_monster.setX(this.calculDrawX(this.maze.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.maze.monster.getRow()));
		this.sprite_monster.setCoord(this.maze.monster.coord);
		this.sprite_monster.setVisible(true);
		this.sprite_shot.setX(this.calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setCoord(this.maze.hunter.getCoord());
		this.sprite_shot.setVisible(true);
	}
	
	/**
	 * Initialisation des sprites.
	 */
	private void initiateSprites() {
		//Initialisation du sprite du monstre
		this.sprite_monster=new CellWithText(this.maze.monster.coord, this.zoom, this.MONSTER_COLOR, this.gap_X, this.gap_Y, "Monster");
		this.sprite_monster.setOnMouseClicked(e->{
			this.select(e, this.sprite_monster.getCoord());
		});
		
		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=new CellWithText(this.maze.hunter.getCoord(), this.zoom, Color.TRANSPARENT, Color.YELLOW, 3, this.gap_X, this.gap_Y, "Hunter");
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(e, this.sprite_shot.getCoord());
		});
		this.sprite_shot.setVisible(false);
		
		//Initialisation du sprite de la sortie
		this.sprite_exit=new CellWithText(this.maze.exit.getCoord(), this.zoom, this.EXIT_COLOR, this.gap_X, this.gap_Y, "Exit");
		this.sprite_exit.setOnMouseClicked(e->{
			this.sprite_exit.toBack();
			this.select(e, this.sprite_exit.getCoord());
		});
		
		//initialisation du rectangle de sélection
		this.selection=new CellWithText(0,0, this.zoom, Color.TRANSPARENT, Color.RED, 3, this.gap_X, this.gap_Y, "Selection");
		this.selection.setOnMouseClicked(e->{
			this.select(e, this.selection.getCoord());
		});
		this.selection.setVisible(false);
		this.group_sprite.getChildren().add(this.selection);
		this.group_sprite.getChildren().add(this.sprite_monster);
		this.group_sprite.getChildren().add(this.sprite_exit);
		this.group_sprite.getChildren().add(this.sprite_shot);
		
	}
	
	/**
     * Dessine le labyrinthe et ses éléments.
	 * 
	 * @return Un groupe contenant les éléments graphiques du labyrinthe.
	 */
	public Group draw() {
		Group root = new Group();
		for(int h=0; h<this.maze.walls.length; h++) {
			for(int l=0; l<this.maze.walls[h].length; l++) {
				Cell r = new Cell(l, h, this.zoom, this.colorOfFloors, this.gap_X, this.gap_Y);
				//Codage des rectangles
				if(this.maze.walls[h][l]) {
					//Code à calculer si c'est un sol : obsolète car la manière de faire les rectangles à changer
				}else {
					r.setFill(this.colorOfWalls);
					r.setStroke(this.colorOfWalls);
					r.setStrokeWidth(1);
				}
				r.setOnMouseClicked(e->{
					this.select(e, r.getCoord());
				});
				
				root.getChildren().add(r);
			}
		}
		return root;
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
				if(e.isShiftDown()) {
					this.maze.move(c);
				}
			}else{
				this.invalidSelection();
				
			}
		}else {
			System.out.println("Pas de sélection : monster IA");
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
	public int getWindow_height() {
		return window_height;
	}

	/**
	 * Définit la hauteur de la fenêtre de jeu.
	 * 
	 * @param window_height La nouvelle hauteur de la fenêtre.
	 */
	public void setWindow_height(int window_height) {
		this.window_height = window_height;
	}

	/**
	 * Obtient la largeur de la fenêtre de jeu.
	 * 
	 * @return La largeur de la fenêtre.
	 */
	public int getWindow_width() {
		return window_width;
	}

	/**
	 * Définit la largeur de la fenêtre de jeu.
	 * 
	 * @param window_width La nouvelle largeur de la fenêtre.
	 */
	public void setWindow_width(int window_width) {
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
}
