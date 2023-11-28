package ch.heigvd.dai;
import ch.heigvd.dai.gameplay.Blackjack;
import ch.heigvd.dai.gameplay.Card;

import java.util.Vector;

public class BlackJackServer extends TcpServer{

    private enum Requests {
        CONNECT,
        NEWGAME,
        HIT,
        STAND,
        QUIT,
    }

    private enum Responses {
        OK,
        DEAL,
        REVEAL,
        ERROR,
    }

    private Blackjack game = new Blackjack(1);
    private boolean inGame = false;

    public BlackJackServer(int port, int serverId) {
        super(port, serverId);
    }
    public BlackJackServer(int port) {
        super(port);
    }

    protected String handleRequest(String request){

        if(request.equals(Requests.CONNECT.name())){
            return Responses.OK.name();
        }
        if(request.equals(Requests.NEWGAME.name())){
            if(inGame){
                return Responses.ERROR.name() + " 'Game already started !'";
            }

            game.startRound();
            inGame = true;

            return Responses.DEAL.name() + " " + game.getDealerHand(false) + " " +  game.getPlayerHand(0) + " " + (game.getPlayerPoints(0));
        }
        if(request.equals(Requests.HIT.name())){
            if(!inGame){
                return Responses.ERROR.name() + " 'Game not started !'";
            }
            game.hit(0);
            int points = game.getPlayerPoints(0);
            if(points > 21){
                inGame = false;
                return Responses.REVEAL.name() + game.getDealerHand(true) + " " + game.getPlayerHand(0) + " " + game.getDealerPoints() + " " + points + " 'You lose !' ";
            }
            return Responses.DEAL.name() + " " + game.getDealerHand(false) + " " + game.getPlayerHand(0) + " " + points;
        }
        if(request.equals(Requests.STAND.name())){
            if(!inGame){
                return Responses.ERROR.name() + " 'Game not started !'";
            }
            inGame = false;
            game.revealDealerHand();
            int points = game.getPlayerPoints(0);
            int dealerPoints = game.getDealerPoints();

            return Responses.REVEAL.name() + " " + game.getDealerHand(true) + " " + game.getPlayerHand(0) + " " + dealerPoints + " " + points + " " + game.gameResult(0);
        }
        if(request.equals(Requests.QUIT.name())){
            return null;
        }
        return Responses.ERROR.name() + " 'Unknown request !'";
    }
}
