package test.Strategy.Sort;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.strategy.sort.SortByTitle;
import main.strategy.sort.SortStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class SortByTitleTest {
    private final SortStrategy sortStrategy = new SortByTitle();

    @Test
    public void testSortByTitle(){
        Film f1 = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.AZIONE)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();

        Film f2 = new Film.Builder("Star Wars", "Lucas", 1977)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.DA_VEDERE)
                .build();

        Film f3 = new Film.Builder("La vita è bella", "Benigni", 1997)
                .genere(Genere.DRAMMATICO)
                .stato(StatoVisione.IN_VISIONE)
                .valutazione(3)
                .build();

        List<Film> result = sortStrategy.sort(List.of(f1, f2, f3));

        assertEquals("Inception", result.getFirst().getTitolo());
        assertTrue(result.size() == 3);
        assertEquals("Star Wars", result.getLast().getTitolo());
    }

}