package main.manager;

import main.observer.FilmSubject;
import main.model.Film;
import main.singleton.DBConnection;

import java.util.Collections;
import java.util.List;

/**
 * @author lucio
 */
public class FilmManager extends FilmSubject {
    private DBConnection db;
    private List<Film> films;

    public FilmManager(){
        db = DBConnection.getInstance();
        films = loadFilmFromDB();
    }

    public FilmManager(List<Film> films){
        this.films = films;
    }

    private List<Film> loadFilmFromDB(){
        return db.getAllFilms();
    }

    public List<Film> getFilms() {
        return Collections.unmodifiableList(films);
    }

    public void addFilm(Film film){
        if(film == null){
            System.err.println("Impossibile aggiungere film: film nullo");
            return;
        }

        if(films.contains(film)){
            System.out.println("Il film \"" + film.getTitolo() + "\" di " + film.getRegista() + " è già presente!");
            return;
        }
        db.addFilm(film);
        films.add(film);
        notifyObservers();
    }

    public void removeFilm(Film film){
        if(film == null){
            System.err.println("Impossibile rimuovere film: film nullo");
            return;
        }

        db.deleteFilm(film);
        films.remove(film);
        notifyObservers();
    }

    public void updateFilm(Film film, Film oldFilm) {
        if (film == null || oldFilm == null) {
            System.err.println("Impossibile aggiornare: film nullo");
            return;
        }

        for (Film existing : films) {
            if (!existing.equals(oldFilm) &&
                    existing.getTitolo().equalsIgnoreCase(film.getTitolo()) &&
                    existing.getRegista().equalsIgnoreCase(film.getRegista())) {
                System.err.println("Aggiornamento non consentito: esiste già un film con stesso titolo e regista (" +
                        film.getTitolo() + " - " + film.getRegista() + ")");
                return;
            }
        }

        boolean updated = db.updateFilm(film, oldFilm);

        if (updated) {
            for (int i = 0; i < films.size(); i++) {
                if (films.get(i).equals(oldFilm)) {
                    films.set(i, film);
                    break;
                }
            }
            notifyObservers();
        } else {
            System.err.println("Errore nell'aggiornamento del film nel DB: " + film.getTitolo());
        }
    }

}
