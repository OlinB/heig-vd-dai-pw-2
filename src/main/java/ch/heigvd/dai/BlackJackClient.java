package ch.heigvd.dai;

import java.io.*;

public class BlackJackClient extends TcpClient {
    public BlackJackClient(String host, int port, int clientId) {
        super(host, port, clientId);
    }

    @Override
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

            if(TEXTUAL_DATA.equals("QUIT")){
                running = false;
                return;
            }

            System.out.println(
                    "[Client " + CLIENT_ID + "] response from server: " + in.readLine()
            );
        } catch(IOException e) {
            System.out.println("[Client " + CLIENT_ID + "] exception: " + e);
        }
    }
}
