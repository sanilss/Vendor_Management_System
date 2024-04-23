package service;

import java.util.Map;

import exception.VendingMachineException;
import model.Coin;
import model.Product;

public class VendingMachine {
    private Map<String, Product> products;
    private Map<Integer, Coin> coins;

    public VendingMachine(Map<String, Product> products, Map<Integer, Coin> coins) {
        this.products = products;
        this.coins = coins;
    }
    
    // Getter method for products
    public Map<String, Product> getProducts() {
        return products;
    }

    // Getter method for coins
    public Map<Integer, Coin> getCoins() {
        return coins;
    }


    public void updateInventory(String productName, int quantity) {
        // Update inventory for a given product
        if (products.containsKey(productName)) {
            Product product = products.get(productName);
            product.setQuantity(product.getQuantity() + quantity);
            // Check if the inventory becomes zero and notify the supplier if necessary
            if (product.getQuantity() == 0) {
                System.out.println("Inventory for product '" + productName + "' is now empty. Please restock.");
                // Notify the vending machine supplier to request a full inventory of the machine
                // This could involve sending a notification or triggering an alert in the system
            }
        }
    }

    public void updateFloat(int coinValue, int quantity) {
        if (isValidCoinValue(coinValue)) {
            if (coins.containsKey(coinValue)) {
                Coin coin = coins.get(coinValue);
                coin.setQuantity(coin.getQuantity() + quantity);
                System.out.println("Float updated successfully.");
            } else {
                System.out.println("Error: Coin value not found in float.");
            }
        } else {
            System.out.println("Error: Invalid coin value. Please enter a valid coin value.");
        }
    }

    public boolean isValidCoinValue(int coinValue) {
        int[] validCoinValues = {1, 5, 10, 25, 50, 100};
        for (int validValue : validCoinValues) {
            if (validValue == coinValue) {
                return true;
            }
        }
        return false;
    }

    public void handleTransaction(Map<String, Integer> productsToBuy, int coinsInserted) throws VendingMachineException {
        // Check if all products to buy are available in the vending machine
        for (String productName : productsToBuy.keySet()) {
            if (!products.containsKey(productName)) {
                throw new VendingMachineException("Product '" + productName + "' not available.");
            }
        }

        int totalPrice = calculateTotalPrice(productsToBuy);

        if (coinsInserted < totalPrice) {
            throw new VendingMachineException("Insufficient coins inserted.");
        }

        if (!checkInventory(productsToBuy)) {
            throw new VendingMachineException("Insufficient inventory for one or more products.");
        }

        processTransaction(productsToBuy, totalPrice);
        int remainingChange = coinsInserted - totalPrice;

        if (remainingChange > 0) {
            giveChange(remainingChange);
            System.out.println("Remaining change to be returned: " + remainingChange + "p");
        }

        // Print selected products
        System.out.println("Selected product(s):");
        for (Map.Entry<String, Integer> entry : productsToBuy.entrySet()) {
            String productName = entry.getKey();
            int quantityToBuy = entry.getValue();
            System.out.println(productName + ": " + quantityToBuy);
        }

        System.out.println("Transaction completed successfully.");
    }


    private int calculateTotalPrice(Map<String, Integer> productsToBuy) {
        int totalPrice = 0;
        for (Map.Entry<String, Integer> entry : productsToBuy.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += products.get(productName).getPrice() * quantity;
        }
        return totalPrice;
    }

    private boolean checkInventory(Map<String, Integer> productsToBuy) {
        for (Map.Entry<String, Integer> entry : productsToBuy.entrySet()) {
            String productName = entry.getKey();
            int quantityToBuy = entry.getValue();
            if (!products.containsKey(productName) || products.get(productName).getQuantity() < quantityToBuy) {
                return false;
            }
        }
        return true;
    }


    private void processTransaction(Map<String, Integer> productsToBuy, int totalPrice) {
        for (Map.Entry<String, Integer> entry : productsToBuy.entrySet()) {
            String productName = entry.getKey();
            int quantityToBuy = entry.getValue();
            Product product = products.get(productName);
            product.setQuantity(product.getQuantity() - quantityToBuy);
        }
    }

    private void giveChange(int change) {
        // Define available coin denominations
        int[] availableCoins = {100, 50, 25, 10, 5, 1};

        // Flag to check if any coins are available for change
        boolean changeGiven = false;

        // Iterate through available coin denominations
        for (int coin : availableCoins) {
            // Deduct coins from the float until the remaining change is zero
            while (change >= coin && coins.containsKey(coin) && coins.get(coin).getQuantity() > 0) {
                change -= coin;
                Coin coinObject = coins.get(coin);
                coinObject.setQuantity(coinObject.getQuantity() - 1);
                changeGiven = true;

                // Check if quantity of this coin denomination becomes zero
                if (coinObject.getQuantity() == 0) {
                    // Do not show individual alerts here
                }
            }
        }

        if (!changeGiven) {
            System.out.println("Error: Insufficient coins available for change.");
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Product product : products.values()) {
            System.out.println(product.getName() + ": " + product.getQuantity());
        }
    }

    public void displayFloat() {
        System.out.println("Current Float:");
        for (Coin coin : coins.values()) {
            System.out.println(coin.getValue() + "p: " + coin.getQuantity());
        }
    }
}