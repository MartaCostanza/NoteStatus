import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
        /*Definiamo il Server socket, il client socket, la porta usata per la connessione ed il client_id.*/
        ServerSocket socket;
        Socket client_socket;
        private int port;
        int client_id = 0;

        Registro VaxRegister = new Registro();
        Registro NoVaxRegister = new Registro();
        RegGuariti GuaritiSRegister = new RegGuariti();
        RegGuariti GuaritiAsRegister=new RegGuariti();


        public static void main(String args[]) {

            if (args.length != 1) {
                System.out.println("Usage: java MyServer <port>");
                return;
            }

            MyServer server = new MyServer(Integer.parseInt(args[0]));
            server.start();
        }

        public MyServer(int port) {
            System.out.println("Initializing server with port " + port);
            this.port = port;
        }


        public void start() {
            try {
                System.out.println("Starting server on port " + port);
                socket = new ServerSocket(port);
                System.out.println("Started server on port " + port);
                while (true) {
                    System.out.println("Listening on port " + port);
                    client_socket = socket.accept();
                    System.out.println("Accepted connection from " + client_socket.getRemoteSocketAddress());
                    // Viene creato il CM
                    MyClientManager cm = new MyClientManager(client_socket, VaxRegister, NoVaxRegister,GuaritiSRegister,GuaritiAsRegister);
                    Thread t = new Thread(cm, "client_" + client_id);
                    client_id++;
                    t.start();


                }

            } catch (IOException e) {
                System.out.println("Could not start server on port " + port);
                e.printStackTrace();
            }
        }

}
