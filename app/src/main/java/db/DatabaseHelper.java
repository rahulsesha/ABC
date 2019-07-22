package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.Slot;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "slots_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Slot.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Slot.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /*public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Slot.COLUMN_NOTE, note);

        // insert row
        long id = db.insert(Slot.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }*/

    public long insertSlot(Slot slot) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Slot.COLUMN_NUMBER, slot.getNumber());
        values.put(Slot.COLUMN_ENTRY_TIMESTAMP, slot.getEntry_timestamp());
        values.put(Slot.COLUMN_EXIT_TIMESTAMP, slot.getExit_timestamp());
        values.put(Slot.COLUMN_VEHICLE_TYPE, slot.getVehicle_type());
        values.put(Slot.COLUMN_FLOOR_TYPE, slot.getFloor_type());
        values.put(Slot.COLUMN_SLOT, slot.getSlot());

        // insert row
        long id = db.insert(Slot.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Slot getNote(String number) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Slot.TABLE_NAME,
                new String[]{Slot.COLUMN_ID,Slot.COLUMN_NUMBER, Slot.COLUMN_ENTRY_TIMESTAMP, Slot.COLUMN_EXIT_TIMESTAMP,Slot.COLUMN_VEHICLE_TYPE,Slot.COLUMN_FLOOR_TYPE,Slot.COLUMN_SLOT},
                Slot.COLUMN_NUMBER + "=?",
                new String[]{String.valueOf(number)}, null, null, null, null);

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();

            // prepare note object
            Slot note = new Slot(
                    cursor.getInt(cursor.getColumnIndex(Slot.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_ENTRY_TIMESTAMP)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_EXIT_TIMESTAMP)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_VEHICLE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_FLOOR_TYPE)),
                    cursor.getString(cursor.getColumnIndex(Slot.COLUMN_SLOT)));

            // close the db connection
            cursor.close();
            return note;
        }
        return null;
    }

    /*public List<Slot> getAllNotes() {
        List<Slot> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Slot.TABLE_NAME + " ORDER BY " +
                Slot.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Slot note = new Slot();
                note.setId(cursor.getInt(cursor.getColumnIndex(Slot.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Slot.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Slot.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }*/

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Slot.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    /*public int updateNote(Slot note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Slot.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Slot.TABLE_NAME, values, Slot.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }*/

    public void deleteNote(Slot slot) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Slot.TABLE_NAME, Slot.COLUMN_NUMBER + " = ?",
                new String[]{String.valueOf(slot.getNumber())});
        db.close();
    }
}
