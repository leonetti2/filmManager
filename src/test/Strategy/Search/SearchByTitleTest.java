package test.Strategy.Search;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.search.SearchByTitle;
import main.strategy.search.SearchStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class SearchByTitleTest {
    private final SearchStrategy searchStrategy = new SearchByTitle();

    @Test
    public void testSearchWithMatchingTitle(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.DA_VEDERE)
                .build();

        List<Film> result = searchStrategy.search(List.of(f1, f2), "Inception");

        assertEquals(1, result.size());
        assertEquals("Inception", result.getFirst().getTitolo());
    }

    @Test
    public void testSearchWithNoMatchingTitle(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        List<Film> result = searchStrategy.search(List.of(f1), "Spiderman");

        assertTrue(result.isEmpty());
    }

}