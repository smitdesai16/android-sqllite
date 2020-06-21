package me.creatorguy.androidsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMainProduct;
    TextView tvMainProducts;
    ProductDBHandler productDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMainProduct = findViewById(R.id.etMainProduct);
        Button btnMainAdd = findViewById(R.id.btnMainAdd);
        Button btnMainRemove = findViewById(R.id.btnMainRemove);
        tvMainProducts = findViewById(R.id.tvMainProducts);
        productDBHandler = new ProductDBHandler(this, null, null, 0);

        btnMainAdd.setOnClickListener(this);
        btnMainRemove.setOnClickListener(this);

        loadDataInView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainAdd:
                productDBHandler.addProduct(new Product(etMainProduct.getText().toString()));
                loadDataInView();
                break;

            case R.id.btnMainRemove:
                productDBHandler.removeProduct(etMainProduct.getText().toString());
                loadDataInView();
                break;

            default:
                break;
        }
    }

    public void loadDataInView() {
        tvMainProducts.setText("");
        List<Product> products = productDBHandler.getProducts();
        for (Product product : products) {
            tvMainProducts.append(product.get_id() + " " + product.getName() + ";");
        }
    }
}