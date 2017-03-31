package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IFactory;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

import java.util.ArrayList;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;
    private Shop NewShop;

    private Factory() {

    }

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    @Override
    public IProduct makeProduct(String barCode, String description) {
        Product newProd = new Product(barCode, description);
        return newProd;
    }

    @Override
    public IStockRecord makeStockRecord(IProduct product) {
        StockRecord newStock = new StockRecord(product);
        return newStock;
    }

    @Override
    public IShop makeShop() {
        Shop NewShop = new Shop();
        return NewShop;
    }

}
