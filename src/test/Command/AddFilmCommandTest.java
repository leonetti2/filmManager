package test.Command;

import main.command.AddFilmCommand;
import main.command.Command;
import main.command.DeleteFilmCommand;
import main.manager.FilmManager;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class AddFilmCommandTest {
    private FilmManager manager;
    private Film test;

    @BeforeEach
    void setUpAll(){
        manager = new FilmManager();
        test = new Film.Builder("Inception", "Nolan", 2010)
                .genere(Genere.FANTASCIENZA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
    }

    @Test
    void testExecuteAddFilm(){
        Command command = new AddFilmCommand(manager, test);
        command.execute();

        assertEquals(1, manager.getFilms().size());
        assertTrue(manager.getFilms().contains(test));
    }

    @AfterEach
    public void cleanDb(){
        for(Film f : List.copyOf(manager.getFilms()))
            new DeleteFilmCommand(manager, f).execute();
    }

}