package com.SmartCampus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Activity{
	EditText email,password,cnf;
	ImageView singup;
	TextView ErrorMsg;
	String mail,pass,conf;
	DataLogin data;
	 public static List<String> mailList= new ArrayList<String>();
	 public static List<String> passList= new ArrayList<String>();
	  private static String KEY_SUCCESS = "success";
	  private static String KEY_PASSWORD = "password";
	  private static String KEY_EMAIL = "email";
	  private static String KEY_CONFORMATION="conformation";
	  private static String KEY_CREATED_AT = "created_at";
	 /** Called when the activity is first created. */
    @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.signup);
	        data=new DataLogin(this);
	        email=(EditText)findViewById(R.id.mailid);
	        password=(EditText)findViewById(R.id.pass);
	      cnf=(EditText)findViewById(R.id.cnf);
	      ErrorMsg = (TextView) findViewById(R.id.sign_error);
	      singup=(ImageView)findViewById(R.id.signup);
			/*singup.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View view) {
					String Conformation=conformation.getText().toString();
					String Email = email.getText().toString();
					String Password = password.getText().toString();
					UserFunctions userFunction = new UserFunctions();
					JSONObject json = userFunction.registerUser(Email, Password,Conformation);
					
					// check for login response
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							registermass.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1){
								// user successfully registred
								// Store user details in SQLite Database
								Database db = new Database(getApplicationContext());
								JSONObject json_user = json.getJSONObject("user");
								
								// Clear all previous data in database
								userFunction.logoutUser(getApplicationContext());
								db.addUser(json_user.getString(KEY_EMAIL),json_user.getString(KEY_CONFORMATION), json_user.getString(KEY_PASSWORD),json_user.getString(KEY_CREATED_AT));						
								// Launch Dashboard Screen
								
								finish();
							}else{
								// Error in registration
								registermass.setText("Error occured in registration");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}); */
	      singup.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View view) {
					mail=email.getText().toString();
					Log.d("mail",""+mail);
					pass=password.getText().toString();
					Log.d("pass",""+pass);
					conf=cnf.getText().toString();
					 if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                     {
					if(email.getText().toString().equalsIgnoreCase(""))
					{
						   Toast.makeText(getApplicationContext(),"Enter email address", Toast.LENGTH_SHORT).show();

					}
					else if(password.getText().toString().equalsIgnoreCase("")){
						   Toast.makeText(getApplicationContext(),"Enter password", Toast.LENGTH_SHORT).show();

					}
                     else if(cnf.getText().toString().equalsIgnoreCase("")){
      				   Toast.makeText(getApplicationContext(),"Enter Confirm password", Toast.LENGTH_SHORT).show();

					}
                     else{
                    	
                    		 if(pass.equals(conf)){
             					data.open();
             					data.insertval(1, mail, pass);
             					data.close();
             					/*mailList.add(mail);
             					Log.d("mailList",""+mailList);
             					passList.add(pass);
             					Log.d("passList",""+passList);*/
             					   Toast.makeText(getApplicationContext(),"Registered successfully ", Toast.LENGTH_SHORT).show();
                                   email.setText("");
                                  password.setText("");
                                  cnf.setText("");
                                  ErrorMsg.setText("");
                                  Intent in= new Intent(getApplicationContext(),Loginactivity.class);
              					startActivity(in);
              					finish();
             				}
             				else{
             					   Toast.makeText(getApplicationContext(),"Please check your confirm password", Toast.LENGTH_SHORT).show();
             					   email.setText("");
             	                     password.setText("");
             	                     cnf.setText("");
             	                     ErrorMsg.setText("SIGNUP FAILED");
             				}
                        
				
                     }
                     }
                     else
                     {
   					   Toast.makeText(getApplicationContext(),"Please enter valid email", Toast.LENGTH_SHORT).show();

                    	
                     }
				}
				
				});

}
}