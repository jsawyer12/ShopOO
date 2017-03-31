package uk.ac.standrews.cs.cs2001.w03.impl;


import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.common.BarCodeAlreadyInUseException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;

import java.util.ArrayList;

/**
 * This class represents a simple shop which can stock and sell products.
 *
 */
public class Shop extends AbstractFactoryClient implements IShop {

    public ArrayList<StockRecord> StockArray;

    public Shop() {
        StockArray = new ArrayList<StockRecord>();
    }

    @Override
    public void registerProduct(IProduct product) throws BarCodeAlreadyInUseException {
        boolean inArray = false;
        for (int i = 0; i < StockArray.size(); i++) {
            if  (StockArray.get(i).getProduct() == product) {
                inArray = true;
                if (StockArray.get(i).getRegistered()) {
                    throw new BarCodeAlreadyInUseException();
                }
                else {
                    StockArray.get(i).setRegistered(true);
                }
            }
        }
        if (!inArray) {
            System.out.println("Product not yet added to Arraylist");
        }
    }


    @Override
    public void unregisterProduct(IProduct product) throws ProductNotRegisteredException {
        boolean noBarCode = false;
        for (int i = 0; i < StockArray.size(); i++) {
            if (StockArray.get(i).getProduct().getBarCode() == product.getBarCode()) {
                noBarCode = true;
                if (StockArray.get(i).getRegistered()) {
                    StockArray.get(i).setRegistered(false);
                }
                else throw new ProductNotRegisteredException();
            }
        }
        if (!noBarCode) {
            System.out.println("Product not yet added to Arraylist");
        }
    }


    @Override
    public void addStock(String barCode) throws ProductNotRegisteredException {
        for (int i = 0; i < StockArray.size(); i++) {
            if (StockArray.get(i).getProduct().getBarCode() == barCode) {
                if (StockArray.get(i).getRegistered()) {
                    StockArray.get(i).addStock();
                }
                else throw new ProductNotRegisteredException();
            }
            else throw new ProductNotRegisteredException();
        }
    }


    @Override
    public void buyProduct(String barCode) throws StockUnavailableException, ProductNotRegisteredException {
        if (StockArray.isEmpty()) {
            throw new StockUnavailableException();
        }
        else {
            for (int i = 0; i < StockArray.size(); i++) {
                if (StockArray.get(i).getProduct().getBarCode() == barCode) {
                    if (StockArray.get(i).getRegistered()) {
                        if (StockArray.get(i).getStockCount() > 0) {
                            StockArray.get(i).buyProduct();
                        }
                        else throw new StockUnavailableException();
                    }
                    else throw new ProductNotRegisteredException();
                }
            }
        }
    }


    @Override
    public int getNumberOfProducts() {
        int numberOfProducts = StockArray.size();
        return numberOfProducts;
    }


    @Override
    public int getTotalStockCount() {
        int totalStockCount = 0;
        for (int i = 0; i < StockArray.size(); i++) {
            totalStockCount = totalStockCount + StockArray.get(i).getStockCount();
        }
        return totalStockCount;
    }


    @Override
    public int getStockCount(String barCode) throws ProductNotRegisteredException {
        int stockCount = 0;
        boolean inArray = false;
        for (int i = 0; i < StockArray.size(); i++) {
            if (StockArray.get(i).getProduct().getBarCode() == barCode) {
                inArray = true;
                if (!StockArray.get(i).getRegistered()) {
                    throw new ProductNotRegisteredException();
                }
                else {
                    stockCount = StockArray.get(i).getStockCount();
                }
            }
        }
        if (!inArray) {
            System.out.println("Product not yet added to arraylist");
            throw new ProductNotRegisteredException();
        }
        return stockCount;
    }

    @Override
    public int getNumberOfSales(String barCode) throws ProductNotRegisteredException {
        int numberOfSales = 0;
        boolean inArray = false;
        for (int i = 0; i < StockArray.size(); i++) {
            if (StockArray.get(i).getProduct().getBarCode() == barCode) {
                inArray = true;
                if (!StockArray.get(i).getRegistered()) {
                    throw new ProductNotRegisteredException();
                } else {
                    numberOfSales = StockArray.get(i).getNumberOfSales();
                }
            }
        }
        if (!inArray) {
            System.out.println("Product not yet added to arraylist");
        }
        return numberOfSales;
    }

    @Override
    public IProduct getMostPopular() throws ProductNotRegisteredException {
        int maxSales = 0;
        int index = 0;
        if (StockArray.isEmpty()) {
            System.out.println("There aren't any products in arraylist");
            throw new ProductNotRegisteredException();
        }
        else {
            for (int i = 0; i < StockArray.size(); i++) {
                if (StockArray.get(i).getNumberOfSales() > maxSales) {
                    maxSales = StockArray.get(i).getNumberOfSales();
                    index = 0;
                }
            }
            return StockArray.get(index).getProduct();
        }
    }
}
