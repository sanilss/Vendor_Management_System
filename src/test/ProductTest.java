package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import model.Product;

public class ProductTest {

    @Test
    public void testGetName() {
        Product product = new Product("Coke", 25, 10);
        assertEquals("Coke", product.getName());
    }

    @Test
    public void testGetPrice() {
        Product product = new Product("Coke", 25, 10);
        assertEquals(25, product.getPrice());
    }

    @Test
    public void testGetQuantity() {
        Product product = new Product("Coke", 25, 10);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        Product product = new Product("Coke", 25, 10);
        product.setQuantity(8);
        assertEquals(8, product.getQuantity());
    }

    
}
