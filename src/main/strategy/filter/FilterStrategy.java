package main.strategy.filter;

import main.model.Film;

import java.util.List;

/**
 * @author lucio
 */
public interface FilterStrategy {
    List<Film> filter(List<Film> films, String value);
}
