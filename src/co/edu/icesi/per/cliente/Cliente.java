package co.edu.icesi.per.cliente;

public class Cliente {
	
	static ComClient com;
	
	public static void main(String[] args) {
		com = new ComClient();
		com.start();
	}

}
