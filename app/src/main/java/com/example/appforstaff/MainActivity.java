package com.example.appforstaff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Connection cnt;
    AdaptStaff adapter;
    String ConnectionResult = "";
    ArrayList<Staff> stafflist = new ArrayList<>();
    ArrayList<Staff> staffList_s  ;
    Intent add_new_staff;
    Intent item_gridd;
    ListView list;
    Spinner sort;
    int[] imagestaff = {R.drawable.ic_launcher_foreground};
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sort_name = { "ФИО", "Почте", "Телефону"};
        sort = findViewById(R.id.sort);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sort_name);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(spinner_adapter);

        list = findViewById(R.id.gridviewlist);

        item_gridd = new Intent(this,item_grid.class);
        add_new_staff = new Intent(this, com.example.appforstaff.add_new_staff.class);

        GetStaffList();


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 );


        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Collections.sort(stafflist, new Comparator<Staff>() {
                            @Override
                            public int compare(Staff staff, Staff t1) {
                                return staff.name.compareTo(t1.name);
                            }
                        });
                        adapter = new AdaptStaff(MainActivity.this, stafflist, imagestaff);
                        list.setAdapter(adapter);
                        break;
                    case 1:
                        Collections.sort(stafflist, new Comparator<Staff>() {
                            @Override
                            public int compare(Staff staff, Staff t1) {
                                return staff.email.compareTo(t1.email);
                            }
                        });
                        adapter = new AdaptStaff(MainActivity.this, stafflist, imagestaff);
                        list.setAdapter(adapter);
                        break;
                    case 2:
                        Collections.sort(stafflist, new Comparator<Staff>() {
                            @Override
                            public int compare(Staff staff, Staff t1) {
                                return staff.phone.compareTo(t1.phone);
                            }
                        });
                        adapter = new AdaptStaff(MainActivity.this, stafflist, imagestaff);
                        list.setAdapter(adapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Staff item = (Staff) list.getItemAtPosition(i);

                item_gridd.putExtra("ФИО",item.name);
                item_gridd.putExtra("Телефон",item.phone);
                item_gridd.putExtra("Почта",item.email);
                item_gridd.putExtra("id", item.id);

                startActivity(item_gridd);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem menuItem2 = menu.findItem(R.id.action_add);
        SearchView  searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void onclick_view_add_staff(View view){startActivity(add_new_staff);}
    public void UpdateList(View view){GetStaffList();}

    public void GetStaffList(){
        try{
            stafflist.clear();
            cnt = SQLConnectHelper.connect();
            if(cnt != null){
                String qu = "select * from Staff";
                Statement statement = cnt.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);
                while (resultSet.next()){
                    Log.d(ConnectionResult, resultSet.getString("name"));
                    stafflist.add(new Staff(resultSet.getString("name"),resultSet.getString("phone"),resultSet.getString("email"),resultSet.getString("id")));
                }
                ConnectionResult = "Success";
                AdaptStaff adapter = new AdaptStaff(this,stafflist,imagestaff);
                list.setAdapter(adapter);
            }
            else {
                ConnectionResult = "Failed";
            }
            Log.d(ConnectionResult,"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.d(ConnectionResult, throwables.getMessage());
        }
    }
}