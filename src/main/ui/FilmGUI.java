package main.ui;

import main.facade.FilmFacade;
import main.model.Film;
import main.model.Genere;
import main.model.StatoVisione;
import main.observer.FilmObserver;
import main.strategy.filter.FilterByGenre;
import main.strategy.filter.FilterByStatus;
import main.strategy.search.SearchByDirector;
import main.strategy.search.SearchByTitle;
import main.strategy.sort.SortByRating;
import main.strategy.sort.SortByTitle;
import main.strategy.sort.SortByYear;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * @author lucio
 */
public class FilmGUI extends JFrame implements FilmObserver {
    private final FilmFacade facade;
    private JTable filmTable;
    private JFrame frame;

    public FilmGUI(){
        facade = new FilmFacade();
        facade.getFilmManager().attach(this);

        initUI();
    }

    @Override
    public void update() {
        List<Film> currentFilms = facade.getFilmManager().getFilms();
        updateFilmListView(currentFilms);
    }

    private void initUI(){
        frame = new JFrame("Film Manager");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createTopPanel(), BorderLayout.NORTH);
        frame.add(createFilmTable(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createTopPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menù");
        addMenuItems(menu);
        menuBar.add(menu);
        panel.add(menuBar, BorderLayout.WEST);

        JTextField searchField = new JTextField(25);
        JComboBox<String> searchType = new JComboBox<>(new String[]{"Titolo", "Regista"});
        JButton searchButton = new JButton("Cerca");
        searchButton.addActionListener(e -> {
            String text = searchField.getText().trim();
            if(text.isEmpty()){
                update();
                return;
            }

            if("Titolo".equals(searchType.getSelectedItem()))
                facade.setSearchStrategy(new SearchByTitle());
            else if("Regista".equals(searchType.getSelectedItem()))
                facade.setSearchStrategy(new SearchByDirector());

            List<Film> result = facade.getSearchedFilms(text);
            updateFilmListView(result);
        });


        JPanel searchPanel = new JPanel();
        searchPanel.add(searchType);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.CENTER);
        //ICONA LOGO

        return panel;
    }

    private void addMenuItems(JMenu menu){
        JMenuItem addItem = new JMenuItem("Aggiungi film");
        addItem.addActionListener(e -> showAddFilmDialog());
        menu.add(addItem);

        JMenuItem deleteItem = new JMenuItem("Elimina film");
        deleteItem.addActionListener(e -> showDeleteFilmDialog());
        menu.add(deleteItem);

        JMenuItem modifyItem = new JMenuItem("Modifica film");
        modifyItem.addActionListener(e -> showModifyFilmDialog());
        menu.add(modifyItem);

        JMenuItem sortItem = new JMenuItem("Ordina film");
        sortItem.addActionListener(e -> showSortFilmsDialog());
        menu.add(sortItem);

        JMenuItem filterItem = new JMenuItem("Filtra film");
        filterItem.addActionListener(e -> showFilterFilmsDialog());
        menu.add(filterItem);
    }

    private JScrollPane createFilmTable(){
        String[] columns = {"Titolo", "Regista", "Anno", "Genere", "Stato", "Valutazione"};
        Object[][] data = getFilmsFromFacade();

        filmTable = new JTable(data, columns);
        return new JScrollPane(filmTable);
    }

    private Object[][] getFilmsFromFacade() {
        List<Film> films = facade.getAllFilms();
        Object[][] data = new Object[films.size()][6];
        int i = 0;
        for(Film f : films){
            data[i++] = new Object[]{
                    f.getTitolo(),
                    f.getRegista(),
                    f.getAnno(),
                    f.getGenere(),
                    f.getStato(),
                    f.getValutazione()
            };
        }
        return data;
    }


