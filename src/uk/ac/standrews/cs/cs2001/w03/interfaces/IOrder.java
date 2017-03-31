package uk.ac.standrews.cs.cs2001.w03.interfaces;

import java.util.ArrayList;

/**
 * Created by jts4 on 28/09/16.
 */
public interface IOrder {

    String getOrderNumber();

    /**
     * displays products in order and total cost
     */
    ArrayList<IProduct> getItemList();
}
