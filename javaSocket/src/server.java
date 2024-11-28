import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6969);
        System.out.println("server started--");

        while(true){
        Socket s = ss.accept();    
        System.out.println("client connected");
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());

        try {
            Object clientMsg = ois.readObject();
            System.out.println("Client: "+(String)clientMsg);

            String serverMsg = ((String)clientMsg).toUpperCase() + " message recieved";
            oos.writeObject(serverMsg);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    
    }
}
