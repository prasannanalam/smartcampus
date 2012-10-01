package com.SmartCampus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Poster extends Activity{
	public static String url="http://dev.thesmartcampus.com/api/v1/smart_posters/1";
	public static final String TAG_RESULTS = "results";
	public static final String TAG_ID = "id";
	public static final String TAG_BANNER = "banner_url";
	public static final String TAG_POSTER_ICON = "poster_icon";
	public static final String TAG_POSTERIMAGE = "posterimage";
	public static final String TAG_LINE_1 = "top_line_1";
	public static final String TAG_LINE_2 = "top_line_2";
	public static final String TAG_USER_ATTENDING = "current_user_attending";
	public static final String TAG_ADDRESS_1 = "location_address_1";
	public static final String TAG_ADDRESS_2 = "location_address_2";
	public static final String TAG_LOCATION_TITLE = "location_title";
	public static final String TAG_BUTTOM_SUBTITLE = "bottom_subtitle";
	public static final String TAG_BUTTOM_TEXT = "bottom_text";
	public static final String TAG_BUTTOM_TITLE = "bottom_title";


	String calName; 
	 String calId; 
	TextView brk,brktext,fb,fbtext,rsvp,fg,ad,bss,bsstext;
	ImageView header,br;
	ProgressDialog dialog;
	// contacts JSONArray
	JSONObject result=null;	     
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.poster);
	        
	        
			
			
	        
	     Map<String, Integer> map = new HashMap<String, Integer>();
	        map.put("sph", R.drawable.spicon);
	        brk=(TextView)findViewById(R.id.broken);
	        brktext=(TextView)findViewById(R.id.broken_text);
	        fb=(TextView)findViewById(R.id.fb);
	        fbtext=(TextView)findViewById(R.id.fb_text);
	        rsvp=(TextView)findViewById(R.id.rsvp);
	        fg=(TextView)findViewById(R.id.fg);
	        ad=(TextView)findViewById(R.id.ad);
	         bss=(TextView)findViewById(R.id.bss);
	         bsstext=(TextView)findViewById(R.id.bsstext);
	        header=(ImageView)findViewById(R.id.icon);
	        br=(ImageView)findViewById(R.id.br);
	     
	        header.setImageResource(R.drawable.spicon);
	       br.setImageResource(R.drawable.spbroken);
	     // br.setImageResource(map.get("sph"));
	        brk.setText("Broken Social Scene");
	        brktext.setText("Hot Water Music,Decemberists Friday,Aug...");
	        fb.setText("Freeborn Hall");
	        fbtext.setText("US Davis 1 shields Avenue Davis,CA 95616... ");
	        rsvp.setText("RSVP");
	        fg.setText("Friends Going:");
	        ad.setText("Additional Information");
	        bss.setText("Broken Social Scene:");
	      // bsstext.setText("Broken Social Scene is a Canadianindie rock band,musical collective including as few as six and as many asninteen members,formed in 1999 by Kevin Drew and Brendan Canning.");
	       
	     // Creating JSON Parser instance
	       /* JsonPoster jParser = new JsonPoster();

			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			Log.d("url",""+url);
			Log.d("json",""+json);
			try {
				
					
					String id = json.getString(TAG_ID);//"id":1
					Log.d("id",""+id);
					String banner = json.getString(TAG_BANNER);//"banner_url":"http://cdn.smartcampus.com/1/banner.jpg"
					Log.d("banner url",""+banner);
					String postericon = json.getString(TAG_POSTER_ICON);//"poster_icon":"http://cdn.smartcampus.com/1/poster_thumb.jpg",
					Log.d("postericon",""+postericon);
					String Pimage = json.getString(TAG_POSTERIMAGE);//"posterimage":{"url":"/uploads/smart_poster/posterimage/1/SC_6_copy_copy.jpg"}
					Log.d("Pimage",""+Pimage);
				    String line1 = json.getString(TAG_LINE_1);//"top_line_1":"Broken Social Scene",
					Log.d("line1 ",""+line1 );
					String line2 = json.getString(TAG_LINE_2);//"top_line_2":"Hot water music, Decemberists, Slayer",
					Log.d("line2 ",""+line2 );
					String user_attending = json.getString(TAG_USER_ATTENDING);//"current_user_attending?":"true"
					Log.d("user_attending ",""+user_attending );
					String address_1 =json.getString(TAG_ADDRESS_1);//"location_address_1":"1234 ventura blvd",
					Log.d("address_1 ",""+address_1);
					String address_2 = json.getString(TAG_ADDRESS_2);//"location_address_2":"Davis, California 95822",
					Log.d("address_2 ",""+address_2);
					String location_title = json.getString(TAG_LOCATION_TITLE);//"location_title":"Freeborn Hall",
					Log.d("location_title ",""+location_title);
					String btitle = json.getString(TAG_BUTTOM_TITLE);//"bottom_title":"Additional Information",
					Log.d("btitle ",""+btitle);
					String bsubtitle = json.getString(TAG_BUTTOM_SUBTITLE);//"bottom_subtitle":"band biography",
					Log.d("bsubtitle ",""+bsubtitle);
					String btext = json.getString(TAG_BUTTOM_TEXT);//"bottom_text":"Broken Social Scene is a Canadian indie rock band, a musical 
					Log.d(" btext  ",""+ btext );
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
	      */
	      }
	public boolean onCreateOptionsMenu(Menu menu) {
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.options_menu, menu);
	    	return true;
	    }
	    
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.sh:
	    		if((Loginactivity.mailList.size()>0)){
	    			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		    		sharingIntent.setType("text/plain");
		    		String shareBody = "Hot Water Music 17th Friday,Aug 2012 13:10:10, Address: US Davis 1 shields Avenue Davis,Description: Broken Social Scene is a Canadianindie rock band,a musical collective including as few as six and as many as nineteen members,formed in 1999 by kevin Drew and Brendan Canning";
		    		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Broken Social Scene");
		    		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		    		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	    		}
	    		/*Intent menu=new Intent(getApplicationContext(),UserMenu.class);    
			     startActivity(menu);*/
	    		else{
	    			Toast.makeText(getApplicationContext(),"Please Login Smart Campus", Toast.LENGTH_SHORT).show();
	    		}
	    	
	    		return true;
	    	   case R.id.cl:
	    		if((Loginactivity.mailList.size()>0)){
	    			
	    			
	    			String[] projection = new String[] { "_id", "name" };
		    		 Log.d("projection",""+projection);
		    		Uri calendars = Uri.parse("content://com.android.calendar/calendars");
		    		     
		    		Cursor managedCursor =
		    		  managedQuery(calendars, projection, null, null, null);
		    		if (managedCursor.moveToFirst()) {
		    			/* String calName; 
		    			 String calId;*/ 
		    			 int nameColumn = managedCursor.getColumnIndex("name");
		    			 Log.d("nameColumn",""+projection);
		    			 int idColumn = managedCursor.getColumnIndex("_id");
		    			 do {
		    			    calName = managedCursor.getString(nameColumn);
		    			    Log.d("name",""+calName);
		    			    calId = managedCursor.getString(idColumn);
		    			    Log.d("calId",""+calId);
		    			 } while (managedCursor.moveToNext());
		    			}
		    		try{
		    		ContentValues event = new ContentValues();
		    		event.put("calendar_id", 1);
		    		 Log.d("calId",""+calId);
		    		event.put("title", "Broken Social Scene");
		    		
		    		event.put("description", "Hot Water Music");
		    		event.put("eventLocation", "US Davis 1 shields Avenue Davis");
		    		
		    		long startMillis = 0;
		    	      long endMillis = 0;
		    	      Calendar beginTime = Calendar.getInstance();
		    	      beginTime.set(2012, 7, 17, 13, 10, 10);
		    	      startMillis = beginTime.getTimeInMillis();
		    	      Calendar endTime = Calendar.getInstance();
		    	      endTime.set(2012, 7, 17, 16, 10, 10);
		    	      endMillis = endTime.getTimeInMillis();
		    		event.put("dtstart", startMillis);
		    		 Log.d("date",""+startMillis);
		    		event.put("dtend", endMillis);
		    		 Log.d("date",""+endMillis);
		    		event.put("allDay", 0); 
		    		event.put("eventStatus", 1);
		    		//event.put("visibility", 0);
		    		//event.put("transparency", 0);
		    		//event.put("hasAlarm", 1);
		    		event.put("eventTimezone", Time.getCurrentTimezone());

		    		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		    		 Log.d("eventsUri",""+eventsUri);
		    		Uri url = this.getContentResolver().insert(eventsUri, event);
					Toast.makeText(getApplicationContext(),"Added to calendar successfully", Toast.LENGTH_SHORT).show();

		    		}catch ( IllegalArgumentException e){
		    	        System.out.println(e.getMessage());
		    	    }
		    	
		    		
	    		}
	    		
	    		else{
	    			Toast.makeText(getApplicationContext(),"Please Login Smart Campus", Toast.LENGTH_SHORT).show();
	    		}
	    	
	    		return true;
	    	case R.id.r:
	    		/*Intent me=new Intent(getApplicationContext(),UserMenu.class);    
			     startActivity(me);*/
					Toast.makeText(getApplicationContext(),"RSVP", Toast.LENGTH_SHORT).show();

	    		return true;
	    	case R.id.home:
	    		if((Loginactivity.mailList.size()>0)){
	    		Intent me=new Intent(getApplicationContext(),Apphome.class);    
			     startActivity(me);
			     finish();
	    		}	//Toast.makeText(getApplicationContext(),"HOME", Toast.LENGTH_SHORT).show();
			     else{
		    			Toast.makeText(getApplicationContext(),"Please Login Smart Campus", Toast.LENGTH_SHORT).show();
		    		}
		    	
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
	   /* @Override
	    public void onBackPressed() {
	    	Log.d("back","back");
	           // Do as you please
	    	 Intent in=new Intent(getApplicationContext(),Apphome.class);    
	   	     startActivity(in); 
	    } */
	   
}
