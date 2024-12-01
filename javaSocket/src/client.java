import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter server IP address: ");
            String serverIp = scanner.nextLine();

            try (Socket socket = new Socket(serverIp, 6969);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Connected to server");

                while (true) {
                    System.out.println("Enter command (ADD id name, DELETE id, VIEW, or EXIT):");
                    String command = scanner.nextLine();

                    oos.writeObject(command); // Send command to server
                    oos.flush(); // Ensure its sent immediately

                    if (command.equalsIgnoreCase("EXIT")) {
                        System.out.println("Disconnected from server");
                        break;
                    }

                    String response = (String) ois.readObject(); // Receive response
                    System.out.println("Server response: " + response);
                }
            } catch (Exception e) {
                System.err.println("Connection error: " + e.getMessage());
            }
        }
    }
}
