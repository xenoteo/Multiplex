package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.util.Locale;

/**
 * Date filter provides filtering a seance by movie title.
 * If the assigned string is a substring of provided movie's name, returns true.
 * Should be used as a part of composite filter.
 *
 * @author Anna Nosek
 *
 */

public class MovieFilter implements SeanceFilter {

    private String movieName;

    public MovieFilter(String movieName){
        this.movieName = movieName;
    }


    @Override
    public boolean apply(Seance seance) {
        String name = seance.getMovie().getName().toLowerCase();
        return name.contains(movieName);
    }

    public void setMovieName(String movieName){
        this.movieName = movieName.toLowerCase();
    }


}
