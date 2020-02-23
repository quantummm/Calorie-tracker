package com.example.calorie1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Main class of the program, also it displays the login and register page
 * This page allows user to enter data and get into the home page of the program
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class MainActivity extends AppCompatActivity {
    private TextView textView,resultTextView,tvw;
    private EditText username,password,firstName,surname,email,height,
            weight,address,postcode,stepsPermile;
    private String levelOfActivity,genderTxt,dob,welcomeName,passwordHash;
    private RadioGroup gender;
    private ArrayList<String> emailArray;
    DatePicker picker;
    Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username= (EditText) findViewById(R.id.editUserName);
        password= (EditText) findViewById(R.id.editPassword);
        firstName= (EditText) findViewById(R.id.editFirstName);
        surname= (EditText) findViewById(R.id.editSurname);
        email=(EditText)findViewById(R.id.editEmail);
        height =(EditText)findViewById(R.id.editHeight);
        weight =(EditText)findViewById(R.id.editWeight);
        address = (EditText)findViewById(R.id.editAddress);
        postcode = (EditText)findViewById(R.id.editPostcode);
        stepsPermile = (EditText)findViewById(R.id.editStepspermile);
        tvw=(TextView)findViewById(R.id.textDob);
        resultTextView = (TextView) findViewById(R.id.tvResult);
        picker=(DatePicker)findViewById(R.id.EditDate);
        btnGet=(Button)findViewById(R.id.btnGetDate);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMonth = null;
                String newDayofMonth = null;
                if (picker.getMonth()+1 < 10) {
                     newMonth = "0"+(picker.getMonth()+1);
                }
                else {
                    newMonth = String.valueOf(picker.getMonth()+1);
                }
                if (picker.getDayOfMonth() < 10) {
                    newDayofMonth = "0"+(picker.getDayOfMonth());
                }
                else {
                    newDayofMonth = String.valueOf(picker.getDayOfMonth());
                }
                dob = String.valueOf(picker.getYear()+"-"+newMonth+"-"+newDayofMonth);
                tvw.setText(dob);
            }
        });
        final Spinner sLevel = (Spinner) findViewById(R.id.levelofactivity) ;
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.levelofactivity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLevel.setAdapter(adapter);
        sLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectLevel = parent.getItemAtPosition(position).toString();
                if (selectLevel != null) {
                    levelOfActivity = selectLevel;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gender = (RadioGroup) findViewById(R.id.editgender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genderTxt = checkedId == R.id.Male ? "Male" : "Female";
            }
        });
        GetEnduserEmailAsyncTask getEnduserEmailAsyncTask = new GetEnduserEmailAsyncTask();
        getEnduserEmailAsyncTask.execute();
        Button registerUser= (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PostUserAndCredentialEntityAsyncTask postUserAndCredentialEntityAsyncTask = new
                        PostUserAndCredentialEntityAsyncTask();
                String registerEmail = email.getText().toString();
                if (firstName.getText().toString().isEmpty()
                        || surname.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty() || dob.isEmpty()
                        || height.getText().toString().isEmpty()
                        || weight.getText().toString().isEmpty() || genderTxt.isEmpty()
                        || address.getText().toString().isEmpty()
                        || postcode.getText().toString().isEmpty() || levelOfActivity.isEmpty()
                        || stepsPermile.getText().toString().isEmpty()
                        || username.getText().toString().isEmpty()
                        ||hashPassword(password.getText().toString()).isEmpty()){
                    Toast.makeText(getApplicationContext(),"All field required to fill",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (emailArray.contains(registerEmail)){
                    email.setError("This email has already registered");
                    return;
                }
               else {
                    postUserAndCredentialEntityAsyncTask.execute(firstName.getText().toString(),
                            surname.getText().toString(), email.getText().toString(),
                            dob, height.getText().toString(), weight.getText().toString(),
                            genderTxt, address.getText().toString(), postcode.getText().toString(),
                            levelOfActivity, stepsPermile.getText().toString(),
                            username.getText().toString(),
                            hashPassword(password.getText().toString()));
                }
            }
        });
        Button signin = (Button) findViewById(R.id.signInButton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordByUsernameAsyncTask getPasswordByUsernameAsyncTask=new
                        GetPasswordByUsernameAsyncTask();
                GetEnduserByCredentialAsyncTask getEnduserByCredential = new
                        GetEnduserByCredentialAsyncTask();
                String strid= username.getText().toString();
                String passwd = password.getText().toString();

                if (!strid.isEmpty()) {
                    if (!passwd.isEmpty()) {
                        String id = username.getText().toString();
                        try {
                            passwordHash=getPasswordByUsernameAsyncTask.execute(id).get();
                            if (passwordHash.equalsIgnoreCase("NO INFO FOUND")) {
                                username.setError("username not correct");
                                return;
                            }
                            else if (!passwordHash.equals(hashPassword(passwd))){
                                password.setError("password not correct");
                                return;
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Successful Login",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        password.setError("Password is required!");
                        return;
                    }
                }
                else {
                    username.setError("Username is required!");
                    return;
                }

                try {
                    welcomeName=getEnduserByCredential.execute(username.getText().toString()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this,HomeScreen.class);
                intent.putExtra ("firstname", welcomeName);
                startActivity(intent);

            }
        });
    }

    /**
     *  calculate the hash password
     * @param password string type password entered by the user
     * @return hashed password in string type
     */
    public static String hashPassword(String password)
    {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * get the password from the server through login username
     */
    private class GetPasswordByUsernameAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            passwordHash=RestClient.getPassword(RestClient.findByUserName(params[0]));
            return passwordHash;
        }

        @Override
        protected void onPostExecute(String message) {
        }
    }

    /**
     * get user's firstname from login username stored in the server-side credential table
     */
    private class GetEnduserByCredentialAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            welcomeName = RestClient.getFirstname(RestClient.findByUserName(params[0]));
            return welcomeName;
        }

        @Override
        protected void onPostExecute(String message) {

        }
    }

    /**
     * post the user information to the user and credential entity in server-side
     */
    private class PostUserAndCredentialEntityAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Date date1 = null;
            Enduser enduser = new Enduser(Integer.valueOf(RestClient.countEnduser()) + 1);
            enduser.setName(params[0]);
            enduser.setSurname(params[1]);
            enduser.setEmail(params[2]);
            try {
                date1 = new SimpleDateFormat("yy-MM-dd").parse(params[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            enduser.setDob(date1);
            enduser.setHeight(Integer.valueOf(params[4]));
            enduser.setWeight(Integer.valueOf(params[5]));
            enduser.setGender(params[6]);
            enduser.setAddress(params[7]);
            enduser.setPostcode(params[8]);
            enduser.setLevelofactivity(Integer.valueOf(params[9]));
            enduser.setStepspermile(Integer.valueOf(params[10]));
            RestClient.createNewEnduser(enduser);

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            Date signupDate = null;
            try {
                signupDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(currentDateTimeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Credential credential = new Credential();
            credential.setUsername(params[11]);
            credential.setPasswordhash(params[12]);
            credential.setSignupdate(signupDate);
            credential.setUserid(enduser);
            RestClient.createNewCredential(credential);
            return "User was added";
        }

        @Override
        protected void onPostExecute(String message) {
            TextView resultTextView = (TextView) findViewById(R.id.tvResult);
            resultTextView.setText(message);
        }
    }

    /**
     * get all the user's email from the server
     */
    private class GetEnduserEmailAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            emailArray = RestClient.getAllEmail(RestClient.findAllUser());
            return RestClient.findAllUser();
        }

        @Override
        protected void onPostExecute(String message) {
            emailArray = RestClient.getAllEmail(message);
        }
    }
}

