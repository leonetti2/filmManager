package main.strategy.sort;

import main.model.Film;

import java.util.List;

/**
 * @author lucio
 */
public interface SortStrategy {
    List<Film> sort(List<Film> films);
}
