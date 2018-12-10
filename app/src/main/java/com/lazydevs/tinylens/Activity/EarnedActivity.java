package com.lazydevs.tinylens.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lazydevs.tinylens.Model.ModelEarn;
import com.lazydevs.tinylens.R;
import com.lazydevs.tinylens.adapter.ProfitBalanceAdapter;

import java.util.ArrayList;

public class EarnedActivity extends AppCompatActivity {

    RecyclerView recyclerViewEarnedList;
    ProfitBalanceAdapter profitBalanceAdapter;
    ArrayList<ModelEarn> modelEarnArrayList;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earned);

        firebaseAuth = FirebaseAuth.getInstance();
        modelEarnArrayList= new ArrayList<>();
        recyclerViewEarnedList=(RecyclerView)findViewById(R.id.recyclerView_earn_list);

        LinearLayoutManager linearVertical = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewEarnedList.setLayoutManager(linearVertical);
        profitBalanceAdapter=new ProfitBalanceAdapter(getApplicationContext(),modelEarnArrayList);
        recyclerViewEarnedList.setAdapter(profitBalanceAdapter);

        Query query = FirebaseDatabase.getInstance().getReference().child("profits");
        query.orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new QueryForProfit());
    }

    public void btn_back_earned(View view) {
        onBackPressed();
        finish();
    }

    private class QueryForProfit implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelEarn modelEarn = dataSnapshot.getValue(ModelEarn.class);
            profitBalanceAdapter.setValues(modelEarn);
            profitBalanceAdapter.notifyDataSetChanged();
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
