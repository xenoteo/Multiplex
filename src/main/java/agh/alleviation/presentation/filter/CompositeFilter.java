package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite filter for seances. Provides adding and removing specific filters.
 * Applies all of the gathered filters on an input. Uses composite pattern.
 *
 * @author Anna Nosek
 */
public class CompositeFilter implements SeanceFilter {

    /**
     * The list of seance filters.
     */
    private List<SeanceFilter> filters;

    /**
     * Instantiates a composite filter.
     */
    public CompositeFilter(){
        this.filters = new ArrayList<>();
    }

    /**
     * Adds a filter.
     *
     * @param filter the filter
     */
    public void addFilter(SeanceFilter filter){
        removeFilter(filter);
        filters.add(filter);
    }

    /**
     * Removes a filter.
     *
     * @param filter the fiter
     */
    public void removeFilter(SeanceFilter filter){
        filters.remove(filter);
    }

    @Override
    public boolean apply(Seance seance) {
        for(SeanceFilter filter: filters){
            if(!filter.apply(seance)) return false;
        }
        return true;
    }

    /**
     * Clears filters.
     */
    public void clearFilters(){
        filters.clear();
    }
}
