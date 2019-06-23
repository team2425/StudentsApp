package com.example.shahulhameedc.studentsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class AdminActivity extends AppCompatActivity {



    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText eId, eName,eemail, ePassword,ecountry;
    //RatingBar ratingBar;
    //Spinner spinnerTeam;
    //ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    List<Register> regList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        eId = (EditText) findViewById(R.id.eId);
        eName = (EditText) findViewById(R.id.eName);
        ePassword = (EditText) findViewById(R.id.epass);
        eemail = (EditText) findViewById(R.id.eemail);
        ecountry = (EditText) findViewById(R.id.ecountry);
        //      ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);

        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        //    progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewRec);

        regList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateRec();
                } else {
                    createRec();
                }
            }
        });
        readRec();
    }


    private void createRec() {
        //String id = eId.getText().toString();
        String name = eName.getText().toString().trim();
        String password = ePassword.getText().toString().trim();
        String email = eemail.getText().toString().trim();
        String country = ecountry.getText().toString().trim();
        //int rating = (int) ratingBar.getRating();
        //String team = spinnerTeam.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            eName.setError("Please enter name");
            eName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ePassword.setError("Please enter password");
            ePassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            eemail.setError("Please enter email");
            eemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(country)) {
            ecountry.setError("Please enter country");
            ecountry.requestFocus();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        params.put("email", email);
        params.put("country", country);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_REC, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readRec() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_REC, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateRec() {
        String id = eId.getText().toString();
        String name = eName.getText().toString().trim();
        String password = ePassword.getText().toString().trim();
        String email = eemail.getText().toString().trim();
        String country = ecountry.getText().toString().trim();

        //int email = (int) ratingBar.getRating();
        //String team = spinnerTeam.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)) {
            eName.setError("Please enter name");
            eName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ePassword.setError("Please enter password");
            ePassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("password", password);
        params.put("email", email);
        params.put("country", country);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_REC, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        eName.setText("");
        ePassword.setText("");
        eemail.setText("");
        ecountry.setText("");
        //ratingBar.setRating(0);
        //spinnerTeam.setSelection(0);

        isUpdating = false;
    }

    private void deleteRec(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_REC + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshRecList(JSONArray register) throws JSONException {
        regList.clear();

        for (int i = 0; i < register.length(); i++) {
            JSONObject obj = register.getJSONObject(i);

            regList.add(new Register(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("password"),
                    obj.getString("email"),
                    obj.getString("country")
            ));
        }

        RegAdapter adapter = new RegAdapter(regList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshRecList(object.getJSONArray("register"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class RegAdapter extends ArrayAdapter<Register> {
        List<Register> regList;

        public RegAdapter(List<Register> regList) {
            super(AdminActivity.this, R.layout.layout_rec_list, regList);
            this.regList = regList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_rec_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Register robj = regList.get(position);

            textViewName.setText(robj.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    eId.setText(String.valueOf(robj.getId()));
                    eName.setText(robj.getName());
                    ePassword.setText(robj.getPassword());
                    eemail.setText(robj.getEmail());
                    ecountry.setText(robj.getCountry());
                    //ratingBar.setRating(robj.getRating());
                    //spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(robj.getTeamaffiliation()));
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);

                    builder.setTitle("Delete " + robj.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteRec(robj.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
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
                Intent obj1 = new Intent(AdminActivity.this, StuActivity.class);
                startActivity(obj1);
                break;
            case R.id.stafid:
                Intent cityMap3 = new Intent(AdminActivity.this, StaffActivity.class);
                startActivity(cityMap3);
                break;
            case R.id.hmid:
                Intent cityMap = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(cityMap);
                break;


        }
        return false;
    }
}