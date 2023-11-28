package ch.heigvd.dai;

import java.io.*;

public class BlackJackClient extends TcpClient {

    private boolean inGame = false;
    public BlackJackClient(String host, int port, int clientId) {
        super(host, port, clientId);
    }

    @Override
    protected void sendRequest(BufferedReader in, BufferedWriter out, BufferedReader reader) {
        try{
            String TEXTUAL_DATA;
            if(!inGame){
                System.out.println("Select an action: ");
                System.out.println("s: Start a new game");
                System.out.println("q: Quit");
                String input;
                System.out.print(">>");
                // Reading data using readLine
                input = reader.readLine();
                while(!input.equals("s") && !input.equals("q")){
                    System.out.println("Invalid action, please try again");
                    System.out.print(">>");
                    input = reader.readLine();
                }
                if(input.equals("s")){
                    TEXTUAL_DATA = "NEWGAME";
                    inGame = true;
                } else{
                    TEXTUAL_DATA = "QUIT";
                }
            } else{
                System.out.println("Select an action: ");
                System.out.println("h: Hit");
                System.out.println("s: Stand");
                System.out.println("q: Quit");
                String input;
                System.out.print(">>");
                // Reading data using readLine
                input = reader.readLine();
                while(!input.equals("h") && !input.equals("s") && !input.equals("q")){
                    System.out.println("Invalid action, please try again");
                    System.out.print(">>");
                    input = reader.readLine();
                }
                if(input.equals("h")) {
                    TEXTUAL_DATA = "HIT";
                }else if(input.equals("s")){
                    TEXTUAL_DATA = "STAND";
                    inGame = false;
                } else{
                    TEXTUAL_DATA = "QUIT";
                }

            }

            out.write(TEXTUAL_DATA + "\n");
            out.flush();

            if(TEXTUAL_DATA.equals("QUIT")){
                running = false;
                return;
            }

            String response = in.readLine();
            String[] split = response.split(" ", 6);
            switch(split[0]){
                case "DEAL":
                    System.out.println("Dealer hand: " + split[1]);
                    System.out.println("Your hand: " + split[2]);
                    System.out.println("Your points: " + split[3]);
                    break;
                case "REVEAL":
                    System.out.println("Dealer hand: " + split[1]);
                    System.out.println("Your hand: " + split[2]);
                    System.out.println("Dealer points: " + split[3]);
                    System.out.println("Your points: " + split[4]);
                    System.out.println(split[5]);
                    inGame = false;
                    break;
                case "ERROR":
                    split = response.split(" ", 2);
                    System.out.println("Error: " + split[1]);
                    break;
            }
        } catch(IOException e) {
            System.out.println("[Client " + CLIENT_ID + "] exception: " + e);
        }
    }
}
