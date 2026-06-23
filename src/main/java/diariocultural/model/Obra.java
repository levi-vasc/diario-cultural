package diariocultural.model;

import org.json.JSONObject;

public abstract class Obra {
    protected String titulo, genero;
    protected int ano;
    private int nota = 0;
    private String review = "";


    public Obra(String titulo, String genero, int ano) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAno() { return ano; }

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

    public static String formatarCampo(String campo) {
        return campo == null || campo.isBlank() ? "Não informado" : campo;
    }

    @Override
    public String toString() {
        return titulo + " (" + ano + ") | Gênero: " + genero + " | Avaliação: " + (nota > 0 ? nota + "/5" : "Não avaliado");
    }

    public String resumo() {
        return toString();
    }

    /**
     * Serializa esta obra para um JSONObject.
     * Converte os atributos comuns da obra (título, gênero)
     * em pares chave-valor dentro de um objeto JSON.
     *
     * @return JSONObject contendo os dados desta obra.
     */
    public JSONObject salvarObra() {
        JSONObject jsonObra = new JSONObject();
        jsonObra.put("titulo", titulo);
        jsonObra.put("genero", genero);
        jsonObra.put("ano", ano);
        jsonObra.put("nota", nota);
        jsonObra.put("review", review);
        return jsonObra;
    }

    /**
     * Desserializa um JSONObject para construir um objeto Obra
     * ou uma de suas subclasses (Livro, Filme, Serie).
     *
     * @param jsonObra O JSONObject contendo os dados da obra a ser lida.
     * @return Uma instância de Obra ou de uma de suas subclasses (Livro, Filme, Serie),
     * populada com os dados do JSON.
    */

    public static Obra lerObra(JSONObject jsonObra) {
        String tipo = jsonObra.getString("tipo");
        switch (tipo) {
            case "Livro":
                return Livro.lerLivro(jsonObra);
            case "Filme":
                return Filme.lerFilme(jsonObra);
            case "Serie":
                return Serie.lerSerie(jsonObra);
            default:
                return null;
        }
    }
}