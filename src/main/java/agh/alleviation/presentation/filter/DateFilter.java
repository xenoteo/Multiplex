package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.time.LocalDateTime;

/**
 * Date filter provides filtering a seance by maximal and minimal date.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 */

public class DateFilter implements SeanceFilter {

    /**
     * The max date.
     */
    private LocalDateTime maxDate = LocalDateTime.MAX;
    /**
     * The min date.
     */
    private LocalDateTime minDate = LocalDateTime.MIN;

    /**
     * Instantiates a date filter.
     */
    public DateFilter(){ }

    /**
     * Instantiates a date filter.
     *
     * @param minDate  the min date
     * @param maxDate  the max date
     */
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

    /**
     * Sets the max date.
     *
     * @param maxDate  the max date
     */
    public void setMaxDate(LocalDateTime maxDate){
        this.maxDate = maxDate;
    }

    /**
     * Sets the min date.
     *
     * @param minDate  the min date
     */
    public void setMinDate(LocalDateTime minDate){
        this.minDate = minDate;
    }

}
