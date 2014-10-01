package com.secretary.schedule;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LessonsDataSource {

    private SQLiteDatabase database;
    private LessonSQLiteHelper dbHelper;
    private String[] allColumns = { LessonSQLiteHelper.COLUMN_ID,
            LessonSQLiteHelper.COLUMN_NUMBER,
            LessonSQLiteHelper.COLUMN_NAME,
            LessonSQLiteHelper.COLUMN_TEACHER_ID0,
            LessonSQLiteHelper.COLUMN_TEACHER_ID1,
            LessonSQLiteHelper.COLUMN_SLOT0,
            LessonSQLiteHelper.COLUMN_SLOT1,
            LessonSQLiteHelper.COLUMN_SLOT2,
            LessonSQLiteHelper.COLUMN_SLOT3,
            LessonSQLiteHelper.COLUMN_TURN,
            LessonSQLiteHelper.COLUMN_ROOM };

    private String[] slotColumnsAndName = {LessonSQLiteHelper.COLUMN_SLOT0,
                                LessonSQLiteHelper.COLUMN_SLOT1,
                                LessonSQLiteHelper.COLUMN_SLOT2,
                                LessonSQLiteHelper.COLUMN_SLOT3,
                                LessonSQLiteHelper.COLUMN_NAME};

    public static int[] RANGE = {0, 12, 24, 36, 48, 60};

    public LessonsDataSource(Context context) {
        dbHelper = new LessonSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Lesson createLesson(int number, String name, int[] teacher_id, int[] slots, String turn, String room) {
        ContentValues values = new ContentValues();

        values.put(LessonSQLiteHelper.COLUMN_NUMBER, number);
        values.put(LessonSQLiteHelper.COLUMN_NAME, name);
        values.put(LessonSQLiteHelper.COLUMN_TEACHER_ID0, teacher_id[0]);
        values.put(LessonSQLiteHelper.COLUMN_TEACHER_ID1, teacher_id[1]);
        values.put(LessonSQLiteHelper.COLUMN_SLOT0, slots[0]);
        values.put(LessonSQLiteHelper.COLUMN_SLOT1, slots[1]);
        values.put(LessonSQLiteHelper.COLUMN_SLOT2, slots[2]);
        values.put(LessonSQLiteHelper.COLUMN_SLOT3, slots[3]);
        values.put(LessonSQLiteHelper.COLUMN_TURN, turn);
        values.put(LessonSQLiteHelper.COLUMN_ROOM, room);

        long insertId = database.insert(LessonSQLiteHelper.TABLE_LESSONS, null, values);
        Cursor cursor = database.query(LessonSQLiteHelper.TABLE_LESSONS,
                allColumns, LessonSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Lesson newLesson = cursorToLesson(cursor);
        cursor.close();
        return newLesson;
    }

    public void selectLesson(int lessonId) {

        Cursor cursor = database.query(LessonSQLiteHelper.TABLE_LESSONS, slotColumnsAndName,
                "_id = ?", new String[] {Integer.toString(lessonId)}, null, null, null, null);

        cursor.moveToFirst();

        for (int i = 0; i < 4; i++) {
            int slotIndex = cursor.getColumnIndex("slot" + Integer.toString(i));
            int slot = cursor.getInt(slotIndex);
            int nameIndex = cursor.getColumnIndex("name");
            String name = cursor.getString(nameIndex);
            ContentValues args = new ContentValues();
            args.put(LessonSQLiteHelper.COLUMN_SLOT, slot);
            args.put(LessonSQLiteHelper.COLUMN_ID, lessonId);
            args.put(LessonSQLiteHelper.COLUMN_NAME, name);
            database.insert(LessonSQLiteHelper.TABLE_USER_LESSONS, null, args);
        }


        ContentValues args = new ContentValues();
        args.put(LessonSQLiteHelper.COLUMN_SELECTED, 1);
        database.update(LessonSQLiteHelper.TABLE_LESSONS,
                args, "_id = ?", new String[] {Integer.toString(lessonId)});

        cursor.close();

        //todo: hacer decentemente. Nueva tabla sólo para slots y leer de ahí
    }

    public Lesson[] readDayLessons(int weekDay){
        Lesson[] dayList = new Lesson[12];

        //cogemos el día de la tab y transformamos a rango de slots correspondientes a ese día

        int[] range = new int[2];
        switch (weekDay) {
            case 1:
                range[0] = RANGE[0];
                range[1] = RANGE[1];
                break;
            case 2:
                range[0] = RANGE[1];
                range[1] = RANGE[2];
                break;
            case 3:
                range[0] = RANGE[2];
                range[1] = RANGE[3];
                break;
            case 4:
                range[0] = RANGE[3];
                range[1] = RANGE[4];
                break;
            case 5:
                range[0] = RANGE[4];
                range[1] = RANGE[5];
                break;
        }


        Cursor cursor = database.query(LessonSQLiteHelper.TABLE_USER_LESSONS, null,
                LessonSQLiteHelper.COLUMN_SLOT + " >= " + range[0] + " AND " + LessonSQLiteHelper.COLUMN_SLOT + " < " + range[1],
                null, null, null, "slot", null);

        cursor.moveToFirst();

        //escribe la clase en la lista con el índice correspondiente al slot
        while (!cursor.isAfterLast()) {
            Lesson lesson = cursorToUserLesson(cursor);
            Log.d("RANGE", Integer.toString(lesson.getSlot0() - range[0]));

            dayList[lesson.getSlot0() - range[0]]= lesson;
            cursor.moveToNext();
        }

        cursor.close();



        return dayList;

    }

    // public void deleteLesson(Lesson lesson) {
    //     long id = lesson.getId();
    //     System.out.println("Lesson deleted with id: " + id);
    //     database.delete(LessonSQLiteHelper.TABLE_MOVIES,   LessonSQLiteHelper.COLUMN_ID
    //             + " = " + id, null);
    // }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<Lesson>();

        Cursor cursor = database.query(LessonSQLiteHelper.TABLE_LESSONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Lesson lesson = cursorToLesson(cursor);
            lessons.add(lesson);
            cursor.moveToNext();
        }

        cursor.close();
        return lessons;
    }

    private Lesson cursorToUserLesson(Cursor cursor) {
        Lesson lesson = new Lesson();
        lesson.setId(cursor.getLong(0));
        lesson.setName(cursor.getString(1));
        lesson.setSlot0(cursor.getInt(2));

        return lesson;
    }

    private Lesson cursorToLesson(Cursor cursor) {
        Lesson lesson = new Lesson();
        lesson.setId(cursor.getLong(0));
        lesson.setNumber(cursor.getInt(1));
        lesson.setName(cursor.getString(2));

        //ligera guarrada: teacher_id y slots se pasan como array
        //seguramente sea más rápido pasar cada uno suelto

        int[] teacher_id = new int[2];

        for (int i = 0; i < 2; i++) {teacher_id[i] = cursor.getInt(i+3);};

        lesson.setTeacherId(teacher_id);

        int[] slots = new int[4];

        for (int i = 0; i < 3; i++) {slots[i] = cursor.getInt(i+5);};

        lesson.setSlots(slots);

        lesson.setTurn(cursor.getString(9));
        lesson.setRoom(cursor.getString(10));
        return lesson;
    }


}
