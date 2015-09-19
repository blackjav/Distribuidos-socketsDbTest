import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
	
	public static final int PUERTO = 6666;
	ServerSocket socketServidor;
	public Servidor()
	{
		try
		{
			socketServidor=
					new ServerSocket(PUERTO);
			System.out.println("Todo funcionando");
		}catch(IOException e){
			e.printStackTrace();
		}
		run();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				Socket socketCliente=
						socketServidor.accept();
				@SuppressWarnings("unused")
				SQLConexion miConexion =
					new SQLConexion(socketCliente);
			}
		}catch(IOException e){e.printStackTrace();}
	}
	
	public static void main(String args[])
	{
		new Servidor();
	}

}
