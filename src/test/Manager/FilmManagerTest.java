package test.Manager;

import main.manager.FilmManager;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class FilmManagerTest {
    private FilmManager manager;

    @BeforeEach
    public void setUpAll(){
        manager = new FilmManager();
    }

    @Test
    void testAddFilm() {
        Film f = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        manager.addFilm(f);

        assertFalse(manager.getFilms().isEmpty());
        assertTrue(manager.getFilms().contains(f));
    }

    @Test
    void testRemoveFilm() {
        Film f = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        manager.addFilm(f);

        manager.removeFilm(f);

        assertTrue(manager.getFilms().isEmpty());
        assertFalse(manager.getFilms().contains(f));
    }

    @Test
    void testUpdateFilm() {
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        manager.addFilm(f1);

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        manager.updateFilm(f2, f1);

        assertFalse(manager.getFilms().contains(f1));
        assertTrue(manager.getFilms().contains(f2));
    }

    @AfterEach
    public void cleanDb(){
        for(Film f : List.copyOf(manager.getFilms()))
            manager.removeFilm(f);
    }
}