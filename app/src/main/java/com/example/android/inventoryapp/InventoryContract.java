package com.example.android.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by mostafa on 28/07/2017.
 */

public final class InventoryContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA = ",";

    private InventoryContract() {
    }

    public static abstract class Table implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String Column_ID = "id";
        public static final String Column_NAME = "name";
        public static final String Column_QUANTITY = "quantity";
        public static final String Column_PRICE = "price";
        public static final String Column_IMAGE = "image";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        Column_ID + " INTEGER PRIMARY KEY UNIQUE ," +
                        Column_NAME + TEXT_TYPE + COMMA +
                        Column_QUANTITY + " INTEGER" + COMMA +
                        Column_PRICE + " REAL" + COMMA +
                        Column_IMAGE + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;
    }
}
