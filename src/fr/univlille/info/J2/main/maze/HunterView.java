/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.maze;

import fr.univlille.info.J2.main.maze.cells.CellWithText;
import fr.univlille.info.J2.main.maze.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Observer;
import fr.univlille.info.J2.main.utils.Subject;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
public class HunterView implements Observer{
	/**
	 * Hauteur de la fenêtre, par défaut 500
	 */
	public double window_height;
	/**
	 * Largeur de la fenêtre, par défaut 500
	 */
	public double window_width;
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
	public Color colorOfWalls;
	/**
	 * Couleur des sols
	 */
	public Color colorOfFloors;
	/**
	 * Couleur du brouillard
	 */
	public Color colorOfFog;
	
	/**
	 * Nom du joueur incarnant le chasseur
	 */
	String hunterName;
	
	/**
	 * Sujet (pour le modèle observé)
	 */
	Maze maze;
	
	/**
	 * Sprite représentant le tir.
	 */
	CellWithText sprite_shot;
	/**
	 * Sprite de sélection.
	 */
	CellWithText selection;
	
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
	 * Groupe pour la gestion des images la map.
	 */
	Group group_img_map;
	
	/**
	 * Groupe pour la gestion des images des sprites.
	 */
	Group group_img_sprite;
	
	/**
	 * Groupe pour la gestion des textes.
	 */
	Group group_texts;
	
	BorderPane bp;
	
	Text notification;
	
