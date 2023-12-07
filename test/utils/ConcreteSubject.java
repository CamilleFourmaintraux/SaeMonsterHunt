package utils;

import fr.univlille.info.J2.main.utils.patrons.Subject;

public class ConcreteSubject extends Subject{
	public void doSomething() {
		this.notifyObservers();
	}
	public void doSomethingWithData(Object o) {
		this.notifyObservers(o);
	}
}
