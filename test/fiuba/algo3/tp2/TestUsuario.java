package fiuba.algo3.tp2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fiuba.algo3.tp2.excepciones.*;
import fiuba.algo3.tp2.juego.JuegoCraft;
import fiuba.algo3.tp2.juego.Usuario;
import fiuba.algo3.tp2.mapa.*;
import fiuba.algo3.tp2.objetosDelMapa.edificios.*;
import fiuba.algo3.tp2.objetosDelMapa.unidades.*;
public class TestUsuario {
	
	private JuegoCraft juego;
	private Usuario user;
	
	private EdificioCentralTerran edCentral1;
	
	@Before
	public void setUp(){
		
		juego = new JuegoCraft();
		user = new Usuario(juego);
		
		try {
			juego.cargarJugador("martin", "verde", "terran");
			juego.cargarJugador("facu", "rojo", "protos");
			
		} catch (CargaJugadorInvalida e) {}	
		
		Mapa mapa1 = new Mapa(new Tamanio(50,50));
		juego.cargarMapa(mapa1);
		juego.iniciarPartida();
		
		edCentral1 = (EdificioCentralTerran) juego.jugadores().get(0).edificioCentral();
	}
	
	private void pasarRondaDeTurnos(int cantidadTurnos){
		
		for(int i = 0; i < cantidadTurnos; i++){
			user.pasarTurno();
			user.pasarTurno();
		}
	}
	
	@Test
	public void testCrearUsuario(){
		
		assertEquals(user.jugadorActual().nombre(), "martin");
	}

	@Test
	public void testPasarTurno(){		
						
		user.pasarTurno();		
		assertEquals(juego.jugadorActual().nombre(), "facu");
	}
	
	@Test
	public void testPedirEdificioCentral(){
		
		assertEquals(user.jugadorActual().edificioCentral().nombre(), "Central terran");
		
		user.pasarTurno();
		assertEquals(user.jugadorActual().edificioCentral().nombre(), "Central protos");
	}
	
	@Test
	public void testConstruirEdificioDeposito(){
		
		Posicion posicion = new Posicion(1,4);
		
		try {
			Edificio edificio = edCentral1.construirAsentamiento();
			
			assertEquals(user.jugadorActual().recursos().mineral(), 900);
			
			user.colocarEdificio(edificio, posicion);			
			
			assertEquals(user.jugadorActual().edificios(), 0);
			
			this.pasarRondaDeTurnos(6);
			
			assertEquals(user.jugadorActual().edificios(), 1);
			assertEquals(edificio.posicion(), posicion);		
			assertEquals(user.jugadorActual().suministros().suministroTotal(),10);
			
		} catch (RecursosInsuficientes | PosicionInvalida e) {}		
	}
	
	@Test
	public void testConstruirEdificioBarracaYunidadMarine(){
		
		Posicion posicion = new Posicion(1,4);
		
		try {
			EdificioBarraca barraca = edCentral1.construirBarraca();
			user.colocarEdificio(barraca, posicion);		
			
			this.pasarRondaDeTurnos(12);
			
			Unidad marine = barraca.construirMarine();
			
			assertEquals(user.jugadorActual().unidades(), 0);
			
			this.pasarRondaDeTurnos(3);
			
			assertEquals(user.jugadorActual().unidades(), 1);
			assertFalse(marine.posicion() == null );
			
		} catch (RecursosInsuficientes | PosicionInvalida e) {}		
	}	
	
	@Test
	public void TestJugadorCreaPuertoEstelarTerranYNaveTransporteTerran(){
		
		try {
			EdificioBarraca barraca = edCentral1.construirBarraca();
			Posicion posicionBarraca = new Posicion(5,6);
			user.colocarEdificio(barraca, posicionBarraca);
			
			this.pasarRondaDeTurnos(12);
			
			EdificioFabrica fabrica = edCentral1.construirFabrica();
			Posicion posicionFabrica = new Posicion(1,4);
			user.colocarEdificio(fabrica, posicionFabrica);
			
			this.pasarRondaDeTurnos(12);
			
			EdificioPuertoEstelarTerran puerto = edCentral1.construirPuertoEstelarTerran();
			Posicion posicionPuerto = new Posicion(1,7);
			user.colocarEdificio(puerto, posicionPuerto);
					
			this.pasarRondaDeTurnos(10);
			puerto.construirNaveTransporte();
			
		} catch (RecursosInsuficientes | RequerimientosInsuficientes | PosicionInvalida e) {}
		
		assertEquals(user.jugadorActual().recursos().mineral(), 400);
		assertEquals(user.jugadorActual().recursos().vespeno(), 700);
		assertEquals(user.jugadorActual().suministros().suministroGastados(), 2);
		assertEquals(user.jugadorActual().unidades(), 0);
		
		this.pasarRondaDeTurnos(7);
		assertEquals(user.jugadorActual().unidades(), 1);
	}
}
