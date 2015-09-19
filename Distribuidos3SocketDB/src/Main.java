import java.io.PrintStream;
import java.net.Socket;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main implements ActionListener {
	private static final int puerto = 6666;
	private JButton jButton;
	private JTextField jtextField1;
	private JFrame jf;
//	private void jButton1ActionPerformed()
	
	public Main ()
	{
		jf = new JFrame("Iniciar");
		jf.setLayout(new FlowLayout());
		Dimension d = new Dimension();
		
//		Botones
		jButton = new JButton("Enviar consulta");
		jtextField1 = new JTextField(30);
	
		
//		agregando botones a la ventana 
		jf.add(jtextField1);
		jf.add(jButton);
		
		
//		Agregando listener
		jButton.addActionListener(this);
		
//		Propiedades de la ventana
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finaliza el programa cuando se da click en la X        
        jf.setResizable(false);//para configurar si se redimensiona la ventana
        jf.setLocation((int) ((d.getWidth()/2)+290), 50);//para ubicar inicialmente donde se muestra la ventana (x, y)
        jf.setSize(400, 250);//configurando tamaño de la ventana (ancho, alto)
        jf.setVisible(true);//configurando visualización de la venta
	}
	
	private void abrirSocket()
	{
		Socket socket = null;
		try
		{
			socket = new Socket("localhost",puerto);
			PrintStream salida = new PrintStream(socket.getOutputStream());
			salida.println(jtextField1.getText());
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			try
			{
				socket.close();
			}catch(Exception ee){}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		abrirSocket();
    }
	
	public static void main (String args[])
	{
		new Main();
	}
}
