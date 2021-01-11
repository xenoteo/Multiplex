package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Date filter provides filtering a seance by maximal and minimal date.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 *
 */

public class DateFilter implements SeanceFilter {

    private LocalDateTime maxDate = LocalDateTime.MAX;
    private LocalDateTime minDate = LocalDateTime.MIN;

    public DateFilter(){

    }
    public DateFilter(LocalDateTime minDate, LocalDateTime maxDate){
        this.minDate = minDate;
        this.maxDate = maxDate;

    }

    @Override
    public boolean apply(Seance seance) {
        LocalDateTime date = seance.getDate();
        return date.isBefore(maxDate) && date.isAfter(minDate)
                || date.isEqual(maxDate)
                || date.isEqual(minDate);
    }

    public void setMaxDate(LocalDateTime maxDate){
        this.maxDate = maxDate;
    }

    public void setMinDate(LocalDateTime minDate){
        this.minDate = minDate;
    }

}
