package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements SeanceFilter {

    private List<SeanceFilter> filters;

    public CompositeFilter(){
        this.filters = new ArrayList<>();
    }

    public void addFilter(SeanceFilter filter){
        removeFilter(filter);
        filters.add(filter);
    }

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

    public void clearFilters(){
        filters.clear();
    }
}
