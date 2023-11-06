package test.utils;

import main.utils.Subject;

public class ConcreteSubject extends Subject{
	public void doSomething() {
		this.notifyObservers();
	}
	public void doSomethingWithData(Object o) {
		this.notifyObservers(o);
	}
}
