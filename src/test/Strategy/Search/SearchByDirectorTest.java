package test.Strategy.Search;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.search.SearchByDirector;
import main.strategy.search.SearchStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class SearchByDirectorTest {
    private final SearchStrategy searchStrategy = new SearchByDirector();

    @Test
    public void testSearchWithMatchingDirector(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.DA_VEDERE)
                .build();

        List<Film> result = searchStrategy.search(List.of(f1, f2), "Nolan");

        assertEquals(1, result.size());
        assertEquals("Nolan", result.getFirst().getRegista());
    }

    @Test
    public void testSearchWithNoMatchingDirector(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        List<Film> result = searchStrategy.search(List.of(f1), "Sorrentino");

        assertTrue(result.isEmpty());
    }

}