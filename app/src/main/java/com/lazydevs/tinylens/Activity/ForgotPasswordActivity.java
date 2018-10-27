package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.lazydevs.tinylens.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailText;
    ImageButton resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailText = (EditText)findViewById(R.id.et_email_fpa);
        resetButton = (ImageButton)findViewById(R.id.bt_resetPassword);
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(emailText.getText().toString().replaceAll(" ",""))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
                                   //go to login page
                                    Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            }

                        });

            }
        });
    }



}
