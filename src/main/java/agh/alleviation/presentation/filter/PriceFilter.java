package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

public class PriceFilter implements SeanceFilter {

    private float maxPrice;

    @Override
    public boolean apply(Seance seance) {
        return seance.getPrice() <= maxPrice;
    }

    public void setMaxPrice(float maxPrice){
        this.maxPrice = maxPrice;
    }

}
