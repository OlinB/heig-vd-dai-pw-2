package ch.heigvd.dai;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "Blackjack", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
        description = "Start a blackjack client or server")
class Blackjack implements Callable<Integer>{

    @Command(name = "server", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
            description = "Start a blackjack server")
    public Integer server() {
        System.out.println("Hello World ! (server)");
        return 0;
    }

    @Command(name = "client", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
            description = "Start a blackjack client")
    public Integer client() {
        System.out.println("Hello World ! (client)");
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
        CommandLine commandLine = new CommandLine(new Blackjack());
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