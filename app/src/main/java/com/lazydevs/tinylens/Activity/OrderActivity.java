package com.lazydevs.tinylens.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylens.Model.ModelOrder;
import com.lazydevs.tinylens.R;

import java.text.DateFormat;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {

    TextView photoOwnerName,tshirtPrice,tshirtOriginalPrice,tshirtAfterDiscountPrice,tshirtDiscount,tshirtContactNo,tvProductPrice;
    ImageView selectedPhoto;

    Spinner spinnerProductTypes;
    Spinner spinnerTshirtSize;
    Spinner spinnerTshirtColor;
    Spinner spinnerProductQuantity;
    LinearLayout tshirtSize;
    
    ArrayAdapter<CharSequence> adapter_product;
    ArrayAdapter<CharSequence> adapter_tshirt_size;
    ArrayAdapter<CharSequence> adapter_tshirt_color;
    ArrayAdapter<CharSequence> adapter_product_quantity;
    private String selceted_product;
    private String selceted_product_quantity;
    private String perProductPrice;
    private int totalAfterDiscount;
    private int photoOwnerProfit;

    private String selceted_tshirt_size;
    private String selceted_tshirt_color;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        photoOwnerName=(TextView)findViewById(R.id.tv_photo_owner_name);
        tshirtPrice=(TextView)findViewById(R.id.tv_tshirt_price_order);
        tvProductPrice=(TextView)findViewById(R.id.tv_product_price);
        tshirtOriginalPrice=(TextView)findViewById(R.id.tv_original_total_tshirt_price);
        tshirtAfterDiscountPrice=(TextView)findViewById(R.id.tv_tshirt_total_after_discount_price);
        tshirtDiscount=(TextView)findViewById(R.id.tv_tshirt_discount_order);
        tshirtContactNo=(TextView)findViewById(R.id.tv_tshirt_contact_no);
        tshirtSize=(LinearLayout)findViewById(R.id.layout_size);
        selectedPhoto=(ImageView)findViewById(R.id.iv_selected_photo_order);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        addItemonProductSelectSpinner();
        addItemonTshirtSizeSelectSpinner();
        addItemonTshirtColorSelectSpinner();


        addItemonProductQuantitySelectSpinner();
        addListenerOnSpinnerItemSelection();

        Glide
                .with(getApplicationContext())
                .load(getIntent().getExtras().getString("image"))
                .into(selectedPhoto);

        photoOwnerName.setText("Photo by"+" "+getIntent().getExtras().getString("user_name"));



    }

    private void addItemonProductQuantitySelectSpinner() {

        spinnerProductQuantity=(Spinner)findViewById(R.id.spinner_product_quantity);
        adapter_product_quantity = ArrayAdapter.createFromResource(this, R.array.product_quantity, R.layout.product_spinner_item);
        adapter_product_quantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductQuantity.setAdapter(adapter_product_quantity);
    }

    private void addItemonTshirtColorSelectSpinner() {

        spinnerTshirtColor=(Spinner)findViewById(R.id.spinner_tshirt_color);
        adapter_tshirt_color = ArrayAdapter.createFromResource(this, R.array.tshirt_color, R.layout.product_spinner_item);
        adapter_tshirt_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTshirtColor.setAdapter(adapter_tshirt_color);
    }


    private void addItemonTshirtSizeSelectSpinner() {

        spinnerTshirtSize=(Spinner)findViewById(R.id.spinner_tshirt_size);
        adapter_tshirt_size = ArrayAdapter.createFromResource(this, R.array.tshirt_size, R.layout.product_spinner_item);
        adapter_tshirt_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTshirtSize.setAdapter(adapter_tshirt_size);
    }

    private void addItemonProductSelectSpinner() {

        spinnerProductTypes=(Spinner)findViewById(R.id.sp_product_types);
        adapter_product = ArrayAdapter.createFromResource(this, R.array.product_types, R.layout.product_spinner_item);
        adapter_product.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductTypes.setAdapter(adapter_product);
    }

    private void addListenerOnSpinnerItemSelection() {

        spinnerProductTypes.setOnItemSelectedListener(new ProductSelectedListener());
        spinnerTshirtSize.setOnItemSelectedListener(new tshirtSizeSelectedListener());
        spinnerTshirtColor.setOnItemSelectedListener(new tshirtColorSelectedListener());
        spinnerProductQuantity.setOnItemSelectedListener(new productQuantitySelectedListener());
    }

    public void btn_back_order(View view) {

        onBackPressed();
        finish();
    }


    private class tshirtSizeSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            selceted_tshirt_size=parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class tshirtColorSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            selceted_tshirt_color=parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class productQuantitySelectedListener implements AdapterView.OnItemSelectedListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            selceted_product_quantity=parent.getItemAtPosition(position).toString();
            Price(selceted_product_quantity,perProductPrice);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class ProductSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            selceted_product=parent.getItemAtPosition(position).toString();
            if(selceted_product.equals("Mug"))
            {
                tvProductPrice.setText("Price/Mug:");
                tshirtPrice.setText("120 BDT");
                perProductPrice="120";
                tshirtSize.setVisibility(LinearLayout.GONE);
                tshirtOriginalPrice.setText("");
                tshirtAfterDiscountPrice.setText("");
                selceted_tshirt_size="Standard";
            }
            else {

                tvProductPrice.setText("Price/T-shirt:");
                tshirtPrice.setText("200 BDT");
                perProductPrice="200";
                tshirtSize.setVisibility(LinearLayout.VISIBLE);
                tshirtOriginalPrice.setText("");
                tshirtAfterDiscountPrice.setText("");
            }
            if (selceted_product_quantity==null)
            {
                Price("1",perProductPrice);
            }
            else
            {
                Price(selceted_product_quantity,perProductPrice);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void btnOrder(View view) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.child("orders").push().getKey();
        ModelOrder order = new ModelOrder(tshirtContactNo.getText().toString(),
                getIntent().getExtras().getString("image"),
                selceted_product,
                key,
                "Size: "+selceted_tshirt_size+"\n"+"Color: "+selceted_tshirt_color+"\n"+"Price/t-shirt: 200BDT"+"\n"+"Original Total: "+tshirtOriginalPrice.getText().toString()+"\n"+"Discount: 10%"+"\n"+"Total After Discount: "+totalAfterDiscount,
                DateFormat.getDateTimeInstance().format(new Date()),
                "Not Received",
                getIntent().getExtras().getString("userId"),
                firebaseUser.getUid(),
                selceted_product_quantity,
                null,String.valueOf(photoOwnerProfit),null);

        databaseReference.child("orders").child(key).setValue(order);
        Toast.makeText(this, "Order Complete", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }

    public void Price(String quantity, String pricePerProduct)
    {
        int price=Integer.parseInt(quantity)*Integer.parseInt(pricePerProduct);
        tshirtOriginalPrice.setText(price+" "+"BDT");
        totalAfterDiscount=price-(price*10)/100;
        int profit=price-(price*5)/100;
        photoOwnerProfit=price-profit;
        tshirtAfterDiscountPrice.setText(totalAfterDiscount+" "+"BDT");
    }
}
