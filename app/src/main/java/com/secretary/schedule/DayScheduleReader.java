package com.secretary.schedule;

/**
 * Created by dan on 19/09/14.
 */
public class DayScheduleReader  {
    public static String[] getStartHours() {
        String[] startHours = {"0830", "0920", "0930", "1020", "1030", "1120", "1130", "1220", "1230", "1320", "1330", "1420"};
        return startHours;

    }

    public static String[] getLessons(int weekDay) {



        String[][] lessons = {{"a", "b", "c", "d", "e", "f"},
                              {"g", "b", "c", "d", "e", "f"},
                              {"m", "b", "c", "d", "e", "f"},
                              {"r", "b", "c", "d", "e", "f"},
                              {"x", "b", "c", "d", "e", "f"}};
        return lessons[weekDay - 1];

    }




}
