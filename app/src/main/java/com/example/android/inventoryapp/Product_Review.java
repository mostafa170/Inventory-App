package com.example.android.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mostafa on 28/07/2017.
 */

public class Product_Review extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_inventory_app);
        InventoryDbHelper InventoryDbHelper = new InventoryDbHelper(this);

        ListView listView = (ListView) findViewById(R.id.inventory_listView);
        // insert
        TextView empty = (TextView) findViewById(R.id.empty);

        ArrayList<product_Inventory> listArray = InventoryDbHelper.readInventory();
        if (listArray.size() == 0) {
            empty.setText("Inventory is empty");
        } else {
            empty.setText("");
        }
        ListViewAdapter customAdapter = new ListViewAdapter(listArray);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_app);
        InventoryDbHelper InventoryDbHelper = new InventoryDbHelper(this);

        ListView listView = (ListView) findViewById(R.id.inventory_listView);
        TextView empty = (TextView) findViewById(R.id.empty);

        ArrayList<product_Inventory> listArray = InventoryDbHelper.readInventory();
        if (listArray.size() == 0) {
            empty.setText("Inventory is empty");
        } else {
            empty.setText("");
        }
        ListViewAdapter customAdapter = new ListViewAdapter(listArray);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);
    }

    public void addNewItem(View view) {
        Intent intent = new Intent(this, Add_Product.class);
        intent.putExtra("HEADER", "Add a New Product");
        startActivity(intent);
    }
}
