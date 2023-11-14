package ch.heigvd.dai;

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
            return Responses.DEAL.name();
        }
        if(request.equals(Requests.HIT.name())){
            if(Math.random() < 0.5){
                return Responses.REVEAL.name();
            }
            return Responses.DEAL.name();
        }
        if(request.equals(Requests.STAND.name())){
            return Responses.REVEAL.name();
        }
        if(request.equals(Requests.QUIT.name())){
            return null;
        }
        return Responses.ERROR.name();
    }
}
