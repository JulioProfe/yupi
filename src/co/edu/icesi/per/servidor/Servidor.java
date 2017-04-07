package co.edu.icesi.per.servidor;


public class Servidor {
	
	static ComServer com;
	
	public static void main(String[] args) {
		com = new ComServer();
		com.start();
	}

}
