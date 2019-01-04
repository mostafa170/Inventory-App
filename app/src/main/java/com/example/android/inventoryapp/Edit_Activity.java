package com.example.android.inventoryapp;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by mostafa on 28/07/2017.
 */

public class Edit_Activity  extends AppCompatActivity {
    public int id;
    public String pName;
    public product_Inventory product = new product_Inventory();
    public static double priceProduct;
    public int quantity1;
    InventoryDbHelper InventoryDbHelper = new InventoryDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        TextView product = (TextView) findViewById(R.id.productName);
        TextView price = (TextView) findViewById(R.id.productPrice1);
        TextView quantity = (TextView) findViewById(R.id.productQuantity);
        TextView orderQuantity = (TextView) findViewById(R.id.order_quantity);

        Intent details = getIntent();
        setTitle("Product Details");
        pName = details.getStringExtra("productName");
        product.setText(pName);
        id = details.getIntExtra("id", 0);
        ContextWrapper cw = new ContextWrapper(this);
        File dir = cw.getFilesDir();
        quantity1 = details.getIntExtra("productQuantity", 0);
        quantity.setText("" + quantity1);
        priceProduct = details.getDoubleExtra("productPrice", 0.0);
        price.setText("" + priceProduct);
        orderQuantity.setText("" + quantity1);

        String imageLocationDir = dir.toString();
        id = details.getIntExtra("id", 0);
        String imagePath = imageLocationDir + "/" + id;
        ImageView imageView = (ImageView) findViewById(R.id.imgIcon);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);
    }

    public void incrementQuantity(View view) {
        if (quantity1 == 100) {
            return;
        }
        quantity1 += 1;
        displayQuantity(quantity1);
    }

    public void decrementQuantity(View view) {
        if (quantity1 == 0) {
            return;
        }
        quantity1 -= 1;
        displayQuantity(quantity1);
    }

    private void displayQuantity(int count) {
        TextView quantity = (TextView) findViewById(R.id.order_quantity);
        quantity.setText("" + count);
    }

    public void onClickDelete(final View view) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("delete product")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItemPermanently(id);
                        Toast.makeText(view.getContext(), "Product has Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(view.getContext(), Product_Review.class);
                        view.getContext().startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void deleteItemPermanently(int id) {
        InventoryDbHelper.deleteProduct(id);
    }

    public void onClickReorder(View view) {

        String subject = "Order from App";
        String message = "Product Name: " + pName +
                "\nProduct Price: " + priceProduct +
                "\nQuantity To be ordered: " + quantity1;
        String[] emails = {"mostafakamelkamel@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }
    public void onClickUpdate(View view) {
        boolean success = InventoryDbHelper.updateQuantity(id, quantity1);
        Log.e("db update success: ", Boolean.toString(success));
        InventoryDbHelper.close();
        TextView quantity = (TextView) findViewById(R.id.productQuantity);
        quantity.setText("" + quantity1);
        Toast.makeText(this, "Update product", Toast.LENGTH_SHORT).show();
    }
}

