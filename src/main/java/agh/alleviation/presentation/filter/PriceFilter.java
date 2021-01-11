package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

/**
 * Date filter provides filtering a seance by maximum price.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 *
 */

public class PriceFilter implements SeanceFilter {

    private float maxPrice;

    public PriceFilter(int maxPrice){
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(Seance seance) {
        return seance.getPrice() <= maxPrice;
    }

    public void setMaxPrice(float maxPrice){
        this.maxPrice = maxPrice;
    }

}
