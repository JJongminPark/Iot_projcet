import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class Sender implements Runnable{
    private HashMap<String, Socket> map = new HashMap<>();

    private BufferedReader inFromUser =
            new BufferedReader(new InputStreamReader(System.in));


    @Override
    public void run(){
        DataOutputStream outToPeer;
        Socket TargetSocket; // Socket to communicate with peer
        String[] split; // tmp space to assign result from split()
        String Target_ip; // is destination where you want to send
        String Msg_to_send; // is message to target_ip

        while(true) {
            try {
                split = inFromUser.readLine().split(":",2); // split UserInput by ":" , you must type "ip:message" form ex) 127.0.0.1:hi , 196.8.4.2:hello
                Target_ip = split[0];
                Msg_to_send = split[1];

                if (map.containsKey(Target_ip)) { //If Target_Ip is already in hash map then use an existing socket
                    TargetSocket = map.get(Target_ip);
                    outToPeer = new DataOutputStream(TargetSocket.getOutputStream());
                } else {//if Target_ip is new one, then create socket to communicate with new peer
                    TargetSocket = new Socket(Target_ip,8181);
                    map.put(Target_ip,TargetSocket); // put (ip,socket) into hash map for future use
                    outToPeer = new DataOutputStream(TargetSocket.getOutputStream());
                }
                outToPeer.writeBytes(Msg_to_send + '\n'); // send message to peer
            } catch (IOException e) {
                System.out.println("IoException in Sender Thread");
                break;
            }
        }
    }
}
