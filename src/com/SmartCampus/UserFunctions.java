package com.SmartCampus;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.SmartCampus.api.JSONParser;


import android.content.Context;

public class UserFunctions {
private JSONParser jsonParser;
	
	private static String loginURL = "http://dev.thesmartcampus.com/api/v1/tokens";
	//private static String registerURL = "http://dev.thesmartcampus.com/users/sign_up";
	
	private static String login_tag = "login";
	//private static String register_tag = "register";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	

	public JSONObject loginUser(String Email, String Password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", Email));
		params.add(new BasicNameValuePair("password", Password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		


		
		
		
		return json;
	}
	/*public JSONObject registerUser(String Email, String Password,String Conformation){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
	    params.add(new BasicNameValuePair("conformation",Conformation));
		params.add(new BasicNameValuePair("email", Email));
		params.add(new BasicNameValuePair("password", Password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}*/
	public boolean isUserLoggedIn(Context context){
		Database db = new Database(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Function get Login status
	 * */
	
	
}
