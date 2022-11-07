package com.example.appforstaff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Objects;

public class item_grid extends AppCompatActivity {
    TextInputLayout name, phone, email;
    ImageView image;
    Integer id;
    Connection cnt;
    String encodedImage;
    Intent Mainactiv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_grid);

        Mainactiv = new Intent(this,MainActivity.class);

        Bundle arguments = getIntent().getExtras();
        String _name = arguments.get("name").toString();
        String _phone = arguments.get("phone").toString();
        String _email = arguments.get("email").toString();
        String _image;
        try {
            _image = arguments.get("image").toString();
        }
        catch (Exception e){
            _image = "";
        }
        id = Integer.valueOf(arguments.get("id").toString());



        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        image = findViewById(R.id.image);

        name.getEditText().setText(_name);
        phone.getEditText().setText(_phone);
        email.getEditText().setText(_email);

        if(_image != ""){
            image.setImageBitmap(getImageBitmap(_image));
        }
        else {
            image.setImageResource(R.drawable.ic_launcher_background);
        }

    }
    public void btn_onclick_back_main(View view){this.finish();}

    public void UpdateItemDialog(View v){
        /*
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Вы точно хотите обновить данные о сотруднике?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            SQLConnectHelper cntn = new SQLConnectHelper();
                            cnt = cntn.connect();
                            String qu = "update Staff set name = '"
                                    + Objects.requireNonNull(name.getEditText().getText())
                                    + "',phone ='" + Objects.requireNonNull(phone.getEditText()).getText()
                                    + "', email ='" + Objects.requireNonNull(email.getEditText()).getText()+
                                    "' where id = " + id;
                            Statement statement = cnt.createStatement();
                            ResultSet resultSet = statement.executeQuery(qu);
                            cnt.close();
                            //((MainActivity)getBaseContext()).GetStaffList();
                        }
                        catch (SQLException throwables) {
                            throwables.printStackTrace();
                            Log.d("Error - ",throwables.getMessage());
                        }
                    }
                })
                .setNegativeButton("Нет", null)
                .create()
                .show();
                */


    }

    public void UpdateItem(View view){
        try{
            SQLConnectHelper cntn = new SQLConnectHelper();
            cnt = cntn.connect();
            String qu = "update Staff set name = '"
                    + Objects.requireNonNull(name.getEditText().getText())
                    + "',phone ='" + Objects.requireNonNull(phone.getEditText()).getText()
                    + "', email ='" + Objects.requireNonNull(email.getEditText()).getText()+
                    "' where id = " + id;
            Statement statement = cnt.createStatement();
            ResultSet resultSet = statement.executeQuery(qu);
            cnt.close();
           // ((MainActivity)getBaseContext()).GetStaffList();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.d("Error - ",throwables.getMessage());
        }
    }



    public void DeleteItemDialog(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Вы точно хотите удалить данного сотрудника?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            SQLConnectHelper connection = new SQLConnectHelper();
                            cnt = connection.connect();
                            String qu = "delete from Staff where id = " + id;
                            Statement statement = cnt.createStatement();
                            ResultSet resultSet = statement.executeQuery(qu);
                            cnt.close();
                        }
                        catch (SQLException throwables) {
                            throwables.printStackTrace();
                            Log.d("Error - ",throwables.getMessage());
                        }
                        //startActivity(Mainactiv);
                    }
                })
                .setNegativeButton("Нет",null)
                .create()
                .show();
    }
    private Bitmap getImageBitmap(String encodedImg) {
        if (encodedImg != null) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(item_grid.this.getResources(),
                R.drawable.ic_launcher_foreground);
    }
}