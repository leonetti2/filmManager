package main.strategy.filter;

import main.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucio
 */
public class FilterByStatus implements FilterStrategy{
    @Override
    public List<Film> filter(List<Film> films, String value) {
        if(value == null || value.isEmpty()) return films;

        List<Film> filtered = new ArrayList<>();
        for(Film f : films)
            if(f.getStato().name().equalsIgnoreCase(value))
                filtered.add(f);
        return filtered;
    }
}
