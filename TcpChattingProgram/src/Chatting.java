import java.net.ServerSocket;
import java.net.Socket;

public class Chatting {
    public  static void main(String argv[]) throws Exception
    {
        Thread Client = new Thread(new Sender());
        Client.start();
        ServerSocket welcomeSocket = new ServerSocket(8080);
        while(true) {
            Socket Conn = welcomeSocket.accept();
            Thread t = new Thread(new O2O_Receiver(Conn.getInetAddress().toString(), Conn));
            t.start();
            System.out.println(Conn.getInetAddress().toString());
        }
    }

}
