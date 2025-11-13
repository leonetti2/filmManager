package main.singleton;

import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lucio
 */
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private final static String DEFAULT_DB = "jdbc:sqlite:database/videoteca.db";

    private DBConnection(){
        try {
            Class.forName("org.sqlite.JDBC");

            new File("database").mkdirs();

            String dbUrl = System.getProperty("db.url");
            if(dbUrl == null || dbUrl.isEmpty())
                dbUrl = System.getenv("DB_URL");
            if(dbUrl == null || dbUrl.isEmpty())
                dbUrl = DEFAULT_DB;

            connection = DriverManager.getConnection(dbUrl);
            System.out.println("Connessione SQLite stabilita con successo: " + DEFAULT_DB);
            createTableIfNotExists();
        }catch (ClassNotFoundException e){
            System.err.println("Driver SQLite non trovato: " + e.getMessage());
        }catch (SQLException e){
            System.err.println("Errore di connessione SQLite: " + e.getMessage());
        }
    }

    public static synchronized DBConnection getInstance(){
        if(instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    private void createTableIfNotExists() throws SQLException{
        String query = """
                CREATE TABLE IF NOT EXISTS film(
                    titolo TEXT NOT NULL,
                    regista TEXT NOT NULL,
                    anno INTEGER,
                    genere TEXT NOT NULL,
                    valutazione INTEGER CHECK(valutazione BETWEEN 1 AND 5),
                    stato TEXT NOT NULL,
                    PRIMARY KEY (titolo, regista)
                    );
                   \s""";
        try(Statement stmt = connection.createStatement()){
            stmt.execute(query);
        }
    }

    public void addFilm(Film film){
        String query = "INSERT OR IGNORE INTO film (titolo, regista, anno, genere, valutazione, stato) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, film.getTitolo());
            pstmt.setString(2, film.getRegista());
            pstmt.setInt(3, film.getAnno());
            pstmt.setString(4, film.getGenere().name());
            pstmt.setInt(5, film.getValutazione());
            pstmt.setString(6, film.getStato().name());
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Errore nell'inserimento del film: " + e.getMessage());
        }
    }

    public List<Film> getAllFilms(){
        List<Film> films = new ArrayList<>();
        String query = "SELECT titolo, regista, anno, genere, valutazione, stato FROM film";

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            while(rs.next()){
                Film film = new Film.Builder(
                        rs.getString("titolo"),
                        rs.getString("regista"),
                        rs.getInt("anno"))
                        .genere(Genere.valueOf(rs.getString("genere")))
                        .valutazione(rs.getInt("valutazione"))
                        .stato(StatoVisione.valueOf(rs.getString("stato")))
                        .build();
                films.add(film);
            }
        }catch (SQLException e){
            System.err.println("Errore nella lettura dei film: " + e.getMessage());
        }
        return films;
    }

    public boolean updateFilm(Film film, Film oldFilm){
        if(!film.getTitolo().equalsIgnoreCase(oldFilm.getTitolo()) ||
           !film.getRegista().equalsIgnoreCase(oldFilm.getRegista())){
            return false;
        }

        String query = """
                UPDATE film 
                SET titolo = ?, regista = ?, anno = ?, genere = ?, valutazione = ?, stato = ?
                WHERE titolo = ? AND regista = ?
                """;

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, film.getTitolo());
            pstmt.setString(2, film.getRegista());
            pstmt.setInt(3, film.getAnno());
            pstmt.setString(4, film.getGenere().name());
            pstmt.setInt(5, film.getValutazione());
            pstmt.setString(6, film.getStato().name());
            pstmt.setString(7, oldFilm.getTitolo());
            pstmt.setString(8, oldFilm.getRegista());

            int rows = pstmt.executeUpdate();
            if(rows > 0){
                return true;
            }else {
                System.err.println("Nessun film trovato di " + oldFilm.getRegista() + " con titolo: " + oldFilm.getTitolo());
            }
        }catch (SQLException e){
            System.err.println("Errore durante l'aggiornamento del film: " + e.getMessage());
        }
        return false;
    }

    public void deleteFilm(Film film){
        String query = "DELETE FROM film WHERE titolo = ? AND regista = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, film.getTitolo());
            pstmt.setString(2, film.getRegista());
            int rows = pstmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Errore durante l'eliminazione del film: " + e.getMessage());
        }
    }

    public void closeConnection(){
        if(connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                System.err.println("Errore durante la chiusura della connessione SQLite: " + e.getMessage());
            }
        }
    }
}
