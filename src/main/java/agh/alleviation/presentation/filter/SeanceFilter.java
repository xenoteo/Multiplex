package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

/**
 * An interface for seance filters. Used to generify filtering.
 *
 * @author Anna Nosek
 */
public interface SeanceFilter {

    /**
     * Applies a filter.
     *
     * @param seance the seance
     * @return whether seance is appropriate for the filter
     */
    boolean apply(Seance seance);
}
