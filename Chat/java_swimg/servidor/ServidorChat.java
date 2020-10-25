
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;

public class ServidorChat
{
    /** Lista en la que se guaradara toda la conversacion */
    private DefaultListModel charla = new DefaultListModel();

    /**
     * Instancia esta clase.
     * @param args
     */
    public static void main(String[] args)
    {
        new ServidorChat();
    }

    /**
     * Se mete en un bucle infinito para ateder clientes, lanzando un hilo
     * para cada uno de ellos.
     */
    public ServidorChat()
    {
        try
        {
            ServerSocket socketServidor = new ServerSocket(5557);
            while (true)
            {
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
