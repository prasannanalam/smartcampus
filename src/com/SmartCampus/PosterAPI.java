package com.SmartCampus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.format.Time;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.SmartCampus.api.JSON;
import com.SmartCampus.api.JsonMap;


public class PosterAPI extends Activity {

	 View mainView = null;
	 float oldDist = 1f;
	 PointF oldDistPoint = new PointF();
	 PointF start = new PointF();
	 PointF mid = new PointF();
	 public static String TAG = "ZOOM";

	 static final int NONE = 0;
	 static final int DRAG = 1;
	 static final int ZOOM = 2;
	 int mode = NONE;
	 public static List<String> locationlist;
		// url to make request
		//private static 
		static InputStream is = null;
		JsonMap jParser;
		JSONArray results = null;
		final Context context = this;
		public static String locationby;
		private Location location;
		private Dialog dialog;
		// JSON Node names
		public static final String TAG_HTML= "html_attributions";
		public  static final String TAG_RESULTS = "results";
		
		public  static final String TAG_FORMATTED_ADDRESS = "formatted_address";
		
		public  static final String TAG_GEOMETRY = "geometry";
		public  static final String TAG_LOCATION= "location";
		public  static final String TAG_LAT = "lat";
		public  static final String TAG_LNG = "lng";
		private boolean fError;
	// url to make request
	private static String url ;
	
	// JSON Node names
	
	private static final String TAG_ID = "id";
	
