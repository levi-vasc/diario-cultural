package diariocultural.model;

import org.json.JSONObject;

public class Livro extends Obra {
    private String autor;
    private String editora;
    private long isbn;
    private String exemplar;

    public Livro(String titulo, String genero, int ano, String autor, String editora, long isbn, String exemplar) {
        super(titulo, genero, ano);
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.exemplar = exemplar;
    }

    public String getAutor() { return autor; }
    public long getIsbn() { return isbn; }

    @Override
    public String toString() {
        return super.toString() + " | Autor: " + formatarCampo(autor) + " | ISBN: " + isbn + " | Editora: " + formatarCampo(editora) + " | Exemplar: " + exemplar + "\n\tReview: " + getReview() + "\n";
    }
    @Override
    public String resumo() {
        return super.toString() + " | Autor: " + formatarCampo(autor) + " | ISBN: " + isbn;
    }

    @Override
    public JSONObject salvarObra() {
        JSONObject jsonLivro = super.salvarObra();
        jsonLivro.put("tipo", "Livro");
        jsonLivro.put("autor", autor);
        jsonLivro.put("editora", editora);
        jsonLivro.put("isbn", isbn);
        jsonLivro.put("exemplar", exemplar);
        return jsonLivro;
    }

    /**
     * Representa um item do tipo Livro, estendendo a classe Obra.
     * Esta classe contém atributos específicos de um livro e é responsável
     * por desserializar um JSONObject em uma instância de Livro.
     * Os métodos lerFilme e lerSerie apresentam lógica semelhante.
     */
    public static Livro lerLivro(JSONObject jsonLivro) {
        String titulo = jsonLivro.getString("titulo");
        String genero = jsonLivro.getString("genero");
        int ano = jsonLivro.getInt("ano");
        String autor = jsonLivro.getString("autor");
        String editora = jsonLivro.getString("editora");
        long isbn = jsonLivro.getLong("isbn");
        String exemplar = jsonLivro.getString("exemplar");

        Livro livro = new Livro(titulo, genero, ano, autor, editora, isbn, exemplar);
        livro.setNota(jsonLivro.optInt("nota", 0)); // Usar optInt com default
        livro.setReview(jsonLivro.optString("review", "")); // Usar optString com default
        return livro;
    }

    public String getEditora() { return editora;}

    public String getExemplar() {return exemplar;}
}