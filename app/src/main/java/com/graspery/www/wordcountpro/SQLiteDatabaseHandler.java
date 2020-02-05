package com.graspery.www.wordcountpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserArchiveDB";
    private static final String TABLE_NAME = "Archive";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_CONTENT = "content";
    private static final String[] COLUMNS = { KEY_ID, KEY_DATE, KEY_CONTENT};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Archive ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT, "
                + "content TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public void deleteOne(int id) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { Integer.toString(id)});
        db.close();
    }

    public Archive getArchive(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Archive archive = new Archive();
        archive.setId(Integer.parseInt(cursor.getString(0)));
        archive.setDate(cursor.getString(1));
        archive.setText(cursor.getString(2));

        return archive;
    }

    public List<Archive> allArchive() {

        List<Archive> archives = new LinkedList<Archive>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Archive archive = null;

        if (cursor.moveToFirst()) {
            do {
                archive = new Archive();
                archive.setId(Integer.parseInt(cursor.getString(0)));
                archive.setDate(cursor.getString(1));
                archive.setText(cursor.getString(2));
                archives.add(archive);
            } while (cursor.moveToNext());
        }

        return archives;
    }

    public void addArchive(Archive archive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, archive.getDate());
        values.put(KEY_CONTENT, archive.getText());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePlayer(Archive archive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, archive.getDate());
        values.put(KEY_CONTENT, archive.getText());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(archive.getId()) });

        db.close();

        return i;
    }

}