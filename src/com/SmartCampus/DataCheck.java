package com.SmartCampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataCheck {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CHECK= "checkbox";
   
	
    private static final String DATABASE_NAME = "Check";
    private static final String DATABASE_TABLE = "box";
    private static final int DATABASE_VERSION = 100;
    
    private static final String DATABASE_CREATE =
        "create table box(_id integer primary key autoincrement , "+ "checkbox text not null)";
	
    private final Context context; 
    private DatabaseHelper DBHelper;
    private static SQLiteDatabase db;
	

    public DataCheck(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            Log.w("Upgrading database", "Upgrading database from version " + oldVersion+ " to "+ newVersion + ", which will destroy all old data");
        	db.execSQL(DATABASE_CREATE);
        	    	 
           // onCreate(db);
        }
    }    
    
   //---opens the database---
    public DataCheck open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
   //---insert a title into the database---
    public long insertval(int _id,String checkbox) 
    {
    	
    	
    	ContentValues initialValues = new ContentValues();
        
 
        initialValues.put(KEY_CHECK, checkbox);
       
        System.out.println("checkbox::::::::"+checkbox);
        
       
        
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public boolean deleteTitle(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
 

    public Cursor getlistitems() 
    {
    	Cursor mCursor =
         db.query(DATABASE_TABLE, new String[] {
        		 
        		 KEY_CHECK
                }, 
                null, 
                null, 
                null, 
                null, 
                null);
    	if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
   }
    public Cursor getTitle(long rowId) throws SQLException 
    {
        Cursor mCursor =  db.query(true, DATABASE_TABLE, new String[] {
        		KEY_ROWID , KEY_CHECK
                		}, 
                		KEY_ROWID+ "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean updateTitle(long rowId, String checkbox)
    	    
    	    {
    	        ContentValues args = new ContentValues();
    	        args.put(KEY_CHECK, checkbox);
    	       
    	        return db.update(DATABASE_TABLE, args, 
    	                         KEY_ROWID + "=" + rowId, null) > 0;
    	    }

}
