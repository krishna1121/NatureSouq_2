package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;
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
import com.naturesouq.model.HomeDataProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends Activity {

    private static final String LOGIN_URL = Utility.baseURL+"login.php";
    private static final String FACEBOOK_LOGIN_URL = Utility.baseURL+"fblogin.php";
    EditText loginEmail,loginPassword;
    TextView t1, t2, t3, signup;
    Button loginbtn, regbtn;
    static Activity activity;
    CallbackManager callbackManager ;
    boolean isFacebookLogin = false ;
    ProgressBar progressBar;
    String email,password, shoppingcart_id = "";
    private String grandTotalAmount;
    private String shipingAmount;
    private String subTotalAmount;
    static HomeDataProvider homeDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);
        activity = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Sign In");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        final LoginButton button = (LoginButton) findViewById(R.id.login_button);
        button.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        button.setBackgroundResource(R.drawable.btn_fb);
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginEmail = (EditText)findViewById(R.id.mail);
        loginPassword = (EditText)findViewById(R.id.password);


        Intent intent = getIntent();
        //Do login when registration success .
        password = intent.getStringExtra("pass");
        email= intent.getStringExtra("email");
        //Do auto login .
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loginEmail.setText(email);
            loginPassword.setText(password);
                JSONObject jobj = new JSONObject();
                try {
                    shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(this);

                    jobj.put("shoppingcart_id", shoppingcart_id);
                    jobj.put("email", email);
                    jobj.put("password", password);
                    jobj.put("apikey", "naturesouq#123@apikey");

                    new LoginTask(jobj).execute(LOGIN_URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        else {
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
                                        shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(Login.this);
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

        t1 = (TextView)findViewById(R.id.tx1);
        t2 = (TextView)findViewById(R.id.tx2);
        t3 = (TextView)findViewById(R.id.tx3);
        loginbtn = (Button)findViewById(R.id.login_btn);
        signup = (TextView)findViewById(R.id.sign_up);

        Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        t1.setTypeface(face1);
        loginEmail.setTypeface(face1);
        loginPassword.setTypeface(face1);
        t2.setTypeface(face1);
        t3.setTypeface(face1);
        signup.setTypeface(face1);

        Typeface face2= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        loginbtn.setTypeface(face2);
    }

    public void doLogin(View view){
         email=loginEmail.getText().toString();
         password=loginPassword.getText().toString();

        JSONObject jobj = new JSONObject();
        email=loginEmail.getText().toString();
        password=loginPassword.getText().toString();
        if(!Utility.isNotNull(email)){
            loginEmail.setError("Email field can't be empty!");
            loginEmail.requestFocus();
        }else if(!Utility.validateEmail(email)){
            loginEmail.setError("Invalid email!");
            loginEmail.requestFocus();
        }else if(!Utility.isNotNull(password)){
            loginPassword.setError("Password field can't be empty!");
            loginPassword.requestFocus();
        }else if(!Utility.validatePassword(password)) {
            loginPassword.setError("Password length must be greater than 5!");
            loginPassword.requestFocus();
        }
        else {

            try {
                shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(this);
                jobj.put("shoppingcart_id", shoppingcart_id);
                jobj.put("email", email);
                jobj.put("password", password);
                jobj.put("apikey", "naturesouq#123@apikey");
                new LoginTask(jobj).execute(LOGIN_URL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                        if(LoginManager.getInstance() != null)
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        break;

                    case "1":
                        String Address="";
                        JSONObject jsonObject = jsonResponse.getJSONObject("data");
                        shipingAmount = jsonObject.getString("shipingAmount");
                        subTotalAmount = jsonObject.getString("subTotalAmount");
                        grandTotalAmount = jsonObject.getString("grandTotalAmount");
                        JSONObject jObj = jsonObject.getJSONObject("cdata");
                        String customer_id = jObj.getString("customer_id");
                        String firstname = jObj.getString("firstname");
                        String lastname = jObj.getString("lastname");
                      //  String email =jObj.getString("email");
                        NatureSouqPrefrences.setUserFirstName(Login.this, firstname);
                        NatureSouqPrefrences.setUserLastName(Login.this, lastname);
                       // String customer_id = "37";
                        if(!TextUtils.isEmpty(customer_id)){
                            NatureSouqPrefrences.setCustomer_id(Login.this, customer_id);
                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("address");
//
                        NatureSouqPrefrences.setLoginCartList(Login.this,jsonArray.length());

                      //  if(jsonArray.length()!=0){}
                        for (int addressCount=0; addressCount<jsonArray.length(); addressCount++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(addressCount);

                            String customer_address_id = jsonObject1.getString("customer_address_id");
                            String city = jsonObject1.getString("city");
                           // String company = jsonObject1.getString("company");
                            String country_id = jsonObject1.getString("country_id");
                            String firstname1 = jsonObject1.getString("firstname");
                            String lastname1 = jsonObject1.getString("lastname");
                            String postcode = jsonObject1.getString("postcode");
                            String region = jsonObject1.getString("region");
                           // String region_id = jsonObject1.getString("region_id");
                            String street = jsonObject1.getString("street");
                            String telephone = jsonObject1.getString("telephone");
                            Address = street+", "+city+"\n"+region+", "+country_id+", "+postcode+"\n"+telephone;
                            //String Address = telephone +"\n"+ street + ", " +city + "\n" + region +", "+ country_id + ", " +postcode;

                           // NatureSouqPrefrences.setUserFirstName(Login.this, firstname);
                           // NatureSouqPrefrences.setUserLastName(Login.this, lastname);
                            NatureSouqPrefrences.setUserContactNo(Login.this, telephone);
                            NatureSouqPrefrences.setAddressId("address_id",Login.this,customer_address_id);

                        }

                        Intent i=getIntent();
                        String source=i.getStringExtra("ComingFrom");

                        if(!isFacebookLogin){
                            //Open checkout page .
                            // save username and password in prefrences.
                            NatureSouqPrefrences.setUserName(Login.this, email);
                            NatureSouqPrefrences.setUserPass(Login.this, password);

                            if(!TextUtils.isEmpty(source)){
                                if(source.equalsIgnoreCase("MyAccount")){
                                    Login.returnInstance().finish();
                                    if(Registration.Instance()!=null)
                                    Registration.Instance().finish();
                                    MainActivity.getInstance().displayView(0,-1,"","Home");

                                }else{
                                    if(jsonArray.length()>=1){
                                        Intent home = new Intent(Login.this, CheckOut.class);
                                        home.putExtra("from","Login");
                                        home.putExtra("shipingAmount",shipingAmount);
                                        home.putExtra("subTotalAmount",subTotalAmount);
                                        home.putExtra("grandTotalAmount",grandTotalAmount);
                                        home.putExtra("Address",Address);
                                        home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivityForResult(home, 1);
                                        finish();
                                    }else{
                                        //Add new Add page
                                        Intent home = new Intent(Login.this, AddNewAddress.class);
                                        startActivityForResult(home, 0);
                                        finish();
                                    }
                                }
                            }else{
                                if(jsonArray.length()>=1){
                                    Intent home = new Intent(Login.this, CheckOut.class);
                                    home.putExtra("from","Login");
                                    home.putExtra("shipingAmount",shipingAmount);
                                    home.putExtra("subTotalAmount",subTotalAmount);
                                    home.putExtra("grandTotalAmount",grandTotalAmount);
                                    home.putExtra("Address",Address);
                                    home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivityForResult(home, 1);
                                    finish();
                                }else{
                                    //Add new Add page
                                    Intent home = new Intent(Login.this, AddNewAddress.class);
                                    startActivityForResult(home, 0);
                                    finish();
                                }
                            }

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }else{

                            NatureSouqPrefrences.setFacebookUser(Login.this, isFacebookLogin);

                            if(!TextUtils.isEmpty(source)){
                                if(source.equalsIgnoreCase("MyAccount")){
                                    Login.returnInstance().finish();
                                    if(Registration.Instance()!=null)
                                    Registration.Instance().finish();
                                    MainActivity.getInstance().displayView(0,-1,"","Home");
                                }else{
                                    if(jsonArray.length()>=1){
                                        //if user have address
                                        Intent home = new Intent(Login.this, CheckOut.class);
                                        home.putExtra("from","Login");
                                        home.putExtra("shipingAmount",shipingAmount);
                                        home.putExtra("subTotalAmount",subTotalAmount);
                                        home.putExtra("grandTotalAmount",grandTotalAmount);
                                        home.putExtra("Address",Address);
                                        home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivityForResult(home, 0);
                                        finish();
                                    }else{
                                        //if user have no add
                                        Intent home = new Intent(Login.this, AddNewAddress.class);
                                        startActivityForResult(home, 0);
                                        finish();
                                    }
                                }
                            }
                        }
                        break;

                    case "2":
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        break;

                    case "3":
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }catch(Exception e){
                //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

    }

    public void onForgotPassword(View view){
        Intent forgotIntent = new Intent(Login.this, ForgotPassword.class);
        forgotIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(forgotIntent);
    }

    public void onSignUp(View view){
        Intent i=getIntent();
        String source=i.getStringExtra("ComingFrom");
        if(!TextUtils.isEmpty(source)){
            if(source.equalsIgnoreCase("MyAccount")){

                Intent registrationIntent = new Intent(Login.this, Registration.class);
                registrationIntent.putExtra("ComingFrom","MyAccount");
                registrationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(registrationIntent);
            }
        }else {

            Intent registrationIntent = new Intent(Login.this, Registration.class);
            registrationIntent.putExtra("ComingFrom","Normal");
            registrationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(registrationIntent);
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
                setResult(15);
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

 /*
    public void showHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.naturesouq", PackageManager.GET_SIGNATURES); //Your package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == resultCode ){
            Intent intent = new Intent();
            setResult(2 ,intent);
            finish();
        }
    }

    public static Activity returnInstance(){

        return activity;
    }
}
