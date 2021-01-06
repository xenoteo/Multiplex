package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateFilter implements SeanceFilter {

    private LocalDateTime maxDate = LocalDateTime.MAX;
    private LocalDateTime minDate = LocalDateTime.MIN;

    public DateFilter(){

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
