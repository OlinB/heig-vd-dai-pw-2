package ch.heigvd.dai;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class TcpClient {

    protected final String HOST;
    protected final int PORT;
    protected final int CLIENT_ID;
    protected boolean running = true;

    public TcpClient(String host, int port, int clientId){
        HOST = host;
        PORT = port;
        CLIENT_ID = clientId;
    }

    public void start() {
        System.out.println(
                "[Client " + CLIENT_ID + "] starting with id " + CLIENT_ID
        );
        System.out.println(
                "[Client " + CLIENT_ID + "] connecting to " + HOST + ":" + PORT
        );

        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
                );
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
        ) {
            System.out.println(
                    "[Client " + CLIENT_ID + "] connected to " + HOST + ":" + PORT
            );
            while(running){
                sendRequest(in, out, reader);
            }


            System.out.println("[Client " + CLIENT_ID + "] closing connection");
        } catch (IOException e) {
            System.out.println("[Client " + CLIENT_ID + "] exception: " + e);
        }
    }

    protected void sendRequest(BufferedReader in, BufferedWriter out, BufferedReader reader) {
        try{
            System.out.print(">>");
            // Reading data using readLine
            String TEXTUAL_DATA = reader.readLine();
            System.out.println(
                    "[Client " +
                            CLIENT_ID +
                            "] sending textual data to server " +
                            HOST +
                            ":" +
                            PORT +
                            ": " +
                            TEXTUAL_DATA
            );

            out.write(TEXTUAL_DATA + "\n");
            out.flush();

            System.out.println(
                    "[Client " + CLIENT_ID + "] response from server: " + in.readLine()
            );
        } catch(IOException e) {
            System.out.println("[Client " + CLIENT_ID + "] exception: " + e);
        }
    }
}
