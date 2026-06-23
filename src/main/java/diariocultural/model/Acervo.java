package diariocultural.model;

import java.text.Normalizer;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Acervo {
    private List<Obra> catalogo = new ArrayList<>();

    /**
     * Adiciona uma nova obra ao acervo.
     * @param item A obra a ser adicionada.
     */
    public void adicionarItem(Obra item) {
        catalogo.add(item);
    }

    /**
     * Remove uma obra.
     * @param obra A obra a ser removida.
     */
    public void removerItem(Obra obra) { catalogo.remove(obra); }

    /**
     * Verifica se uma obra já existe.
     * - Para livros, compara pelo ISBN.
     * - Para filmes e séries, compara pelo título e ano.
     * @param novaObra A obra a ser verificada.
     * @return true se a obra já existe, false caso contrário.
     */
    public boolean existeItem(Obra novaObra) {
        for (Obra existente : catalogo) {
            if (novaObra instanceof Livro livroNovo && existente instanceof Livro livroExistente) {
                if (livroNovo.getIsbn() == livroExistente.getIsbn()) {
                    return true;
                }
            } else if ((novaObra instanceof Filme || novaObra instanceof Serie) &&
                    existente.getClass().equals(novaObra.getClass())) {
                if (novaObra.getTitulo().equalsIgnoreCase(existente.getTitulo()) &&
                        novaObra.getAno() == existente.getAno()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Formata um texto, removendo acentos e convertendo para letras minúsculas. Lida com as entradas "imperfeitas" do usuário.
     * @param texto O texto a ser normatizado.
     * @return O texto normalizado.
     */
    public static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    /**
     * Busca obras no acervo com base em um termo de busca e tipo esperado.
     * @param termoBusca    O termo a ser buscado (ex: título, autor, elenco etc.)
     * @param tipoEsperado  O tipo de obra esperado ("livro", "filme", "serie")
     * @return Lista de obras que correspondem ao termo de busca e ao tipo.
     */
    public List<Obra> buscarItem(String termoBusca, String tipoEsperado) {
        String[] termos = normalizar(termoBusca).split("\\s+");

        return catalogo.stream()
                .filter(obra -> {
                    // Filtro pelo tipo
                    boolean tipoCorreto = switch (tipoEsperado) {
                        case "livro" -> obra instanceof Livro;
                        case "filme" -> obra instanceof Filme;
                        case "serie" -> obra instanceof Serie;
                        default -> false;
                    };
                    if (!tipoCorreto) return false;

                    // Monta os dados relevantes da obra
                    StringBuilder dadosObra = new StringBuilder();
                    if (obra instanceof Livro livro) {
                        dadosObra.append(livro.getTitulo()).append(" ")
                                .append(livro.getGenero()).append(" ")
                                .append(livro.getAutor()).append(" ")
                                .append(livro.getIsbn()).append(" ")
                                .append(livro.getAno());
                    } else if (obra instanceof Filme filme) {
                        dadosObra.append(filme.getTitulo()).append(" ")
                                .append(filme.getGenero()).append(" ")
                                .append(filme.getDiretor()).append(" ")
                                .append(filme.getElenco()).append(" ")
                                .append(filme.getAno());
                    } else if (obra instanceof Serie serie) {
                        dadosObra.append(serie.getTitulo()).append(" ")
                                .append(serie.getGenero()).append(" ")
                                .append(serie.getElenco()).append(" ")
                                .append(serie.getAno());
                    }

                    String dadosNormalizados = normalizar(dadosObra.toString());
                    return Arrays.stream(termos).allMatch(dadosNormalizados::contains);
                })
                .toList();
    }

    /**
     * Lista todas as obras de um tipo (livro, filme ou série) ordenadas pela nota (da maior para a menor).
     * @return Lista de obras ordenadas por nota decrescente.
     */
    public List<Obra> listarItens() {
        List<Obra> ordenada = new ArrayList<>(catalogo);
        ordenada.sort(Comparator.comparingInt(Obra::getNota).reversed());
        return ordenada;
    }

    /**
     * Lista as obras de um tipo específico (livro, filme ou série), ordenadas por nota.
     * @param tipo O tipo de obra desejado ("livro", "filme", "serie").
     * @return Lista das obras do tipo indicado, ordenadas por nota decrescente. Além disso, exibe informações mais enxutas que a busca.
     */
    public List<Obra> listarItensPorTipo(String tipo) {
        List<Obra> filtrados = new ArrayList<>();

        for (Obra item : catalogo) {
            boolean tipoCorreto = switch (tipo.toLowerCase()) {
                case "livro" -> item instanceof Livro;
                case "filme" -> item instanceof Filme;
                case "serie" -> item instanceof Serie;
                default -> false;
            };

            if (tipoCorreto) {
                filtrados.add(item);
            }
        }

        // Ordenar por nota decrescente
        filtrados.sort(Comparator.comparingInt(Obra::getNota).reversed());
        return filtrados;
    }

    /**
     * Converte o catálogo atual de obras em um JSONArray formatado para persistência.
     * Cada obra no catálogo é convertida em um JSONObject através do método
     * salvarObra().
     *
     * @return Um JSONArray contendo a representação JSON de todas as obras no catálogo.
     */
    public JSONArray salvarArq() {
        JSONArray jsonCatalogo = new JSONArray();

        for (Obra obra : catalogo) {
            jsonCatalogo.put(obra.salvarObra());
        }

        return jsonCatalogo;
    }

    /**
     * Lê um JSONArray e constrói um novo objeto Acervo a partir dele. Este método é estático.
     *
     * @param jsonCatalogo O JSONArray contendo a representação JSON das obras.
     * @return Novo objeto Acervo preenchido com as obras lidas do JSON.
     */
    public static Acervo lerArq(JSONArray jsonCatalogo) {
        Acervo acervo = new Acervo();

        for (int i = 0; i < jsonCatalogo.length(); i++) {
            JSONObject jsonObra = jsonCatalogo.getJSONObject(i);
            Obra obra = Obra.lerObra(jsonObra);
            if (obra != null) {
                acervo.adicionarItem(obra);
            }
        }
        return acervo;
    }

}