package main.strategy.sort;

import main.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author lucio
 */
public class SortByTitle implements SortStrategy{
    @Override
    public List<Film> sort(List<Film> films) {
        List<Film> result = new ArrayList<>(films);
        result.sort(new TitleComparator());
        return result;
    }

    static class TitleComparator implements Comparator<Film>{
        @Override
        public int compare(Film f1, Film f2) {
            return f1.getTitolo().compareTo(f2.getTitolo());
        }
    }
}
