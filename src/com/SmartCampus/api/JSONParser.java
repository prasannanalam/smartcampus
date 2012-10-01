package com.SmartCampus.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.SmartCampus.Apphome;
import com.SmartCampus.Loginactivity;


import android.util.Log;

public class JSONParser {
static InputStream is = null;
static JSONObject jObj = null;
static String json = "";

// constructor
public JSONParser() {

}

public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

	// Making HTTP request
	try {
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpPost request = new HttpPost(url);
		request.setEntity(new UrlEncodedFormEntity(params));
		 request.setHeader("Accept", "application/json");
		 request.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded"));
		HttpResponse httpResponse = httpClient.execute(request);
		HttpEntity httpEntity = httpResponse.getEntity();
		is = httpEntity.getContent();

	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		json = sb.toString();
		Log.e("JSON.....!!", json);
		
		 JSONObject json_data= new JSONObject(json);
String status = json_data.getString("status");
		 Log.d("log_tag.....!"," status: "+json_data.getString("status"));
		// Log.d("log_tag.....!"," message: "+json_data.getString("message"));
		//JSONObject jsonObject = jsonArray.getJSONObject(i);

	if (status.equals("pass")){

		Loginactivity.loginresult= "pass";
	}
	else{
		Loginactivity.loginresult= "fail";
	}
		
	String token=json_data.getString("token");
	Log.d("log_tag......!","token:"+json_data.getString("token"));
	if(token.equals("z1F4TahSSFcu7Sc1jtsh")){
		Apphome.logintoken="z1F4TahSSFcu7Sc1jtsh";
	}
	} catch (Exception e) {
		Log.e("Buffer Error", "Error converting result " + e.toString());
	}

	// try parse the string to a JSON object
	try {
		jObj = new JSONObject(json);			
	} catch (JSONException e) {
		Log.e("JSON Parser", "Error parsing data " + e.toString());
	}

	// return JSON String
	return jObj;

}
}

