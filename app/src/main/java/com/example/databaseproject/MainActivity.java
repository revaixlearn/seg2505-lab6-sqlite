package com.example.databaseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView idView;
    private EditText productBox;
    private EditText skuBox;
    private FeedReaderDbHelper feedReaderDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = findViewById(R.id.productID);
        productBox = findViewById(R.id.productName);
        skuBox = findViewById(R.id.productSku);

        // Persisting the database connection (Android Developers SQLite guide)
        feedReaderDbHelper = new FeedReaderDbHelper(this);
        if (feedReaderDbHelper.findEntryIdsByTitle("SEG2505").isEmpty()) {
            feedReaderDbHelper.insertEntry("SEG2505", "Semaine 6 : SQLite et Android");
        }
    }

    @Override
    protected void onDestroy() {
        feedReaderDbHelper.close();
        super.onDestroy();
    }

    public void newProduct(View view) {
        String name = productBox.getText().toString().trim();
        String skuText = skuBox.getText().toString().trim();
        if (name.isEmpty() || skuText.isEmpty()) {
            Toast.makeText(this, "Enter a name and a SKU", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDBHandler dbHandler = new MyDBHandler(this);
        Product product = new Product(name, Integer.parseInt(skuText));
        dbHandler.addProduct(product);

        productBox.setText("");
        skuBox.setText("");
        idView.setText(R.string.not_assigned);
        Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
    }

    public void lookupProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        Product product = dbHandler.findProduct(productBox.getText().toString().trim());

        if (product != null) {
            idView.setText(String.valueOf(product.getId()));
            skuBox.setText(String.valueOf(product.getSku()));
        } else {
            idView.setText("No match found");
        }
    }

    public void deleteProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.removeProduct(productBox.getText().toString().trim());

        if (result) {
            idView.setText("Record deleted");
            productBox.setText("");
            skuBox.setText("");
        } else {
            idView.setText("No match found");
        }
    }
}