	 private static final String TAG_UID = "Uid";
	public static final String TAG_BANNER = "banner_url";
	public static final String TAG_POSTER_ICON = "poster_icon";
	public static final String TAG_POSTERIMAGE = "posterimage";
	public static final String TAG_LINE_1 = "top_line_1";
	public static final String TAG_LINE_2 = "top_line_2";
	public static final String TAG_USER_ATTENDING = "current_user_attending?";
	public static final String TAG_ADDRESS_1 = "location_address_1";
	public static final String TAG_ADDRESS_2 = "location_address_2";
	public static final String TAG_LOCATION_TITLE = "location_title";
	public static final String TAG_BUTTOM_SUBTITLE = "bottom_subtitle";
	public static final String TAG_BUTTOM_TEXT = "bottom_text";
	public static final String TAG_BUTTOM_TITLE = "bottom_title";
	// contacts JSONArray
	ZoomControls mZoomControls1;
	JSONArray contacts = null;
	String calName; 
	 String calId; 
	 NetworkInfo mWifi;
	TextView brk,brktext,fb,fbtext,rsvp,fg,ad,bss,bsstext;
	ImageView header,br,map,share,brsvp,h,cala,pick;
 ImageView da,sa,ua,cra,click_da,click_more;
	ScrollView l1,l2;
	HorizontalScrollView hs;
	DataCheck dcb;
	//LinearLayout l2;
	String btext,id,banner,postericon,Pimage, line1, line2,user_attending, address_1, address_2, location_title, btitle,bsubtitle,replace;
	static String Longi;
	static String Lat;
	final static String SYSTEM_NEWLINE  = "\n";
	final static float COMPLEXITY = 5.12f;  //Reducing this will increase efficiency but will decrease effectiveness
	final static Paint p = new Paint();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posterapi);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		Log.d("Uid",""+SmartPoster.UID);
		
		String Uid="1";
		/*String Uid=SmartPoster.uList.get(0);
		
		Log.d("uid",""+Uid);*/
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
	       mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	      if (mWifi.isConnected()) {
	          // Do whatever
	    	  Log.d("wifi","on");
	      }
	      else{
	      Log.d("wifi","off");
			Toast.makeText(getApplicationContext(),"Please turn on WIFI", Toast.LENGTH_SHORT).show();
	      }
		url = "http://dev.thesmartcampus.com/api/v1/smart_posters/"+Uid+"";
		Log.d("URL",""+url);
		Log.d("uid",""+Uid);
		try{
		// Creating JSON Parser instance
		JSON jParser = new JSON();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		Log.d("url",""+url);
		Log.d("json",""+json);
		if(json==null){
			//Toast.makeText(getApplicationContext(),"API not found", Toast.LENGTH_SHORT).show();

		}
		else{
		try {
			
			         id = json.getString(TAG_ID);//"id":1
				Log.d("id",""+id);
				 banner = json.getString(TAG_BANNER);//"banner_url":"http://cdn.smartcampus.com/1/banner.jpg"
				Log.d("banner url",""+banner);
				 postericon = json.getString(TAG_POSTER_ICON);//"poster_icon":"http://cdn.smartcampus.com/1/poster_thumb.jpg",
				Log.d("postericon",""+postericon);
				 Pimage = json.getString(TAG_POSTERIMAGE);//"posterimage":{"url":"/uploads/smart_poster/posterimage/1/SC_6_copy_copy.jpg"}
				Log.d("Pimage",""+Pimage);
			    line1 = json.getString(TAG_LINE_1);//"top_line_1":"Broken Social Scene",
				Log.d("line1 ",""+line1 );
				 line2 = json.getString(TAG_LINE_2);//"top_line_2":"Hot water music, Decemberists, Slayer",
				Log.d("line2 ",""+line2 );
				 user_attending = json.getString(TAG_USER_ATTENDING);//"current_user_attending?":"true"
				Log.d("user_attending ",""+user_attending );
				 address_1 =json.getString(TAG_ADDRESS_1);//"location_address_1":"1234 ventura blvd",
				Log.d("address_1 ",""+address_1);
				 address_2 = json.getString(TAG_ADDRESS_2);//"location_address_2":"Davis, California 95822",
				Log.d("address_2 ",""+address_2);
				 location_title = json.getString(TAG_LOCATION_TITLE);//"location_title":"Freeborn Hall",
				Log.d("location_title ",""+location_title);
				 btitle = json.getString(TAG_BUTTOM_TITLE);//"bottom_title":"Additional Information",
				Log.d("btitle ",""+btitle);
				 bsubtitle = json.getString(TAG_BUTTOM_SUBTITLE);//"bottom_subtitle":"band biography",
				Log.d("bsubtitle ",""+bsubtitle);
			 btext = json.getString(TAG_BUTTOM_TEXT);/*"<html><body>"
				          + "<p align=\"justify\">"  +  json.getString(TAG_BUTTOM_TEXT)+ "</p> "
				             + "</body></html>";//"bottom_text":"Broken Social Scene is a Canadian indie rock band, a musical 
*/				Log.d(" btext  ",""+ btext );
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		}
		}
		catch(IllegalArgumentException e1){
			
		}
		dcb=new DataCheck(this);
		dcb.open();
		Cursor items=dcb.getlistitems();
		dcb.close();
		if(items.getCount()==0){
			final Dialog dialog = new Dialog(this);

			 dialog.setContentView(R.layout.tips);
			 dialog.setTitle("Tips");
			 dialog.show();
			 TextView text=(TextView)dialog.findViewById(R.id.text);
			 ImageView image=(ImageView)dialog.findViewById(R.id.image);
			 CheckBox check=(CheckBox)dialog.findViewById(R.id.checkBox1);
			 check.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("box","check");
					if (((CheckBox) v).isChecked()) {
						dcb.open();
						dcb.insertval(1, "checkbox");
						dcb.close();
						/*Toast.makeText(PosterAPI.this,
					 	   "checkBox", Toast.LENGTH_LONG).show();
					*/
					}
					else{
						dcb.open();
						dcb.deleteTitle(1);
						dcb.close();
						/*Toast.makeText(PosterAPI.this,
	                               "UncheckBox", Toast.LENGTH_LONG).show();*/
					}
				}
			});
			 Button ok = (Button)dialog.findViewById(R.id.ok);
			 ok.setOnClickListener(new View.OnClickListener() {
			 	
			 	public void onClick(View v) {
			 		// TODO Auto-generated method stub
			 		dialog.cancel();
			 		
			 	}
			 });
		}
		/*if(items.moveToFirst())
		  {
		   do{
			
			 
			
		   }while(items.moveToNext());
		   items.close();
		}*/
		
		l1=(ScrollView)findViewById(R.id.l1);
		
		l2=(ScrollView)findViewById(R.id.l2);
		hs=(HorizontalScrollView)findViewById(R.id.hscroll);
		 mainView =(RelativeLayout)findViewById(R.id.rlayout);
		 
		 ImageView buttonZoomIn = (ImageView)findViewById(R.id.zoomIn);
		 ImageView buttonZoomOut = (ImageView)findViewById(R.id.zoomOut);
		 

		 /* buttonZoomOut.setOnClickListener(new OnClickListener() {
			   
			   public void onClick(View v) {
			    zoom(0.5f,0.5f,new PointF(0,0));    
			   }
			  });*/
			  buttonZoomOut.setOnClickListener(new OnClickListener() {
			   
			   public void onClick(View v) {
			    zoom(1f,1f,new PointF(0,0));  
			   
			   
			   }
			  });
			  buttonZoomIn.setOnClickListener(new OnClickListener() {
			   
			   
			   public void onClick(View v) {
			    zoom(1.5f,1.5f,new PointF(0,0));   
			    findViewById(R.id.l2).getParent().requestDisallowInterceptTouchEvent(false);
			   }
			  });
			  mainView.setOnTouchListener(new OnTouchListener() {
				   public boolean onTouch(View v, MotionEvent event) {
				    Log.d(TAG, "mode=DRAG");
				    switch (event.getAction() & MotionEvent.ACTION_MASK) {
				    case MotionEvent.ACTION_DOWN:
				     start.set(event.getX(), event.getY());
				     Log.d(TAG, "mode=DRAG");
				     mode = DRAG;
				     findViewById(R.id.l1).getParent().requestDisallowInterceptTouchEvent(false);
				     break;
				    case MotionEvent.ACTION_POINTER_DOWN:
				     oldDist = spacing(event);
				     oldDistPoint = spacingPoint(event);
				     Log.d(TAG, "oldDist=" + oldDist);
				     if (oldDist > 10f) {
				      midPoint(mid, event);
				      mode = ZOOM;
				      Log.d(TAG, "mode=ZOOM");
				     }
				     findViewById(R.id.l1).getParent().requestDisallowInterceptTouchEvent(false);
				     System.out.println("current time :" + System.currentTimeMillis());
				     break;// return !gestureDetector.onTouchEvent(event);
				    case MotionEvent.ACTION_UP:
				    case MotionEvent.ACTION_POINTER_UP:
				     Log.d(TAG, "mode=NONE");
				     mode = NONE;
				     findViewById(R.id.l1).getParent().requestDisallowInterceptTouchEvent(false);
				     break;
				    case MotionEvent.ACTION_MOVE:
				    	 findViewById(R.id.l1).getParent().requestDisallowInterceptTouchEvent(false);
				     if (mode == DRAG) {

				     } else if (mode == ZOOM) {
				      PointF newDist = spacingPoint(event);
				      float newD = spacing(event);
				      Log.e(TAG, "newDist=" + newDist);
				      float[] old = new float[9];
				      float[] newm = new float[9];
				      Log.e(TAG, "x=" + old[0] + ":&:" + old[2]);
				      Log.e(TAG, "y=" + old[4] + ":&:" + old[5]);
				      float scale = newD / oldDist;
				      float scalex = newDist.x / oldDistPoint.x;
				      float scaley = newDist.y / oldDistPoint.y;
				      zoom(scale, scale, start);
				     }
				     break;
				    }
				    return true;
				   }
				  });
		/* mZoomControls1 = (ZoomControls)findViewById(R.id.zoomControls);
	       mZoomControls1.show(); 
	       
	       mZoomControls1.setOnZoomInClickListener(new
	    		   ZoomControls.OnClickListener()
	    		           {


	    		                           public void onClick(View v) {
	    		                                   // TODO Auto-generated method stub
	    		                                   mZoomControls1.setIsZoomInEnabled
	    		   (true);
	    		                                   Log.d("mZoomControls in","mZoomControls");
	    		                                   mZoomControls1.setIsZoomOutEnabled
	    		   (true);
	    		                                   Log.d("mZoomControls out","mZoomControls");
	    		                           }


	    		           });


	    		            zoom controls out 
	    		           mZoomControls1.setOnZoomOutClickListener(new
	    		   ZoomControls.OnClickListener()
	    		           {


	    		                           public void onClick(View v) {
	    		                                   // TODO Auto-generated method stub
	    		                                   mZoomControls1.setIsZoomInEnabled
	    		   (true);
	    		                                   Log.d("mZoomControls innnnnnnnn","mZoomControls");
	    		                                   mZoomControls1.setIsZoomOutEnabled
	    		   (true);
	    		                                   Log.d("mZoomControls outttttt","mZoomControls");
	    		                           }


	    		           });*/

		/* Map<String, Integer> map = new HashMap<String, Integer>();
	        map.put("sph", R.drawable.spicon);*/
			  Typeface fontsub = Typeface.createFromAsset(getAssets(), "roboto/Roboto-Regular.ttf"); 
	        brk=(TextView)findViewById(R.id.broken);
	        brktext=(TextView)findViewById(R.id.broken_text);
	        fb=(TextView)findViewById(R.id.fb);
	        fbtext=(TextView)findViewById(R.id.fb_text);
	        rsvp=(TextView)findViewById(R.id.rsvp);
	        fg=(TextView)findViewById(R.id.fg);
	        ad=(TextView)findViewById(R.id.ad);
	         bss=(TextView)findViewById(R.id.bss);
	         bsstext=(TextView)findViewById(R.id.bsstext);
	        
	       //bsstext.loadData(getString(R.string.hello),"text/html","utf-8"); 
	         
	         da=(ImageView)findViewById(R.id.downarrow);
	         ua=(ImageView)findViewById(R.id.ra);
	         sa=(ImageView)findViewById(R.id.more);
	         cra=(ImageView)findViewById(R.id.cra);
	         click_da=(ImageView)findViewById(R.id.click_downarrow);
	        header=(ImageView)findViewById(R.id.icon);
	        br=(ImageView)findViewById(R.id.br);
	        br=(ImageView)findViewById(R.id.br);
	       map=(ImageView)findViewById(R.id.map);
	       /*share=(ImageView)findViewById(R.id.share);
	       cala=(ImageView)findViewById(R.id.cal);
	       brsvp=(ImageView)findViewById(R.id.brsvp);
	      h=(ImageView)findViewById(R.id.home);*/
	      pick=(ImageView)findViewById(R.id.pick);
	      click_more=(ImageView)findViewById(R.id.click_more);
	         Log.d(" btext  ",""+ btext );
	        header.setImageResource(R.drawable.spicon3);
	         br.setImageResource(R.drawable.spbroken);
	     // br.setImageResource(map.get("sph"));
	        brk.setText( line1);
	        brktext.setText( line2);
	        fb.setText(location_title);
	        fbtext.setText(address_1+","+address_2);
	        rsvp.setText("RSVP");
	        fg.setText("Friends Going:");
	        ad.setText( btitle);
	        bss.setText(line1);
	        bsstext.setText(btext);
	        bsstext.setVisibility(View.GONE);
	        bss.setVisibility(View.GONE);
	       cra.setVisibility(View.GONE);
	       click_da.setVisibility(View.GONE);
	       brktext.setVisibility(View.GONE);
	       pick.setVisibility(View.GONE);
	      fg.setVisibility(View.GONE);
	      click_more.setVisibility(View.GONE);
	    //  l2.setVisibility(View.GONE);
	    replace=address_2.replace(" ", "%20");
	   // replace=sname.replace(" ", "%20");
	        Log.d("replace",""+replace);
	        brk.setTypeface(fontsub);
	        fg.setTypeface(fontsub);
	        ad.setTypeface(fontsub);
	        bss.setTypeface(fontsub);
	        fbtext.setTypeface(fontsub);
	        rsvp.setTypeface(fontsub);
	        brktext.setTypeface(fontsub);
	        bsstext.setTypeface(fontsub);
	        
	        new myList(PosterAPI.this).execute();
	      l1.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                  
                    findViewById(R.id.l2).getParent().requestDisallowInterceptTouchEvent(false);
                    //findViewById(R.id.hscroll).getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            });
           l2.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event)
                {
                   
                                        // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
          /* hs.setOnTouchListener(new View.OnTouchListener() {

               public boolean onTouch(View v, MotionEvent event)
               {
                  
                                       // Disallow the touch request for parent scroll on touch of child view
                   v.getParent().requestDisallowInterceptTouchEvent(true);
                   return false;
               }
           });*/
map.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent	in=new Intent(getApplicationContext(),locationmap.class);    
	     startActivity(in);
	     //Toast.makeText(getApplicationContext()," go to Map", Toast.LENGTH_SHORT).show();
	}
});
/*share.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if((Loginactivity.mailList.size()>0)){
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    		sharingIntent.setType("text/plain");
    		//String shareBody = "Hot Water Music 17th Friday,Aug 2012 13:10:10, Address: US Davis 1 shields Avenue Davis,Description: Broken Social Scene is a Canadianindie rock band,a musical collective including as few as six and as many as nineteen members,formed in 1999 by kevin Drew and Brendan Canning";
    		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,line1);
    		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, btext);
    		startActivity(Intent.createChooser(sharingIntent, "Share via"));
		}
		Intent menu=new Intent(getApplicationContext(),UserMenu.class);    
	     startActivity(menu);
		else{
			Toast.makeText(getApplicationContext(),"Please Login Smart Campus", Toast.LENGTH_SHORT).show();
		}
	}
});    */
		
