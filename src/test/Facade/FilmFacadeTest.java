package test.Facade;

import main.command.DeleteFilmCommand;
import main.facade.FilmFacade;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.filter.FilterByGenre;
import main.strategy.search.SearchByTitle;
import main.strategy.sort.SortByRating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class FilmFacadeTest {
    private FilmFacade facade;

    @BeforeEach
    public void setUpAll(){
        facade = new FilmFacade();
    }

    @Test
    void testAddFilm() {
        Film f = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f);

        assertTrue(facade.getFilmManager().getFilms().size() == 1);
    }

    @Test
    void testUpdateFilm() {
        Film f = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f);

        Film fm = new Film.Builder("Spiderman", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        facade.updateFilm(fm, f);
        assertFalse(facade.getFilmManager().getFilms().getFirst().getTitolo().equals("Inception"));
        assertTrue(facade.getFilmManager().getFilms().getFirst().getTitolo().equals("Spiderman"));
    }

    @Test
    void testDeleteFilm() {
        Film f = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f);

        facade.deleteFilm(f);

        assertTrue(facade.getFilmManager().getFilms().isEmpty());
    }

    @Test
    void testGetFilteredFilms() {
        facade.setFilterStrategy(new FilterByGenre());
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f1);

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f2);

        List<Film> result = facade.getFilteredFilms("FANTASCIENZA");

        assertFalse(result.size() == 2);
        assertTrue(result.getFirst().getTitolo().equals("Star Wars"));
    }

    @Test
    void testGetSortedFilms() {
        facade.setSortStrategy(new SortByRating());

        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f1);

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(2)
                .build();
        facade.addFilm(f2);

        Film f3 = new Film.Builder("Spiderman", "Raimi", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(4)
                .build();
        facade.addFilm(f3);

        List<Film> result = facade.getSortedFilms();

        assertTrue(result.size() == 3);
        assertEquals("Star Wars", result.getFirst().getTitolo());
        assertEquals("Inception", result.getLast().getTitolo());

    }

    @Test
    void testGetSearchedFilms() {
        facade.setSearchStrategy(new SearchByTitle());

        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f1);

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        facade.addFilm(f2);

        List<Film> result = facade.getSearchedFilms("Star Wars");

        assertTrue(result.size() == 1);
        assertEquals("Star Wars", result.getFirst().getTitolo());
    }

    @AfterEach
    public void cleanDb(){
        for(Film film : List.copyOf(facade.getFilmManager().getFilms()))
            facade.deleteFilm(film);
    }
}