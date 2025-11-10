package main.strategy.search;

import main.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucio
 */
public class SearchByTitle implements SearchStrategy{
    @Override
    public List<Film> search(List<Film> films, String value){
        if(value == null || value.isEmpty()) return films;

        List<Film> result = new ArrayList<>();
        for(Film f : films)
            if(f.getTitolo().toLowerCase().contains(value.toLowerCase()))
                result.add(f);
        return result;
    }
}
