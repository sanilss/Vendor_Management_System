package test;
import static org.junit.Assert.assertEquals;


import org.junit.Test;
import model.Coin;

public class CoinTest {

    @Test
    public void testGetValue() {
        Coin coin = new Coin(10, 5);
        assertEquals(10, coin.getValue());
    }

    @Test
    public void testGetQuantity() {
        Coin coin = new Coin(10, 5);
        assertEquals(5, coin.getQuantity());
    }

    @Test
    public void testSetValue() {
        Coin coin = new Coin(10, 5);
        coin.setQuantity(8);
        assertEquals(8, coin.getQuantity());
    }

    // Add more test cases as needed
}

