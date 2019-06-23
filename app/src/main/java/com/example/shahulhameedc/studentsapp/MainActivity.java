package com.example.shahulhameedc.studentsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.drawablepg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.stuid:
                Intent obj1 = new Intent(MainActivity.this, StuActivity.class);
                startActivity(obj1);
                break;
            case R.id.stafid:
                Intent cityMap3 = new Intent(MainActivity.this, StaffActivity.class);
                startActivity(cityMap3);
                break;
            case R.id.admid:
                Intent cityMap = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(cityMap);
                break;


        }
        return false;
    }
}
