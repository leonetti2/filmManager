package test.Command;

import main.command.Command;
import main.command.DeleteFilmCommand;
import main.command.UpdateFilmCommand;
import main.manager.FilmManager;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class UpdateFilmCommandTest {
    private FilmManager manager;
    private Film test;

    @BeforeEach
    public void setUpAll(){
        manager = new FilmManager();
        test = new Film.Builder("Tre uomini e una gamba", "Vanzina", 2014)
                .genere(Genere.COMMEDIA)
                .stato(StatoVisione.VISTO)
                .valutazione(5)
                .build();
        manager.addFilm(test);
    }

    @Test
    public void testExecuteUpdateFilm(){
        Film testM = new Film.Builder("Tre uomini e una gamba", "Vanzina", 2014)
                .genere(Genere.COMMEDIA)
                .stato(StatoVisione.VISTO)
                .valutazione(1)
                .build();
        Command command = new UpdateFilmCommand(manager, test, testM);
        command.execute();

        assertEquals(1, manager.getFilms().size());
        assertTrue(manager.getFilms().getFirst().equals(test));
        assertEquals(test.getValutazione(), manager.getFilms().getFirst().getValutazione());
    }

    @AfterEach
    public void cleanDb(){
        for(Film f : List.copyOf(manager.getFilms()))
            new DeleteFilmCommand(manager, f).execute();
    }

}