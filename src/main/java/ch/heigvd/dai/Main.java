package ch.heigvd.dai;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "Blackjack", mixinStandardHelpOptions = true, version = "Blackjack 0.1",
        description = "host a Blackjack server")
class Blackjack implements Callable<Integer>{

    @Command(name = "server")
    public Integer prepare() {
        System.out.println("Hello World ! (server)");
        return 0;
    }

    @Command(name = "client")
    public Integer process() {
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