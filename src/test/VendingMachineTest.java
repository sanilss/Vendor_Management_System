package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exception.VendingMachineException;
import model.Coin;
import model.Product;
import service.VendingMachine;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        // Initialize vending machine with products and coins
        Map<String, Product> products = new HashMap<>();
        products.put("Coke", new Product("Coke", 25, 10));
        products.put("Pepsi", new Product("Pepsi", 35, 15));
        products.put("Sprite", new Product("Sprite", 45, 12));

        Map<Integer, Coin> coins = new HashMap<>();
        coins.put(1, new Coin(1, 10));
        coins.put(5, new Coin(5, 10));
        coins.put(10, new Coin(10, 10));
        coins.put(25, new Coin(25, 10));
        coins.put(50, new Coin(50, 10));
        coins.put(100, new Coin(100, 10));

        vendingMachine = new VendingMachine(products, coins);
    }

    @Test
    public void testUpdateInventory() {
        // Arrange
        String productName = "Coke";
        int initialQuantity = vendingMachine.getProducts().get(productName).getQuantity();
        int quantityToAdd = 5;

        // Act
        vendingMachine.updateInventory(productName, quantityToAdd);

        // Assert
        assertEquals(initialQuantity + quantityToAdd, vendingMachine.getProducts().get(productName).getQuantity());
    }

    @Test
    public void testUpdateFloat() {
        // Arrange
        int coinValue = 10;
        int initialQuantity = vendingMachine.getCoins().get(coinValue).getQuantity();
        int quantityToAdd = 5;

        // Act
        vendingMachine.updateFloat(coinValue, quantityToAdd);

        // Assert
        assertEquals(initialQuantity + quantityToAdd, vendingMachine.getCoins().get(coinValue).getQuantity());
    }

    @Test
    public void testIsValidCoinValue_ValidCoin() {
        // Arrange
        int coinValue = 10;

        // Act
        boolean isValid = vendingMachine.isValidCoinValue(coinValue);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testIsValidCoinValue_InvalidCoin() {
        // Arrange
        int coinValue = 3; // Assuming 3 is not a valid coin value

        // Act
        boolean isValid = vendingMachine.isValidCoinValue(coinValue);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testHandleTransaction_SuccessfulTransaction() throws VendingMachineException {
        // Arrange
        Map<String, Integer> productsToBuy = new HashMap<>();
        productsToBuy.put("Coke", 2);
        int coinsInserted = 60; // Enough for two Cokes

        // Act
        vendingMachine.handleTransaction(productsToBuy, coinsInserted);

        // Assert
        // Check if inventory is updated correctly
        assertEquals(8, vendingMachine.getProducts().get("Coke").getQuantity());
    }

    @Test(expected = VendingMachineException.class)
    public void testHandleTransaction_ExactCoinsInserted() throws VendingMachineException {
        // Arrange
        Map<String, Integer> productsToBuy = new HashMap<>();
        productsToBuy.put("Coke", 1); // Buying one Coke
        int coinsInserted = 20; // Exactly the price of one Coke

        // Act
        vendingMachine.handleTransaction(productsToBuy, coinsInserted);
    }

    @Test(expected = VendingMachineException.class)
    public void testHandleTransaction_ProductNotAvailable() throws VendingMachineException {
        // Arrange
        Map<String, Integer> productsToBuy = new HashMap<>();
        productsToBuy.put("Fanta", 1); // Buying a product not available
        int coinsInserted = 50; // Enough coins to cover the price

        // Act
        vendingMachine.handleTransaction(productsToBuy, coinsInserted);
    }

    @Test(expected = VendingMachineException.class)
    public void testHandleTransaction_InsufficientQuantity() throws VendingMachineException {
        // Arrange
        Map<String, Integer> productsToBuy = new HashMap<>();
        productsToBuy.put("Pepsi", 20); // Buying more Pepsi than available
        int coinsInserted = 500; // Enough coins to cover the price

        // Act
        vendingMachine.handleTransaction(productsToBuy, coinsInserted);
    }
}
