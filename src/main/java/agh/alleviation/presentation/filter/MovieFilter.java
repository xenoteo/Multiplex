package agh.alleviation.presentation.filter;

import agh.alleviation.model.Seance;

import java.util.Locale;

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
