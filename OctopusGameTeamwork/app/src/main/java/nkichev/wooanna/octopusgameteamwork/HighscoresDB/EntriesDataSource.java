package nkichev.wooanna.octopusgameteamwork.HighscoresDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EntriesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLiteDatabase databaseForReading;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_SCORE };

    public EntriesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void openForReading() {
        databaseForReading = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void closeForReading() {
        databaseForReading.close();
    }

    public Entry createEntry(String name, long score) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);
        long insertId = database.insert(MySQLiteHelper.TABLE_ENTRIES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Entry newEntry = cursorToEntry(cursor);
        cursor.close();
        return newEntry;
    }

    public long findEntry(String name) {
        Cursor entryCursor = null;
        long id = -1;
        try{
            entryCursor = databaseForReading.rawQuery("SELECT _id FROM Entries WHERE Name=?",
                    new String[] {name + ""});
            if(entryCursor.getCount() > 0) {
                entryCursor.moveToFirst();
                id = entryCursor.getLong(entryCursor.getColumnIndex("_id"));
            }

            return id;
        }finally {
            entryCursor.close();
        }
    }

    public void updateEntry(long score, long id) {
        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.COLUMN_SCORE, score);

        database.update(MySQLiteHelper.TABLE_ENTRIES, cv, "_id " + " = " + id, null);
    }

    public ArrayList<Entry> getAllEntries() {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_SCORE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Entry entry = cursorToEntry(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return entries;
    }

    public long getBestScore(){
        Entry entryWithBestScore = new Entry();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_SCORE + " DESC");

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            entryWithBestScore = cursorToEntry(cursor);
            cursor.close();
            return entryWithBestScore.getScore();
        }

        else return -1;
    }

    private Entry cursorToEntry(Cursor cursor) {
        Entry entry = new Entry();
        entry.setId(cursor.getLong(0));
        entry.setName(cursor.getString(1));
        entry.setScore(cursor.getLong(2));
        return entry;
    }
}
