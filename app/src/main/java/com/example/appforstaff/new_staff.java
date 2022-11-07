package com.example.appforstaff;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class new_staff extends AppCompatActivity {
    private EditText nameEdt, emailEdt, phoneEdt;

    private Button postDataBtn;

    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_staff);
        nameEdt = findViewById(R.id.idEdtName);
        emailEdt = findViewById(R.id.idEdtEmail);
        phoneEdt = findViewById(R.id.idEdtPhone);

        postDataBtn = findViewById(R.id.idBtnPost);

        responseTV = findViewById(R.id.idTVResponse);

        //loadingPB = findViewById(R.id.idLoadingPB);
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdt.getText().toString().isEmpty() && emailEdt.getText().toString().isEmpty() && phoneEdt.getText().toString().isEmpty()) {
                    Toast.makeText(new_staff.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEdt.getText().toString(), emailEdt.getText().toString(), phoneEdt.getText().toString());
            }
        });

    }
    private void postData(String name, String phone, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/НаумовСА/api/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Staff modal = new Staff(name, email, phone);
        Call<Staff> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                Toast.makeText(new_staff.this, "Data added to API", Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
                phoneEdt.setText("");
                emailEdt.setText("");
                nameEdt.setText("");
                Staff responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Email : " + responseFromAPI.getEmail() + "\n" + "Phone : " + responseFromAPI.getPhone();
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}