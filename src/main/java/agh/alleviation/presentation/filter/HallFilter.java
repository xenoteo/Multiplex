package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

public class HallFilter implements SeanceFilter {

    private int hallNumber;

    public HallFilter(){

    }

    @Override
    public boolean apply(Seance seance) {
        return seance.getHall().getNumber() == hallNumber;
    }

    public void setHallNumber(int hallNumber){
        this.hallNumber = hallNumber;
    }



}
