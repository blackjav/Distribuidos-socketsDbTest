import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


public class SQLConexion {
	
	protected Socket socketCliente;
	protected BufferedReader entrada;
	protected PrintStream salida;
	protected String cosulta;
	
	public SQLConexion(Socket socketCliente)
	{
		this.socketCliente = socketCliente;
		try
		{
			entrada = 
					new BufferedReader(new InputStreamReader(this.socketCliente.getInputStream()));
			salida = 
					new PrintStream(this.socketCliente.getOutputStream());
			
		}catch(IOException e )
		{
			e.printStackTrace();
			try
			{
				this.socketCliente.close();
				
			}catch(IOException e2){}
			return;
		
		}
		run();
		
	}
	
	public void run()
	{
		try
		{
			String consulta = entrada.readLine();
			System.out.println("Consulta a Ejecutar : " + consulta);
			ejecutarSQL(consulta);
		}catch(IOException e)
		{
		}finally {
			try
			{
				socketCliente.close();
			}catch(IOException e){}
		}
	}
	
	public void ejecutarSQL(String consulta)
	{
		Connection cnn	;
		Statement st; 
		ResultSet rs;
		ResultSetMetaData resultSetMetadata;
		boolean existenmMasFilas;
		String driver = "com.mysql.jdbc.Driver";
		String usuario ="root", clave="root", registro;
		int numeroColumnas, i;
		
		try
		{
			Class.forName(driver);
			cnn = DriverManager.getConnection("jdbc:mysql://localhost/socket",usuario,clave);
			st= cnn.createStatement();
			rs = st.executeQuery(consulta);
			existenmMasFilas = rs.next();
			if(!existenmMasFilas)
			{
				salida.println("No hay mas filas");
				return;
				
			}
			resultSetMetadata = rs.getMetaData();
			numeroColumnas = resultSetMetadata.getColumnCount();
			System.out.println(numeroColumnas+" columnas con resultado");
			while(existenmMasFilas)
			{
				registro = "";
				for(i=1 ; i <= numeroColumnas; i++)
				{
					registro = registro.concat(rs.getString(i)+ "   ");
				}
				salida.println(registro);
				System.out.println(registro);
				existenmMasFilas = rs.next();
			}
			rs.close();
			st.close();
			cnn.close();
		}catch(Exception es){
			System.out.println("" + es.toString());
			}
	}

}
