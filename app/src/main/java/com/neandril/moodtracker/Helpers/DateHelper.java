package com.neandril.moodtracker.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public DateHelper() {
    }

    public String getCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String strDate = format.format(today);

        return strDate;
    }
}
