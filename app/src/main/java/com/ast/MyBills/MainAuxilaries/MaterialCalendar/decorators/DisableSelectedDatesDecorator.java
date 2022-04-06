package com.ast.MyBills.MainAuxilaries.MaterialCalendar.decorators;

import android.content.Context;
import android.text.style.ForegroundColorSpan;


import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.CalendarDay;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.DayViewDecorator;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.DayViewFacade;
import com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.spans.SideDotSpan;
import com.ast.MyBills.R;

import java.util.Collection;
import java.util.HashSet;


/**
 * Created by usamak.mirza@gmail.com
 */


/**
 * Decorate several days with a dot outlined
 */
public class DisableSelectedDatesDecorator implements DayViewDecorator {

    private int colorCircle;
    private int colorStroke;
    private float radius;
    private String strStartDate;
    private HashSet<CalendarDay> dates;
    private Context context;

    public DisableSelectedDatesDecorator(Context _context, Collection<CalendarDay> _dates) {
//        this.colorCircle = _colorCircle;
//        this.colorStroke = _colorStroke;
//        this.radius = _radius;
        this.context = _context;
        this.dates = new HashSet<>(_dates);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);

    }

    @Override
    public void decorate(DayViewFacade view) {
// TODO: 17-Jan-22  to  change important dates color 
        view.addSpan(new SideDotSpan(colorCircle, colorStroke, radius));
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)));
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shp_circle_red));
        //view.setSelectionDrawable(getDrawable(R.drawable.drawable_rectangle));
        view.setDaysDisabled(true);
        view.areDaysDisabled();
    }
}
