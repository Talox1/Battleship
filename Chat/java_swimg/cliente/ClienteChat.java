


import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class ClienteChat
{
    /** Socket con el servidor del chat */
    private Socket socket;

    /** Panel con la ventana del cliente */
    private PanelCliente panel;

    /**
     * Arranca el Cliente de chat.
     * @param args
     */
    public static void main(String[] args)
    {
        new ClienteChat();
    }

    /**
     * Crea la ventana, establece la conexi�n e instancia al controlador.
     */
    public ClienteChat()
    {
        try
        {
            //creaYVisualizaVentana();
            socket = new Socket("localhost", 5557);
            //ControlCliente control = new ControlCliente(socket, panel);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Crea una ventana, le mete dentro el panel para el cliente y la visualiza
     */
   
}
