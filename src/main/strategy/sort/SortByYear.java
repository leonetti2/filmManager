package main.strategy.sort;

import main.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author lucio
 */
public class SortByYear implements SortStrategy{
    @Override
    public List<Film> sort(List<Film> films) {
        List<Film> result = new ArrayList<>(films);
        result.sort(new YearComparator());
        return result;
    }

    static class YearComparator implements Comparator<Film>{
        @Override
        public int compare(Film f1, Film f2) {
            return Integer.compare(f1.getAnno(), f2.getAnno());
        }
    }
}
