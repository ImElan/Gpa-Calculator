package gpa.mit.india;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DatabaseName = "results.db";
    private static final String TableName = "students_table";
    //    private static final String COL_1 = "SECRET_ID";
    private static final String COL_2 = "NAME_TAG";
    private static final String COL_3 = "CREDITS";
    private static final String COL_4 = "GPA";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TableName + "(NAME_TAG TEXT , CREDITS TEXT , GPA TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    boolean AddData(String name_tag,String Credits,String GPA)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name_tag);
        contentValues.put(COL_3,Credits);
        contentValues.put(COL_4,GPA);


        long result = db.insert(TableName,null,contentValues);
        return result != -1;
    }

    Cursor ViewData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TableName,null);
        return cursor;
    }

    boolean EditData(String name_id,String name_tag,String Credits,String GPA)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,name_tag);
        contentValues.put(COL_3,Credits);
        contentValues.put(COL_4,GPA);
        db.update(TableName,contentValues,"NAME_TAG = ?",new String[] {name_id});
        return true;
    }

    Integer DeletaData(String name_tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int temp =  db.delete(TableName,"NAME_TAG = ?",new String[]{name_tag});
        Log.d("CHECK_TAG",Integer.toString(temp));
        return temp;
    }

    boolean checkAlreadyExist(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT " + COL_2 + " FROM " + TableName + " WHERE " + COL_2 + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
            return false;
    }
}
