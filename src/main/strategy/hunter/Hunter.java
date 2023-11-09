/**
 * Le package main.strategy.hunter contient les classes relatives à la gestion
 * du chasseur dans le jeu. Il propose des stratégies pour le comportement du chasseur.
 */
package main.strategy.hunter;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import main.maze.cells.Coordinate;
import main.utils.Utils;
/**
 * La classe Hunter représente un chasseur dans le jeu. Elle implémente l'interface IHunterStrategy
 * pour définir différentes stratégies pour le chasseur.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Hunter implements IHunterStrategy{
	/**
     * Tableau pour stocker les traces laissées par le chasseur dans le labyrinthe.
	 */
	public int[][] traces;
	/**
	 * Les coordonnées du dernier tir du chasseur.
	 */
	public ICoordinate coord;
	/**
	 * Niveau de l'IA du chasseur.
	 */
	public String IA_level;
	
	/**
     * Constructeur de la classe Hunter, crée un Chasseur.
     *
     * @param height     	La hauteur du labyrinthe.
     * @param width      	La largeur du labyrinthe.
     * @param coord_hunter 	Les coordonnées du chasseur.
     * @param IA_level   	Le niveau de l'IA du chasseur.
	 */
	public Hunter(int height, int width, ICoordinate coord_hunter, String IA_level) {
		this.coord=coord_hunter;
		this.initialize(height, width);
		this.IA_level=IA_level;
	}
	
	/**
	 * Initialise le tableau des traces avec des valeurs par défaut.
     *
     * @param nbrRows Le nombre de lignes du labyrinthe.
     * @param nbrCols Le nombre de colonnes du labyrinthe.
     */
	@Override
	public void initialize(int nbrRows, int nbrCols) {
		this.traces=new int[nbrRows][nbrCols];
		for(int h=0; h<this.traces.length;h++) {
			for(int l=0; l<this.traces[h].length;l++) {
				traces[h][l]=-2;// -2 -> Inexploré, -1 -> Mur, 0 -> pas de trace >0 -> trace (tour)
			}
		}
		
	}
	
	/**
     * Définit la trace laissée par le chasseur à des coordonnées spécifiques.
     *
     * @param c     Les coordonnées où la trace doit être définie.
     * @param trace La valeur de la trace.
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	
	/**
     * Obtient la valeur de la trace laissée par le chasseur à des coordonnées spécifiques.
     *
     * @param c Les coordonnées de la trace à obtenir.
     * @return La valeur de la trace.
	 */
	public int getTrace(ICoordinate c) {
		return this.traces[c.getRow()][c.getCol()];
	}
	
	//Métodes à placer dans Maze
	/*public void shoot(ICoordinate newCoord) {
		this.coord=newCoord;
		this.monsterTurn=true;
		this.notifyObservers(newCoord);
	}
	
	public int[][] getTraces() {
		return traces;
	}

	public void actualizeTraces(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
		this.notifyObservers();
	}*/
	
	/**
     * Obtient le numéro de ligne actuelle du chasseur.
     *
     * @return Le numéro de ligne actuelle du chasseur.
	 */
	public int getRow() {
		return this.coord.getRow();
	}
	
	/**
     * Obtient le numéro de colonne actuelle du chasseur.
     *
     * @return Le numéro de colonne actuelle du chasseur.
	 */
	public int getCol() {
		return this.coord.getCol();
	}
	
	/**
     * Obtient les coordonnées actuelles du chasseur.
     *
     * @return Les coordonnées actuelles du chasseur.
	 */
	public ICoordinate getCoord() {
		return this.coord;
	}
	
	/**
     * Définit les coordonnées actuelles du chasseur.
     *
     * @param c Les nouvelles coordonnées du chasseur.
	 */
	public void setCoord(ICoordinate c) {
		this.coord=c;
	}
	
	/**
     * Implémentation de l'action de l'IA facile du chasseur.
     *
     * @return Les nouvelles coordonnées pour l'action de l'IA facile du chasseur.
	 */
	public ICoordinate easy_IA_action() {
		//Utils.wait(1);
		int y = Utils.random.nextInt(this.traces.length);
		int x = Utils.random.nextInt(this.traces[y].length);
		return new Coordinate(y,x);
	}
	
	/**
     * Implémentation de l'action de l'IA intermédiaire du chasseur.
     *
     * @return Les nouvelles coordonnées pour l'action de l'IA intermédiaire du chasseur.
	 */
	public ICoordinate moderate_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	/**
     * Implémentation de l'action de l'IA hardcore du chasseur.
     *
     * @return Les nouvelles coordonnées pour l'action de l'IA hardcore du chasseur.
	 */
	public ICoordinate hardcore_IA_action() {
		return this.easy_IA_action(); //TODO
	}
	
	/**
     * Méthode principale pour le jeu du chasseur. Implémente le comportement du chasseur.
     * 
     * @return Les nouvelles coordonnées pour l'action du chasseur.
	 */
	@Override
	public ICoordinate play() {
		if(this.IA_level.equals("IA-Easy")){
			return this.easy_IA_action();
		}else if(this.IA_level.equals("IA-Moderate")) {
			return this.moderate_IA_action();
		}else if(this.IA_level.equals("IA-Hardcore")) {
			return this.hardcore_IA_action();
		}
		return null;
	}
	
	/**
     * Méthode de mise à jour appelée lorsqu'un événement de cellule se produit.
     *
     * @param arg0 L'événement de cellule.	 
     */
	@Override
	public void update(ICellEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
