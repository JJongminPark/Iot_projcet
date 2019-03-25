import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class O2O_Receiver implements Runnable
{
    private String ip;
    private Socket Conn_Socket;
    private BufferedReader inFromClient;


    O2O_Receiver(String ip, Socket Conn_Socket){
        this.ip = ip;
        this.Conn_Socket = Conn_Socket;
    }
    @Override
    public void run(){
        try{
            System.out.println("created");
            inFromClient = new BufferedReader(new
                    InputStreamReader(Conn_Socket.getInputStream()));
        } catch(IOException e){
            System.out.println("Fail to create Socket inputstream");
        }

        while(true) {
            try {
                String Msg_from_peer = inFromClient.readLine();
                System.out.println("From " + ip + " : " + Msg_from_peer);
            } catch (IOException e) {
                System.out.println("IoException1");
                break;
            }
        }
    }
}