/*cala.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if((Loginactivity.mailList.size()>0)){
			
			
			String[] projection = new String[] { "_id", "name" };
    		 Log.d("projection",""+projection);
    		Uri calendars = Uri.parse("content://com.android.calendar/calendars");
    		     
    		Cursor managedCursor =
    		  managedQuery(calendars, projection, null, null, null);
    		if (managedCursor.moveToFirst()) {
    			 String calName; 
    			 String calId; 
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
    		event.put("title", line1);
    		
    		event.put("description", line2);
    		event.put("eventLocation", address_1+","+address_2);
    		
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
	
	}

	private ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return null;
	}
});   
*/
/*h.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if((Loginactivity.mailList.size()>0)){
    		Intent me=new Intent(getApplicationContext(),Apphome.class);    
		     startActivity(me);
		     finish();
    		}	//Toast.makeText(getApplicationContext(),"HOME", Toast.LENGTH_SHORT).show();
		     else{
	    			Toast.makeText(getApplicationContext(),"Please Login Smart Campus", Toast.LENGTH_SHORT).show();
	    		}
	    	
	}
});   */
/*brsvp.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),"RSVP", Toast.LENGTH_SHORT).show();
	}
});   
*/
da.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 click_da.setVisibility(View.VISIBLE);
	       brktext.setVisibility(View.VISIBLE);
	       da.setVisibility(View.GONE);
	}
});   
click_da.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 click_da.setVisibility(View.GONE);
	       brktext.setVisibility(View.GONE);
	       da.setVisibility(View.VISIBLE);
	}
});
ua.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		bsstext.setVisibility(View.VISIBLE);
        bss.setVisibility(View.VISIBLE);
        ua.setVisibility(View.GONE);
        l2.setVisibility(View.VISIBLE);
        findViewById(R.id.l2).getParent().requestDisallowInterceptTouchEvent(false);
       cra.setVisibility(View.VISIBLE);
	}
});   
cra.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		bsstext.setVisibility(View.GONE);
		l2.setVisibility(View.GONE);
        bss.setVisibility(View.GONE);
        ua.setVisibility(View.VISIBLE);
       cra.setVisibility(View.GONE);
	}
});   
sa.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 pick.setVisibility(View.VISIBLE);
	      fg.setVisibility(View.VISIBLE);
	      click_more.setVisibility(View.VISIBLE);
	      sa.setVisibility(View.GONE);
	}
});   
click_more.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 pick.setVisibility(View.GONE);
	      fg.setVisibility(View.GONE);
	      click_more.setVisibility(View.GONE);
	      sa.setVisibility(View.VISIBLE);
	}
});  

