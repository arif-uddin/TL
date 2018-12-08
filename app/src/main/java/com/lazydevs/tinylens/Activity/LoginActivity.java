package com.lazydevs.tinylens.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylens.helper.Helper;
import com.lazydevs.tinylens.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText pass;
    TextView Register;
    CheckBox checkBoxShowPassword;
    ImageButton login;
    FirebaseAuth firebaseAuth;
    Helper helper;
    TextView forgetPassword;
    AVLoadingIndicatorView loginIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.et_email);
        pass= findViewById(R.id.et_pass);
        Register = findViewById(R.id.bt_register);
        login = (ImageButton) findViewById(R.id.bt_login);
        forgetPassword = findViewById(R.id.tv_forget_pass_la);
        checkBoxShowPassword=(CheckBox)findViewById(R.id.cb_show_password);
        loginIndicator=(AVLoadingIndicatorView)findViewById(R.id.login_indicator);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper=new Helper();
                String Email = email.getText().toString().replaceAll(" ","");
                String Pass = pass.getText().toString();
                if (Email.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();

                }
                else {
                    
                    boolean isEmailValid=helper.isEmailValid(Email);
                    
                    if (isEmailValid)
                    {
                      if (Pass.isEmpty())
                      {
                          Toast.makeText(LoginActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();

                      } 
                      
                      else {
                          
                          boolean isPasswordValid= helper.isPasswordValid(Pass);
                          
                          if(isPasswordValid)
                          {
                              loginUser(Email,Pass);
                              login.setVisibility(ImageButton.GONE);
                              loginIndicator.setVisibility(AVLoadingIndicatorView.VISIBLE);

                          }
                          
                          else 
                          {
                              Toast.makeText(LoginActivity.this, "Password must be at least 6 character", Toast.LENGTH_SHORT).show();

                          }
                      }
                        
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Email is invalid!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        checkBoxShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBoxShowPassword.isChecked()) {
                    // show password
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    // hide password
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }

   void loginUser(String Email,String Pass)
    {
        firebaseAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user!=null)
                            {
                                Toast.makeText(LoginActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            //updateUI(user);
                        } else {
                            login.setVisibility(ImageButton.VISIBLE);
                            loginIndicator.setVisibility(AVLoadingIndicatorView.GONE);

                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


//    protected void onStop() {
//        super.onStop();
//        finish();
//    }

    public void btnExplore(View view) {
        Intent intent= new Intent(LoginActivity.this,ExploreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
