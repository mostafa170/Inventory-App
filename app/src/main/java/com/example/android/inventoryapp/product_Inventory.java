package com.example.android.inventoryapp;

/**
 * Created by mostafa on 28/07/2017.
 */

public class product_Inventory {
    private int mId;
    private String mProduct;
    private int mQuantity;
    private double mprice;

    public product_Inventory() {
        super();
    }

    public product_Inventory(String product, int quantity, double price) {
        mProduct = product;
        mQuantity = quantity;
        mprice = price;

    }

    public int getmId() {
        return mId;
    }

    public String getmProduct() {
        return mProduct;
    }

    public double getmPrice() {
        return mprice;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmProduct(String name) {
        mProduct = name;
    }

    public void setmPrice(double price) {
        mprice = price;
    }

    public void setmQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void setmID(int id) {
        mId = id;
    }

    public void quantitySale() {
        mQuantity -= 1;
        if (mQuantity < 0) {
            mQuantity = 0;
        }
    }
}
