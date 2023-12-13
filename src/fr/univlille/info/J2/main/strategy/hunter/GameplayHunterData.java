package fr.univlille.info.J2.main.strategy.hunter;

public class GameplayHunterData {
	/**
	 * La portée bonus pour la vision du hunter à chachun de ses tirs (sachant que seul le tir précis qui touche le monstre déclenche la fin de jeu)
	 */
	private int bonusRange;
	/**
	* String de la Strategy du chasseur
	**/
	private String IA;
	
	public GameplayHunterData(String IA, int bonusRange) {
		this.IA = IA;
		this.bonusRange = bonusRange;
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
}
