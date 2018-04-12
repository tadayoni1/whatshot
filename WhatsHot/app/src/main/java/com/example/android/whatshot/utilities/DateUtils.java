package com.example.android.whatshot.utilities;

import java.util.Calendar;

public class DateUtils {

    public static int getDayOfTheWeekForNow() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return -1;
        }
    }

    public static int getHourOfTheDayForNow() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
}
