package com.SmartCampus;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SmartPoster extends Activity {
    private static final String TAG = "SmartCampus";
    private boolean mResumed = false;
    private boolean mWriteMode = false;
    NfcAdapter mNfcAdapter;
    EditText mNote;
    public static String UID;
    DataUid data;
    String time;
     PendingIntent mNfcPendingIntent;
     IntentFilter[] mWriteTagFilters;
    IntentFilter[] mNdefExchangeFilters;
    Calendar currentDate;
    
    public static List<String>uidList= new ArrayList<String>();
    public static List<String>uList= new ArrayList<String>();
    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        setContentView(R.layout.smart_poster);
        uList.clear();
        data=new DataUid(this);
      currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= 
        new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        time = formatter.format(currentDate.getTime());
        System.out.println("Now the date is :=>  " + time);
       // findViewById(R.id.write_tag).setOnClickListener(mTagWriter);
        mNote = ((EditText) findViewById(R.id.note));
        mNote.addTextChangedListener(mTextWatcher);
        mNote.setVisibility(View.GONE);
        // Handle all of our received NFC intents in this activity.
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters for reading a note from a tag or exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        } catch (MalformedMimeTypeException e) { }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

        // Intent filters for writing to a tag
      IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
        // Sticky notes received from Android
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            NdefMessage[] messages = getNdefMessages(getIntent());
            byte[] payload = messages[0].getRecords()[0].getPayload();
            setNoteBody(new String(payload));
            Log.d("payload",""+payload);
            UID=  mNote.getText().toString();
            Log.d("uid",""+UID);
           
            data.open();
			Cursor getdetails=data.getlistitems();
			 if(getdetails.moveToFirst())
			  {
			   do{
			 String ccat=getdetails.getString(getdetails.getColumnIndex("uid"));

			   Log.d("ccat",""+ccat);
		        //System.out.println("name::::::::::"+cat);
		      uidList.add(ccat);
		        
			   }while(getdetails.moveToNext());
			  }
			 Log.d(" catList",""+ uidList);
			 //catList.contains(cat);
			// Log.d(" catList",""+catList.contains(cat));
			 int index = uidList.indexOf(UID);
			    if(index == -1){
            
                        data.insertval(1,UID,time);
                      Log.d("data inserted",""+UID+time);
          
           
			    }
			    data.close();
			    if(UID!=null){
			    	uList.add(UID);
	            	Intent back=new Intent(SmartPoster.this,PosterAPI.class);
	        
	          Log.d("UID",""+UID);
					startActivity(back);
					finish();
	            }
			    else{
					Toast.makeText(getApplicationContext(),"API not found", Toast.LENGTH_SHORT).show();

			    }
            setIntent(new Intent()); // Consume this intent.
        }
        enableNdefExchangeMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        mNfcAdapter.disableForegroundNdefPush(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // NDEF exchange mode
        if (!mWriteMode && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] msgs = getNdefMessages(intent);
            promptForContent(msgs[0]);
        }

        // Tag writing mode
       /* if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            writeTag(getNoteAsNdef(), detectedTag);
        }*/
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        public void afterTextChanged(Editable arg0) {
            if (mResumed) {
                mNfcAdapter.enableForegroundNdefPush(SmartPoster.this, getNoteAsNdef());
            }
        }
    };

   /* private View.OnClickListener mTagWriter = new View.OnClickListener() {
        public void onClick(View arg0) {
            // Write to a tag for as long as the dialog is shown.
            disableNdefExchangeMode();
          //  enableTagWriteMode();

            new AlertDialog.Builder(StickyNotesActivity.this).setTitle("Touch tag to write")
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            disableTagWriteMode();
                            enableNdefExchangeMode();
                        }
                    }).create().show();
        }
    };*/

    private void promptForContent(final NdefMessage msg) {
    	 UID = new String(msg.getRecords()[0].getPayload());
         
    	 Log.d("body",""+UID);
    	 data.open();
			Cursor getdetails=data.getlistitems();
			 if(getdetails.moveToFirst())
			  {
			   do{
			 String ccat=getdetails.getString(getdetails.getColumnIndex("uid"));

			   Log.d("ccat",""+ccat);
		        //System.out.println("name::::::::::"+cat);
		      uidList.add(ccat);
		        
			   }while(getdetails.moveToNext());
			  }
			 Log.d(" catList",""+ uidList);
			 //catList.contains(cat);
			// Log.d(" catList",""+catList.contains(cat));
			 int index = uidList.indexOf(UID);
			    if(index == -1){
         
                     data.insertval(1,UID,time);
                   Log.d("data inserted",""+UID+time);
       
        
			    }
			    data.close();
         if(UID!=null){
        	 uList.add(UID);
        	 Log.d("ulist",""+uList.get(0));
         	Intent back=new Intent(SmartPoster.this,PosterAPI.class);
         	
	          Log.d("UID",""+UID);
				startActivity(back);
				
         }
         else{
 			Toast.makeText(getApplicationContext(),"API not found", Toast.LENGTH_SHORT).show();

         }
         setNoteBody(UID);
    }

    private void setNoteBody(String body) {
        Editable text = mNote.getText();
        UID=mNote.getText().toString();
        Log.d("uid",""+UID);
        text.clear();
        text.append(body);
    }

    private NdefMessage getNoteAsNdef() {
        byte[] textBytes = mNote.getText().toString().getBytes();
        NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),
                new byte[] {}, textBytes);
        return new NdefMessage(new NdefRecord[] {
            textRecord
        });
    }

    NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (/*NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                ||*/ NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                    record
                });
                msgs = new NdefMessage[] {
                    msg
                };
            }
        } else {
            Log.d(TAG, "Unknown intent.");
            finish();
        }
        return msgs;
    }

    private void enableNdefExchangeMode() {
        mNfcAdapter.enableForegroundNdefPush(SmartPoster.this, getNoteAsNdef());
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
    }

   /* private void disableNdefExchangeMode() {
        mNfcAdapter.disableForegroundNdefPush(this);
        mNfcAdapter.disableForegroundDispatch(this);
    }
*/
   /* private void enableTagWriteMode() {
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] {
            tagDetected
        };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }*/

    private void disableTagWriteMode() {
        mWriteMode = false;
        mNfcAdapter.disableForegroundDispatch(this);
    }

   /* boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;

        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    toast("Tag is read-only.");
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    toast("Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size
                            + " bytes.");
                    return false;
                }

                ndef.writeNdefMessage(message);
                toast("Wrote message to pre-formatted tag.");
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        toast("Formatted tag and wrote message");
                        return true;
                    } catch (IOException e) {
                        toast("Failed to format tag.");
                        return false;
                    }
                } else {
                    toast("Tag doesn't support NDEF.");
                    return false;
                }
            }
        } catch (Exception e) {
            toast("Failed to write tag");
        }

        return false;
    }*/

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
}