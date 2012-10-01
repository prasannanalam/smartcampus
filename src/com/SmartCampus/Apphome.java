package com.SmartCampus;







import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Apphome extends Activity {
	ImageView go,access,note,smart,attendance,wallet;
	TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	EditText edit;
	public static String logintoken = "";
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        access=(ImageView)findViewById(R.id.i1);
      wallet=(ImageView)findViewById(R.id.i2);
        attendance=(ImageView)findViewById(R.id.i3);
        smart=(ImageView)findViewById(R.id.i4);
        note=(ImageView)findViewById(R.id.i5);
        go=(ImageView)findViewById(R.id.go);
        edit=(EditText)findViewById(R.id.et);
        Typeface fontsub = Typeface.createFromAsset(getAssets(), "roboto/Roboto-Bold.ttf"); 
        Typeface fontsub1 = Typeface.createFromAsset(getAssets(), "roboto/Roboto-Regular.ttf"); 

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6); 
        tv7=(TextView)findViewById(R.id.tv7);
        tv8=(TextView)findViewById(R.id.tv8);
        
        tv1.setTypeface(fontsub);
        tv2.setTypeface(fontsub);
        tv3.setTypeface(fontsub);
        tv4.setTypeface(fontsub1);
        tv5.setTypeface(fontsub1);
        tv6.setTypeface(fontsub1);
        tv7.setTypeface(fontsub1);
        tv8.setTypeface(fontsub1);
      if(Apphome.logintoken.equals("z1F4TahSSFcu7Sc1jtsh")){
    	  edit.setText("z1F4TahSSFcu7Sc1jtsh");
      }
      smart.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
      		
      		
      		/*Intent in= new Intent(getApplicationContext(),SmartPoster.class);
			   startActivity(in);*/
      	}
      	});
        /*  access.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        	access.setImageResource(R.drawable.access1);
    }
      });
     wallet.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
  }
    });
      attendance.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
  }
    });
      smart.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
  }
    });
      note.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
  }
    });
      access.setOnClickListener(new View.OnClickListener(){
      	
      	public void onClick(View v) {
  }
    });
      */
      
}
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.option_logout, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.logout:
			//Toast.makeText(getApplicationContext(),"Logout", Toast.LENGTH_SHORT).show();
              Loginactivity.mailList.clear();
              Intent me=new Intent(getApplicationContext(),Loginactivity.class);    
			     startActivity(me);
			     finish();
    		return true;
    	}
   // default:
		return super.onOptionsItemSelected(item);
	}
    
}