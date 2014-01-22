package com.parlour.business.database;

import com.parlour.business.model.Member;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "membersdb";	
	private static final int DATABASE_VERSION = 2;
	
	private static final String CREATE_USER_TABLE = "CREATE TABLE "
			+ Constants.TABLE_USER + "(" + Constants.KEY_EMAIL + " TEXT PRIMARY KEY, "
			+ Constants.KEY_PASSWORD + " TEXT NOT NULL, " + Constants.KEY_FULLNAME + " TEXT, "
			+ Constants.KEY_PHONENO + " TEXT" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER_TABLE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);

		// Create tables again
		onCreate(db);

	}
	
	// Adding new member
    public long addMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_EMAIL, member.getEmail()); 
        values.put(Constants.KEY_PASSWORD, member.getPassword());
        values.put(Constants.KEY_FULLNAME, member.getFullName());
        values.put(Constants.KEY_PHONENO, member.getPhoneNumber());
 
        // Inserting Row
        long id = db.insert(Constants.TABLE_USER, null, values);
        db.close(); // Closing database connection
        return id;
    }
    
 // Getting single Member
    public Member getMember(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
		Cursor cursor = db.query(Constants.TABLE_USER, new String[] { Constants.KEY_EMAIL,
				Constants.KEY_PASSWORD, Constants.KEY_FULLNAME, Constants.KEY_PHONENO },
				Constants.KEY_EMAIL+ "=? AND "+ Constants.KEY_PASSWORD+ "=?",
				new String[] {username, password}, null, null, null, null);
		
        
		Member member = null;
        if (cursor != null){
            cursor.moveToFirst();
            member = new Member(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        return member;
    }

}
