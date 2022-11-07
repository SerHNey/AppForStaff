package com.example.appforstaff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class add_new_staff extends AppCompatActivity {
    Intent Mainactiv;
    TextInputLayout name, phone , email;
    private Button postDataBtn;
    Bitmap image;
    ConstraintLayout table;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_staff);
        Mainactiv = new Intent(this,MainActivity.class);
        name = (TextInputLayout) findViewById(R.id.name);
        phone = (TextInputLayout) findViewById(R.id.phone);
        email = (TextInputLayout) findViewById(R.id.email);
        table = (ConstraintLayout) findViewById(R.id.table);

        postDataBtn = findViewById(R.id.add_new_staf);
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_staff(name.getEditText().toString(), email.getEditText().toString(), phone.getEditText().toString());
            }
        });
    }

    public void btn_onclick_to_main_act(View view){
        back();
    }
    public void back(){startActivity(Mainactiv);}


    public void add_staff(String name, String email, String phone){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/НаумовСА/api/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Staff staff = new Staff(name, email, phone);
        Call<Staff> call = retrofitAPI.createPost(staff);
        call.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                Toast.makeText(add_new_staff.this, "Data added to API", Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
                //phone.setText("");
                //email.setText("");
                //name.setText("");
                Staff responseFromAPI = response.body();
                //String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Email : " + responseFromAPI.getEmail() + "\n" + "Phone : " + responseFromAPI.getPhone();
                //responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                //responseTV.setText("Error found is : " + t.getMessage());
            }
        });

    }

}