package com.secretary.schedule;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DayScheduleFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    private OnFragmentInteractionListener mListener;
    public static final String[] startHours = {"0830", "0920", "0930", "1020", "1030", "1120", "1130", "1220", "1230", "1320", "1330", "1420" };
    public static final String[] endHours = {"0920", "1020", "1120", "1220", "1320", "1420"};
    public static final String[] subjects = {"EDO", "mecanica", "electro","EDO", "mecanica", "electro"};
    public static final String[] subjects2 = {"calc", "fisica", "dibujo","calc", "fisica", "dibujo"};

    protected int mWeekday;

    private LessonsDataSource datasource;
    private Lesson[] lessonArray;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment DayScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayScheduleFragment newInstance(int sectionNumber) {
        DayScheduleFragment fragment = new DayScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public DayScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mWeekday = getArguments().getInt(ARG_SECTION_NUMBER);
            datasource = new LessonsDataSource(getActivity());
            datasource.open();

            //todo: mover esto de aqui para que no sólo se lea la primera vez que se abre (a onresume supongo, ver docs)

            lessonArray = datasource.readDayLessons(mWeekday);

            int[] range = new int[2];
            switch (mWeekday) {
                case 1:
                    range[0] = LessonsDataSource.RANGE[0];
                    range[1] = LessonsDataSource.RANGE[1];
                    break;
                case 2:
                    range[0] = LessonsDataSource.RANGE[1];
                    range[1] = LessonsDataSource.RANGE[2];
                    break;
                case 3:
                    range[0] = LessonsDataSource.RANGE[2];
                    range[1] = LessonsDataSource.RANGE[3];
                    break;
                case 4:
                    range[0] = LessonsDataSource.RANGE[3];
                    range[1] = LessonsDataSource.RANGE[4];
                    break;
                case 5:
                    range[0] = LessonsDataSource.RANGE[4];
                    range[1] = LessonsDataSource.RANGE[5];
                    break;
            }

            //lee la lista e introduce clases vacías donde no hay nada para luego pasar al adaptador

            for (int i = 0; i < lessonArray.length; i++){
                if (lessonArray[i] == null){
                    lessonArray[i] = new Lesson();
                    Log.d("NUMERO", Integer.toString(i));
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day_schedule, container, false);

        DayAdapter(rootView);






        // Inflate the layout for this fragment
        return rootView;
    }

    private void DayAdapter(View rootView) {
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.day_table);


        for (int i = 0; i < (startHours.length / 2); i++) {
            TableRow tableRow = new TableRow(getActivity());

            View tableCell = getActivity().getLayoutInflater().inflate(R.layout.table_row, tableRow, true);


            TextView hour1 = (TextView) tableRow.findViewById(R.id.hour1);
            TextView hour2 = (TextView) tableRow.findViewById(R.id.hour2);
            TextView lessonName = (TextView) tableRow.findViewById(R.id.lesson_name);
            TextView lessonExtra = (TextView) tableRow.findViewById(R.id.lesson_extra);

            hour1.setText(DayScheduleReader.getStartHours()[i]);
            hour2.setText(DayScheduleReader.getStartHours()[i+1]);

            lessonExtra.setText("extratext");

                lessonName.setText(lessonArray[i].getName());

            if ()






            tableLayout.addView(tableRow);




        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
