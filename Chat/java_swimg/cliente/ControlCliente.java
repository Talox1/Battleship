/**
 * Javier Abell�n, 25 Mayo 2006
 * Para Cliente de chat
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Clase que atiende el socket y las peticiones de usuario. 
 * Lo que llega por el socket lo muestra en el textArea del panel, lo que
 * escribe el usuario en el panel lo env�a por el socket.
 * @author Chuidiang
 *
 */
public class ControlCliente implements ActionListener, Runnable
{
    /** Para lectura de datos del socket */
    private DataInputStream dataInput;

    /** Para escritura de datos en el socket */
    private DataOutputStream dataOutput;

    /** Panel con los controles para el usuario */
    private PanelCliente panel;

    /**
     * Contruye una instancia de esta clase, lanzando un hilo para atender al
     * socket.
     * @param socket El socket
     * @param panel El panel del usuario
     */
    public ControlCliente(Socket socket, PanelCliente panel)
    {
        this.panel = panel;
        try
        {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            panel.addActionListener(this);
            Thread hilo = new Thread(this);
            hilo.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Recoge el texto del panel y lo env�a por el socket.
     * El panel llamar� a este m�todo cuando el usuario escriba algo y 
     * pulse el bot�n de "enviar" o pulse "enter" sobre el textfield.
     */
    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            dataOutput.writeUTF(panel.getTexto());
        } catch (Exception excepcion)
        {
            excepcion.printStackTrace();
        }
    }

    /**
     * M�todo run para antender al socket. Todo lo que llega por el socket se
     * escribe en el panel.
     */
    public void run()
    {
        try
        {
            while (true)
            {
                String texto = dataInput.readUTF();
                panel.addTexto(texto);
                panel.addTexto("\n");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
