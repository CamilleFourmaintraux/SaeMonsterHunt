package fr.univlille.info.J2.main.strategy.hunter;

import java.io.Serializable;

public class GameplayHunterData implements Serializable{
	private static final long serialVersionUID = 4790709667460899455L;
	/**
	 * La portée bonus pour la vision du hunter à chachun de ses tirs (sachant que seul le tir précis qui touche le monstre déclenche la fin de jeu)
	 */
	private int bonusRange;
	/**
	* String de la Strategy du chasseur
	**/
	private String IA;
	/**
	* String du nom du joueur
	**/
	private String name;
	
	/**
	 * Crée un objet pour stocker les données du chassseur concernant les paramètres de gameplay.
	 * @param name				Nom du joueur associé à ce objet de données
     * @param hunter_IA 		Niveau de l'IA du chasseur.
     * @param bonusRange 		int correspondant à la portée de bonus du monstre.
	 */
	public GameplayHunterData(String name, String IA, int bonusRange) {
		this.IA = IA;
		this.bonusRange = bonusRange;
		this.name=name;
	}

	/**
	 * Obtient le niveau de l'IA du chasseur.
	 *
	 * @return Le niveau de l'IA du chasseur.
	 */
	public String getIA() {
		return IA;
	}

	/**
	 * Obtient la portée bonus de la vision du chasseur.
	 *
	 * @return La portée bonus de la vision du chasseur.
	 */
	public int getBonusRange() {
		return bonusRange;
	}
	
	
	public String getName() {
		return this.name;
	}

	public void setBonusRange(int bonusRange) {
		this.bonusRange = bonusRange;
	}

	public void setIA(String iA) {
		IA = iA;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
