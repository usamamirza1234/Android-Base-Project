package com.ast.MyBills.MainAuxilaries.MaterialCalendar.decorators;


import android.content.Context;

import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.CalendarDay;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.DayViewDecorator;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.DayViewFacade;
import com.ast.MyBills.R;

import java.util.HashSet;


/**
 * Created by bilalahmed on 21/04/2017.
 * bilalahmed.cs@live.com
 */

/**
 * Decorate several days with a dot outlined
 */
public class EventDecorator implements DayViewDecorator {

    private int colorCircle;
    private int colorStroke;
    private float radius;
    private HashSet<CalendarDay> dates;
    private Context mContext;
    private int mTotalDays = 150;
    private int mDaysCount = 0;

    public EventDecorator(Context _context) {
//        this.colorCircle = _colorCircle;
//        this.colorStroke = _colorStroke;
//        this.radius = _radius;
//        this.dates = new HashSet<>(_dates);
        this.mContext = _context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        return dates.contains(day);
        CalendarDay date = CalendarDay.today();

        return (day.isAfter(date)) ? true : false;
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new SideDotSpan(mContext.getResources().getColor(R.color.pf_green), 2, 2));
//        view.addSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.pf_green)));
        view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.slc_chb_enabled_dates));
        view.setDaysDisabled(false);
    }
}
