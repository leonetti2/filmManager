package main.command;


import main.manager.FilmManager;
import main.model.Film;

/**
 * @author lucio
 */
public class AddFilmCommand implements Command{
    private final FilmManager filmManager;
    private final Film film;

    public AddFilmCommand(FilmManager filmManager, Film film){
        this.filmManager = filmManager;
        this.film = film;
    }

    @Override
    public void execute() {
        filmManager.addFilm(film);
        System.out.println("Film aggiunto: " + film.getTitolo());
    }
}
