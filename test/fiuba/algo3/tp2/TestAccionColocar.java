package fiuba.algo3.tp2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fiuba.algo3.tp2.acciones.AccionColocarEdificio;
import fiuba.algo3.tp2.acciones.AccionColocarEnSuelo;
import fiuba.algo3.tp2.acciones.AccionColocarUnidad;
import fiuba.algo3.tp2.acciones.AccionPreguntar;
import fiuba.algo3.tp2.excepciones.PosicionInvalida;
import fiuba.algo3.tp2.juego.RecursosDelJugador;
import fiuba.algo3.tp2.mapa.Mapa;
import fiuba.algo3.tp2.mapa.Posicion;
import fiuba.algo3.tp2.mapa.Tamanio;
import fiuba.algo3.tp2.objetosDelMapa.Recurso;
import fiuba.algo3.tp2.objetosDelMapa.RecursoGasVespeno;
import fiuba.algo3.tp2.objetosDelMapa.RecursoMineral;
import fiuba.algo3.tp2.objetosDelMapa.Terreno;
import fiuba.algo3.tp2.objetosDelMapa.edificios.EdificioCentroMineral;
import fiuba.algo3.tp2.objetosDelMapa.edificios.EdificioDeposito;
import fiuba.algo3.tp2.objetosDelMapa.edificios.EdificioEnConstruccion;
import fiuba.algo3.tp2.objetosDelMapa.edificios.EdificioRefineria;
import fiuba.algo3.tp2.objetosDelMapa.unidades.UnidadEspectro;
import fiuba.algo3.tp2.objetosDelMapa.unidades.UnidadGolliat;
import fiuba.algo3.tp2.objetosDelMapa.unidades.UnidadMarine;

public class TestAccionColocar {
	
	private Mapa mapa;
	private AccionPreguntar preguntar;
	
	private AccionColocarEnSuelo colocarSuelo;
	private AccionColocarUnidad	colocarUnidad;
	private AccionColocarEdificio colocarEdificio;
	
	private EdificioEnConstruccion edificioEnConstruccion = new EdificioEnConstruccion(new EdificioDeposito());
		
	@Before
	public void setUp(){
		mapa = new Mapa(new Tamanio(10,10));
		preguntar = new AccionPreguntar(mapa);
		
		colocarSuelo = new AccionColocarEnSuelo(mapa);
		colocarUnidad = new AccionColocarUnidad(mapa);
		colocarEdificio = new AccionColocarEdificio(mapa);		
	}
	
	/************************************ Tests Colocar En Suelo **************************************/
	
