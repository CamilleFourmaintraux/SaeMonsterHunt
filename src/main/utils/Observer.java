package main.utils;
/**
 * L'interface Observer d�finit les m�thodes que les objets observateurs doivent impl�menter
 * pour �tre notifi�s des changements dans un objet observable (impl�mentant l'interface Subject).
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public interface Observer {
	/**
     * Cette m�thode est appel�e lorsqu'un changement est notifi� par le sujet observ�.
	 * 
	 * @param s Le sujet observable ayant �mis la notification de changement.
	 */
	public void update(Subject s);
	/**
     * Cette m�thode est appel�e lorsqu'un changement est notifi� par le sujet observ�,
     * et elle peut recevoir des informations suppl�mentaires sous la forme d'un objet.
	 * 
	 * @param s Le sujet observable ayant �mis la notification de changement.
	 * @param o Un objet contenant des informations suppl�mentaires sur le changement.
	 */
	public void update(Subject s, Object o);
}
