package com.lazydevs.tinylens.Activity;

import android.app.DownloadManager;
import android.icu.text.LocaleDisplayNames;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lazydevs.tinylens.R;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderStatus,orderId,orderDate,productType,productQuantity,orderDescription,buyerName,photoBy,tvTransactionId;
    ImageView orderedPhoto;
    Button btnSend, banCancel;
    String OrderId;
    LinearLayout layoutTransaction,layoutOrderCancel;
    private FirebaseAuth firebaseAuth;
    private Handler mWaitHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        firebaseAuth = FirebaseAuth.getInstance();

        layoutOrderCancel=findViewById(R.id.layout_order_cancel);
        layoutTransaction=findViewById(R.id.layout_transaction);
        orderStatus=(TextView)findViewById(R.id.tv_order_status);
        orderedPhoto=(ImageView)findViewById(R.id.iv_ordered_photo);
        orderId=(TextView) findViewById(R.id.tv_orderid);
        orderDate=(TextView) findViewById(R.id.tv_order_date);
        productType=(TextView) findViewById(R.id.tv_product_type);
        productQuantity=(TextView) findViewById(R.id.tv_product_quantity);
        orderDescription=(TextView) findViewById(R.id.tv_order_description);
        buyerName=(TextView) findViewById(R.id.tv_buyer_name);
        photoBy=(TextView) findViewById(R.id.tv_photo_owner_name_order);
        btnSend=(Button) findViewById(R.id.btn_send);
        banCancel=(Button) findViewById(R.id.btn_cancel);
        tvTransactionId=(TextView)findViewById(R.id.tv_transaction_id);

        String OrderStatus=getIntent().getExtras().getString("order_status");

        if (OrderStatus.equals("Received"))
        {
            layoutTransaction.setVisibility(LinearLayout.VISIBLE);
        }else{
            layoutTransaction.setVisibility(LinearLayout.GONE);
        }

        if(OrderStatus.equals("Not Received"))
        {
            layoutOrderCancel.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            layoutOrderCancel.setVisibility(LinearLayout.GONE);
        }


        Glide
                .with(getApplicationContext())
                .load(getIntent().getExtras().getString("ordered_image_url"))
                .into(orderedPhoto);

        OrderId=getIntent().getExtras().getString("order_id");
        orderId.setText("Order ID: "+" "+OrderId);
        orderDate.setText("Order Date: "+" "+getIntent().getExtras().getString("order_date"));
        productType.setText("Product Type: "+" "+getIntent().getExtras().getString("order_product_type"));
        productQuantity.setText("Quantity: "+" "+getIntent().getExtras().getString("order_product_quantity"));
        orderDescription.setText(getIntent().getExtras().getString("order_description"));
        buyerName.setText("Order by: "+getIntent().getExtras().getString("buyer_name"));
        photoBy.setText("Photo by: "+getIntent().getExtras().getString("owner_name"));
        orderStatus.setText("Order Status: "+" "+OrderStatus);


        banCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("orders");
                query.orderByKey().equalTo(OrderId).addChildEventListener(new QueryForOrderCancel());
                Toast.makeText(OrderDetailActivity.this, "Order Canceled", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("orders");
                query.orderByKey().equalTo(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
                        databaseReference.child(OrderId).child("transactionId").setValue(tvTransactionId.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(OrderDetailActivity.this, "Done", Toast.LENGTH_SHORT).show();
                mWaitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                        finish();
                    }
                }, 1000);
            }
        });

    }

    public void btn_back_order_detail(View view) {
        onBackPressed();
        finish();
    }

    private class QueryForOrderCancel implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("orders");
            databaseReference.child(OrderId).removeValue();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
