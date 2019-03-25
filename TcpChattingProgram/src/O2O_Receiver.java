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
            inFromClient= new BufferedReader(new
                    InputStreamReader(Conn_Socket.getInputStream()));
        }catch(IOException e){
            System.out.println("IoException Fail to get inputStream");
        }

        while(true) {
            try {
                String Msg_from_peer = inFromClient.readLine();// read msg from peer
                System.out.println("From " + ip + " : " + Msg_from_peer);// print msg
            }catch (IOException e) {
                    System.out.println("IoException1 Fail to readLine");
                    break;
            }
        }
    }
}
