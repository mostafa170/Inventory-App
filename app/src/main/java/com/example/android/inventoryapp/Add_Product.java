package com.example.android.inventoryapp;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by mostafa on 28/07/2017.
 */

public class Add_Product extends AppCompatActivity {
    public String price;
    public String quantity;
    public String name;
    public long nextID;

    public product_Inventory product = new product_Inventory();
    InventoryDbHelper InventoryDbHelper = new InventoryDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        nextID = (InventoryDbHelper.rowCount() + 1);
        Intent intent = getIntent();
        String message = intent.getStringExtra("Add New Product");
        setTitle(message);
    }

    public void onClickSubmit(View view) {
        EditText nameText = (EditText) findViewById(R.id.productName);
        EditText priceText = (EditText) findViewById(R.id.productPrice);
        EditText quantityText = (EditText) findViewById(R.id.productQuantity);
        ImageView img = (ImageView) findViewById(R.id.imageSelected);
        name = nameText.getText().toString();
        price = (priceText.getText().toString());
        quantity = (quantityText.getText().toString());

        if (nameText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "name is required", Toast.LENGTH_LONG).show();
            nameText.setError("name is required");
        } else if (priceText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Price is required", Toast.LENGTH_LONG).show();
            priceText.setError("Price is required");
        } else if (quantityText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Quantity is required", Toast.LENGTH_LONG).show();
            quantityText.setError("Quantity is required");
        } else if (img.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Upload image", Toast.LENGTH_LONG).show();
        } else {
            InventoryDbHelper.addEntry(new product_Inventory(name, Integer.parseInt(quantity), Double.parseDouble(price)));
            Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Product_Review.class);
            startActivity(intent);
            Log.v("VALUES : ", name + " " + price + " " + quantity);
        }
    }

    public void btnImageOnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 &&
                resultCode == RESULT_OK && null != data) {
            Toast.makeText(this, "Uploading", Toast.LENGTH_LONG).show();
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.imageSelected);
                imageView.setImageBitmap(bitmap);
                String filename = Long.toString(nextID);
                saveToInternalStorage(bitmap, filename);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Could not load this image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToInternalStorage(Bitmap bmp, String filename) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File appDirectory = contextWrapper.getFilesDir();

        File currentPath = new File(appDirectory, filename);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(currentPath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
