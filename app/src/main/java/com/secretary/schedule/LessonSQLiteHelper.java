package com.secretary.schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLClientInfoException;
import java.util.List;

public class LessonSQLiteHelper extends SQLiteOpenHelper {
    // name the Table
    public static final String TABLE_LESSONS = "lessons";
    public static final String TABLE_USER_LESSONS = "user_lessons";
    // name the Columns of the Table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEACHER_ID0 = "teacher_id0";
    public static final String COLUMN_TEACHER_ID1 = "teacher_id1";
    public static final String COLUMN_SLOT0 = "slot0";
    public static final String COLUMN_SLOT1 = "slot1";
    public static final String COLUMN_SLOT2 = "slot2";
    public static final String COLUMN_SLOT3 = "slot3";

    public static final String COLUMN_SLOT = "slot";

    public static final String COLUMN_TURN = "turn";
    public static final String COLUMN_ROOM = "room";

    public static final String COLUMN_SELECTED = "selected";
    public static final String COLUMN_COLOR = "color";


    private static final String DATABASE_NAME = "store.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation declaration
    private static final String TABLE_CREATE_LESSONS = "create table "
            + TABLE_LESSONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NUMBER + " integer, " + COLUMN_NAME
            + " text not null, " + COLUMN_TEACHER_ID0 + " integer," + COLUMN_TEACHER_ID1 + " integer, "
            + COLUMN_SLOT0 + " integer, " + COLUMN_SLOT1 + " integer, " + COLUMN_SLOT2 + " integer, "
            + COLUMN_SLOT3 + " integer, " + COLUMN_TURN + " text not null, " + COLUMN_ROOM + " text not null, "
            + COLUMN_SELECTED + " boolean CHECK (" + COLUMN_SELECTED + " IN (0,1))" + ")";

    private static final String TABLE_CREATE_USER_LESSONS = "create table "
            + TABLE_USER_LESSONS + "(" + COLUMN_ID
            + " integer, " + COLUMN_NAME
            + " text not null, " + COLUMN_SLOT + " integer" + ")";

    public static final String SELECT_LESSON = "insert into " + TABLE_USER_LESSONS + " select * from " + TABLE_LESSONS +
    " where " + COLUMN_NUMBER + " = ";

    // our object constructor
    public LessonSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_LESSONS);
        db.execSQL(TABLE_CREATE_USER_LESSONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LessonSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSONS);
        onCreate(db);

    }

}