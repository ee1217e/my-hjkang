package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	
	Calculator cal;
	
	@Before
	public void setup() {
		cal = new Calculator();
		System.out.println("setup");
	}

	@Test
	public void add() {
		//assertThat(cal.add(10, 20), is(7)); // assertEquals와 같다.
		assertEquals(30, cal.add(10, 20));
		System.out.println("add");
	}

	@Test
	public void minus() throws Exception {
		assertEquals(10, cal.minus(30, 20));
		System.out.println("minus");
	}
	
	@After
	public void teardown(){
		System.out.println("teardown");
	}
}
