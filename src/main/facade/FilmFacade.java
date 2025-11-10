package main.facade;

import main.command.AddFilmCommand;
import main.command.CommandInvoker;
import main.command.DeleteFilmCommand;
import main.command.UpdateFilmCommand;
import main.manager.FilmManager;
import main.model.Film;
import main.strategy.filter.FilterByGenre;
import main.strategy.filter.FilterStrategy;
import main.strategy.search.SearchByTitle;
import main.strategy.search.SearchStrategy;
import main.strategy.sort.SortByTitle;
import main.strategy.sort.SortStrategy;

import java.util.List;

/**
 * @author lucio
 */
public class FilmFacade {
    private final FilmManager filmManager;
    private final CommandInvoker invoker;

    private FilterStrategy filterStrategy;
    private SortStrategy sortStrategy;
    private SearchStrategy searchStrategy;

    public FilmFacade(){
        filmManager = new FilmManager();
        invoker = new CommandInvoker();
        searchStrategy = new SearchByTitle();
        sortStrategy = new SortByTitle();
        filterStrategy = new FilterByGenre();
    }

    public void setFilterStrategy(FilterStrategy strategy){
        filterStrategy = strategy;
    }

    public void setSortStrategy(SortStrategy strategy){
        sortStrategy = strategy;
    }

    public void setSearchStrategy(SearchStrategy strategy){
        searchStrategy = strategy;
    }

    public void addFilm(Film film){
        invoker.executeCommand(new AddFilmCommand(filmManager, film));
    }

    public void updateFilm(Film film, Film oldFilm){
        invoker.executeCommand(new UpdateFilmCommand(filmManager, film, oldFilm));
    }

    public void deleteFilm(Film film){
        invoker.executeCommand(new DeleteFilmCommand(filmManager, film));
    }

    public List<Film> getFilteredFilms(String value){
        return filterStrategy.filter(filmManager.getFilms(), value);
    }

    public List<Film> getSortedFilms(){
        return sortStrategy.sort(filmManager.getFilms());
    }

    public List<Film> getSearchedFilms(String value){
        return searchStrategy.search(filmManager.getFilms(), value);
    }

    public List<Film> getAllFilms(){
        return filmManager.getFilms();
    }

    public FilmManager getFilmManager(){
        return filmManager;
    }
}
