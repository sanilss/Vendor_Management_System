package MainMethod;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import exception.VendingMachineException;
import model.Coin;
import model.Product;
import service.VendingMachine;

public class Main {
    public static void main(String[] args) {
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

        VendingMachine vendingMachine = new VendingMachine(products, coins);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Welcome to the Vending Machine!");
                System.out.println("1. Buy a product");
                System.out.println("2. Check inventory");
                System.out.println("3. Update inventory");
                System.out.println("4. Update float");
                System.out.println("5. Display float");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        buyProduct(scanner, vendingMachine, products);
                        break;
                    case 2:
                        vendingMachine.displayInventory();
                        break;
                    case 3:
                        updateInventory(scanner, vendingMachine);
                        break;
                    case 4:
                        System.out.print("Enter coin value to update float: ");
                        int coinValue = scanner.nextInt();
                        System.out.print("Enter quantity to add: ");
                        int quantity = scanner.nextInt();
                        vendingMachine.updateFloat(coinValue, quantity);
                        break;
                    case 5:
                        vendingMachine.displayFloat();
                        break;
                    case 6:
                        System.out.println("Thank you for using the Vending Machine!");
                        scanner.close(); // Close the scanner before exiting
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("Input not found. Please enter the required input.");
            } catch (NullPointerException e) {
                System.out.println("Product not found. Please enter a valid product name.");
            }
        }
    }

    private static void buyProduct(Scanner scanner, VendingMachine vendingMachine, Map<String, Product> products) {
        System.out.println("Available Products:");
        for (Product product : products.values()) {
            System.out.println(product.getName() + " - Price: " + product.getPrice() + "p, Quantity: " + product.getQuantity());
        }

        Map<String, Integer> productsToBuy = new HashMap<>();
        int totalCost = 0;

        // Prompt user to select products and quantities
        while (true) {
            System.out.print("Enter product name to buy (or type 'done' to finish): ");
            String productName = scanner.next();
            if (productName.equalsIgnoreCase("done")) {
                break;
            }

            if (!products.containsKey(productName)) {
                System.out.println("Product '" + productName + "' not found.");
                continue;
            }

            Product product = products.get(productName);
            System.out.print("Enter quantity to buy: ");
            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be a positive number.");
                continue;
            }

            if (quantity > product.getQuantity()) {
                System.out.println("Insufficient quantity available for product '" + productName + "'.");
                continue;
            }

            totalCost += product.getPrice() * quantity;
            productsToBuy.put(productName, quantity);
        }

        // Prompt user to insert coins
        System.out.println("Total cost: " + totalCost + "p");
        System.out.print("Enter coins inserted: ");
        int coinsInserted = scanner.nextInt();

        // Process the transaction
        System.out.println("Processing transaction...");
        try {
            vendingMachine.handleTransaction(productsToBuy, coinsInserted);
        } catch (VendingMachineException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void updateInventory(Scanner scanner, VendingMachine vendingMachine) {
  
            System.out.print("Enter product name to update inventory: ");
            String productName = scanner.next();
            System.out.print("Enter quantity to add: ");
            int quantity = scanner.nextInt();
            vendingMachine.updateInventory(productName, quantity);
            System.out.println("Inventory updated successfully.");
    }
}
