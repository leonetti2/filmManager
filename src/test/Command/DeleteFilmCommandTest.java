package test.Command;

import main.command.Command;
import main.command.DeleteFilmCommand;
import main.manager.FilmManager;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lucio
 */
class DeleteFilmCommandTest {
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
    public void testExecuteDeleteFilm(){
        Command command = new DeleteFilmCommand(manager, test);
        command.execute();

        assertTrue(manager.getFilms().isEmpty());
    }

}