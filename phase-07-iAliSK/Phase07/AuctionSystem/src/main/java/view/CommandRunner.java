package view;

import controller.JedisManager;
import model.Antique;
import model.BidStatus;
import model.Bidder;

import java.util.Scanner;

public class CommandRunner {
    private final static Scanner sc = new Scanner(System.in);
    private static CommandRunner instance;
    private final JedisManager jedisMgr;

    private CommandRunner() {
        jedisMgr = JedisManager.getInstance();
    }

    public static CommandRunner getInstance() {
        if (instance == null) {
            instance = new CommandRunner();
        }
        return instance;
    }

    public void run() {
        System.out.println("Connecting...");
        jedisMgr.startConnection("127.0.0.1", 6379);
        System.out.println("Connected! Enter help or exit.");

        String command = "";
        while (!command.equals("exit")) {
            System.out.print("> ");
            execute(command = sc.nextLine());
        }

        jedisMgr.closeConnection();
    }

    private void execute(String command) {
        String[] args = command.split("\\s+");

        switch (args[0]) {
            case "help":
                showHelp();
                break;
            case "add":
                jedisMgr.setAntique(Antique.builder().id(args[1]).name(args[2])
                        .startPrice(Long.parseLong(args[3])).build());
                break;
            case "reg":
                jedisMgr.setBidder(Bidder.builder().id(args[1]).name(args[2])
                        .budget(Long.valueOf(args[3])).build());
                break;
            case "auct":
                jedisMgr.setAuction(args[1]);
                break;
            case "bid":
                BidStatus status = jedisMgr.setBid(args[1], args[2], Long.parseLong(args[3]));
                printBidStatus(status);
                break;
            case "ttl":
                jedisMgr.setAuctionTTL(Long.parseLong(args[1]));
            case "bidder":
                System.out.println(jedisMgr.getBidder(args[1]));
                break;
            case "antique":
                System.out.println(jedisMgr.getAntique(args[1]));
        }
    }

    private void showHelp() {
        System.out.println("add <ANTIQUE_ID> <NAME> <START_PRICE>\n" +
                "reg <BIDDER_ID> <NAME> <BUDGET>\n" +
                "auct <ANTIQUE_ID>\n" +
                "bid <BIDDER_ID> <ANTIQUE_ID> <VALUE>\n" +
                "bidder <BIDDER_ID>\n" +
                "antique <ANTIQUE_ID>\n" +
                "ttl <SECONDS>\n"
        );
    }

    private void printBidStatus(BidStatus status) {
        String msg = null;
        switch (status) {
            case EXPIRED:
                msg = "Auction expired or not started yet!";
                break;
            case BAD_VALUE:
                msg = "You must offer more value than current!";
                break;
            case INSUFFICIENT_BUDGET:
                msg = "Your budget is insufficient!";
                break;
            case SUCCESS:
                msg = "Done.";
        }
        System.out.println(msg);
    }
}
