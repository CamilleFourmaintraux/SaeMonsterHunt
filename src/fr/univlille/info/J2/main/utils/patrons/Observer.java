/**
 * Le package main.utils contient des classes et des méthodes utilitaires pour diverses opérations
 * au sein de l'application. Il comprend des fonctionnalités pour la gestion de l'observateur (Observer)
 * et du sujet (Subject) pour le modèle de conception Observer, ainsi que des méthodes utilitaires
 * pour la génération de nombres aléatoires et la création d'arrière-plans graphiques.
 */
package fr.univlille.info.J2.main.utils.patrons;

/**
 * L'interface Observer définit les méthodes que les objets observateurs doivent implémenter
 * pour être notifiés des changements dans un objet observable (implémentant l'interface Subject).
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public interface Observer {
	/**
     * Cette méthode est appelée lorsqu'un changement est notifié par le sujet observé.
	 *
	 * @param s Le sujet observable ayant émis la notification de changement.
	 */
	public void update(Subject s);
	/**
     * Cette méthode est appelée lorsqu'un changement est notifié par le sujet observé,
     * et elle peut recevoir des informations suppl�mentaires sous la forme d'un objet.
	 *
	 * @param s Le sujet observable ayant émis la notification de changement.
	 * @param o Un objet contenant des informations supplémentaires sur le changement.
	 */
	public void update(Subject s, Object o);
}
