package com.example.introslider.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.introslider.R;

public class SignInActivity extends AppCompatActivity {

    Button signInBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

            signInBT = findViewById(R.id.signInBT);

            signInBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(SignInActivity.this, "It is In Another Sdk and Module!", Toast.LENGTH_LONG).show();
                }
            });
    }
}
