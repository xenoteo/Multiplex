package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

/**
 * Date filter provides filtering a seance by maximum price.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 */
public class PriceFilter implements SeanceFilter {

    /**
     * The max price.
     */
    private float maxPrice;

    /**
     * Instantiates a price filter.
     *
     * @param maxPrice the max price
     */
    public PriceFilter(int maxPrice){
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean apply(Seance seance) {
        return seance.getPrice() <= maxPrice;
    }

    /**
     * Sets the max price.
     *
     * @param maxPrice the max price
     */
    public void setMaxPrice(float maxPrice){
        this.maxPrice = maxPrice;
    }

}
