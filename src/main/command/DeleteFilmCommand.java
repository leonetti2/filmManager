package main.command;

import main.manager.FilmManager;
import main.model.Film;

/**
 * @author lucio
 */
public class DeleteFilmCommand implements Command{
    private final FilmManager filmManager;
    private final Film film;

    public DeleteFilmCommand(FilmManager filmManager, Film film){
        this.filmManager = filmManager;
        this.film = film;
    }

    @Override
    public void execute() {
        filmManager.removeFilm(film);
        System.out.println("Film eliminato: " + film.getTitolo());
    }
}
