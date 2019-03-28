package org.neuroph.core;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Zoran, Tijana
 */
public class ConnectionTest {

	Neuron fromNeuron, toNeuron;
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		fromNeuron = new Neuron();
		toNeuron = new Neuron();
	}


	@Test(expected = IllegalArgumentException.class)
	public void connectionIn() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(fromNeuron, null);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("To neuron in connection cant be null!");

	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn2() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(null, toNeuron);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("From neuron in connection cant be null !");
	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn3() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(null, toNeuron, 12);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("From neuron in connection cant be null !");
	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn4() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(fromNeuron, null, 0);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("To neuron in connection cant be null!");

	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn5() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(fromNeuron, null, new Weight(4));
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("To neuron in connection cant be null!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn6() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(null, toNeuron, new Weight(2));
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("From neuron in connection cant be null !");
	}

	@Test(expected = IllegalArgumentException.class)
	public void connectionIn7() {
		@SuppressWarnings("unused")
		Connection conn = new Connection(fromNeuron, toNeuron, null);
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Connection Weight cant be null!");

	}

	@Test
	public void connectionIn8() {
		Connection conn = new Connection(fromNeuron, toNeuron, new Weight(23));
		Assert.assertNotNull(conn);
	}

	/**
	 * Test of getWeight method, of class Connection.
	 */
	@Test
	public void testGetWeight() {
		Connection instance = new Connection(fromNeuron, toNeuron, 0.777);
		double expResult = 0.777;
		double result = instance.getWeight().value;
		assertEquals(expResult, result, 0d);
	}

	/**
	 * Test of setWeight method, of class Connection.
	 */
	@Test
	public void testSetWeight() {
		Weight weight = new Weight(0.88);
		Connection instance = new Connection(fromNeuron, toNeuron);
		instance.setWeight(weight);
		assertEquals(weight, instance.getWeight());
	}

	/**
	 * Test of setWeight method, of class Connection.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void throwsExceptionWhenWeightIsSetToNull() {
		Weight weight = null;
		Connection instance = new Connection(fromNeuron, toNeuron);
		instance.setWeight(weight);
	}

	/**
	 * Test of getInput method, of class Connection.
	 */
	@Test
	public void testGetInput() {
		Connection instance = new Connection(fromNeuron, toNeuron, 1.0);
		fromNeuron.setOutput(0.5);
		double expResult = 0.5;
		double result = instance.getInput();
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of getWeightedInput method, of class Connection.
	 */
	@Test
	public void testGetWeightedInput() {
		Connection instance = new Connection(fromNeuron, toNeuron, 0.5);
		fromNeuron.setOutput(0.5);
		double expResult = 0.25;
		double result = instance.getWeightedInput();
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of getFromNeuron method, of class Connection.
	 */
	@Test
	public void testGetFromNeuron() {
		Connection instance = new Connection(fromNeuron, toNeuron);
		Neuron expResult = fromNeuron;
		Neuron result = instance.getFromNeuron();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setFromNeuron method, of class Connection.
	 */
	@Test
	public void testSetFromNeuron() {
		Connection instance = new Connection(fromNeuron, toNeuron);
		assertEquals(fromNeuron, instance.getFromNeuron());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwExceptionOnNullSetFromNeuron() {
		Connection instance = new Connection(null, toNeuron);
	}

	/**
	 * Test of getToNeuron method, of class Connection.
	 */
	@Test
	public void testGetToNeuron() {
		Connection instance = new Connection(fromNeuron, toNeuron);
		Neuron expResult = toNeuron;
		Neuron result = instance.getToNeuron();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setToNeuron method, of class Connection.
	 */
	@Test
	public void testSetToNeuron() {
		Connection instance = new Connection(fromNeuron, toNeuron);
		assertEquals(toNeuron, instance.getToNeuron());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwExceptionOnNullSetToNeuron() {
		Connection instance = new Connection(fromNeuron, null);
	}
}
