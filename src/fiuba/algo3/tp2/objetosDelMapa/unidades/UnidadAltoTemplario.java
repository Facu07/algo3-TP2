package fiuba.algo3.tp2.objetosDelMapa.unidades;

import javax.swing.ImageIcon;

import fiuba.algo3.tp2.juego.RecursosDelJugador;


public class UnidadAltoTemplario extends UnidadTerrestre {

	public UnidadAltoTemplario() {
		
		super("Alto templario", 40, 40, 7, 7, new RecursosDelJugador(50,150), 2, 0, 0, 0, 0, 2, new ImageIcon(UnidadAltoTemplario.class.getResource("/imagenes/unidadesTerrestre.jpg")));
	}
}