package diariocultural.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Serie extends Obra {
    private int ano_encerramento;
    private String elenco;
    private String titulo_original;
    private String onde_assistir;
    private List<Temporada> temporadas;
    private int nota;

    public Serie(String titulo, String genero, int ano, int ano_encerramento, String elenco, String titulo_original, String onde_assistir) {
        super(titulo, genero, ano);
        this.ano_encerramento = ano_encerramento;
        this.elenco = elenco;
        this.titulo_original = titulo_original;
        this.onde_assistir = onde_assistir;
        this.temporadas = new ArrayList<>();
    }

    public void adicionarTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    public String getElenco() {
        return elenco;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public int getNumTemporadas() {
        return temporadas.size();
    }

    // diferente das outras classes, o getNota de séries calcula a média das notas das TEMPORADAS daquela série.
    @Override
    public int getNota() {
        if (temporadas.isEmpty()) return 0;

        int soma = 0, cont = 0;
        for (Temporada t : temporadas) {
            if (t.getNota() > 0) {
                soma += t.getNota();
                cont++;
            }
        }

        return (cont > 0) ? Math.round((float) soma / cont) : 0;
    }

    @Override
    public String resumo() {
        int notaMedia = getNota();
        return titulo + " (" + ano + "-" + ano_encerramento + ") | Gênero: " + genero + " | Avaliação: " + (notaMedia > 0 ? notaMedia + "/5" : "Não avaliado") + " | Temporadas: " + temporadas.size();
    }

    @Override
    public String toString() {
        int notaMedia = getNota();
        String info = titulo + " (" + ano + "-" + ano_encerramento + ") | Gênero: " + genero + " | Elenco: " + formatarCampo(elenco) + " | Avaliação: " + (notaMedia > 0 ? notaMedia + "/5" : "Não avaliado") + " | Título original: " + formatarCampo(titulo_original) + " | Onde assistir: " + formatarCampo(onde_assistir);
        for (Temporada t : temporadas) {
            info += "\n\tTemporada " + t.getNumero() + " (" + t.getAno() + ") | Episódios: " + t.getNumEpisodios() + " | Avaliação: " + t.getNota() + " | Review: " + formatarCampo(t.getReview());
        }
        info += "\n";
        return info;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public JSONObject salvarObra() {
        JSONObject jsonSerie = super.salvarObra();
        jsonSerie.put("tipo", "Serie");
        jsonSerie.put("ano_encerramento", ano_encerramento);
        jsonSerie.put("elenco", elenco);
        jsonSerie.put("titulo_original", titulo_original);
        jsonSerie.put("onde_assistir", onde_assistir);

        JSONArray jsonTemporadas = new JSONArray();
        for (Temporada temporada : temporadas) {
            jsonTemporadas.put(temporada.salvarTemp());
        }
        jsonSerie.put("temporadas", jsonTemporadas);
        return jsonSerie;
    }

    public static Serie lerSerie(JSONObject jsonSerie) {
        String titulo = jsonSerie.getString("titulo");
        String genero = jsonSerie.getString("genero");
        int ano = jsonSerie.getInt("ano");
        int ano_encerramento = jsonSerie.getInt("ano_encerramento");
        String elenco = jsonSerie.getString("elenco");
        String titulo_original = jsonSerie.getString("titulo_original");
        String onde_assistir = jsonSerie.getString("onde_assistir");

        Serie serie = new Serie(titulo, genero, ano, ano_encerramento, elenco, titulo_original, onde_assistir);
        serie.setNota(jsonSerie.getInt("nota"));
        serie.setReview(jsonSerie.getString("review"));

        JSONArray jsonTemporadas = jsonSerie.getJSONArray("temporadas");
        for (int i = 0; i < jsonTemporadas.length(); i++) {
            JSONObject jsonTemp = jsonTemporadas.getJSONObject(i);
            Temporada temporada = Temporada.lerTemp(jsonTemp);
            serie.adicionarTemporada(temporada);
        }

        return serie;
    }

    public int getAno_encerramento() { return ano_encerramento; }

    public String getTitulo_original() { return titulo_original; }

    public String getOnde_assistir() {return onde_assistir;}
}
