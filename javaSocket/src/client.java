import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        System.out.println("client started--");
        Socket s = new Socket("127.0.0.1",6969);
        System.out.println("client connected");


        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        
        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        oos.writeObject(msg);

        try {
            Object serverMsg = ois.readObject();
            System.out.println("Server: "+(String)serverMsg);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

}
