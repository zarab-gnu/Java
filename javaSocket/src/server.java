import java.io.*;
import java.net.*;
import java.util.HashMap;

public class server {
    private static final HashMap<Integer, String> studentDatabase = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6969);
        System.out.println("Server started on port 6969...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                String clientRequest = (String) ois.readObject();
                if (clientRequest.equalsIgnoreCase("EXIT")) {
                    System.out.println("Client disconnected");
                    break;
                }
                String response = processRequest(clientRequest);
                oos.writeObject(response);
                oos.flush();
            }
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private static synchronized String processRequest(String request) {
        String[] parts = request.split(" ", 3);
        String command = parts[0].toUpperCase();

        switch (command) {
            case "ADD":
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                studentDatabase.put(id, name);
                return "Student added: ID=" + id + ", Name=" + name;

            case "DELETE":
                id = Integer.parseInt(parts[1]);
                if (studentDatabase.remove(id) != null) {
                    return "Student with ID=" + id + " deleted.";
                } else {
                    return "Student with ID=" + id + " not found.";
                }

            case "VIEW":
                if (parts.length == 2) {
                    id = Integer.parseInt(parts[1]);
                    String student = studentDatabase.get(id);
                    return (student != null) ? "Student: ID=" + id + ", Name=" + student
                            : "Student with ID=" + id + " not found.";
                } else {
                    return studentDatabase.isEmpty() ? "No students in the database." : studentDatabase.toString();
                }

            default:
                return "Invalid command. Use ADD, DELETE, VIEW, or VIEW id.";
        }
    }
}
