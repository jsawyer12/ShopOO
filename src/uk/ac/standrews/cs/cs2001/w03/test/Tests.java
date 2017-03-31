package uk.ac.standrews.cs.cs2001.w03.test;

import org.junit.Before;
import org.junit.Test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.common.BarCodeAlreadyInUseException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.impl.*;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This is a JUnit test suite for the Shop ADT classes.
 * 
 */

public class Tests extends AbstractFactoryClient {

    Product product = (Product) getFactory().makeProduct("123", "Bread");
    StockRecord stockRecord = (StockRecord) getFactory().makeStockRecord(product);
    Shop newShop = (Shop) getFactory().makeShop();
    Order order = new Order("12345");
    Customer customer = new Customer("Jeff", "123 Granada Ave");

    /**
     *  Confirms product returns proper value for bar code
     */
    @Test
    public void TestProductBarCode() {
        assertEquals(product.getBarCode(), "123");
        System.out.println("Product returns correct bar code");
    }

    /**
     * Confirms product returns proper value for description
     */
    @Test
    public void TestProductDescription() {
        assertEquals(product.getDescription(), "Bread");
        System.out.println("Product returns correct description");
    }

    /**
     * Tests to make sure returns correct product
     */
    @Test
    public void TestGetProduct() {
        assertSame(stockRecord.getProduct(), product);
        System.out.println("Stock Record method getProduct returned same product");
    }

    /**
     * Tests if stock count returns correct value before and after adjustments
     */
    @Test
    public void TestGetStockCount() {
        assertEquals(stockRecord.getStockCount(), 1);
        stockRecord.addStock();
        assertEquals(stockRecord.getStockCount(), 2);
        System.out.println("Stock Record returns correct stock count");
    }

    /**
     * Tests if addStock method actually adds a product to a product stock
     */
    @Test
    public void TestAddStock() {
        assertEquals(stockRecord.getStockCount(), 1);
        stockRecord.addStock();
        assertEquals(stockRecord.getStockCount(), 2);
        System.out.println("Stock Record addStock method successful");
    }

    /**
     * Tests to see if product can be bought
     */
    @Test
    public void TestBuyProduct() {
        assertEquals(stockRecord.getStockCount(), 1);
        try {
            stockRecord.buyProduct();
            assertEquals(stockRecord.getStockCount(), 0);
            System.out.println("Product stock: " + stockRecord.getStockCount());
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        }

    }

    /**
     * Tests to see if exception comes up at correct time
     */
    @Test
    public void TestBuyProductException() {
        assertEquals(stockRecord.getStockCount(), 1);
        try {
            stockRecord.buyProduct();
            assertEquals(stockRecord.getStockCount(), 0);
            stockRecord.buyProduct();
        } catch (StockUnavailableException e) {
            System.out.println("Successfully returns exception when product out of stock");
        }
    }

    /**
     * Tests to see if stock record returns proper number of sales for product before/ after changes
     */
    @Test
    public void TestGetNumberOfSales() {
        assertEquals(stockRecord.getNumberOfSales(), 0);
        try {
            stockRecord.buyProduct();
            assertEquals(stockRecord.getNumberOfSales(), 1);
            System.out.println("Product stock: " + stockRecord.getStockCount());
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        }
        System.out.println("Stock Record returns correct number of sales");
    }

    /**
     * Tests to see if Stock count get's adjusted through addition
     */
    @Test
    public void TestStockChanges() {
        System.out.println("Stock count before adding 1: " + stockRecord.getStockCount());
        assertEquals(stockRecord.getStockCount(), 1);
        stockRecord.addStock();
        System.out.println("Stock count after adding 1: " + stockRecord.getStockCount());
        assertEquals(stockRecord.getStockCount(), 2);
    }

    /**
     * Tests to see if Stock count get's adjusted through addition
     */
    @Test
    public void TestStockChanges2() {
        System.out.println("Stock count before adding 1: " + stockRecord.getStockCount());
        assertEquals(stockRecord.getStockCount(), 1);
        try {
            stockRecord.buyProduct();
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        }
        System.out.println("Stock count after buying 1: " + stockRecord.getStockCount());
        assertEquals(stockRecord.getStockCount(), 0);
    }

