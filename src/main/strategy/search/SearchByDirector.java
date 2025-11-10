package main.strategy.search;

import main.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucio
 */
public class SearchByDirector implements SearchStrategy{
    @Override
    public List<Film> search(List<Film> films, String value) {
        if(value == null || value.isEmpty()) return films;

        List<Film> result = new ArrayList<>();
        for(Film f : films)
            if(f.getRegista().toLowerCase().contains(value.toLowerCase()))
                result.add(f);
        return result;
    }
}
