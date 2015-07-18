package fiuba.algo3.tp2.mapa;

public class Celda {
	
	protected Object contenido;
	
	public Celda(Object unContenido){
		contenido = unContenido;
	}

	public Object contenido() {	
		return contenido;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		return true;
	}
}