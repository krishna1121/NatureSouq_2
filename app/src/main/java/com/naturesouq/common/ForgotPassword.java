package com.naturesouq.common;

import android.app.Activity;
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
 * Created by SI_Android_Binit on 12/25/2015.
 */
public class ForgotPassword extends Activity {
    private static final String FORGOT_PASSWORD_URL=Utility.baseURL+"forgetPassword.php";
    EditText userEmail;
    Button submit;
    TextView desc;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Forgot Password");
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        userEmail = (EditText)findViewById(R.id.forgot_mail);
        submit = (Button)findViewById(R.id.submit_btn);
        desc = (TextView)findViewById(R.id.forgotPasswordDesc);

        Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        Typeface face2= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        userEmail.setTypeface(face1);
        desc.setTypeface(face1);
        submit.setTypeface(face2);
    }

    public void onSubmit(View view){
        String email=userEmail.getText().toString();
        if(!Utility.isNotNull(email)){

            userEmail.setError("Email field Can't be empty!");
            userEmail.requestFocus();
        }else if(!Utility.validateEmail(email)){

            userEmail.setError("Invalid email!");
            userEmail.requestFocus();
        } else{

            try{
                JSONObject jobj = new JSONObject();
                jobj.put("email", email);
                jobj.put("apikey","naturesouq#123@apikey");
                new ForgotPasswordTask(jobj).execute(FORGOT_PASSWORD_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ForgotPasswordTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        public ForgotPasswordTask(JSONObject jobj) {
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
               // Toast.makeText(getApplicationContext(), "Exception @ ForgotPassword.onPreExecute ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if(status.equals("1")) {
                    String data = jsonResponse.getString("data");
                    userEmail.setText("");
                    Toast.makeText(getApplicationContext(), data,Toast.LENGTH_LONG).show();
                   // finish();
                }
                if(status.equals("2")){
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
                if(status.equals("0")){
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.INVISIBLE);

            } catch (Exception e) {
                progressbar.setVisibility(View.INVISIBLE);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check Network Connection !", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
