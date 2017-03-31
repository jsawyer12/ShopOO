package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

/**
 * This class represents a record held by the shop for a particular product.
 *
 */
public class StockRecord implements IStockRecord {

    private IProduct product;
    private int count;
    private int sales;
    private boolean registered;

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean getRegistered() {
        return this.registered;
    }

    @Override
    public IProduct getProduct() {
        return this.product;
    }


    @Override
    public int getStockCount() {
        return count;
    }


    @Override
    public int getNumberOfSales() {
        return sales;
    }


    @Override
    public void addStock() {
        count++;
    }


    @Override
    public void buyProduct() throws StockUnavailableException {
        if (count > 0) {
            count--;
            sales++;
        }
        else {
            throw new StockUnavailableException();
        }
    }

    public StockRecord(IProduct product) {
        this.product = product;
        this.count = 1;
        this.sales = 0;
        this.registered = false;
    }
}
