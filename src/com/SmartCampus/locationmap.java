package com.SmartCampus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class locationmap extends MapActivity{
    private MapController mapControll;
    private GeoPoint geoPoint=null;
    private MapView mapview;
    private boolean fError;
    private Location location;
	private Dialog dialog;
    private MyItemizedOverlay userPicOverlay;
    private MyItemizedOverlay nearPicOverlay;
    private Drawable userPic,atmPic;
    private OverlayItem nearatms[]= new OverlayItem[50];;
    public static Context context;
    //double latitude=40.79250;
    //double longitude=-73.95190;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        context = getApplicationContext();
        setContentView(R.layout.mapview);
        latitude=Double.parseDouble(PosterAPI.Lat);
        Log.d("latitude",""+latitude);
        longitude=Double.parseDouble(PosterAPI.Longi);
        Log.d("longitude",""+longitude);
        /*LocationManager locManager;
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000L,500.0f, locationListener);
         location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
       // Log.d("location",""+location);
        Runnable showWaitDialog = new Runnable() {

			@Override
			public void run() {
				while (location == null) {
					// Wait for first GPS Fix (do nothing until loc != null)
				}
				// After receiving first GPS Fix dismiss the Progress Dialog
				dialog.dismiss();
				// find.setEnabled(true);
			}
		};

		// Create a Dialog to let the User know that we're waiting for a GPS Fix
		dialog = ProgressDialog.show(PosterAPI.this, "Please wait...",
				"Make sure your mobile is open to sky,Retrieving GPS data ...",
				true);
		dialog.setCancelable(true);
		Thread t = new Thread(showWaitDialog);
		t.start();
        
        if(location != null)                                
        {
            double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d("lat&lng",""+latitude+longitude);
       TraveltrotActivity.lat = latitude;
       TraveltrotActivity.longitude = longitude;	
        //geoPoint = new GeoPoint((int) latitude * 1000000, (int) longitude * 1000000);
        }  
        else{
        	 Log.d("location",""+location);
        	dialog.dismiss();
        }
*/
        showMap();
    }
    /* private void updateWithNewLocation(Location location) {
    // TextView myLocationText = (TextView)findViewById(R.id.nearme);
     String latLongString = "";
     if (location != null) {
         double lat = location.getLatitude();
         double lng = location.getLongitude();
         latLongString = "Lat:" + lat + "\nLong:" + lng;

         Log.d("lat&lngstring",""+latLongString);

     } else {
         latLongString = "No location found";
         Log.d("lat&lngstring",""+latLongString);
     }
     
     //Toast.makeText(getApplicationContext(),latLongString, Toast.LENGTH_SHORT).show();
      ///myLocationText.setText("Your Current Position is:\n" +
             //latLongString);
 }

 private final LocationListener locationListener = new LocationListener() {
     public void onLocationChanged(Location location) {
         updateWithNewLocation(location);
     }

     public void onProviderDisabled(String provider) {
         updateWithNewLocation(null);
     }

     public void onProviderEnabled(String provider) {
     }

     public void onStatusChanged(String provider, int status, Bundle extras) {
     }
 };*/
    public void showMap() {
        // TODO Auto-generated method stub

        try {
            geoPoint = new GeoPoint((int)(latitude * 1E6),(int)(longitude * 1E6));          
            mapview = (MapView)findViewById(R.id.mapView);
            mapControll= mapview.getController();
            mapview.setBuiltInZoomControls(true);
            mapview.setStreetView(true);
            mapControll.setZoom(16);
            mapControll.animateTo(geoPoint);
            {
            userPic = this.getResources().getDrawable(R.drawable.cpin);
            userPicOverlay = new MyItemizedOverlay(userPic);
            OverlayItem overlayItem = new OverlayItem(geoPoint, "I'm Here!!!", null);
            userPicOverlay.addOverlay(overlayItem);
            mapview.getOverlays().add(userPicOverlay);
              Log.d("view",""+userPicOverlay);
            }
           /* {

            atmPic = this.getResources().getDrawable(R.drawable.push);
            nearPicOverlay = new MyItemizedOverlay(atmPic);
            Log.d("Miles16.nearme[0......",""+PosterAPI.locationlist.size()); 
			for (int i = 0; i <PosterAPI.locationlist.size(); i++) {
				Log.d("Miles16.nearme[0......",""+PosterAPI.locationlist); 
				
				String st = PosterAPI.locationlist.get(i);
                String str[] = st.split(":");
                Log.d("Miles16.nearme[0......",""+str.length); 
                for (int p = 0; p < str.length; p++) {
                    System.out.println(str[p]);
                    String lat=str[p];
                    Log.d("p",""+p);
                    Log.d("split lat",""+lat);
                    String longi=str[++p];
                    Log.d("p",""+p);
                    Log.d("split long",""+longi);
                    nearatms[i] = new OverlayItem(new GeoPoint((int)((Double.parseDouble(lat)) * 1E6),(int)((Double.parseDouble(longi) )* 1E6)),"Name", null);//just check the brackets i just made change here so....
                    Log.d("p",""+p);
                    Log.d(" lat",""+Double.parseDouble(lat));
                    Log.d(" long",""+Double.parseDouble(longi));
                    Log.d("Miles16.get i",""+nearatms[i]);
                }
                nearPicOverlay.addOverlay(nearatms[i]);
                //Log.d("Miles16.get i",""+Location.locationlist.get(1));
                mapview.getOverlays().add(nearPicOverlay);
                Log.d("view2",""+nearPicOverlay);
                mapview.invalidate();
            }
            
            }*/  //Added symbols will be displayed when map is redrawn so force redraw now
            mapview.postInvalidate();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(locationmap.this, "Error in retrieving ",Toast.LENGTH_SHORT).show();
            doError(e);
    		Log.d("doerror","error");
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
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
}