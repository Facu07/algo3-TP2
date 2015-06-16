package fiuba.algo3.tp2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSuministro {
	
	@Test 
	public void testCrearSuministro(){
			
		Suministro suministro = new Suministro();
		
		assertEquals(suministro.suministroGastados(), 0);
		assertEquals(suministro.suministroTotal(),0);
	}
	
	@Test 
	public void testAgregarSuministro(){
			
		Suministro suministro = new Suministro();
		
		suministro.agregar(10);
		assertEquals(suministro.suministroGastados(), 0);
		assertEquals(suministro.suministroTotal(),10);
	}
	
	@Test 
	public void testQuitarSuministro(){
			
		Suministro suministro = new Suministro();
		
		suministro.agregar(10);
		suministro.quitar(5);
		
		assertEquals(suministro.suministroGastados(), 0);
		assertEquals(suministro.suministroTotal(),5);
	}
	
	@Test 
	public void testGastarSuministro(){
			
		Suministro suministro = new Suministro();
		
		suministro.agregar(10);
		suministro.gastar(5);
		
		assertEquals(suministro.suministroGastados(), 5);
		assertEquals(suministro.suministroTotal(),10);
	}
	
	@Test 
	public void testNoDeberiaGastarSuministroDeMas(){
			
		Suministro suministro = new Suministro();
		
		suministro.agregar(2);
		suministro.gastar(5);
		
		assertEquals(suministro.suministroGastados(), 0);
		assertEquals(suministro.suministroTotal(),2);
	}
	
	@Test 
	public void testReponerSuministro(){
			
		Suministro suministro = new Suministro();
		
		suministro.agregar(10);
		suministro.gastar(8);
		suministro.reponer(6);
		
		assertEquals(suministro.suministroGastados(), 2);
		assertEquals(suministro.suministroTotal(),10);
	}

}