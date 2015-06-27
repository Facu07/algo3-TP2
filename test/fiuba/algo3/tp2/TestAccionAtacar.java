package fiuba.algo3.tp2;


import static org.junit.Assert.*;

import org.junit.Test;

public class TestAccionAtacar {
		
	@Test
	public void testUnidadAtacaAOtraAdistancia() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marineAtacante = new UnidadMarine();
		UnidadMarine marineAtacado = new UnidadMarine();
	
		Posicion posicionAtacante = new Posicion(4,4);
		Posicion posicionAtacado = new Posicion(6,6);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marineAtacante);
		colocar.colocarUnidadTerrestre(posicionAtacado, marineAtacado);
	
		atacar.atacarTierra(posicionAtacado, marineAtacante);

		assertEquals(34, marineAtacado.vidaActual());	
	}
	
	@Test
	public void testUnidadAtacaAOtraSinDistancia() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadZealot unidadMelee = new UnidadZealot();
		UnidadMarine unidadAtacada = new UnidadMarine();
	
		Posicion posicionAtacante = new Posicion(4,5);
		Posicion posicionAtacado = new Posicion(5,5);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, unidadMelee);
		colocar.colocarUnidadTerrestre(posicionAtacado, unidadAtacada);
	
		atacar.atacarTierra(posicionAtacado, unidadMelee);

		assertEquals(32, unidadAtacada.vidaActual());	
	}
	
	@Test
	public void testUnidadNoDebeAtacarEstaFueraDeRango() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marineAtacante = new UnidadMarine();
		UnidadMarine marineAtacado = new UnidadMarine();
	
		Posicion posicionAtacante = new Posicion(3,3);
		Posicion posicionAtacado = new Posicion(8,3);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marineAtacante);
		colocar.colocarUnidadTerrestre(posicionAtacado, marineAtacado);
	
		atacar.atacarTierra(posicionAtacado, marineAtacante);

		assertEquals(40, marineAtacado.vidaActual());	
	}
	
	@Test
	public void testUnidadAtacaEdificio() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marine = new UnidadMarine();
		EdificioBarraca edificio = new EdificioBarraca(new RecursosDelJugador(1000,1000), new Suministro());
	
		Posicion posicionAtacante = new Posicion(3,3);
		Posicion posicionAtacado = new Posicion(7,3);
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marine);
		colocar.colocarEdificio(posicionAtacado, edificio);
	
		atacar.atacarTierra(posicionAtacado, marine);

		assertEquals(994, edificio.vidaActual());	
	}
	
	@Test
	public void testUnidadAtacaEdificioDesdeLaOtraEsquina() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marine = new UnidadMarine();
		EdificioBarraca edificio = new EdificioBarraca(new RecursosDelJugador(1000,1000), new Suministro());
	
		Posicion posicionAtacante = new Posicion(7,3);
		Posicion posicionEdificio = new Posicion(2,2);
		Posicion posicionDeAtaque = new Posicion(3,3);
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marine);
		colocar.colocarEdificio(posicionEdificio, edificio);
	
		atacar.atacarTierra(posicionDeAtaque, marine);

		assertEquals(994, edificio.vidaActual());	
	}
	
	@Test
	public void testUnidadMataAOtra() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marineAtacante = new UnidadMarine();
		UnidadMarine marineAtacado = new UnidadMarine();
	
		Posicion posicionAtacante = new Posicion(4,4);
		Posicion posicionAtacado = new Posicion(6,6);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marineAtacante);
		colocar.colocarUnidadTerrestre(posicionAtacado, marineAtacado);
	
		for(int i = 0; i<10; i++)
		atacar.atacarTierra(posicionAtacado, marineAtacante);

		assertEquals(0, marineAtacado.vidaActual());
		assertFalse(preguntar.hayEnTierra(posicionAtacado, marineAtacado));
	}
	
	@Test
	public void testUnidadTerrestreAtacaAUnidadAerea() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadMarine marineAtacante = new UnidadMarine();
		UnidadEspectro espectroAtacado = new UnidadEspectro();
	
		Posicion posicionAtacante = new Posicion(4,4);
		Posicion posicionDeAtaque = new Posicion(6,6);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadTerrestre(posicionAtacante, marineAtacante);
		colocar.colocarUnidadAerea(posicionDeAtaque, espectroAtacado);
	
		atacar.atacarAire(posicionDeAtaque, marineAtacante);

		assertEquals(114, espectroAtacado.vidaActual());
	}
	
	@Test
	public void testUnidadAereaAtacaAOtraUnidadAerea() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadEspectro espectroAtacante = new UnidadEspectro();
		UnidadEspectro espectroAtacado = new UnidadEspectro();
	
		Posicion posicionAtacante = new Posicion(4,4);
		Posicion posicionDeAtaque = new Posicion(6,6);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadAerea(posicionAtacante, espectroAtacante);
		colocar.colocarUnidadAerea(posicionDeAtaque, espectroAtacado);
	
		atacar.atacarAire(posicionDeAtaque, espectroAtacante);

		assertEquals(100, espectroAtacado.vidaActual());
	}
	
	@Test
	public void testUnidadAereaAtacaAUnidadTerrestre() {
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionAtacar atacar  = new AccionAtacar(mapa);
	
		UnidadEspectro espectroAtacante = new UnidadEspectro();
		UnidadGolliat golliatAtacado = new UnidadGolliat();
	
		Posicion posicionAtacante = new Posicion(4,4);
		Posicion posicionDeAtaque = new Posicion(6,6);	
	
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarUnidadAerea(posicionAtacante, espectroAtacante);
		colocar.colocarUnidadTerrestre(posicionDeAtaque, golliatAtacado);
	
		atacar.atacarTierra(posicionDeAtaque, espectroAtacante);

		assertEquals(117, golliatAtacado.vidaActual());
	}
}
