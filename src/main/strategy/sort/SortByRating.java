package main.strategy.sort;

import main.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author lucio
 */
public class SortByRating implements SortStrategy{
    @Override
    public List<Film> sort(List<Film> films) {
        List<Film> result = new ArrayList<>(films);
        result.sort(new RatingComparator());
        return result;
    }

    static class RatingComparator implements Comparator<Film>{
        @Override
        public int compare(Film f1, Film f2) {
            return Integer.compare(f1.getValutazione(), f2.getValutazione());
        }
    }
}
