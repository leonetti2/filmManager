package test.Strategy.Filter;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.filter.FilterByGenre;
import main.strategy.filter.FilterStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class FilterByGenreTest {
    private final FilterStrategy filter = new FilterByGenre();

    @Test
    public void testFilterWithMatchingGenre(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.DA_VEDERE)
                .build();

        List<Film> result = filter.filter(List.of(f1, f2), Genere.AZIONE.name());

        assertEquals(1, result.size());
        assertEquals(Genere.AZIONE, result.getFirst().getGenere());
    }

    @Test
    public void testFilterWithNoMatchingGenre(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        List<Film> result = filter.filter(List.of(f1), Genere.FANTASCIENZA.name());

        assertTrue(result.isEmpty());
    }

}