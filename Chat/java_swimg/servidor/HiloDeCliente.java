import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class HiloDeCliente implements Runnable{
    

    /** Socket al que est� conectado el cliente */
    private Socket socket;

    /** Para lectura de datos en el socket */
    private DataInputStream dataInput;

    /** Para escritura de datos en el socket */
    private DataOutputStream dataOutput;

    private Scanner sc = new Scanner(System.in);
    /**
     * Crea una instancia de esta clase y se suscribe a cambios en la charla.
     * @param charla La lista de textos que componen la charla del chat
     * @param socket Socket con el cliente.
     */
    public HiloDeCliente(Socket socket){

        this.socket = socket;
        try  {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Atiende el socket. Todo lo que llega lo mete en la charla.
     */
    public void run()
    {
        try
        {
            while (true)
            {

                String texto = sc.nextLine();
                synchronized (this)
                {
                    
                    System.out.println(">>"+texto);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

  
    }

