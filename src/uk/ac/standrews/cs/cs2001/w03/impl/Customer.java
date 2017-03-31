package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.ICustomer;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IOrder;

import java.util.ArrayList;

/**
 * Created by jts4 on 28/09/16.
 */
public class Customer implements ICustomer {

    private String name;
    private String address;
    public ArrayList<IOrder> orderList = new ArrayList<IOrder>();

    public Customer(String name, String address) {
        this.address = address;
        this.name = name;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void makePurchase(IOrder order) {
        orderList.add(order);
    }
}
