package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;

/**
 * This class represents products that can be stocked and sold in a shop.
 *
 */
public class Product implements IProduct {

    private String barCode;
    private String description;

   @Override
   public String getBarCode() {
       return this.barCode;
    }


   @Override
    public String getDescription() {
       return this.description;
    }

    public Product(String barCode, String description) {
        this.barCode = barCode;
        this.description = description;
    }
}
