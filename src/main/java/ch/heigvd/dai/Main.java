package ch.heigvd.dai;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "Blackjack", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
        description = "Start a blackjack client or server")
class Blackjack implements Callable<Integer>{

    @Command(name = "server", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
            description = "Start a blackjack server")
    public Integer server(@Option(names = {"-p", "--port"}, description = "Server port", defaultValue = "42069") int port) {
        BlackJackServer server = new BlackJackServer(port);
        server.start();
        return 0;
    }

    @Command(name = "client", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
            description = "Start a blackjack client")
    public Integer client(@Option(names = {"-s", "--server"}, description = "Server address", defaultValue = "127.0.0.1") String server,
                          @Option(names = {"-p", "--port"}, description = "Server port", defaultValue = "42069") int port,
                          @Option(names = "--id", description = "Player id", defaultValue = "0") int id) {
        if(id == 0){
            id = (int) (Math.random() * 1000000);
        }
        BlackJackClient client = new BlackJackClient(server, port, id);
        client.start();
        return 0;
    }

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    private boolean helpRequested = false;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "Display version info")
    private boolean versionRequested = false;

    @Override
    public Integer call() {
        System.out.println("Subcommand needed: 'client' or 'server'");
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new ch.heigvd.dai.Blackjack());
        if(commandLine.isUsageHelpRequested()){
            commandLine.usage(System.out);
            return;
        }
        else if (commandLine.isVersionHelpRequested()){
            commandLine.printVersionHelp(System.out);
            return;
        }
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}