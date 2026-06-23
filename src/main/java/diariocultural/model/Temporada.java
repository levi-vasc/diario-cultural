package diariocultural.model;

import org.json.JSONObject;

public class Temporada {
    private int numero;
    private int ano;
    private int numEpisodios;
    private int nota = 0;
    private String review = "";

    public void setNota(int nota) {
        if (nota >= 1 && nota <= 5) {
            this.nota = nota;
        }
    }

    public int getNota() {
        return nota;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public Temporada(int numero, int ano, int numEpisodios) {
        this.numero = numero;
        this.ano = ano;
        this.numEpisodios = numEpisodios;
    }

    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public int getNumEpisodios() {
        return numEpisodios;
    }

    public JSONObject salvarTemp() {
        JSONObject jsonTemporada = new JSONObject();
        jsonTemporada.put("numero", numero);
        jsonTemporada.put("ano", ano);
        jsonTemporada.put("numEpisodios", numEpisodios);
        jsonTemporada.put("nota", nota);
        jsonTemporada.put("review", review);
        return jsonTemporada;
    }

    public static Temporada lerTemp(JSONObject jsonTemporada) {
        int numero = jsonTemporada.getInt("numero");
        int ano = jsonTemporada.getInt("ano");
        int numEpisodios = jsonTemporada.getInt("numEpisodios");
        Temporada temporada = new Temporada(numero, ano, numEpisodios);
        temporada.setNota(jsonTemporada.getInt("nota"));
        temporada.setReview(jsonTemporada.getString("review"));
        return temporada;
    }
}