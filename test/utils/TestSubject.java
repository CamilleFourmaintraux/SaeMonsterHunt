package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSubject {
	ConcreteSubject cs = new ConcreteSubject();
	ConcreteObserver co = new ConcreteObserver();

	@Test
	public void test_attach() {
		assertFalse(co.updated);
		cs.attach(co);
		assertFalse(co.updated);
		cs.doSomething();
		assertTrue(co.updated);
	}

	@Test
	public void test_detach() {
		cs.attach(co);
		assertFalse(co.updated);
		cs.detach(co);
		assertFalse(co.updated);
		cs.doSomething();
		assertFalse(co.updated);
	}

	@Test
	public void test_notifyWithData() {
		cs.attach(co);
		assertFalse(co.updated);
		assertEquals(null,co.data);
		cs.doSomethingWithData("go");
		assertTrue(co.updated);
		assertEquals("go",co.data);
	}

}
