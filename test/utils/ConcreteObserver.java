package utils;

import fr.univlille.info.J2.main.utils.patrons.Observer;
import fr.univlille.info.J2.main.utils.patrons.Subject;

public class ConcreteObserver implements Observer{

	public boolean updated = false;
	public Object data = null;

	@Override
	public void update(Subject s) {
		this.updated=true;

	}

	@Override
	public void update(Subject s, Object o) {
		this.updated=true;
		data = o;
	}

}
