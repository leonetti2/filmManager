package main.strategy.search;

import java.util.List;
import main.model.Film;

/**
 * @author lucio
 */
public interface SearchStrategy {
    List<Film> search(List<Film> films, String value);
}
