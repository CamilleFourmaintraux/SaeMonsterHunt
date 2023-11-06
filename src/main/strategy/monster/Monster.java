/**
 * Le package main.strategy.monster contient les classes relatives à la gestion
 * du monstree dans le jeu. Il propose des stratégies pour le comportement du monstre
 * et les informations concernant la sortie du labyrinthe.
 */
package main.strategy.monster;

import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import main.maze.cells.Coordinate;
import main.utils.Utils;
/**
 * 
 * La classe Monster représente un Monster dans le jeu. Elle implémente l'interface IMonsterStrategy
 * pour définir différentes stratégies pour le monstre.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Monster implements IMonsterStrategy{
	/**
	 * La grille de murs du labyrinthe.
	 */
	public boolean[][] walls;
	/**
	 * Les coordonnées initiales du monstre.
	 */
	public ICoordinate coord;
	/**
	 * Le niveau de l'IA du monstre.
	 */
	public String IA_level;
	
	/**
     * Constructeur de la classe Monster, crée un Monstre.
	 * 
	 * @param walls 	La grille de murs du labyrinthe.
	 * @param coord		Les coordonnées initiales du monstre.
	 * @param IA_level	 Le niveau de l'IA du monstre.
	 */
	public Monster(boolean[][] walls,ICoordinate coord, String IA_level) {
		super();
		this.initialize(walls);
		this.coord = coord;
		this.IA_level=IA_level;
	}
	
	/**
     * Initialise les murs du labyrinthe pour le monstre.
     * 
     * @param walls La grille de murs du labyrinthe.
	 */
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
	}
	
	/**
     * Obtient la ligne de la coordonnée du monstre.
     *
     * @return La ligne de la coordonnée du monstre.
	 */
	public int getRow() {
		return this.coord.getRow();
	}
	
	/**
     * Obtient la colonne de la coordonnée du monstre.
     *
     * @return La colonne de la coordonnée du monstre.
	 */
	public int getCol() {
		return this.coord.getCol();
	}
	
	/**
     * Obtient les coordonnées du monstre.
     *
     * @return Les coordonnées du monstre.
	 */
	public ICoordinate getCoord() {
		return this.coord;
	}

	/**
     * Définit les coordonnées du monstre.
     *
     * @param c Les nouvelles coordonnées du monstre.
	 */
	public void setCoord(ICoordinate c) {
		this.coord=c;
	}

	/**
     * Obtient la grille de murs du labyrinthe.
     *
     * @return La grille de murs du labyrinthe.
	 */
	public boolean[][] getWalls() {
		return walls;
	}
	
	/**
     * Implémente l'action de l'IA facile du monstre.
     *
     * @return Les nouvelles coordonnées du monstre après une action de l'IA facile.
	 */
	public ICoordinate easy_IA_action() {
		int x = this.getCol()+(Utils.random.nextInt(3)-1);
		int y = this.getRow()+(Utils.random.nextInt(3)-1);
		return new Coordinate(y,x);
	}
	
	/**
     * Implémente l'action de l'IA modérée du monstre.
     *
     * @return Les nouvelles coordonnées du monstre après une action de l'IA modérée.
	 */
	public ICoordinate moderate_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	/**
     * Implémente l'action de l'IA hardcore du monstre.
     *
     * @return Les nouvelles coordonnées du monstre après une action de l'IA hardcore.
	 */
	public ICoordinate hardcore_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	/**
     * Méthode principale pour le jeu du monstre. Implémente le comportement du monstre.
     *
     * @return Les nouvelles coordonnées du monstre après son action.
	 */
	@Override
	public ICoordinate play() {
		// TODO Auto-generated method stub
		return this.easy_IA_action();
	}
	
	/**
     * Met à jour l'état du monstre en fonction d'un événement de cellule.
     *
     * @param arg0 L'événement de cellule qui a eu lieu.
     */
	@Override
	public void update(ICellEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
