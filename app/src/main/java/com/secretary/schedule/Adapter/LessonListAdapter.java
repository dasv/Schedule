package com.secretary.schedule.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.secretary.schedule.Lesson;
import com.secretary.schedule.R;

import java.util.List;

public class LessonListAdapter extends ArrayAdapter<Lesson> {
    private int  resource;
    private LayoutInflater inflater;
    private Context context;

    // our constructor takes 3 parameters; keep this in mind when you set the adapter.
    //  resourceId will be our row_layout resource. Context and List should be clear.
    public LessonListAdapter(Context ctx, int resourceId, List<Lesson> objects) {
        super( ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;

    }



    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {



                  /* create a new view of my layout and inflate it in the row */
        convertView = (LinearLayout) inflater.inflate( resource, null );

                  /* Extract the SWMovie object to show. getItem() comes from ArrayAdapter class */
        Lesson lesson = getItem(position);


                  /* set SWMovie movie in movie TextView. getMovie is from our SWMovie class */
        TextView txtLesson = (TextView) convertView.findViewById(R.id.lesson);
        txtLesson.setText(lesson.getName());

                 /* set SWMovie episode in episode TextView */
        TextView txtTurn = (TextView) convertView.findViewById(R.id.turn);
        txtTurn.setText(lesson.getTurn());

        TextView txtId = (TextView) convertView.findViewById(R.id.lesson_id);
        txtId.setText(""+lesson.getId());

        return convertView;
    }
}