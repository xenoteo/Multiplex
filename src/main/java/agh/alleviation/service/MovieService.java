package agh.alleviation.service;

import agh.alleviation.model.*;
import agh.alleviation.persistence.GenreRepository;
import agh.alleviation.persistence.MovieRepository;
import agh.alleviation.persistence.TicketRepository;
import agh.alleviation.util.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for manipulating the movie repository.
 *
 * @author Anna Nosek
 */
@Service
@Transactional
public class MovieService extends EntityObjectService<Movie, MovieRepository> {
    private final GenreRepository genreRepository;
    private final TicketRepository ticketRepository;

    /**
     * Instantiates a new Movie service.
     *
     * @param movieRepository the movie repository
     * @param genreRepository the genre repository
     */
    @Autowired
    public MovieService(
        MovieRepository movieRepository, GenreRepository genreRepository, TicketRepository ticketRepository) {
        repository = movieRepository;
        this.genreRepository = genreRepository;
        this.ticketRepository = ticketRepository;
    }

    /**
     * Add movie
     *
     * @param name        the name
     * @param genreName   the genre
     * @param description the description
     * @param director    the director
     * @param actors      the actors
     * @return the movie
     */
    public Movie addMovie(String name, String genreName, String description, String director, String actors) {
        Genre genre = getGenre(genreName);
        Movie movie = new Movie(name, genre, description, director, actors);
        repository.save(movie);
        return movie;
    }

    /**
     * Get genre of given name, if such genre does not exist, create it first
     *
     * @param name the name
     * @return the genre
     */
    public Genre getGenre(String name) {
        Genre genre = genreRepository.findByName(name);
        if (genre == null) genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }

    /**
     * Find movie by name
     *
     * @param name the name
     * @return the movie
     */
    public Movie findMovie(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<EntityObject> update(EntityObject movie) {
        repository.save((Movie) movie);
        movie = repository.findByIdWithSeances(movie.getId());
        return super.update(movie);
    }

    /**
     * Override method to get seances associated with movie
     * Because of lazy loading, they are not loaded at the object creation.
     *
     * @param movie movie to delete
     * @return list of entity objects deleted with movie
     */
    @Override
    public List<EntityObject> delete(EntityObject movie) {
        movie = repository.findByIdWithSeances(movie.getId());
        return super.delete(movie);
    }

    public void rateMovie(Ticket ticket, Rating rating) {

        Movie ratedMovie = ticket.getSeance().getMovie();

        if (!ticket.getIsRated()) {
            if (rating == Rating.POSITIVE) ratedMovie.setLikes(ratedMovie.getLikes() + 1);
            else ratedMovie.setDislikes(ratedMovie.getDislikes() + 1);
            ticket.setIsRated(true);
        } else if (rating != ticket.getIsRatingPositive()) {
            int difference = rating == Rating.POSITIVE ? 1 : -1;
            ratedMovie.setLikes(ratedMovie.getLikes() + difference);
            ratedMovie.setDislikes(ratedMovie.getDislikes() - difference);
        }

        ticket.setIsRatingPositive(rating);

        ticketRepository.save(ticket);
        repository.save(ratedMovie);
    }
}