	/**
	 * Scène pour l'affichage.
	 */
	Scene scene;

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
	public HunterView(double window_height, double window_width, int gap_X, int gap_y, int zoom, Color colorOfWalls,
		Color colorOfFloors, Color colorOfFog, Maze maze, String hunterName) {
		
		//Initiation de la fenetre
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.colorOfFog = colorOfFog;
		this.hunterName=hunterName;
		
		this.maze = maze;
		this.maze.attach(this);
		
		//Initiation des groupes
		this.group_img_map = new Group();
		this.group_img_sprite=new Group();
		this.group_sprite=new Group();
		this.group_texts=new Group();
		this.group_map = new Group();
		this.group_stage=new Group();
		
		this.group_stage.getChildren().add(group_img_map);
		this.group_stage.getChildren().add(group_img_sprite);
		this.group_stage.getChildren().add(group_texts);
		this.group_stage.getChildren().add(group_map);
		this.group_stage.getChildren().add(group_sprite);
		
		this.notification = new Text("Welcome to Monster Hunter - THE GAME");
		
		VBox vbox = new VBox();
		Label player_name = new Label(this.hunterName);
		player_name.setTextFill(Color.WHITE);
		this.notification.setFill(Color.WHITE);//.setTextFill(Color.WHITE);
		vbox.getChildren().addAll(player_name,this.notification);
		this.bp=new BorderPane(group_stage);
		this.bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.bp.setTop(vbox);
		
		
		//Scene
		this.scene=new Scene(bp, this.window_width,this.window_height,Color.BLACK);

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
		this.sprite_shot.setX(calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setVisible(true);
		if(this.maze.spotted) {
			this.notification.setText("ATTENTION - Le monstre à été détecté dans l'une de vos cases \ndéjà découverte lors d'un tour précédent !");
		}else {
			this.notification.setText("");
		}
		
	}
	
	/**
     * Méthode pour dessiner le labyrinthe et les éléments associés.
	 */
	public void draw() {
		for(int h=0; h<this.maze.hunter.traces.length; h++) {
			for(int l=0; l<this.maze.hunter.traces[h].length; l++) {
				//Codage des rectangles permettant le contrôle
				CellWithText cell = new CellWithText(l, h, zoom, this.colorOfFog,Color.DARKGREY,1,this.gap_X,this.gap_Y,new Text(""),Utils.floor_dungeon);
				cell.setFocusTraversable(false);
				cell.setOnMouseEntered(event -> {
					this.select(cell);
					this.scene.setOnMouseClicked(e -> { //Vérifie le clic sur la scene et sur sur la cell sinon bug (pour une raison inconnue)
						this.selectionLocked(cell);
					});
				});
				
				group_map.getChildren().add(cell);
				this.group_texts.getChildren().add(cell.getText());
				}
			}
	}
	
	
	/*//TODO Corriger et finir les fonctions redraw
	public void redraw(int new_gap_X, int new_gap_Y, int new_zoom) {
		for(Node e:this.group_map.getChildren()) {
			CellWithText r = (CellWithText)e;
			this.redrawCell(r, new_gap_X, new_gap_Y, new_zoom);
		}
		for(Node e:this.group_sprite.getChildren()) {
			CellWithText r = (CellWithText)e;
			this.redrawCell(r, new_gap_X, new_gap_Y, new_zoom);
		}
	}
	
	public void redrawCell(CellWithText r,int new_gap_X, int new_gap_Y, int new_zoom) {
		int y = this.calculCoordX(r);
		int x = this.calculCoordY(r);
		this.setZoom(new_zoom);
		this.setGap_X(new_gap_X);
		this.setGap_Y(new_gap_Y);
		r.setX(calculDrawX(x));
		r.setY(calculDrawY(y));
		r.setWidth(zoom);
		r.setHeight(zoom);
		r.setX(x+(zoom/3));
		r.setY(y+(zoom/2));
	}*/
	
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
	 * Initialisation des sprites.
	 */
	private void initiateSprites() {
		//initialisation du sprite de selection
		this.selection =  new CellWithText(0,0, this.zoom, Color.TRANSPARENT, Color.RED, 3, this.gap_X, this.gap_Y, "Shot",Utils.monster_ocean);
		this.selection.setVisible(false);
		
		//initialisation du sprite du tir
		this.sprite_shot=new CellWithText(this.maze.hunter.getCoord(), this.zoom, Color.TRANSPARENT, Color.YELLOW, 5, this.gap_X, this.gap_Y, "Shot",Utils.monster_ocean);
		this.sprite_shot.setVisible(false);
		this.sprite_shot.setOnMouseEntered(event -> {
			this.select(this.sprite_shot);
			this.scene.setOnMouseClicked(e -> { //Vérifie le clic sur la scene et sur sur la cell sinon bug (pour une raison inconnue)
				this.selectionLocked(this.sprite_shot);
			});
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
		if(this.maze.getHunterIa().equals("Player")) {
			int y = this.calculCoordY(r);
			int x = this.calculCoordX(r);
			this.selection.setY(this.calculDrawY(y));
			this.selection.setX(this.calculDrawX(x));
			this.selection.toFront();
			this.selection.setVisible(true);
		}
	}
	
	public void selectionLocked(CellWithText cell) {
		if(this.maze.getHunterIa().equals("Player")) {
			int y = this.calculCoordY(cell);
			int x = this.calculCoordX(cell);
			ICoordinate c = new Coordinate(y,x);
			if(this.maze.shoot(c)) {
				this.actualizeCell(c);
			}
		}else {
			this.notification.setText("Pas de sélection possible : "+this.hunterName+" est une IA.");
		}
	}
	
	
	//TODO Aucun rectangle trouvé
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
	
	/**
     * Méthode pour actualiser une cellule du labyrinthe après un événement.
	 * 
	 * @param c Les coordonnées de la cellule à actualiser.
	 */
	public void actualizeCell(ICoordinate c) {
		try{
			CellWithText cwt = searchSprite(this.group_map, c);
			this.maze.revealCell(cwt,this.colorOfWalls,this.colorOfFloors);
		}catch(Exception exception) {
			System.out.println("Error in HunterView at method : actualizeCell => Aucun Rectangle Correspondant !");
		}
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
	public int getGap_Y() {
		return gap_Y;
	}

	/**
	 * Définit la position Y.
	 * @param gap_Y La nouvelle positon Y.
	 */
	public void setGap_Y(int gap_Y) {
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
	

}
