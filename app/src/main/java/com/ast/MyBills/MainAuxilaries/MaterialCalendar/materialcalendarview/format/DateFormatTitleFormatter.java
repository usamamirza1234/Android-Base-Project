package com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.format;


import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Format using a {@linkplain DateFormat} instance.
 */
public class DateFormatTitleFormatter implements TitleFormatter {

    private final DateFormat dateFormat;

    /**
     * Format using "LLLL yyyy" for formatting
     */
    public DateFormatTitleFormatter() {
        this.dateFormat = new SimpleDateFormat(
                "LLLL yyyy", Locale.ENGLISH
        );
    }

    /**
     * Format using a specified {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    public DateFormatTitleFormatter(DateFormat format) {
        this.dateFormat = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence format(CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
