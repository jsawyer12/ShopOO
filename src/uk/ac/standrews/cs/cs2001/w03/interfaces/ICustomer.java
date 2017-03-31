package uk.ac.standrews.cs.cs2001.w03.interfaces;

/**
 * Created by jts4 on 28/09/16.
 */
public interface ICustomer {

    String getName();

    String getAddress();

    void makePurchase(IOrder order);
}