    private void showAddFilmDialog(){
        JDialog dialog = new JDialog(frame, "Aggiungi Film", true);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));

        JTextField titoloField = new JTextField();
        JTextField registaField = new JTextField();
        JTextField annoField = new JTextField();
        JComboBox<Genere> genereBox = new JComboBox<>(Genere.values());
        genereBox.setSelectedItem(Genere.ALTRO);
        JComboBox<StatoVisione> statoBox = new JComboBox<>(StatoVisione.values());
        statoBox.setSelectedItem(StatoVisione.DA_VEDERE);
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingBox.setSelectedItem(3);

        dialog.add(new JLabel("Titolo:"));
        dialog.add(titoloField);
        dialog.add(new JLabel("Regista:"));
        dialog.add(registaField);
        dialog.add(new JLabel("Anno:"));
        dialog.add(annoField);
        dialog.add(new JLabel("Genere:"));
        dialog.add(genereBox);
        dialog.add(new JLabel("Stato visione:"));
        dialog.add(statoBox);
        dialog.add(new JLabel("Valutazione:"));
        dialog.add(ratingBox);

        JButton ok = new JButton("Aggiungi");
        ok.addActionListener(e -> {
            try{
                Film f = new Film.Builder(titoloField.getText().trim().replaceAll("\\s+", " "), registaField.getText().trim().replaceAll("\\s+", " "), Integer.parseInt(annoField.getText()))
                        .genere((Genere) genereBox.getSelectedItem())
                        .stato((StatoVisione) statoBox.getSelectedItem())
                        .valutazione((Integer) ratingBox.getSelectedItem())
                        .build();
                facade.addFilm(f);
                dialog.dispose();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(dialog, "Errore nei dati inseriti", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel());
        dialog.add(ok);
        dialog.pack();
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showDeleteFilmDialog(){
        int selectedRow = filmTable.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(frame, "Seleziona un film da eliminare", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Film film = facade.getAllFilms().get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(frame, "Eliminare \"" + film.getTitolo() + "\"?", "Conferma", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) facade.deleteFilm(film);
    }

    private void showModifyFilmDialog(){
        int selectedRow = filmTable.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(frame, "Seleziona un film da modificare", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Film oldFilm = facade.getAllFilms().get(selectedRow);
        JDialog dialog = new JDialog(frame, "Modifica Film", true);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));

        JTextField titoloField = new JTextField(oldFilm.getTitolo());
        JTextField registaField = new JTextField(oldFilm.getRegista());
        JTextField annoField = new JTextField(Integer.toString(oldFilm.getAnno()));
        JComboBox<Genere> genereBox = new JComboBox<>(Genere.values());
        genereBox.setSelectedItem(oldFilm.getGenere());
        JComboBox<StatoVisione> statoBox = new JComboBox<>(StatoVisione.values());
        statoBox.setSelectedItem(oldFilm.getStato());
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingBox.setSelectedItem(oldFilm.getValutazione());

        dialog.add(new JLabel("Titolo:"));
        dialog.add(titoloField);
        dialog.add(new JLabel("Regista:"));
        dialog.add(registaField);
        dialog.add(new JLabel("Anno:"));
        dialog.add(annoField);
        dialog.add(new JLabel("Genere:"));
        dialog.add(genereBox);
        dialog.add(new JLabel("Stato visione:"));
        dialog.add(statoBox);
        dialog.add(new JLabel("Valutazione:"));
        dialog.add(ratingBox);

        JButton ok = new JButton("Salva modifiche");
        ok.addActionListener(e -> {
            try {
                Film f = new Film.Builder(titoloField.getText().trim().replaceAll("\\s+", " "), registaField.getText().trim().replaceAll("\\s+", " "), Integer.parseInt(annoField.getText()))
                        .genere((Genere) genereBox.getSelectedItem())
                        .stato((StatoVisione) statoBox.getSelectedItem())
                        .valutazione((Integer) ratingBox.getSelectedItem())
                        .build();
                facade.updateFilm(f, oldFilm);
                dialog.dispose();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(dialog, "Errore nei dati modificati", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel());
        dialog.add(ok);
        dialog.pack();
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showSortFilmsDialog(){
        JDialog dialog = new JDialog(frame, "Ordina Film", true);
        dialog.setLayout(new GridLayout(0, 1, 5, 5));

        JRadioButton byTitle = new JRadioButton("Per Titolo");
        JRadioButton byYear = new JRadioButton("Per Anno");
        JRadioButton byRating = new JRadioButton("Per Valutazione");

        dialog.add(byTitle);
        dialog.add(byYear);
        dialog.add(byRating);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            if(byTitle.isSelected())
                facade.setSortStrategy(new SortByTitle());
            else if(byYear.isSelected())
                facade.setSortStrategy(new SortByYear());
            else if(byRating.isSelected())
                facade.setSortStrategy(new SortByRating());
            else {
                JOptionPane.showMessageDialog(dialog, "Nessuna opzione selezionata");
                return;
            }
            updateFilmListView(facade.getSortedFilms());
            dialog.dispose();
        });

        dialog.add(ok);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }


    private void showFilterFilmsDialog(){
        JDialog dialog = new JDialog(frame, "Filtra Film", true);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Genere", "Stato"});
        JComboBox<String> valueBox = new JComboBox<>();

        typeBox.addActionListener(e -> {
            valueBox.removeAllItems();
            if(typeBox.getSelectedItem().equals("Genere")){
                for(Genere g : Genere.values())
                    valueBox.addItem(g.name());
            }else {
                for(StatoVisione s : StatoVisione.values()){
                    valueBox.addItem(s.name());
                }
            }
        });
        typeBox.setSelectedIndex(0);

        JButton ok = new JButton("Applica");
        ok.addActionListener(e -> {
            String type = (String) typeBox.getSelectedItem();
            String value = (String) valueBox.getSelectedItem();

            if(type.equals("Genere")){
                facade.setFilterStrategy(new FilterByGenre());
            }else {
                facade.setFilterStrategy(new FilterByStatus());
            }
            updateFilmListView(facade.getFilteredFilms(value));
            dialog.dispose();
        });

        dialog.add(new JLabel("Tipo filtro:"));
        dialog.add(typeBox);
        dialog.add(new JLabel("Valore:"));
        dialog.add(valueBox);
        dialog.add(ok);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void updateFilmListView(List<Film> films) {
        String[] columns = {"Titolo", "Regista", "Anno", "Genere", "Stato", "Valutazione"};
        Object[][] data = new Object[films.size()][6];
        int i = 0;
        for(Film f : films){
            data[i++] = new Object[]{
                    f.getTitolo(),
                    f.getRegista(),
                    f.getAnno(),
                    f.getGenere(),
                    f.getStato(),
                    f.getValutazione()
            };
        }
        filmTable.setModel(new DefaultTableModel(data, columns));
        frame.revalidate();
        frame.repaint();
    }

}
