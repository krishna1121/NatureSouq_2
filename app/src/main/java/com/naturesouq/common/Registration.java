package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.naturesouq.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;

/**
 * Created by SI_Android_Binit on 12/10/2015.
 */
public class Registration extends Activity {
    private static final String REGISTRATION_URL = Utility.baseURL+"registration.php";
   // private static final String LOGIN_URL = "http://192.185.24.213/~naturesouq/login.php";
    private static final String FACEBOOK_LOGIN_URL = Utility.baseURL+"fblogin.php";
    Button registerbtn;
    CallbackManager callbackManager ;
    boolean isFacebookLogin = false ;
    ProgressBar progressBar;
    String email,password, UserName, UserEmail, UserPass, UserConfirmPass, shoppingcart_id="";
    EditText name, mail, pass, confirm_pass ;
    private String grandTotalAmount;
    private String shipingAmount;
    private String subTotalAmount;
    int counter;
    EditText firstName,lastName;
    static  Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.registration);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Registration");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firstName = (EditText) findViewById(R.id.fname);
        lastName = (EditText) findViewById(R.id.lname);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.password);
        confirm_pass = (EditText) findViewById(R.id.confirmPassword);
        registerbtn = (Button) findViewById(R.id.register_btn);
        final LoginButton button = (LoginButton) findViewById(R.id.login_button);
        button.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        button.setBackgroundResource(R.drawable.btn_fb);
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegistration(v);
            }
        });

        //Do normal login
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    isFacebookLogin = true;
                    //Store Facebook data to webservice .
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    // Application code
                                    try {
                                        String picture = object.getString("picture");
                                        JSONObject jobj = new JSONObject(picture);
                                        JSONObject dataObj = jobj.getJSONObject("data");

                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        String url = dataObj.getString("url").replace("\\", "");

                                        JSONObject fbJobj = new JSONObject();
                                        shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(Registration.this);
                                        fbJobj.put("shoppingcart_id", shoppingcart_id);
                                        fbJobj.put("name", name);
                                        fbJobj.put("email", email);
                                        fbJobj.put("status", "1");
                                        fbJobj.put("apikey", "naturesouq#123@apikey");

                                        new LoginTask(fbJobj).execute(FACEBOOK_LOGIN_URL);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.v("Login", response.toString());
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday,picture");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    // App code
                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Toast.makeText(getApplicationContext(), "Error-Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

    public void doRegistration(View view){

        String UserFirstName = firstName.getText().toString();
        String UserLastName = lastName.getText().toString();
        UserEmail = mail.getText().toString();
        UserPass = pass.getText().toString();
        UserConfirmPass = confirm_pass.getText().toString();

        if(!Utility.isNotNull(UserFirstName)){
            firstName.setError("First Name field can't be empty!");
            firstName.requestFocus();
        }else if (!Utility.isNotNull(UserLastName)){
            lastName.setError("Last Name field can't be empty!");
            lastName.requestFocus();
        }else if(!Utility.isNotNull(UserEmail)){
            mail.setError("Email field can't be empty!");
            mail.requestFocus();
        }else if(!Utility.validateEmail(UserEmail)){
            mail.setError("Invalid email!");
            mail.requestFocus();
        }else if(!Utility.isNotNull(UserPass)){
            pass.setError("Password field can't be empty!");
            pass.requestFocus();
        }else if(!Utility.validatePassword(UserPass)) {
            pass.setError("Password length must be greater than 5!");
            pass.requestFocus();
        }else if(!Utility.isNotNull(UserConfirmPass)){
            confirm_pass.setError("Confirm-Password Field can't be empty!");
            confirm_pass.requestFocus();
        }else if(!Utility.validatePassword(UserConfirmPass)) {
            confirm_pass.setError("Password length must be greater than 5!");
            confirm_pass.requestFocus();
        }else if(!UserPass.equals(UserConfirmPass)) {
            confirm_pass.setError("Password field didn't match!");
            confirm_pass.requestFocus();
        }else {
            boolean networkStatus = new DialogBox(activity).checkInternetConnection();
            JSONObject jobj = new JSONObject();
            if (networkStatus) {
                    try {
                        UserName = UserFirstName+ " "+UserLastName;
                        jobj.put("email", UserEmail);
                        jobj.put("name", UserName);
                        jobj.put("password", UserPass);
                        jobj.put("apikey", "naturesouq#123@apikey");
                        new RegistrationTask(jobj).execute(REGISTRATION_URL);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
//                if (isFacebookLogin){
//                    new LoginTask(jobj).execute(FACEBOOK_LOGIN_URL);
//                }
            }
        }
    }

    //Do sign in .
    public void onSignIn(View v) {
        try {
            Intent signIntent = new Intent(this, Login.class);
            signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

        public class LoginTask extends AsyncTask<String, Void, String> {
            JSONObject obj;

            //Show registration progress.
            public LoginTask(JSONObject jobj) {
                obj = jobj;
            }

            @Override
            protected String doInBackground(String... urls) {
                return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String status = jsonResponse.getString("status");
                    String message = jsonResponse.getString("message");

                    switch (status){
                        case "0":
                            Toast.makeText(getApplicationContext(), "Server error !", Toast.LENGTH_SHORT).show();
                            break;

                        case "1":
                            JSONObject jsonObject = jsonResponse.getJSONObject("data");
                            shipingAmount = jsonObject.getString("shipingAmount");
                            subTotalAmount = jsonObject.getString("subTotalAmount");
                            grandTotalAmount = jsonObject.getString("grandTotalAmount");
                            JSONObject jObj = jsonObject.getJSONObject("cdata");
                            String customer_id = jObj.getString("customer_id");

                            // String customer_id = "37";
                            if(!TextUtils.isEmpty(customer_id)){
                                NatureSouqPrefrences.setCustomer_id(Registration.this, customer_id);
                            }

                            JSONArray jsonArray = jsonObject.getJSONArray("address");

                            //  if(jsonArray.length()!=0){}
                            for (int addressCount=0; addressCount<jsonArray.length(); addressCount++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(addressCount);

                                String customer_address_id = jsonObject1.getString("customer_address_id");
                                String city = jsonObject1.getString("city");
                                // String company = jsonObject1.getString("company");
                                String country_id = jsonObject1.getString("country_id");
                                String firstname = jsonObject1.getString("firstname");
                                String lastname = jsonObject1.getString("lastname");
                                String postcode = jsonObject1.getString("postcode");
                                String region = jsonObject1.getString("region");
                                // String region_id = jsonObject1.getString("region_id");
                                String street = jsonObject1.getString("street");
                                String telephone = jsonObject1.getString("telephone");

                                String Address_street = telephone +"\n"+ street + ", " +city + "\n" + region +", "+ country_id + ", " +postcode;
                               // NatureSouqPrefrences.setAddress(Registration.this, Address_street);

                                NatureSouqPrefrences.setUserFirstName(Registration.this, firstname);
                                NatureSouqPrefrences.setUserLastName(Registration.this, lastname);
                                NatureSouqPrefrences.setUserContactNo(Registration.this, telephone);
                            }
                            if(!isFacebookLogin){
                                //Open checkout page .
                                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                // save username and password in prefrences.
                                NatureSouqPrefrences.setUserName(Registration.this,email);
                                NatureSouqPrefrences.setUserPass(Registration.this,password);
                                Intent home = new Intent(Registration.this, CheckOut.class);
                                startActivityForResult(home, 1);
                                finish();
                                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }else{
                                //Open trails list .
                                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(Registration.this, CheckOut.class);
                                startActivityForResult(home, 0);
                                finish();
                                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "2":
                            Toast.makeText(getApplicationContext(), "Server error !", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Invalid response", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                super.onPostExecute(result);
            }
        }

    public class RegistrationTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        //Show registration progress.


        public RegistrationTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "Exception @ RegistrationActivity.onPreExecute ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                //Now do auto login .

                switch (status) {
                    case "0":
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                       // String data = jsonResponse.getString("data");
                        //Start Login activity with current credentials .
                        Intent loginIntent = new Intent(Registration.this, Login.class);
                        String email = obj.getString("email");
                        String pass = obj.getString("password");
                        loginIntent.putExtra("loginwithsignup", "true");
                        loginIntent.putExtra("email", email);
                        loginIntent.putExtra("pass", pass);
                        Intent i=getIntent();
                        String source=i.getStringExtra("ComingFrom");
                        loginIntent.putExtra("ComingFrom",source); //Source=MyAccount or Normal
                        startActivity(loginIntent);
                        Login.returnInstance().finish();
                        finish();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        break;
                }
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                e.printStackTrace();
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
    public static Activity Instance(){

        return activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode ,resultCode , data);

    }
}
