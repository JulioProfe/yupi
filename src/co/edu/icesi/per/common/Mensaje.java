package co.edu.icesi.per.common;
import java.io.Serializable;


public class Mensaje implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8769017488557151628L;
	private String mensaje;
	
	public Mensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	 
}
