package controller;


import lombok.SneakyThrows;
import model.Antique;
import model.BidStatus;
import model.Bidder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

class JedisManagerTest {

    private JedisManager jedisMgr;
    private Jedis connection;

    @BeforeEach
    void setUp() {
        jedisMgr = JedisManager.getInstance();
        jedisMgr.startConnection("localhost", 6379);
        connection = jedisMgr.getConnection();
    }

    @AfterEach
    void tearDown() {
        connection.flushAll();
        jedisMgr.closeConnection();
    }

    @Test
    void startConnection() {
        assertNotNull(connection);
    }

    @Test
    void closeConnection() {
        jedisMgr.closeConnection();
        assertNull(jedisMgr.getConnection());
    }

    private Bidder buildBidder() {
        return Bidder.builder().id("B1").name("Ali").budget(1000L).build();
    }

    private Antique buildAntique() {
        return Antique.builder().id("A1").name("Antique").startPrice(100L).build();
    }

    @Test
    void setGetBidderTest() {
        Bidder excepted = buildBidder();
        jedisMgr.setBidder(excepted);
        Bidder actual = jedisMgr.getBidder(excepted.getId());
        assertEquals(excepted, actual);
    }

    @Test
    void setGetAntiqueTest() {
        Antique excepted = buildAntique();
        jedisMgr.setAntique(excepted);
        Antique actual = jedisMgr.getAntique(excepted.getId());
        assertEquals(excepted, actual);
    }

    @Test
    void setAuctionTest() {
        Antique antique = buildAntique();

        long exceptedVal = antique.getStartPrice();
        long exceptedTTL = 20;

        jedisMgr.setAuctionTTL(exceptedTTL);
        jedisMgr.setAntique(antique);
        jedisMgr.setAuction(antique.getId());

        long actualTTL = connection.ttl("auction:A1");
        assertEquals(exceptedTTL, actualTTL);

        long actualVal = Long.parseLong(connection.hget("auction:A1", "currVal"));
        assertEquals(exceptedVal, actualVal);
    }

    @Test
    void bidSuccessTest() {
        BidStatus actualStatus = simulateBid(200L, 10, 0);

        // check for bid status
        assertEquals(BidStatus.SUCCESS, actualStatus);

        // check for updated value
        long exceptedVal = 200L;
        long actualVal = Long.parseLong(connection.hget("auction:A1", "currVal"));
        assertEquals(exceptedVal, actualVal);

        // check for bidder budget
        long exceptedBudget = 800L;
        long actualBudget = Long.parseLong(connection.hget("bidder:B1", "budget"));
        assertEquals(exceptedBudget, actualBudget);
    }

    @Test
    void bidExpiredTest() {
        BidStatus actualStatus = simulateBid(500, 1, 2);
        assertEquals(BidStatus.EXPIRED, actualStatus);
    }

    @Test
    void bidBadValueTest() {
        BidStatus actualStatus = simulateBid(20, 10, 0);
        assertEquals(BidStatus.BAD_VALUE, actualStatus);
    }

    @Test
    void bidInsufficientBudgetTest() {
        BidStatus actualStatus = simulateBid(2000, 10, 0);
        assertEquals(BidStatus.INSUFFICIENT_BUDGET, actualStatus);
    }

    @Test
    void newBidTest() throws InterruptedException {
        Antique antique = buildAntique();
        Bidder bidder1 = buildBidder();
        Bidder bidder2 = buildBidder();
        bidder2.setId("B2");

        jedisMgr.setBidder(bidder1);
        jedisMgr.setBidder(bidder2);
        jedisMgr.setAntique(antique);
        jedisMgr.setAuctionTTL(10);
        jedisMgr.setAuction(antique.getId());

        BidStatus status1 = jedisMgr.setBid(bidder1.getId(), antique.getId(), 500L);
        assertEquals(BidStatus.SUCCESS, status1);

        Thread.sleep(1000);

        BidStatus status2 = jedisMgr.setBid(bidder2.getId(), antique.getId(), 600L);
        assertEquals(BidStatus.SUCCESS, status2);


        // test money back to last bidder
        long budget1 = Long.parseLong(connection.hget("bidder:B1", "budget"));
        assertEquals(1000L, budget1);

        // test money deduction from new bidder
        long budget2 = Long.parseLong(connection.hget("bidder:B2", "budget"));
        assertEquals(400L, budget2);
    }

    @SneakyThrows
    private BidStatus simulateBid(long exceptedVal, long ttl, long delay) {
        Bidder bidder = buildBidder();
        Antique antique = buildAntique();

        jedisMgr.setBidder(bidder);
        jedisMgr.setAntique(antique);
        jedisMgr.setAuctionTTL(ttl);
        jedisMgr.setAuction(antique.getId());

        Thread.sleep(delay * 1000);

        return jedisMgr.setBid(bidder.getId(), antique.getId(), exceptedVal);
    }
}