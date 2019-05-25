package com.neandril.moodtracker.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class for retrieve current date from anywhere easily
 */
public class DateHelper {

    public static String getCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        return format.format(today);
    }
}
