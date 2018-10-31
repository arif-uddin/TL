package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylens.R;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
    }

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void resendVerification(View view) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(VerificationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            //Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(VerificationActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void enterApp(View view) {
        user.reload();
        if (user.isEmailVerified()) {
            Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void btn_back_verification(View view) {
        Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

