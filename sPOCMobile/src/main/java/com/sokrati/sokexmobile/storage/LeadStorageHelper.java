package com.sokrati.sokexmobile.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sokrati.sokexmobile.model.Lead;
import com.sokrati.sokexmobile.model.LeadData;

public class LeadStorageHelper extends SQLiteOpenHelper         // this class is storing data in phones memory.. persisting app data in phone
{
    public enum LEAD_STATE
    {
        PERSISTED,
        READY,
        UPLOADED
    }

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "sokrati.db";
    private static final String TABLE_NAME = "sokleads";
    private static final String TABLE_CREATE =
        "CREATE TABLE "
                + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "payload TEXT, "
                + "state INT DEFAULT 0, "
                + "timestamp INT"
                + ");";

    public LeadStorageHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        Log.w(LeadStorageHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addLead(Lead lead)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Long timestamp = lead.getTimestamp();
        String serializedParams = lead.getSerializedParams();

        ContentValues values = new ContentValues();
        values.put("payload", serializedParams); // Contact Name
        values.put("timestamp", timestamp); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public long getSize()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long size = new File(db.getPath()).length();
        return size;
    }

    public long getCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long queryNumEntries = DatabaseUtils.queryNumEntries(db, TABLE_NAME, "state = " + LEAD_STATE.PERSISTED.ordinal());
        return queryNumEntries;
    }
    
    public void updateLeadState(int id, LEAD_STATE leadState)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("state", leadState.ordinal()); // Contact Name
        db.update(TABLE_NAME, values, "id = " + id, null);
        db.close();
    }

    public void updateLeadState(LEAD_STATE leadState)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("state", leadState.ordinal()); // Contact Name
        db.update(TABLE_NAME, values, null, null);
        db.close();
    }

    public List<LeadData> getLeads(LEAD_STATE state)
    {
        ArrayList<LeadData> out = new ArrayList<LeadData>();
        SQLiteDatabase db = this.getReadableDatabase();
        
//        Cursor cursor = db.query(TABLE_NAME, null, "state=?", new String[]{String.valueOf(state.ordinal())}, null, null, "timestamp", null);
        String query = "select * from " + TABLE_NAME +  " where state = " + state.ordinal() + " order by timestamp";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = 
                        cursor.getInt(cursor.getColumnIndex("id"));
                String payLoad = 
                        cursor.getString(cursor.getColumnIndex("payload"));
                Long timeStamp = 
                    cursor.getLong(cursor.getColumnIndex("timestamp"));
                LeadData data = 
                    new LeadData(id, payLoad, timeStamp);
                out.add(data);
            } while (cursor.moveToNext());
        }
        
//        deleteLeads(cursor.getInt(cursor.getColumnIndex("id")));
        return out;
    }
    
   /* private void deleteLeads(int maxCount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id <= " + maxCount, null);
    }*/
    
    public void delete(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = " + id, null);
    }
}
