package com.example.parsestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    private EditText etNewusername;
    private EditText etNewpassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // check if user is already logged in after opening the app again
        // if so, go to main activity
//        if (ParseUser.getCurrentUser() != null){
//            goMainActivity();
//        }

        etNewpassword = findViewById(R.id.etNewpassword);
        etNewusername = findViewById(R.id.etNewusername);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up button");
                // Create the ParseUser
                ParseUser user = new ParseUser();
                signupUser(user);
            }
        });
    }

    private void signupUser(ParseUser parseUser) {
        // Set core properties
        parseUser.setUsername(etNewusername.getText().toString());
        parseUser.setPassword(etNewpassword.getText().toString());
        // Invoke signUpInBackground
        parseUser.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with sign up", e);
                    Toast.makeText(SignupActivity.this, "Issue with signup!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Upon logging in properly, navigate to the main activity using intent
                goMainActivity();
                Toast.makeText(SignupActivity.this, "Sign up Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
