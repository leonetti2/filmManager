package test.Strategy.Filter;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.filter.FilterByStatus;
import main.strategy.filter.FilterStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class FilterByStatusTest {
    private final FilterStrategy filter = new FilterByStatus();

    @Test
    public void testFilterWithMatchingStatus(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.DA_VEDERE)
                .build();

        List<Film> result = filter.filter(List.of(f1, f2), StatoVisione.VISTO.name());

        assertEquals(1, result.size());
        assertEquals(StatoVisione.VISTO, result.getFirst().getStato());
    }

    @Test
    public void testFilterWithNoMatchingStatus(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        List<Film> result = filter.filter(List.of(f1), StatoVisione.DA_VEDERE.name());

        assertTrue(result.isEmpty());
    }

}