/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		context);

	// set title
	alertDialogBuilder.setTitle("Tips");

	// set dialog message
	alertDialogBuilder
		.setMessage("Click yes to exit!")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				
			}
		  })
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

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
	    		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, line1);
	    		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, btext);
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
	    			 String calName; 
	    			 String calId; 
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
	    		event.put("title", line1);
	    		
	    		event.put("description", btext);
	    		event.put("eventLocation", address_1+","+address_2);
	    		
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
    		Intent me1=new Intent(getApplicationContext(),Apphome.class);    
		     startActivity(me1);
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
    public class myList extends AsyncTask<Void, Void, Bundle> {
		// TODO Auto-generated method stub
		
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address="+replace+"&sensor=false";
		
		 private Context ctx;
	        ProgressDialog dlg;
	        String xml = null;

	        public myList(Context context) {
	            ctx = context;

	        }

	        @Override
	        protected void onPreExecute() {
	            // super.onPreExecute();

	            dlg = new ProgressDialog(PosterAPI.this);
	            dlg.setCanceledOnTouchOutside(false);
	            dlg.setCancelable(false);
	            dlg.setMessage("Please wait....");
	            dlg.show();

	            // setContentView(R.layout.splash);
	        }
	        protected Bundle doInBackground(Void... params) {
	            Bundle b = new Bundle();

	         
	    		
	    		Log.d("url",""+url);
	            try {
	    			// defaultHttpClient
	    			DefaultHttpClient httpClient = new DefaultHttpClient();
	    			HttpPost httpPost = new HttpPost(url);

	    			HttpResponse httpResponse = httpClient.execute(httpPost);
	    			HttpEntity httpEntity = httpResponse.getEntity();
	    			is = httpEntity.getContent();			

	    		} catch (UnsupportedEncodingException e) {
	    			e.printStackTrace();
	    		} catch (ClientProtocolException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}

	            return b;
	        }

	        @Override
	        protected void onPostExecute(Bundle b) {

	            dlg.dismiss();
	           
	            try {        
	            	
		//resultlist = new ArrayList<HashMap<String, String>>();
		// Hashmap for ListView
	
		// Creating JSON Parser instance
	 jParser = new JsonMap();
		Log.d("parser",""+jParser);
		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		Log.d("json",""+json);
		
			//for(int i = 0; i < html_attributions.length(); i++){
			//html_attributions=json.getJSONArray(TAG_HTML);
			//JSONObject h = html_attributions.getJSONObject(i);
			//}
			// Getting Array of Contacts
			results = json.getJSONArray(TAG_RESULTS);
			Log.d("parser",""+results);
			Log.d("length",""+results.length());
			if(results.length()==0){
				Log.d("length",""+results.length());
				Toast.makeText(getApplicationContext(),"Not found", Toast.LENGTH_SHORT).show();
			}
			else{
			// looping through All Contacts
			for(int i = 0; i < results.length(); i++){
				
			
			
				Log.d("length",""+results.length());
				JSONObject c = results.getJSONObject(i);
				
				// Storing each json item in variable
				
				String formatted_address = c.getString(TAG_FORMATTED_ADDRESS);
				
				// Phone number is agin JSON Object
				JSONObject geometry = c.getJSONObject(TAG_GEOMETRY);
				JSONObject loca = geometry.getJSONObject(TAG_LOCATION);
				 Lat = loca.getString(TAG_LAT);
			     Longi =loca.getString(TAG_LNG);
				Log.d("lat and lng",""+Lat+Longi);
				 				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				//map.put(TAG_ID, id);
				//map.put(TAG_NAME, name);
				//map.put(TAG_VICINITY, vicinity);
				map.put(TAG_FORMATTED_ADDRESS, formatted_address);
				
				//map.put(TAG_LAT,home);
				//map.put(TAG_LNG, office);
             /*locationlist.add(loca.getString(TAG_LAT)+":"+loca.getString(TAG_LNG));
               Log.d("locationlist",""+locationlist);*/
				// adding HashList to ArrayList
				/*resultlist.add(map);
				Log.d("list",""+resultlist);*/
			}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(PosterAPI.this, "Error in retrieving ",Toast.LENGTH_SHORT).show();
           doError(e);
   		Log.d("doerror","error");
		}
	        }
    }
    
    public void doError(Exception ex1) {
		if (!fError) {
			fError = true;
			Log.d("doerror","error");
			setErrorDialog();

			ex1.printStackTrace();
			try {
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}

		try {

		} catch (Exception ignore) {
		}
	}

	private void setErrorDialog() {
		Log.d("doerror","error");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please check your internet connection once.");
		builder.setCancelable(false);

		/* Open settings */
		builder.setNeutralButton("Open settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent1 = new Intent(
								Settings.ACTION_WIRELESS_SETTINGS);
						startActivityForResult(intent1, 42);
					}
				});
		/* Close application */
		builder.setNegativeButton("Close App",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						System.exit(0);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}   
	 /** zooming is done from here */
	 @TargetApi(11)
	public void zoom(Float scaleX,Float scaleY,PointF pivot){
	  l1.setPivotX(pivot.x);
	 l1.setPivotY(pivot.y);  
	  l1.setScaleX(scaleX);
	  l1.setScaleY(scaleY);  
	 } 
	 private float spacing(MotionEvent event) {
		  // ...
		  float x = event.getX(0) - event.getX(1);
		  float y = event.getY(0) - event.getY(1);
		  return FloatMath.sqrt(x * x + y * y);
		 }

		 private PointF spacingPoint(MotionEvent event) {
		  PointF f = new PointF();
		  f.x = event.getX(0) - event.getX(1);
		  f.y = event.getY(0) - event.getY(1);
		  return f;
		 }

		 /**
		  * the mid point of the first two fingers
		  */
		 private void midPoint(PointF point, MotionEvent event) {
		  // ...
		  float x = event.getX(0) + event.getX(1);
		  float y = event.getY(0) + event.getY(1);
		  point.set(x / 2, y / 2);
		 }
}
