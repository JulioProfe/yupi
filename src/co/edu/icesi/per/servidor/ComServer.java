package co.edu.icesi.per.servidor;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ComServer extends Thread {
	
	private ServerSocket ss;
	private Socket s;
	private final int PORT = 5000;
	private boolean online;
	
	
	public ComServer() {
		try {
			ss = new ServerSocket(PORT);
			s = null;
			online = true;
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {
		while (online) {			
			try {
				if(s == null){
					s = ss.accept();
					enviarArchivos();
				}
				sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			} catch (SocketException e) {
				s = null;				
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
	}

	private void enviarArchivos() throws IOException {
		File[] nombres = cargarRutas();			
		DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
		salida.writeInt(nombres.length);
		for (int j = 0; j < nombres.length; j++) {
			byte[] buffer = cargarArchivo(nombres[j]);			
			salida.writeUTF(nombres[j].getName());
			salida.writeInt(buffer.length);
			salida.write(buffer);
			salida.flush();
		}		
	}

	private byte[] cargarArchivo(File archivo) throws IOException {		
		FileInputStream inFile =  new FileInputStream(archivo);
		byte[] buf = new byte[inFile.available()];
		inFile.read(buf);
		inFile.close();
		return buf;		
	}

	private File[] cargarRutas() {		
		File carpetaData = new File("data/images/");
		File[] arregloRetorno = carpetaData.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File arch, String name) {
				boolean retorno = (name.endsWith(".jpg")||name.endsWith(".png"));
				return retorno;
			}
		});	
		if(arregloRetorno!=null){
			System.out.println("cantidad de archivos: " + arregloRetorno.length);
		}else{
			System.out.println("no hay archivos");
		}
		return arregloRetorno;
	}

}
