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

public class Loginactivity extends Activity {
	EditText emailid,pass;
	TextView ErrorMsg;
	DataLogin data;
	 public static List<String> mailList= new ArrayList<String>();
	 public static List<String> passList= new ArrayList<String>();
	String mail,password;
	private static String KEY_CREATED_AT = "created_at";
	private static String KEY_SUCCESS = "success";
	private static String KEY_EMAIL = "Email";
	private static String KEY_UID = "uid";
	private static String KEY_PASSWORD = "Password";
	public static String loginresult = "";
	ImageView login,signup;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        data=new DataLogin(this);
        emailid=(EditText)findViewById(R.id.mailid);
        pass=(EditText)findViewById(R.id.pass);
        login=(ImageView)findViewById(R.id.login);
      
 
     /* else{
			Toast.makeText(getApplicationContext(),"successfully login", Toast.LENGTH_SHORT).show();

      }*/
        ErrorMsg = (TextView) findViewById(R.id.login_error);
        
        signup = (ImageView)findViewById(R.id.sign);
        signup.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		Intent in= new Intent(getApplicationContext(),SignUp.class);
				startActivity(in);
				finish();
				
        	}
        	});
        login.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		
        		
/*{"token":"mDr9idpB7jucqnc6ykQQ"}
        		
        		
        		
        		//login.setImageResource(R.drawable.login2);
        	    String email = emailid.getText().toString();
				String password = pass.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(email, password);
				if(Loginactivity.loginresult.equals("pass")){
				 Intent in= new Intent(getApplicationContext(),Apphome.class);
				   startActivity(in);
				   Toast.makeText(getApplicationContext(),"successfully login", Toast.LENGTH_SHORT).show();
				   finish();
				}
				else{
					emailid.setText("");
					pass.setText("");
					ErrorMsg.setText("LOGIN FAILED");
				}
				
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						ErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							Database db = new Database(getApplicationContext());
							JSONObject json_user = json.getJSONObject("Smart Campus");
							
							
						
							db.addUser(json_user.getString(KEY_EMAIL), json_user.getString(KEY_PASSWORD),json.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));						
                               Intent in= new Intent(getApplicationContext(),Apphome.class);
							   startActivity(in);
							   finish();
						}else
						{
							emailid.setText("");
							pass.setText("");
							ErrorMsg.setText("Invalid email or password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//finally{
				//login
					//Intent i=new Intent(getApplicationContext(),Apphome.class);
					//startActivity(i);
				//}
				
			}
		});*/
        		/*if((SignUp.mailList.size()>0)&& (SignUp.passList.size()>0)){
        	        
        	    
        		mail=emailid.getText().toString();
        		password=pass.getText().toString();
        		int index = SignUp.mailList.indexOf(mail);
        		int ind = SignUp.passList.indexOf(password);
        		if(index==-1||ind==-1){
        			
			Toast.makeText(getApplicationContext(),"Please enter correct values", Toast.LENGTH_SHORT).show();
			emailid.setText("");
			pass.setText("");
			ErrorMsg.setText("LOGIN FAILED");
		         }else{
			
				
					Intent in= new Intent(getApplicationContext(),Apphome.class);
					startActivity(in);
					ErrorMsg.setText("");
					Toast.makeText(getApplicationContext(),"successfully login", Toast.LENGTH_SHORT).show();
				
				
				//else if(password.getText().toString().equalsIgnoreCase(""))
				//{
					//password.setError("enter password");
				//}
				
				
					
					emailid.setText("");
					pass.setText("");
					ErrorMsg.setText("");
				
				
		}
		}
        		else{
					Toast.makeText(getApplicationContext(),"Please Create Your Account", Toast.LENGTH_SHORT).show();
					emailid.setText("");
					pass.setText("");
					ErrorMsg.setText("LOGIN FAILED");
        		}
        		ErrorMsg.setText("");*/
        		mail=emailid.getText().toString();
        		Log.d("mail",""+mail);
        		password=pass.getText().toString();
        		Log.d("password",""+password);
        		
        		if (emailid.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                {
        		if(emailid.getText().toString().equalsIgnoreCase("") ){
					//name.setError("please enter name");
					
					Toast.makeText(getApplicationContext(),"please enter valiad email",Toast.LENGTH_SHORT).show();
				}
				else if(pass.getText().toString().equalsIgnoreCase("")){
					
					//pass.setError("please enter password");
					Toast.makeText(getApplicationContext(),"please enter password",Toast.LENGTH_SHORT).show();
				}
				else{
					if(mail.length() > 0 && password.length() >0){
						data.open();
						if(data.Login(mail, password)){
							mailList.add(mail+""+password);
							//passList.add(mail+""+password);
			        		Log.d("mailList",""+mailList.size());
							Intent menu = new Intent(Loginactivity.this,
									SmartPoster.class);
					    	startActivity(menu);
					    	finish();
							
					    	Toast.makeText(Loginactivity.this,"Successfully Login", Toast.LENGTH_LONG).show();
						
						//emailid.setText("");
						pass.setText("");
						ErrorMsg.setText("");
						}
						else{
							Toast.makeText(Loginactivity.this,"Invalid Emailid/Password", Toast.LENGTH_LONG).show();
						
							//emailid.setText("");
							pass.setText("");
							ErrorMsg.setText("LOGIN FAILED");
							//name.setError("enter name");
							//pass.setError("enter password");
						}
						data.close();
					}
					else{
						Toast.makeText(Loginactivity.this,"Please Enter Values", Toast.LENGTH_LONG).show();
						ErrorMsg.setText("LOGIN FAILED");
					}
					
				}
                }else
                {
					   Toast.makeText(getApplicationContext(),"Please enter valid email", Toast.LENGTH_SHORT).show();

               	
                }
        	}
        	
       }); 

       // cancel.setOnClickListener(new OnClickListener() {
			
			//public void onClick(View v) {

				//name.setText("");
				//password.setText("");
			//}
			//});

    }
}
