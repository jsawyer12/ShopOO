package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.ICustomer;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IOrder;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;

import java.util.ArrayList;

/**
 * Created by jts4 on 28/09/16.
 */
public class Order implements IOrder {

    private String orderNumber;
    public ArrayList<IProduct> itemList;

    public Order(String orderNumber) {
        itemList = new ArrayList<IProduct>();
        this.orderNumber = orderNumber;
    }

    @Override
    public String getOrderNumber() {
        return this.orderNumber;
    }

    @Override
    public ArrayList<IProduct> getItemList() {
        return this.itemList;
    }
}
