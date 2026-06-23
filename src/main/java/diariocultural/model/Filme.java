package diariocultural.model;

import org.json.JSONObject;

public class Filme extends Obra {
    private String duracao;
    private String direcao;
    private String roteiro;
    private String elenco;
    private String titulo_original;
    private String onde_assistir;

    public Filme(String titulo, String genero, int ano, String duracao, String direcao, String roteiro, String elenco, String titulo_original, String onde_assistir) {
        super(titulo, genero, ano);
        this.duracao = duracao;
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.titulo_original = titulo_original;
        this.onde_assistir = onde_assistir;
    }

    public String getDiretor() { return direcao; }
    public String getElenco() { return elenco; }
    public String getDuracao() { return duracao; }

    @Override
    public String resumo() {
        return super.toString() + " | Direção: " + formatarCampo(direcao);
    }
    @Override
    public String toString() {
        return super.toString() + " | Direção: " + formatarCampo(direcao) + " | Título original: " + formatarCampo(titulo_original) + " | Duração: " + formatarCampo(duracao) + " | Onde assistir: " + formatarCampo(onde_assistir) + "\n\tRoteiro: " + formatarCampo(roteiro) + " | Elenco: " + formatarCampo(elenco) + "\n\tReview: " + formatarCampo(getReview()) + "\n";
    }

    @Override
    public JSONObject salvarObra() {
        JSONObject jsonFilme = super.salvarObra();
        jsonFilme.put("tipo", "Filme");
        jsonFilme.put("duracao", duracao);
        jsonFilme.put("direcao", direcao);
        jsonFilme.put("roteiro", roteiro);
        jsonFilme.put("elenco", elenco);
        jsonFilme.put("titulo_original", titulo_original);
        jsonFilme.put("onde_assistir", onde_assistir);
        return jsonFilme;
    }

    public static Filme lerFilme(JSONObject jsonFilme) {

        String titulo = jsonFilme.getString("titulo");
        String genero = jsonFilme.getString("genero");
        int ano = jsonFilme.getInt("ano");
        String duracao = jsonFilme.getString("duracao");
        String direcao = jsonFilme.getString("direcao");
        String roteiro = jsonFilme.getString("roteiro");
        String elenco = jsonFilme.getString("elenco");
        String titulo_original = jsonFilme.getString("titulo_original");
        String onde_assistir = jsonFilme.getString("onde_assistir");

        Filme filme = new Filme(titulo, genero, ano, duracao, direcao, roteiro, elenco, titulo_original, onde_assistir);
        filme.setNota(jsonFilme.optInt("nota", 0)); // Usar optInt com default
        filme.setReview(jsonFilme.optString("review", "")); // Usar optString com default
        return filme;
    }

    public String getOnde_assistir() {return onde_assistir;
    }

    public String getTitulo_original() {return titulo_original;}

    public String getRoteiro() { return roteiro;}
}