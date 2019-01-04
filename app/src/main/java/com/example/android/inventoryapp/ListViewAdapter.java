package com.example.android.inventoryapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mostafa on 28/07/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    private static final String TAG = ListViewAdapter.class.getSimpleName();
    ArrayList<product_Inventory> products;

    public ListViewAdapter(ArrayList<product_Inventory> products) {
        this.products = new ArrayList<product_Inventory>(products);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list_view, parent, false);
        }

        final product_Inventory dataModel = products.get(index);


        final TextView productName = (TextView) view.findViewById(R.id.productName);
        productName.setText(dataModel.getmProduct());

        final TextView productAvailable = (TextView) view.findViewById(R.id.productAvailable);
        productAvailable.setText("" + dataModel.getmQuantity());

        final TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText("$" + dataModel.getmPrice());

        Button button = (Button) view.findViewById(R.id.listItemButton);


        this.notifyDataSetChanged();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDbHelper InventoryDbHelper = new InventoryDbHelper(view.getContext());
                if (dataModel.getmQuantity() == 0) {
                    Toast.makeText(parent.getContext(),"Not available stock of" + dataModel.getmProduct(), Toast.LENGTH_SHORT).show();
                } else {
                    InventoryDbHelper.updateProduct(dataModel.getmId(), dataModel);
                    productAvailable.setText("" + dataModel.getmQuantity());
                    Toast.makeText(parent.getContext(), "Quantity for " + dataModel.getmProduct() + " decremented", Toast.LENGTH_SHORT).show();
                }
                dataModel.quantitySale();
                productAvailable.setText("" + dataModel.getmQuantity());
                InventoryDbHelper.close();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent details = new Intent(parent.getContext(), Edit_Activity.class);
                details.putExtra("productName", dataModel.getmProduct());
                details.putExtra("productQuantity", dataModel.getmQuantity());
                details.putExtra("productPrice", dataModel.getmPrice());
                details.putExtra("id", dataModel.getmId());
                parent.getContext().startActivity(details);

            }
        });
        return view;
    }
}
