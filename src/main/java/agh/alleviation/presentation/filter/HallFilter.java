package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

/**
 * Date filter provides filtering a seance by hall number.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 */
public class HallFilter implements SeanceFilter {

    /**
     * The hall number.
     */
    private int hallNumber;

    /**
     * Instantiates a hall filter.
     *
     * @param hallNumber  the hall number
     */
    public HallFilter(int hallNumber){
        this.hallNumber = hallNumber;
    }

    @Override
    public boolean apply(Seance seance) {
        return seance.getHall().getNumber() == hallNumber;
    }

    /**
     * Sets the hall number.
     *
     * @param hallNumber  the hall number
     */
    public void setHallNumber(int hallNumber){
        this.hallNumber = hallNumber;
    }

}
