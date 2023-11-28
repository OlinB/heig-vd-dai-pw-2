package ch.heigvd.dai;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class TcpServer {

    protected final int PORT;
    protected final int SERVER_ID;

    public TcpServer(int port, int serverId) {
        PORT = port;
        SERVER_ID = serverId;
    }

    public TcpServer(int port) {
        PORT = port;
        SERVER_ID = (int) (Math.random() * 1000000);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println(
                    "[Server " + SERVER_ID + "] starting with id " + SERVER_ID
            );
            System.out.println(
                    "[Server " + SERVER_ID + "] listening on port " + PORT
            );

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("[Server " + SERVER_ID + "] exception: " + e);
        }
    }

    protected String handleRequest(String request){
        return "Answer from server " + SERVER_ID;
    }

    private class ClientHandler implements Runnable {

        private final Socket socket;
        private boolean running = true;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    socket; // This allow to use try-with-resources with the socket
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                    );
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),
                                    StandardCharsets.UTF_8
                            )
                    )
            ) {
                System.out.println(
                        "[Server " +
                                SERVER_ID +
                                "] new client connected from " +
                                socket.getInetAddress().getHostAddress() +
                                ":" +
                                socket.getPort()
                );
                while(running){

                    String data = in.readLine();

                    System.out.println(
                            "[Server " +
                                    SERVER_ID +
                                    "] received textual data from client: " +
                                    data
                    );

                    String TEXTUAL_DATA = handleRequest(data);

                    if(TEXTUAL_DATA == null){
                        running = false;
                        continue;
                    }

                    System.out.println(
                            "[Server " +
                                    SERVER_ID +
                                    "] sending response to client: " +
                                    TEXTUAL_DATA
                    );

                    out.write(TEXTUAL_DATA + "\n");
                    out.flush();
                }

                System.out.println("[Server " + SERVER_ID + "] closing connection");
            } catch (IOException e) {
                System.out.println("[Server " + SERVER_ID + "] exception: " + e);
            }
        }
    }
}
