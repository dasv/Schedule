package com.secretary.schedule;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.secretary.schedule.Adapter.LessonListAdapter;

public class ListFiller extends ListActivity {
    ListView lessonList;
    private LessonsDataSource datasource;
    private ArrayList<Integer> intList= new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_classes_settings);

        intList.clear();

        datasource = new LessonsDataSource(this);
        datasource.open();

        List myList = datasource.getAllLessons();
        lessonList = (ListView) findViewById(android.R.id.list);
        lessonList.setAdapter(new LessonListAdapter(this, R.layout.row_layout, myList));

        Button saveBtn = (Button)findViewById(R.id.finish_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (intList.size() !=0) {
                    for (int i = 0; i < intList.size(); i++){
                        datasource.selectLesson(intList.get(i));
                    }
                }
                else {Toast.makeText(ListFiller.this, "no classes selected!", Toast.LENGTH_LONG).show();}

            }
        });
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ListView lessonList = (ListView) findViewById(android.R.id.list);
        LessonListAdapter lessonAdapter = (LessonListAdapter) lessonList.getAdapter();
        Lesson lesson = null;
        switch (view.getId()) {
            case R.id.showList:

                //generador de clases de prueba

                List myList = new ArrayList();
                int j = 0;
                for (int i = 0; i < 30; i++) {



                    int number = 55000000 + i;
                    String name = "Clase " + Integer.toString(i);
                    int teacher_id[] = {0, 1};

                    int slots[] = {j, j+1, j+2, j+3};

                    String turn = null;
                    if (i < 10) turn = "1M1";
                    else if (i < 20) turn = "1M2";
                    else turn = "1M3";

                    String room = "room";

                    j = j + 4;

                    myList.add(new Lesson(number, name, teacher_id, slots, turn, room));
                    lesson = new Lesson(number, name, teacher_id, slots, turn, room);
                    lesson = datasource.createLesson(lesson.getNumber(),
                            lesson.getName(), lesson.getTeacherId(), lesson.getSlots(), lesson.getTurn(), lesson.getRoom());
                    lessonAdapter.add(lesson);
                }
                break;


        }
        lessonAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Tiene que haber otra manera de hacer esto que no sea leyendo el textview
        //aunque parece que no

        TextView selectionId = (TextView) v.findViewById(R.id.lesson_id);
        String selectionIdValue = selectionId.getText().toString();
        TextView selectionTurn = (TextView) v.findViewById(R.id.turn);
        String selectionTurnValue = selectionTurn.getText().toString();

        Toast.makeText(this, selectionIdValue, Toast.LENGTH_LONG).show();

        intList.add(Integer.parseInt(selectionIdValue));

    }
/*
public void finishClick() {
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < intList.size(); i++){
                datasource.selectLesson(intList.get(i));

            }
            Log.d("BUTTON", "button clicked");
        }
    });*/



    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.classes_settings, menu);
        return true;
    }

}