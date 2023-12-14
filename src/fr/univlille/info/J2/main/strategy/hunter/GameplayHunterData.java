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
	
	private String name;
	
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
	protected String getIA() {
		return IA;
	}

	/**
	 * Obtient la portée bonus de la vision du chasseur.
	 *
	 * @return La portée bonus de la vision du chasseur.
	 */
	protected int getBonusRange() {
		return bonusRange;
	}
	
	
	protected String getName() {
		return this.name;
	}
}
