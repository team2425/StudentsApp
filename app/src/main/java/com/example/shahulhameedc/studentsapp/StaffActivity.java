package com.example.shahulhameedc.studentsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
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
                Intent obj1 = new Intent(StaffActivity.this, StuActivity.class);
                startActivity(obj1);
                break;
            case R.id.admid:
                Intent cityMap3 = new Intent(StaffActivity.this, AdminActivity.class);
                startActivity(cityMap3);
                break;
            case R.id.hmid:
                Intent cityMap = new Intent(StaffActivity.this, MainActivity.class);
                startActivity(cityMap);
                break;


        }
        return false;
    }
}
