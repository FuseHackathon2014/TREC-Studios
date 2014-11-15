package com.trecstudios.pillbox;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by derek on 11/15/14.
 */
public interface PillTiming {
    public abstract Date getNextTarget(Date previousTarget);
    public abstract double pillsPerDay();

    // class ByDays
    // class MorningAndAfternoon
    class ByHour implements  PillTiming{
        private final int hours;
        public ByHour(int hours){
            this.hours = hours;
        }
        @Override
        public Date getNextTarget(Date previousTarget){
            Calendar cal = Calendar.getInstance();
            cal.setTime(previousTarget);
            cal.add(Calendar.HOUR, this.hours);
            return cal.getTime();
        }
        @Override
        public double pillsPerDay(){
            return 24.0 / hours;
        }
    }
}
