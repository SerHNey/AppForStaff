package com.example.appforstaff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Connection cnt;
    SimpleAdapter adapter;
    Intent add_new_staff;
    Intent item_gridd;
    GridView list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn_add_staff =(ImageButton) findViewById(R.id.btn_add_staff);
        ImageButton btn_update_list =(ImageButton) findViewById(R.id.btn_update_list);
        list = (GridView) findViewById(R.id.gridviewlist);



        item_gridd = new Intent(this,item_grid.class);
        add_new_staff = new Intent(this, com.example.appforstaff.add_new_staff.class);



        btn_update_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                Statement statement;
                try {
                    cnt = SQLConnectHelper.connect();
                    if (cnt != null) {
                        String query = "select * from Staff";
                        statement = cnt.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            Map<String, String> tab = new HashMap<String, String>();
                            tab.put("name", resultSet.getString("name"));
                            tab.put("phone", resultSet.getString("phone"));
                            tab.put("email", resultSet.getString("email"));
                            data.add(tab);


                        }
                        String[] from = {"name", "phone", "email"};
                        int[] to = {R.id.name, R.id.phone, R.id.email};
                        adapter = new SimpleAdapter(MainActivity.this, data, R.layout.gridviewlayout, from, to);
                        list.setAdapter(adapter);
                        statement.close();
                    }

                } catch (Exception exception) {
                    Log.d("ERROR", exception.getMessage());
                }
            }
        });
    }
    public void onclick_view_add_staff(View view){startActivity(add_new_staff);}
}