package com.naturesouq.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;

import org.json.JSONObject;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */
public class ChangePassword extends Activity {
    private static final String CHANGE_PASSWORD_URL = Utility.baseURL + "changepassword.php";
    EditText oldPassword, newPassword, confirmPassword;
    Button submit;
    String customer_id = "", email, previousPassword;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Change Password");
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        submit = (Button) findViewById(R.id.submit_btn);

        Typeface face1 = Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        oldPassword.setTypeface(face1);
        newPassword.setTypeface(face1);
        confirmPassword.setTypeface(face1);
        submit.setTypeface(face2);
    }

    public void OnChangePassword(View view) {
        boolean oldPassValidationFlag = false;
        boolean confirmPassAndNewPassValidationFlag  = false;

        previousPassword = NatureSouqPrefrences.getUserPass(this);
        customer_id = NatureSouqPrefrences.getCustomer_id(this);
        email = NatureSouqPrefrences.getUserName(this);

        String oldPasswordStr = oldPassword.getText().toString();
        String newPass = newPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (oldPasswordStr.length() > 0) {
            if (!oldPasswordStr.equals(previousPassword)) {
                oldPassword.setError("Incorrect old password !");
            }else{
                oldPassValidationFlag = true ;
            }
        } else {
            oldPassword.setError("Please povide old password !");
        }

        if (newPass.length() > 0) {
            if (!(newPass.length() > 5)) {
                newPassword.setError("Password length must be greater than five !");
            }
        } else {
            newPassword.setError("Please povide new password !");
        }

        if (confirmPass.length() > 0) {
            if (!(newPass.length() > 5)) {
                confirmPassword.setError("Password length must be greater than five !");
            }
        } else {
            confirmPassword.setError("Please povide confirm password !");
        }

        if(! ((confirmPass.length() > 0 && newPass.length() > 0) && confirmPass.equals(newPass) ) ){
            confirmPassword.setError("New password and confirm passoword are not equal !");
        }else{
            confirmPassAndNewPassValidationFlag = true ;
        }

        if(confirmPassAndNewPassValidationFlag && oldPassValidationFlag){
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("customer_id", customer_id);
                jobj.put("email", email);
                jobj.put("oldpassword", oldPasswordStr);
                jobj.put("newpassword", newPass);
                jobj.put("apikey", "naturesouq#123@apikey");
                new ChangePasswordTask(jobj).execute(CHANGE_PASSWORD_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ChangePasswordTask extends AsyncTask<String, Void, String> {
        JSONObject obj;

        public ChangePasswordTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                progressbar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if (status.equals("1")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    //Make changes in preferences .
                    //finish();
                } else if (status.equals("2")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else if (status.equals("0")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                NatureSouqPrefrences.setUserPass(getApplicationContext(), newPassword.getText().toString());
                oldPassword.setText("");
                newPassword.setText("");
                confirmPassword.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), " Communication with server failed !", Toast.LENGTH_LONG).show();
            }
            progressbar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

    }


    public void onSubmit(View view) {
        //do some things
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
