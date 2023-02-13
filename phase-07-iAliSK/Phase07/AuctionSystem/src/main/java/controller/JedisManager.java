package controller;

import lombok.Getter;
import lombok.Setter;
import model.Antique;
import model.BidStatus;
import model.Bidder;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

public class JedisManager {
    private static JedisManager instance;

    @Getter
    private Jedis connection;

    @Setter
    private long auctionTTL;

    private JedisManager() {
        auctionTTL = 10;
    }

    public static JedisManager getInstance() {
        if (instance == null) {
            instance = new JedisManager();
        }
        return instance;
    }

    public void startConnection(String host, int port) {
        connection = new Jedis(host, port);
    }

    public void closeConnection() {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private String getBidderKey(String bidderId) {
        return "bidder:".concat(bidderId);
    }

    private String getAntiqueKey(String antiqueId) {
        return "antique:".concat(antiqueId);
    }

    private String getAuctionKey(String antiqueId) {
        return "auction:".concat(antiqueId);
    }

    public void setBidder(Bidder bidder) {
        String bidderKey = getBidderKey(bidder.getId());
        connection.hmset(bidderKey, new HashMap<>() {{
            put("name", bidder.getName());
            put("budget", bidder.getBudget().toString());
        }});
    }

    public Bidder getBidder(String bidderId) {
        String key = getBidderKey(bidderId);
        return Bidder.builder()
                .id(bidderId)
                .name(connection.hget(key, "name"))
                .budget(Long.valueOf(connection.hget(key, "budget")))
                .build();
    }

    public void setAntique(Antique antique) {
        String antiqueKey = getAntiqueKey(antique.getId());
        connection.hmset(antiqueKey, new HashMap<>() {{
            put("name", antique.getName());
            put("startPrice", antique.getStartPrice().toString());
        }});
    }

    public Antique getAntique(String antiqueId) {
        String antiqueKey = getAntiqueKey(antiqueId);
        return Antique.builder()
                .id(antiqueId)
                .name(connection.hget(antiqueKey, "name"))
                .startPrice(Long.valueOf(connection.hget(antiqueKey, "startPrice")))
                .build();
    }

    public void setAuction(String antiqueId) {
        String auctionKey = getAuctionKey(antiqueId);
        Long startPrice = getAntique(antiqueId).getStartPrice();
        connection.hset(auctionKey, "currVal", startPrice.toString());
        connection.expire(auctionKey, auctionTTL);
    }

    public BidStatus setBid(String bidderId, String antiqueId, Long value) {
        String key = getAuctionKey(antiqueId);
        if (!connection.exists(key)) {
            return BidStatus.EXPIRED;
        }
        if (!isValidOffer(key, value)) {
            return BidStatus.BAD_VALUE;
        }
        if (!deductNewBidderBudget(bidderId, value)) {
            return BidStatus.INSUFFICIENT_BUDGET;
        }
        addLastBidderBudget(key);
        updateAuction(key, bidderId, value);
        return BidStatus.SUCCESS;
    }

    private boolean isValidOffer(String auctionKey, Long value) {
        String currVal = connection.hget(auctionKey, "currVal");
        return value > Long.parseLong(currVal);
    }

    private boolean deductNewBidderBudget(String bidderId, Long value) {
        String bidderKey = getBidderKey(bidderId);
        Long bidderBudget = Long.parseLong(connection.hget(bidderKey, "budget"));
        if (value > bidderBudget) {
            return false;
        }
        connection.hset(bidderKey, "budget", String.valueOf(bidderBudget - value));
        return true;
    }

    private void addLastBidderBudget(String auctionKey) {
        String bidderId = connection.hget(auctionKey, "bidderId");
        if (bidderId != null) {
            long currVal = Long.parseLong(connection.hget(auctionKey, "currVal"));
            String bidderKey = getBidderKey(bidderId);
            connection.hincrBy(bidderKey, "budget", currVal);
        }
    }

    private void updateAuction(String auctionKey, String newBidderId, Long value) {
        connection.hset(auctionKey, "bidderId", newBidderId);
        connection.hset(auctionKey, "currVal", String.valueOf(value));
        connection.expire(auctionKey, auctionTTL);
    }

}