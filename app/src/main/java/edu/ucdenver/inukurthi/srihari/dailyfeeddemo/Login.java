package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {


    TextInputEditText editText_email;
    TextInputEditText editText_password;
    ProgressBar progressBar;
    Button button_login;
    TextView textView_register_click;
    ActivityLoginBinding binding;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearLayoutLogin.setBackgroundResource(R.drawable.login_background);
        editText_email = binding.editTextEmail;
        editText_password = binding.editTextPassword;
        progressBar = binding.progressBar;
        button_login = binding.buttonLogin;
        textView_register_click = binding.textViewRegisterClick;

        textView_register_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String emailAddress = editText_email.getText().toString();
                String password = editText_password.getText().toString();

                if (emailAddress.equalsIgnoreCase("")) {
                    Toast.makeText(Login.this, "Email address field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equalsIgnoreCase("")) {
                    Toast.makeText(Login.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // Log.d(TAG, "signInWithEmail:success");
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(Login.this
                                            , MainActivity.class));
                                    finish();
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }
                            }
                        });
            }
        });
    }
}