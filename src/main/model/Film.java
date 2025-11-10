package main.model;

import java.time.LocalDate;

/**
 * @author lucio
 */
public class Film {
    private String titolo;
    private String regista;
    private int anno;
    private Genere genere;
    private int valutazione;
    private StatoVisione stato;

    public Film(Builder builder){
        titolo = builder.titolo; regista = builder.regista; anno = builder.anno;
        genere = builder.genere; valutazione = builder.valutazione; stato = builder.stato;
    }

    public String getTitolo() { return titolo; }

    public String getRegista() { return regista; }

    public int getAnno() { return anno; }

    public Genere getGenere() { return genere; }

    public int getValutazione() { return valutazione; }

    public StatoVisione getStato() { return stato; }

    public void setTitolo(String titolo) { this.titolo = titolo; }

    public void setRegista(String regista) { this.regista = regista; }

    public void setAnno(int anno) { this.anno = anno; }

    public void setGenere(Genere genere) { this.genere = genere; }

    public void setValutazione(int valutazione) { this.valutazione = valutazione; }

    public void setStato(StatoVisione stato) { this.stato = stato; }

    @Override
    public String toString() {
        return String.format("Film[titolo=%s, regista=%s, anno=%d, genere=%s, valutazione=%d, stato=%s]",
                titolo, regista, anno, genere, valutazione, stato);
    }

    public static class Builder{
        private final String titolo;
        private final String regista;
        private final int anno;

        private Genere genere = Genere.ALTRO;
        private int valutazione = 3;
        private StatoVisione stato = StatoVisione.DA_VEDERE;

        public Builder(String titolo, String regista, int anno){
            if(titolo == null || titolo.isEmpty())
                throw new IllegalArgumentException("Il titolo del film è obbligatorio");
            this.titolo = titolo;

            if(regista == null || regista.isEmpty())
                throw new IllegalArgumentException("Il regista del film è obbligatorio");
            this.regista = regista;

            if(anno < 1930 || anno > LocalDate.now().getYear())
                throw new IllegalArgumentException("Anno non valido: deve essere compreso tra 1930 e " + LocalDate.now().getYear());
            this.anno = anno;
        }

        public Builder genere(Genere genere){
            if(genere != null) this.genere = genere;
            return this;
        }

        public Builder valutazione(int valutazione){
            if(valutazione < 1 || valutazione > 5)
                System.out.println("Attenzione: valutazione fuori dai limiti");
            else
                this.valutazione = valutazione;
            return this;
        }

        public Builder stato(StatoVisione stato){
            if(stato != null) this.stato = stato;
            return this;
        }

        public Film build(){
            return new Film(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || !(o instanceof Film))
            return false;
        Film f = (Film) o;

        return this.titolo.equals(f.titolo) &&
                this.regista.equals(f.regista);
    }
}
