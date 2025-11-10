package main.command;

import main.manager.FilmManager;
import main.model.Film;

/**
 * @author lucio
 */
public class UpdateFilmCommand implements Command{
    private final FilmManager filmManager;
    private final Film film;
    private final Film oldFilm;

    public UpdateFilmCommand(FilmManager filmManager, Film film, Film oldFilm){
        this.filmManager = filmManager;
        this.film = film;
        this.oldFilm = oldFilm;
    }

    @Override
    public void execute() {
        filmManager.updateFilm(film, oldFilm);
    }
}
