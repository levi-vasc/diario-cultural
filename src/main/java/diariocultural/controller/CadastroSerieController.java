package diariocultural.controller;

import diariocultural.model.Serie;
import diariocultural.model.Temporada;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class CadastroSerieController {
    @FXML
    private TextField titulo, txt_genero, ano, encerramento, elenco, titulo_original, onde_assistir;
    @FXML
    private Button genero, salvar_serie;

    // Campos para Temporada
    @FXML
    private TextField numTemporada, anoTemporada, numEpisodiosTemporada;
    @FXML
    private Button addTemporadaButton;

    private ContextMenu menu_generos;
    private Controller main_controller;
    private List<Temporada> temporadasAdicionadas = new ArrayList<>();
    private int proximaTemporadaEsperada = 1; // Campo para controlar a próxima temporada

    public void setMainController(Controller main_controller) {
        this.main_controller = main_controller;
    }

    @FXML
    public void initialize() {
        // Inicializa o campo numTemporada com "1"
        numTemporada.setText(String.valueOf(proximaTemporadaEsperada));
        numTemporada.setEditable(false); // Impede que o usuário edite o número da temporada
    }

    public void configurarMenuGeneros() {
        this.menu_generos = main_controller.criarMenuGeneros(txt_genero);
        txt_genero.setEditable(false);
        genero.setOnAction(event -> {
            this.menu_generos.show(genero, Side.BOTTOM, 0, 0);
        });
    }

    @FXML
    private void handleAddTemporada(ActionEvent event) {
        List<String> erros = new ArrayList<>();
        int numeroTemp = 0; // Será lido do campo, mas validado contra proximaTemporadaEsperada
        int anoTemp = 0;
        int numEpisodiosTemp = 0;

        // Validação do Número da Temporada
        try {
            if (numTemporada.getText().trim().isEmpty()) {
                erros.add("O campo 'Número da Temporada' não pode estar vazio.");
            } else {
                numeroTemp = Integer.parseInt(numTemporada.getText());
                if (numeroTemp != proximaTemporadaEsperada) {
                    erros.add("O número da temporada deve ser " + proximaTemporadaEsperada + ".");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("Erro interno: O número da temporada não é um número válido.");
        }

        try {
            if (anoTemporada.getText().trim().isEmpty()) {
                erros.add("O campo 'Ano da Temporada' não pode estar vazio.");
            } else {
                anoTemp = Integer.parseInt(anoTemporada.getText());
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Ano da Temporada' contém um valor inválido. Por favor, insira apenas números.");
        }

        try {
            if (numEpisodiosTemporada.getText().trim().isEmpty()) {
                erros.add("O campo 'Nº de Episódios' não pode estar vazio.");
            } else {
                numEpisodiosTemp = Integer.parseInt(numEpisodiosTemporada.getText());
                if (numEpisodiosTemp < 0) {
                    erros.add("O campo 'Nº de Episódios' não pode ser negativo.");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Nº de Episódios' contém um valor inválido. Por favor, insira apenas números.");
        }

        // Validação cronológica e de intervalo do ano da temporada
        int anoLancamentoSerie = 0;
        try {
            if (!ano.getText().trim().isEmpty()) {
                anoLancamentoSerie = Integer.parseInt(ano.getText());
            }
        } catch (NumberFormatException e) {
            // Ignorar, erro já será capturado no handleSalvarSerie
        }

        int anoEncerramentoSerie = 0;
        try {
            if (!encerramento.getText().trim().isEmpty()) {
                anoEncerramentoSerie = Integer.parseInt(encerramento.getText());
            }
        } catch (NumberFormatException e) {
            // Ignorar, erro já será capturado no handleSalvarSerie
        }

        if (anoLancamentoSerie != 0 && anoTemp < anoLancamentoSerie) {
            erros.add("O ano da temporada (" + anoTemp + ") não pode ser menor que o ano de lançamento da série (" + anoLancamentoSerie + ").");
        }
        // Validação para ano de encerramento
        if (anoEncerramentoSerie != 0 && anoTemp > anoEncerramentoSerie) {
            erros.add("O ano da temporada (" + anoTemp + ") não pode ser maior que o ano de encerramento da série (" + anoEncerramentoSerie + ").");
        } else if (anoEncerramentoSerie == 0 && anoTemp > 2025) { // Usando o ano atual como limite superior para séries em andamento
            erros.add("O ano da temporada (" + anoTemp + ") não pode ser maior que o ano atual (2025) para séries em andamento.");
        }

        // Validação de ordem cronológica do ano da temporada em relação à temporada anterior
        if (!temporadasAdicionadas.isEmpty()) {
            Temporada ultimaTemporada = temporadasAdicionadas.get(temporadasAdicionadas.size() - 1);
            if (anoTemp < ultimaTemporada.getAno()) {
                erros.add("O ano da temporada (" + anoTemp + ") não pode ser menor que o ano da temporada anterior (" + ultimaTemporada.getAno() + ").");
            }
        }


        if (!erros.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Erro de Validação da Temporada", "Por favor, corrija os seguintes erros:", String.join("\n", erros));
            return;
        }

        Temporada novaTemporada = new Temporada(numeroTemp, anoTemp, numEpisodiosTemp);
        temporadasAdicionadas.add(novaTemporada);
        mostrarAlerta(AlertType.INFORMATION, "Sucesso!", "Temporada Adicionada", "Temporada " + numeroTemp + " adicionada com sucesso!");

        // Prepara para a próxima temporada
        proximaTemporadaEsperada++;
        limparCamposTemporada();
        numTemporada.setText(String.valueOf(proximaTemporadaEsperada)); // Define o próximo número de temporada
    }

    @FXML
    private void handleSalvarSerie(ActionEvent event) {
        List<String> erros = new ArrayList<>();

        String tituloSerie = titulo.getText();
        if (tituloSerie.trim().isEmpty()) {
            erros.add("O campo 'Título' não pode estar vazio.");
        }

        String elencoSerie = main_controller.verificacaoDadosOpcionais(elenco);

        int encerramentoSerie = 0;
        try {
            if (!encerramento.getText().trim().isEmpty() && !encerramento.getText().trim().equals("-")) {
                encerramentoSerie = Integer.parseInt(encerramento.getText());
                if (encerramentoSerie < Integer.parseInt(ano.getText())) {
                    erros.add("O ano de encerramento não pode ser menor que o ano de lançamento.");
                }
                if (encerramentoSerie > 2025) { // Mesma validação de ano para o encerramento
                    erros.add("Insira um ano de encerramento menor que 2026.");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Encerramento' contém um valor inválido. Por favor, insira apenas números ou deixe vazio para série em andamento.");
        }


        String generoSerie = txt_genero.getText();
        if (generoSerie.trim().isEmpty()) {
            erros.add("O campo 'Gênero' não pode estar vazio. Por favor, selecione um gênero.");
        }

        int anoSerie = 0;
        try {
            if (ano.getText().trim().isEmpty()) {
                erros.add("O campo 'Lançamento' não pode estar vazio.");
            } else {
                anoSerie = Integer.parseInt(ano.getText());
                if (anoSerie > 2025) {
                    erros.add("Insira um ano de lançamento menor que 2026.");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Lançamento' contém um valor inválido. Por favor, insira apenas números.");
        }

        String tituloOriginalSerie = main_controller.verificacaoDadosOpcionais(titulo_original);

        String ondeAssistirSerie = main_controller.verificacaoDadosOpcionais(onde_assistir);

        // Validação final de que há pelo menos uma temporada
        if (temporadasAdicionadas.isEmpty()) {
            erros.add("A série deve ter pelo menos uma temporada cadastrada.");
        }

        if (!erros.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Erro de Validação", "Por favor, corrija os seguintes erros:", String.join("\n", erros));
            return;
        }

        Serie novaSerie = new Serie(tituloSerie, generoSerie, anoSerie, encerramentoSerie, elencoSerie, tituloOriginalSerie, ondeAssistirSerie);

        // Adiciona as temporadas à série
        for (Temporada temporada : temporadasAdicionadas) {
            novaSerie.adicionarTemporada(temporada);
        }

        if (main_controller != null) {
            if (main_controller.getAcervo().existeItem(novaSerie)) {
                mostrarAlerta(AlertType.WARNING, "Série já cadastrada", "Item Duplicado", "Uma série com o mesmo título e ano de lançamento já existe no acervo.");
            } else {
                main_controller.getAcervo().adicionarItem(novaSerie); // Adiciona ao acervo
                main_controller.salvarDados(); // Salva os dados no arquivo
                mostrarAlerta(AlertType.INFORMATION, "Sucesso!", "Série Cadastrada", "A série '" + tituloSerie + "' foi cadastrada e salva com sucesso!");
                limparCampos(); // Limpa os campos após o sucesso
            }
        }
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    private void limparCamposTemporada() {
        // numTemporada não é limpo, é atualizado para o próximo número
        anoTemporada.clear();
        numEpisodiosTemporada.clear();
    }

    private void limparCampos() {
        titulo.clear();
        ano.clear();
        encerramento.clear();
        elenco.clear();
        txt_genero.clear();
        onde_assistir.clear();
        titulo_original.clear();
        limparCamposTemporada(); // Limpa também os campos de temporada
        temporadasAdicionadas.clear(); // Limpa a lista de temporadas adicionadas
        proximaTemporadaEsperada = 1; // Reseta para a temporada 1
        numTemporada.setText(String.valueOf(proximaTemporadaEsperada)); // Atualiza o campo para 1
    }
}