    /**
     * Tests to see if registerProduct method registers product successfully
     */
    @Test
    public void TestRegisterProduct() {
        newShop.StockArray.add(stockRecord);
        assertFalse(newShop.StockArray.get(0).getRegistered());
        try {
            newShop.registerProduct(product);
            assertTrue(newShop.StockArray.get(0).getRegistered());
            System.out.println("Product successfully registered");
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that product doesn't register when incorrect product is chosen
     */
    @Test
    public void TestRegisterProduct2() {
        newShop.StockArray.add(stockRecord);
        assertFalse(newShop.StockArray.get(0).getRegistered());
        try {
            IProduct product2 = getFactory().makeProduct("321", "Bread");
            newShop.registerProduct(product2);
            assertFalse(newShop.StockArray.get(0).getRegistered());
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that exception is brought up when product is already registered
     */
    @Test
    public void TestRegisterProductException() {
        newShop.StockArray.add(stockRecord);
        assertFalse(newShop.StockArray.get(0).getRegistered());
        try {
            newShop.registerProduct(product);
            assertTrue(newShop.StockArray.get(0).getRegistered());
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception raised when registered product attempted to be registered again");
        }
    }


    /**
     * Tests to make sure unregisterProduct method unregisters product
     */
    @Test
    public void TestUnregisterProduct() {
        stockRecord.setRegistered(true);
        newShop.StockArray.add(stockRecord);
        assertTrue(newShop.StockArray.get(0).getRegistered());
        try {
            newShop.unregisterProduct(product);
            assertFalse(newShop.StockArray.get(0).getRegistered());
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that product doesn't unregister when incorrect product is chosen
     */
    @Test
    public void TestUnregisterProduct2() {
        stockRecord.setRegistered(true);
        newShop.StockArray.add(stockRecord);
        assertTrue(newShop.StockArray.get(0).getRegistered());
        try {
            IProduct product2 = getFactory().makeProduct("321", "Bread");
            newShop.unregisterProduct(product2);
            assertTrue(newShop.StockArray.get(0).getRegistered());
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that exception is brought up when product is already unregistered
     */
    @Test
    public void TestUnregisterProductException() {
        stockRecord.setRegistered(true);
        newShop.StockArray.add(stockRecord);
        assertTrue(newShop.StockArray.get(0).getRegistered());
        try {
            newShop.unregisterProduct(product);
            assertFalse(newShop.StockArray.get(0).getRegistered());
            newShop.unregisterProduct(product);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception raised when unregistered product attempted to be unregistered again");
        }
    }

    /**
     * Tests that shop can add stock to its inventory
     */
    @Test
    public void TestAddShopStock() {
        stockRecord.setRegistered(true);
        newShop.StockArray.add(stockRecord);
        assertTrue(newShop.StockArray.get(0).getRegistered());
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            newShop.addStock("123");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 2);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that exception is brought up when stock is attempted to be added to barcode not stored in Shop
     */
    @Test
    public void TestAddShopStockException() {
        stockRecord.setRegistered(true);
        newShop.StockArray.add(stockRecord);
        assertTrue(newShop.StockArray.get(0).getRegistered());
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            newShop.addStock("321");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception raised when stock attempted to be added to product with incorrect barcode");
        }
    }

    /**
     * Tests that exception is brought up when stock is not registered
     */
    @Test
    public void TestAddShopStockException2() {
        newShop.StockArray.add(stockRecord);
        assertFalse(newShop.StockArray.get(0).getRegistered());
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            newShop.addStock("123");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception raised when stock attempted to be added to unregistered product");
        }
    }

    /**
     * Tests that product in inventory can successfully be purchased
     */
    @Test
    public void TestShopBuyProduct() {
        newShop.StockArray.add(stockRecord);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 0);
        try {
            newShop.buyProduct("123");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 0);
            assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 1);
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that incorrect barcode brings up ProductNotRegisteredException
     */
    @Test
    public void TestShopBuyProductException() {
        newShop.StockArray.add(stockRecord);
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            newShop.buyProduct("321");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception successfully brought up when product not registered for sale when attempted to be purchased");
        }
    }

    /**
     * Tests that product with 0 stock brings up StockUnavailableException
     */
    @Test
    public void TestShopBuyProductException2() {
        newShop.StockArray.add(stockRecord);
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
            assertEquals(newShop.StockArray.get(0).getStockCount(), 0);
            newShop.buyProduct("123");
        } catch (StockUnavailableException e) {
            System.out.println("Exception successfully brought up when stock is no longer available");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     *  Tests that correct number of different products are returned in stockarray
     */
    @Test
    public void TestGetNumberOfProducts() {
        newShop.StockArray.add(stockRecord);
        assertEquals(newShop.getNumberOfProducts(), 1);
        newShop.StockArray.get(0).addStock();
        assertEquals(newShop.getNumberOfProducts(), 1);
        Product product2 = (Product) getFactory().makeProduct("123", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        assertEquals(newShop.getNumberOfProducts(), 2);
    }

    /**
     * Tests that correct products x stocks for each product are returned
     */
    @Test
    public void TestGetTotalStockCount() {
        newShop.StockArray.add(stockRecord);
        assertEquals(newShop.getTotalStockCount(), 1);
        newShop.StockArray.get(0).addStock();
        assertEquals(newShop.StockArray.get(0).getStockCount(), 2);
        assertEquals(newShop.getTotalStockCount(), 2);
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        assertEquals(newShop.StockArray.get(1).getStockCount(), 1);
        assertEquals(newShop.getTotalStockCount(), 3);
    }

    /**
     * Tests that correct stock count is returned from searched barcode
     */
    @Test
    public void TestShopGetStockCount() {
        newShop.StockArray.add(stockRecord);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertTrue(newShop.StockArray.get(0).getRegistered());
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            assertEquals(newShop.getStockCount("123"), 1);
            assertEquals(newShop.getStockCount("123"), newShop.StockArray.get(0).getStockCount());
            newShop.addStock("123");
            assertEquals(newShop.getStockCount("123"), 2);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     * Tests that 0 is returned and message brought up when barcode not in arraylist
     */
    @Test
    public void TestShopGetStockCount2() {
        newShop.StockArray.add(stockRecord);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception not thrown here");
        }
        assertTrue(newShop.StockArray.get(0).getRegistered());
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            assertEquals(newShop.getStockCount("321"), 0);
            newShop.addStock("123");
            assertEquals(newShop.getStockCount("123"), 2);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception thrown because not in arraylist");
        }
    }

    /**
     * Tests that ProductNotRegisteredException is thrown when attempt to get stock count
     * of unregistered product is made
     */
    @Test
    public void TestShopGetStockCountException() {
        newShop.StockArray.add(stockRecord);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            assertEquals(newShop.getStockCount("123"), 1);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        newShop.StockArray.get(0).addStock();
        assertEquals(newShop.StockArray.get(0).getStockCount(), 2);
        assertEquals(newShop.getTotalStockCount(), 2);
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        assertEquals(newShop.StockArray.get(1).getStockCount(), 1);
        try {
            assertEquals(newShop.getStockCount("123"), 2);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception called successfully when product hasn't been registered");
        }
    }

    /**
     *  Tests that shop returns correct number of sales before/after purchase of product
     */
    @Test
    public void TestShopGetNumOfSales() {
        newShop.StockArray.add(stockRecord);
        try {
            assertFalse(newShop.StockArray.get(0).getRegistered());
            newShop.registerProduct(product);
            assertTrue(newShop.StockArray.get(0).getRegistered());
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 0);
        assertEquals(newShop.StockArray.get(0).getStockCount(), 1);
        try {
            assertEquals(newShop.StockArray.get(0).getNumberOfSales(), newShop.getNumberOfSales("123"));
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
            assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 1);
            assertEquals(newShop.StockArray.get(0).getNumberOfSales(), newShop.getNumberOfSales("123"));
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }

    }

    /**
     *  Tests that shop returns 0 and message if bar code isn't in arraylist
     */
    @Test
    public void TestShopGetNumOfSales2() {
        try {
            assertEquals(0, newShop.getNumberOfSales("123"));
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        newShop.StockArray.add(stockRecord);
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     *  Tests to see if exception is thrown when product with barcode isn't registered
     */
    @Test
    public void TestShopGetNumOfSalesException() {
        newShop.StockArray.add(stockRecord);
        assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 0);
        try {
            assertEquals(newShop.getNumberOfSales("123"), newShop.StockArray.get(0).getNumberOfSales());
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception thrown when number of sales for unregistered product is searched");
        }
        try {
            newShop.registerProduct(product);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.getNumberOfSales("123"), newShop.StockArray.get(0).getNumberOfSales());
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception not thrown here because product is registered");
        }
        try {
            newShop.buyProduct("123");
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(newShop.StockArray.get(0).getNumberOfSales(), 1);
        try {
            assertEquals(newShop.getNumberOfSales("123"), newShop.StockArray.get(0).getNumberOfSales());
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     *  Tests for most popular product in arraylist
     */
    @Test
    public void TestGetMostPopular() {
        newShop.StockArray.add(stockRecord);
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        try {
            newShop.registerProduct(product);
            newShop.registerProduct(product2);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.StockArray.get(0).getProduct().getBarCode(), "123");
            newShop.buyProduct("123");
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertTrue(newShop.getNumberOfSales("123") > newShop.getNumberOfSales("321"));
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.getMostPopular(), product);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }

    }

    /**
     *  Tests for most popular when there isn't anything in arraylist
     */
    @Test
    public void TestGetMostPopular2() {
        try {
            stockRecord.buyProduct();
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.getMostPopular(), product);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception brought up when arraylist is empty");
        }
    }

    /**
     *  Tests for most popular when there are two equally popular products
     */
    @Test
    public void TestGetMostPopular3() {
        newShop.StockArray.add(stockRecord);
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        try {
            newShop.registerProduct(product);
            newShop.registerProduct(product2);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
            newShop.buyProduct("321");
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.getNumberOfSales("123"),newShop.getNumberOfSales("321"));
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            assertEquals(newShop.getMostPopular(), product);
            System.out.println("1st product with most sales is returned");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
    }

    /**
     *  Tests to see if exception is thrown when product with most sales isn't registered
     */
    @Test
    public void TestGetMostPopularException() {
        newShop.StockArray.add(stockRecord);
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        newShop.StockArray.add(stockRecord2);
        try {
            newShop.registerProduct(product2);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception successfully brought up when unregistered product attempted to be purchased");
        }
        try {
            assertEquals(newShop.getNumberOfSales("123"), newShop.getNumberOfSales("321"));
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception successfully brought up when unregistered product attempted to be purchased");
        }
        try {
            assertEquals(newShop.getMostPopular(), product);
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception successfully brought up when unregistered product attempted to be purchased");
        }
    }

    /**
     * Tests customer info is correct
     */
    @Test
    public void TestCustomerInfo() {
        assertEquals(customer.getAddress(), "123 Granada Ave");
        assertEquals(customer.getName(), "Jeff");
    }

    /**
     * Tests order info is correct
     */
    @Test
    public void TestOrderInfo() {
        assertEquals(order.getOrderNumber(), "12345");
    }

    /**
     * Tests empty item list in order
     */
    @Before
    public void TestOrderItemList() {
        assertTrue(order.getItemList().isEmpty());
    }

    /**
     * Tests item list in order
     */
    @Test
    public void TestAddToItemList() {
        try {
            newShop.StockArray.add(stockRecord);
            newShop.registerProduct(product);
            assertTrue(stockRecord.getRegistered());
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
            order.itemList.add(product);
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(order.itemList.get(0), product);
    }

    /**
     * Tests to see if method returns correct list and list is responsive to changes
     */
    @Test
    public void TestGetItemList() {
        Product product2 = (Product) getFactory().makeProduct("321", "Bread");
        StockRecord stockRecord2 = (StockRecord) getFactory().makeStockRecord(product2);
        try {
            newShop.StockArray.add(stockRecord);
            newShop.registerProduct(product);
            newShop.StockArray.add(stockRecord2);
            newShop.registerProduct(product2);
        } catch (BarCodeAlreadyInUseException e) {
            System.out.println("Exception should not be thrown here");
        }
        try {
            newShop.buyProduct("123");
            order.itemList.add(product);
            newShop.buyProduct("321");
            order.itemList.add(product2);
        } catch (StockUnavailableException e) {
            System.out.println("Exception should not be thrown here");
        } catch (ProductNotRegisteredException e) {
            System.out.println("Exception should not be thrown here");
        }
        assertEquals(order.itemList.size(), 2);
    }

    /**
     * Tests to see if order is properly added to customer's list of orders
     */
    @Before
    public void TestCustomerOrderList() {
        customer.makePurchase(order);
        assertFalse(customer.orderList.isEmpty());
    }
}
