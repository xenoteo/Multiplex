package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;


/**
 * Date filter provides filtering a seance by hall number.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 *
 */

public class HallFilter implements SeanceFilter {

    private int hallNumber;

    public HallFilter(int hallNumber){
        this.hallNumber = hallNumber;
    }

    @Override
    public boolean apply(Seance seance) {
        return seance.getHall().getNumber() == hallNumber;
    }

    public void setHallNumber(int hallNumber){
        this.hallNumber = hallNumber;
    }



}
