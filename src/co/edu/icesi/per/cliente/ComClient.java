package co.edu.icesi.per.cliente;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ComClient extends Thread {

	private Socket s;
	private final int PORT = 5000;
	private boolean online;

	public ComClient() {
		online = true;
	}

	private void recibirArchivos() throws IOException {
		DataInputStream entrada = new DataInputStream(new BufferedInputStream(
				s.getInputStream()));
		int numArchivos = entrada.readInt();
		for (int i = 0; i < numArchivos; i++) {
			String nombre = entrada.readUTF();
			int cantBytes = entrada.readInt();
			byte[] buf = new byte[cantBytes];
			int j = 0;
			while (j < cantBytes) {
				buf[j] = entrada.readByte();
				j++;
			}
			guardarArchivo(nombre, buf);
		}
	}

	private void guardarArchivo(String nombre, byte[] buf) throws IOException {
		try {
			File archivo = new File("dataCliente/" + nombre);
			archivo.createNewFile();
			FileOutputStream salida = new FileOutputStream(archivo);
			salida.write(buf);
			salida.flush();
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (online) {
			if (s == null) {
				try {
					s = new Socket(InetAddress.getByName("127.0.0.1"), PORT);
					recibirArchivos();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
