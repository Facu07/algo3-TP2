package fiuba.algo3.tp2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAccionColocar {
	
	@Test
	public void testColocarTerrenoEnPosicionValida(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		Posicion posicion = new Posicion(1,1);
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		colocar.colocarTerrenoEn(posicion);
		
		assertTrue(preguntar.hayTerreno(posicion));
	}
	
	@Test
	public void testColocarTerrenoEnTodoElMapa(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		Posicion posicionAux;
		
		colocar.colocarTerrenoEnTodoElMapa();
		
		for (int i = 1; i<= mapa.tamanio().enX(); i++)
			for (int j = 1; j<=mapa.tamanio().enY(); j++){

				posicionAux = new Posicion(i,j);
				assertTrue(preguntar.hayTerreno(posicionAux));
		}
	}
	
	@Test
	public void testColocarRecursoMineral(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		RecursoMineral mineral = new RecursoMineral();
		Posicion posicion = new Posicion(1,1);
		
		colocar.colocarRecurso(posicion, mineral);
		
		assertTrue(preguntar.hayMineral(posicion));		
	}
		
	@Test
	public void testColocarRecursoGasVespeno(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		RecursoGasVespeno gasVespeno = new RecursoGasVespeno();
		Posicion posicion = new Posicion(1,1);
		
		colocar.colocarRecurso(posicion, gasVespeno);
		
		assertTrue(preguntar.hayGasVespeno(posicion));	
	}
	
	@Test
	public void testNoDebeColocarUnidadMarineCuandoNoHayTerreno(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		UnidadMarine marine = new UnidadMarine();
		Posicion posicion = new Posicion(5,5);
		
		colocar.colocarUnidadTerrestre(posicion,marine);
				
		assertFalse(preguntar.hayUnidadTerrestre(posicion));
	}
	
	@Test
	public void testColocarUnidadMarine(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		UnidadMarine marine = new UnidadMarine();
		Posicion posicion = new Posicion(5,5);
		
		colocar.colocarTerrenoEn(posicion);
		colocar.colocarUnidadTerrestre(posicion,marine);
				
		assertTrue(preguntar.hayUnidadTerrestre(posicion));
		assertEquals(marine.posicion(), posicion);
	}
	
	@Test
	public void testColocarEdificioBarraca(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		EdificioBarraca barraca = new EdificioBarraca();
		Posicion posicion1 = new Posicion(1, 1);
		Posicion posicion2 = new Posicion(1, 2);
		Posicion posicion3 = new Posicion(2, 1);
		Posicion posicion4 = new Posicion(2, 2);
		
		colocar.colocarTerrenoEnTodoElMapa();
		colocar.colocarEdificio(posicion1,barraca);
		
		assertTrue(preguntar.hayEdificio(posicion1));
		assertTrue(preguntar.hayEdificio(posicion2));
		assertTrue(preguntar.hayEdificio(posicion3));
		assertTrue(preguntar.hayEdificio(posicion4));
		assertEquals(barraca.posicion(), posicion1);
	}
	
	@Test
	public void testNoDebeColocarEdificioBarracaCuandoNoHayTerreno(){
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		EdificioBarraca barraca = new EdificioBarraca();		
		Posicion posicion1 = new Posicion(1, 1);
		Posicion posicion2 = new Posicion(1, 2);
		Posicion posicion3 = new Posicion(2, 1);
		Posicion posicion4 = new Posicion(2, 2);
		
		colocar.colocarEdificio(posicion1,barraca);
			
		assertFalse(preguntar.hayEdificio(posicion1));
		assertFalse(preguntar.hayEdificio(posicion2));
		assertFalse(preguntar.hayEdificio(posicion3));
		assertFalse(preguntar.hayEdificio(posicion4));
		assertTrue(barraca.posicion() == null);
	}
	
	@Test
	public void testColocarEdificioRecolectorDeMinerialSobreRecursoMineral(){ 
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		Recurso mineral = new RecursoMineral();
		EdificioCentroMineral recolectorMineral = new EdificioCentroMineral();
		Posicion posicion = new Posicion(4,4);
		
		colocar.colocarRecurso(posicion, mineral);
		colocar.colocarRecolectorDeMineral(posicion, recolectorMineral);
		
		assertTrue(preguntar.hayEnSuelo(posicion, mineral));
		assertTrue(preguntar.hayEnTierra(posicion, recolectorMineral));
	}
	
	@Test
	public void testNoDebeColocarEdificioRecolectorDeMinerialSinRecursoMineral(){ 
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		EdificioCentroMineral recolectorMineral = new EdificioCentroMineral();
		Posicion posicion = new Posicion(4,4);
		
		colocar.colocarRecolectorDeMineral(posicion, recolectorMineral);
		
		assertFalse(preguntar.hayEnTierra(posicion, recolectorMineral));		
	}
	
	@Test
	public void testColocarEdificioRecolectorDeGasVespenoSobreRecursoGasVespeno(){ 
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		Recurso gasVespeno = new RecursoGasVespeno();
		EdificioTerran recolectorGasVespeno = new EdificioRefineria();
		Posicion posicion = new Posicion(4,4);
		
		colocar.colocarRecurso(posicion, gasVespeno);
		colocar.colocarRecolectorDeGasVespeno(posicion, recolectorGasVespeno);
		
		assertTrue(preguntar.hayEnSuelo(posicion, gasVespeno));
		assertTrue(preguntar.hayEnTierra(posicion, recolectorGasVespeno));	
	}
	
	@Test
	public void testNoDebeColocarEdificioRecolectorDeGasVespenoSinRecursoGasVespeno(){ 
		
		Mapa mapa = new Mapa(new Tamanio(10,10));
		AccionColocar colocar = new AccionColocar(mapa);
		AccionPreguntar preguntar = new AccionPreguntar(mapa);
		
		EdificioTerran recolectorGasVespeno = new EdificioRefineria();
		Posicion posicion = new Posicion(4,4);
		
		colocar.colocarRecolectorDeGasVespeno(posicion, recolectorGasVespeno);
				
		assertFalse(preguntar.hayEnTierra(posicion, recolectorGasVespeno));		
	}
}