package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;


/**
 *
 * An interface for seance filters. Used to generify filtering.
 *
 * @author Anna Nosek
 */
public interface SeanceFilter {

    boolean apply(Seance seance);
}