	@Test
	public void testColocarTerreno(){
				
		Posicion posicion = new Posicion(1,1);
		try {
			colocarSuelo.colocarTerrenoEn(posicion);
			
			assertTrue(mapa.contenido(posicion, mapa.suelo) instanceof Terreno);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarTerrenoEnTodoElMapa(){
				
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			
			for (int i = 1; i<= mapa.tamanio().enX(); i++)
				for (int j = 1; j<= mapa.tamanio().enY(); j++)

					assertTrue(preguntar.hayTerreno(new Posicion(i,j)));
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarRecursoMineral(){
			
		RecursoMineral mineral = new RecursoMineral(2000);
		Posicion posicion = new Posicion(1,1);
		
		try {
			colocarSuelo.colocarRecurso(posicion, mineral);
			assertTrue(preguntar.hayMineral(posicion));		
			
		} catch (PosicionInvalida e) {}		
	}
		
	@Test
	public void testColocarRecursoGasVespeno() {
				
		RecursoGasVespeno gasVespeno = new RecursoGasVespeno(2000);
		Posicion posicion = new Posicion(1,1);
		
		try {
			colocarSuelo.colocarRecurso(posicion, gasVespeno);
			
			assertTrue(preguntar.hayGasVespeno(posicion));	
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarRecursoSobreTerreno() {
				
		RecursoGasVespeno gasVespeno = new RecursoGasVespeno(2000);
		Posicion posicion = new Posicion(1,1);
		
		try {
			colocarSuelo.colocarTerrenoEn(posicion);
			colocarSuelo.colocarRecurso(posicion, gasVespeno);
			
			assertTrue(preguntar.hayGasVespeno(posicion));
			
		} catch (PosicionInvalida e) {}		
	}
	
	/********************************** Tests Colocar Unidad ***************************************/
	
	@Test
	public void testNoDebeColocarUnidadTerrestreCuandoNoHayTerreno(){
				
		UnidadMarine unidadTerrestre = new UnidadMarine();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarUnidad.realizar(posicion,unidadTerrestre);
			
			assertFalse(preguntar.estaOcupadoTierra(posicion));
			
		} catch (PosicionInvalida e) {}
	}
	
	@Test
	public void testColocarUnidadTerrestre(){
				
		UnidadGolliat unidadTerrestre = new UnidadGolliat();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarUnidad.realizar(posicion, unidadTerrestre);
			
			assertTrue(preguntar.hayEnTierra(posicion, unidadTerrestre));
			assertEquals(unidadTerrestre.posicion(), posicion);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarUnidadTerrestreSobreOtra(){
				
		UnidadMarine unidadTerrestre1 = new UnidadMarine();
		UnidadGolliat unidadTerrestre2 = new UnidadGolliat();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarUnidad.realizar(posicion, unidadTerrestre1);
			colocarUnidad.realizar(posicion, unidadTerrestre2);
						
		} catch (PosicionInvalida e) {}		
		
		try {
			assertTrue(preguntar.hayEnTierra(posicion, unidadTerrestre1));
			assertEquals(unidadTerrestre1.posicion(), posicion);
			assertEquals(unidadTerrestre2.posicion(), null);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarUnidadAerea(){
				
		UnidadEspectro unidadAerea = new UnidadEspectro();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarUnidad.realizar(posicion, unidadAerea);
			
			assertTrue(preguntar.hayEnAire(posicion, unidadAerea));
			assertEquals(unidadAerea.posicion(), posicion);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarUnidadAereaSobreOtra(){
				
		UnidadEspectro unidadAerea1 = new UnidadEspectro();
		UnidadEspectro unidadAerea2 = new UnidadEspectro();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarUnidad.realizar(posicion, unidadAerea1);
			colocarUnidad.realizar(posicion, unidadAerea2);
						
		} catch (PosicionInvalida e) {}		
		
		try {
			assertTrue(preguntar.hayEnAire(posicion, unidadAerea1));
			assertEquals(unidadAerea1.posicion(), posicion);
			assertEquals(unidadAerea2.posicion(), null);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarUnidadAereaYTerrestreUnaSobreOtra(){
				
		UnidadEspectro unidadAerea = new UnidadEspectro();
		UnidadMarine unidadTerrestre = new UnidadMarine();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarSuelo.colocarTerrenoEn(posicion);
			colocarUnidad.realizar(posicion, unidadTerrestre);
			colocarUnidad.realizar(posicion, unidadAerea);
			
			assertTrue(preguntar.hayEnTierra(posicion, unidadTerrestre));
			assertTrue(preguntar.hayEnAire(posicion, unidadAerea));
			assertEquals(unidadAerea.posicion(), posicion);
			assertEquals(unidadTerrestre.posicion(), posicion);
						
		} catch (PosicionInvalida e) {}			
	}
	
	/****************************** Tests Colocar Edificio *****************************************/
	
	@Test
	public void testColocarEdificioSobreTerreno(){
				
		EdificioDeposito edificio = new EdificioDeposito();
		Posicion posicion = new Posicion(1, 1);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarEdificio.realizar(posicion, edificio);
			
			assertTrue(preguntar.hayEnTierra(posicion, edificio));
			assertTrue(preguntar.hayEnTierra(posicion.obtenerNuevaMovidaEn(1, 0), edificio));
			assertTrue(preguntar.hayEnTierra(posicion.obtenerNuevaMovidaEn(0, 1), edificio));
			assertTrue(preguntar.hayEnTierra(posicion.obtenerNuevaMovidaEn(1, 1), edificio));
			
			assertEquals(edificio.posicion(), posicion);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarEdificioSinTerreno(){
				
		EdificioDeposito edificio = new EdificioDeposito();
		Posicion posicion = new Posicion(1, 1);
		
		try {
			colocarEdificio.realizar(posicion, edificio);
			
		} catch (PosicionInvalida e) {}	
		
		try {
			assertFalse(preguntar.estaOcupadoTierra(posicion));
			assertEquals(edificio.posicion(), null);
			
		} catch (PosicionInvalida e) {}
	}
		
	@Test
	public void testColocarEdificioRecolectorDeMinerial(){ 
			
		Recurso mineral = new RecursoMineral(1000);
		EdificioCentroMineral recolectorMineral = new EdificioCentroMineral(new RecursosDelJugador(1000,1000));
		Posicion posicion = new Posicion(4,4);
		
		try {
			colocarSuelo.colocarRecurso(posicion, mineral);
			colocarEdificio.realizar(posicion, recolectorMineral);
			
			assertTrue(preguntar.hayMineral(posicion));
			assertTrue(preguntar.hayEnTierra(posicion, recolectorMineral));
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarEdificioRecolectorDeMinerialSinRecursoMineral(){ 
			
		EdificioCentroMineral recolectorMineral = new EdificioCentroMineral(new RecursosDelJugador(1000,1000));
		Posicion posicion = new Posicion(4,4);
		
		try {
			colocarEdificio.realizar(posicion, recolectorMineral);			
			
		} catch (PosicionInvalida e) {}		
		
		try {
			assertFalse(preguntar.estaOcupadoTierra(posicion));
			
		} catch (PosicionInvalida e) {}
	}
		
	@Test
	public void testColocarEdificioRecolectorDeGasVespeno(){ 
				
		Recurso gasVespeno = new RecursoGasVespeno(2000);
		EdificioRefineria recolectorGasVespeno = new EdificioRefineria(new RecursosDelJugador(1000,1000));
		Posicion posicion = new Posicion(4,4);
		
		try {
			colocarSuelo.colocarRecurso(posicion, gasVespeno);
			colocarEdificio.realizar(posicion, recolectorGasVespeno);
			
			assertTrue(preguntar.hayGasVespeno(posicion));
			assertTrue(preguntar.hayEnTierra(posicion, recolectorGasVespeno));
			
		} catch (PosicionInvalida e) {}			
	}
	
	@Test
	public void testNoDebeColocarEdificioRecolectorDeGasVespenoSobreRecursoMineral(){ 
				
		Recurso mineral = new RecursoMineral(2000);
		EdificioRefineria recolectorGasVespeno = new EdificioRefineria(new RecursosDelJugador(1000,1000));
		Posicion posicion = new Posicion(4,4);
		
		try {
			colocarSuelo.colocarRecurso(posicion, mineral);
			colocarEdificio.realizar(posicion, recolectorGasVespeno);
						
		} catch (PosicionInvalida e) {}	
		
		try {
			assertTrue(preguntar.hayMineral(posicion));
			assertFalse(preguntar.estaOcupadoTierra(posicion));
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarEdificioEnConstruccionSobreOtroIgual(){
				
		EdificioEnConstruccion edificio2 = new EdificioEnConstruccion( new EdificioDeposito());
		Posicion posicion1 = new Posicion(5,5);
		Posicion posicion2 = new Posicion(6,6);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarEdificio.realizar(posicion1, edificioEnConstruccion);
			colocarEdificio.realizar(posicion2, edificio2);
			
		} catch (PosicionInvalida e) {}
		
				
		try {
			assertTrue(preguntar.hayEnTierra(posicion1, edificioEnConstruccion));
			assertTrue(preguntar.hayEnTierra(posicion1.obtenerNuevaMovidaEn(1, 0), edificioEnConstruccion));
			assertTrue(preguntar.hayEnTierra(posicion1.obtenerNuevaMovidaEn(0, 1), edificioEnConstruccion));
			assertTrue(preguntar.hayEnTierra(posicion2, edificioEnConstruccion));
			
			assertFalse(preguntar.estaOcupadoTierra(posicion2.obtenerNuevaMovidaEn(1, 0)));
			assertFalse(preguntar.estaOcupadoTierra(posicion2.obtenerNuevaMovidaEn(0, 1)));
			assertFalse(preguntar.estaOcupadoTierra(posicion2.obtenerNuevaMovidaEn(1, 1)));
			
			assertEquals(edificioEnConstruccion.posicion(), posicion1);
			assertEquals(edificio2.posicion(), null);
			
		} catch (PosicionInvalida e) {}
	}
	
	@Test
	public void testNoDebeColocarEdificioEnConstruccionSobreOtroIgualBis(){
				
		EdificioEnConstruccion edificio1 = new EdificioEnConstruccion( new EdificioDeposito());
		EdificioDeposito edificio2 = new EdificioDeposito();
		Posicion posicion1 = new Posicion(5,5);
		Posicion posicion2 = new Posicion(6,6);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarEdificio.realizar(posicion2, edificio2);
			colocarEdificio.realizar(posicion1, edificioEnConstruccion);
			
		} catch (PosicionInvalida e) {}
						
		try {
			assertTrue(preguntar.hayEnTierra(posicion2, edificio2));
			assertTrue(preguntar.hayEnTierra(posicion2.obtenerNuevaMovidaEn(1, 0), edificio2));
			assertTrue(preguntar.hayEnTierra(posicion2.obtenerNuevaMovidaEn(0, 1), edificio2));
			assertTrue(preguntar.hayEnTierra(posicion2.obtenerNuevaMovidaEn(1,1), edificio2));
			
			assertFalse(preguntar.estaOcupadoTierra(posicion1));
			assertFalse(preguntar.estaOcupadoTierra(posicion1.obtenerNuevaMovidaEn(1, 0)));
			assertFalse(preguntar.estaOcupadoTierra(posicion1.obtenerNuevaMovidaEn(0, 1)));
			
			assertEquals(edificio2.posicion(), posicion2);
			assertEquals(edificio1.posicion(), null);
			
		} catch (PosicionInvalida e) {}
	}
	
	@Test
	public void testNoDebeColocarEdificioSinoEntraEnElMapa(){
				
		EdificioDeposito edificio = new EdificioDeposito();
		Posicion posicion = new Posicion(10,10);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarEdificio.realizar(posicion, edificio);
			
		} catch (PosicionInvalida e) {}
						
		try {
			assertFalse(preguntar.estaOcupadoTierra(posicion));			
			assertEquals(edificio.posicion(), null);
			
		} catch (PosicionInvalida e) {}
	}
	
	/*********************** test colocar Edificios Y Unidades *******************************/
	
	@Test
	public void testNoDebeColocarUnidadTerrestreSobreEdificio(){
				
		UnidadMarine unidadTerrestre = new UnidadMarine();
		EdificioDeposito edificio = new EdificioDeposito();
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarEdificio.realizar(posicion, edificio);
			colocarUnidad.realizar(posicion, unidadTerrestre);
			
		} catch (PosicionInvalida e) {}		
				
		try {
			assertTrue(preguntar.hayEnTierra(posicion, edificio));
			assertEquals(unidadTerrestre.posicion(), null);
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testNoDebeColocarEdificioEnConstruccionSobreUnidadTerrestre(){
				
		UnidadMarine unidadTerrestre = new UnidadMarine();
		
		Posicion posicion1 = new Posicion(5,5);
		Posicion posicion2 = new Posicion(6,6);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarUnidad.realizar(posicion2, unidadTerrestre);
			colocarEdificio.realizar(posicion1, edificioEnConstruccion);
			
		} catch (PosicionInvalida e) {}
		
		try {
			assertFalse(preguntar.estaOcupadoAire(posicion1));
			assertFalse(preguntar.estaOcupadoAire(posicion1.obtenerNuevaMovidaEn(1, 0)));
			assertFalse(preguntar.estaOcupadoAire(posicion1.obtenerNuevaMovidaEn(0, 1)));
			
			assertEquals(edificioEnConstruccion.posicion(), null);
			
			assertTrue(preguntar.hayEnTierra(posicion2, unidadTerrestre));
			
		} catch (PosicionInvalida e) {}		
	}
	
	@Test
	public void testColocarEdificioEnConstruccionYUnidadAerea(){
				
		UnidadEspectro unidadAerea = new UnidadEspectro();
		
		Posicion posicion = new Posicion(5,5);
		
		try {
			colocarSuelo.colocarTerrenoEnTodoElMapa();
			colocarUnidad.realizar(posicion, unidadAerea);
			colocarEdificio.realizar(posicion, edificioEnConstruccion);
			
		} catch (PosicionInvalida e) {}
		
		try {
			assertTrue(preguntar.hayEnTierra(posicion, edificioEnConstruccion));
			assertTrue(preguntar.hayEnAire(posicion, unidadAerea));
			
		} catch (PosicionInvalida e) {}		
	}
}
