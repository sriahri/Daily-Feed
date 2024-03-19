package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {


    TextInputEditText editText_username;
    TextInputEditText editText_email;
    TextInputEditText editText_password;
    TextInputEditText editText_re_enter_password;

    ProgressBar progressBar;
    Button button_register;

    TextView textView_login_click;
    private FirebaseAuth mAuth;


    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearLayoutRegister.setBackgroundResource(R.drawable.register_background);
        editText_username = binding.editTextUsername;
        editText_email = binding.editTextEmail;
        editText_password = binding.editTextPassword;
        editText_re_enter_password = binding.editTextReEnterPassword;
        button_register = binding.buttonRegister;
        progressBar = binding.progressBar;
        textView_login_click = binding.textViewLoginClick;

        textView_login_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String userName = editText_username.getText().toString();
                String emailAddress = editText_email.getText().toString();
                String password = editText_password.getText().toString();
                String reEnterPassword = editText_re_enter_password.getText().toString();

                if (userName.equalsIgnoreCase("")) {
                    Toast.makeText(Register.this, "User name field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (emailAddress.equalsIgnoreCase("")) {
                    Toast.makeText(Register.this, "Email address field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equalsIgnoreCase("")) {
                    Toast.makeText(Register.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (reEnterPassword.equalsIgnoreCase("")) {
                    Toast.makeText(Register.this, "Re-enter password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(reEnterPassword)) {
                    Toast.makeText(Register.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information
                                    // Log.d(TAG, "createUserWithEmail:success");
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                    // updateUI(user);

                                    Toast.makeText(Register.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }
                            }
                        });



            }
        });
    }
}