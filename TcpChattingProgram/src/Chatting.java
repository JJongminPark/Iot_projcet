import java.net.ServerSocket;
import java.net.Socket;

public class Chatting {
    public  static void main(String[] argv) throws Exception
    {
        Thread Client = new Thread(new Sender()); // create Client which is able to send message to peers
        Client.start();
        ServerSocket welcomeSocket = new ServerSocket(8080);
        while(true){
            Socket Conn = welcomeSocket.accept();// create new Tread for each Connection every time a new connection is accepted by welcomeSocket
            Thread t = new Thread(new O2O_Receiver(Conn.getInetAddress().toString(), Conn));
            t.start();
        }
    }

}
