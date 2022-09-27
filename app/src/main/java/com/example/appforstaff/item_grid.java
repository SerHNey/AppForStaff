package com.example.appforstaff;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.net.CookieHandler;

public class item_grid extends AppCompatActivity {
    TextInputLayout name, phone, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_grid);
        Bundle arguments = getIntent().getExtras();
        String _name = arguments.get("").toString();
        String _phone = arguments.get("Имя").toString();
        String _email = arguments.get("Отчество").toString();

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);


        name.getEditText().setText(_name);
        phone.getEditText().setText(_phone);
        email.getEditText().setText(_email);

    }
    public void btn_onclick_back_main(View view){this.finish();}
}