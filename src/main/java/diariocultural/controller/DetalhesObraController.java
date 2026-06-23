package diariocultural.controller;

import diariocultural.model.Filme;
import diariocultural.model.Livro;
import diariocultural.model.Obra;
import diariocultural.model.Serie;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class DetalhesObraController {

    @FXML
    private Label lblTitulo, lblGenero, lblAnoLancamento;

    // Labels específicos para série
    @FXML
    private Label lblEncerramentoLabel, lblEncerramento;
    @FXML
    private Label lblElencoLabel, lblElenco;
    @FXML
    private Label lblTituloOriginalLabel, lblTituloOriginal;
    @FXML
    private Label lblOndeAssistirLabel, lblOndeAssistir;
    @FXML
    private Label lblNumTemporadasLabel, lblNumTemporadas;

    // Labels específicos para filme
    @FXML
    private Label lblDiretorLabel, lblDiretor;
    @FXML
    private Label lblDuracaoLabel, lblDuracao;
    @FXML
    private Label lblRoteiroLabel, lblRoteiro;

    // Labels específicos para livro
    @FXML
    private Label lblAutorLabel, lblAutor;
    @FXML
    private Label lblEditoraLabel, lblEditora;
    @FXML
    private Label lblIsbnLabel, lblIsbn;
    @FXML
    private Label lblExemplarLabel, lblExemplar;

    //Componentes de nota e review
    @FXML
    private Slider sliderNota;
    @FXML
    private TextArea txtAreaReview;

    private Obra obraAtual;
    private Controller main_controller;

    public void setMainController(Controller controller) {
        this.main_controller = controller;
    }

    public void setObra(Obra obra) {
        this.obraAtual = obra;
        exibirDetalhesObra();
    }

    @FXML
    public void initialize() {
        // Inicializa o slider para mostrar a nota atual da obra
        sliderNota.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Arredonda o valor do slider para o inteiro mais próximo
            sliderNota.setValue(Math.round(newVal.doubleValue()));
        });
    }

    private void exibirDetalhesObra() {
        if (obraAtual == null) {
            return;
        }

        lblTitulo.setText(obraAtual.getTitulo());
        lblGenero.setText(obraAtual.getGenero());
        lblAnoLancamento.setText(String.valueOf(obraAtual.getAno()));

        sliderNota.setValue(obraAtual.getNota()); // Define o valor do slider para a nota atual da obra
        txtAreaReview.setText(obraAtual.getReview()); // Define o texto da área de review para o review atual

        //Esconde campos não pertinentes
        setCamposVisiveis(false, lblEncerramentoLabel, lblEncerramento, lblElencoLabel, lblElenco,
                lblTituloOriginalLabel, lblTituloOriginal, lblOndeAssistirLabel, lblOndeAssistir,
                lblNumTemporadasLabel, lblNumTemporadas,
                lblDiretorLabel, lblDiretor, lblDuracaoLabel, lblDuracao, lblRoteiroLabel, lblRoteiro,
                lblAutorLabel, lblAutor, lblEditoraLabel, lblEditora, lblIsbnLabel, lblIsbn, lblExemplarLabel, lblExemplar);

        GridPane.setRowIndex(lblTitulo, 0);
        GridPane.setRowIndex(lblGenero, 1);
        GridPane.setRowIndex(lblAnoLancamento, 2);

        if (obraAtual instanceof Serie) {
            Serie serie = (Serie) obraAtual;
            setCamposVisiveis(true, lblEncerramentoLabel, lblEncerramento, lblElencoLabel, lblElenco,
                    lblTituloOriginalLabel, lblTituloOriginal, lblOndeAssistirLabel, lblOndeAssistir,
                    lblNumTemporadasLabel, lblNumTemporadas);

            lblEncerramento.setText(serie.getAno_encerramento() == 0 ? "Em andamento" : String.valueOf(serie.getAno_encerramento()));
            lblElenco.setText(serie.getElenco());
            lblTituloOriginal.setText(serie.getTitulo_original());
            lblOndeAssistir.setText(serie.getOnde_assistir());
            lblNumTemporadas.setText(String.valueOf(serie.getNumTemporadas()));

            // Definir os rowIndex para os campos de Série que estão visíveis
            GridPane.setRowIndex(lblEncerramentoLabel, 3);
            GridPane.setRowIndex(lblEncerramento, 3);
            GridPane.setRowIndex(lblElencoLabel, 4);
            GridPane.setRowIndex(lblElenco, 4);
            GridPane.setRowIndex(lblTituloOriginalLabel, 5);
            GridPane.setRowIndex(lblTituloOriginal, 5);
            GridPane.setRowIndex(lblOndeAssistirLabel, 6);
            GridPane.setRowIndex(lblOndeAssistir, 6);
            GridPane.setRowIndex(lblNumTemporadasLabel, 7);
            GridPane.setRowIndex(lblNumTemporadas, 7);

        } else if (obraAtual instanceof Filme) {
            Filme filme = (Filme) obraAtual;
            setCamposVisiveis(true, lblDiretorLabel, lblDiretor, lblDuracaoLabel, lblDuracao,
                    lblRoteiroLabel, lblRoteiro, lblElencoLabel, lblElenco,
                    lblTituloOriginalLabel, lblTituloOriginal, lblOndeAssistirLabel, lblOndeAssistir);

            lblDiretor.setText(filme.getDiretor());
            lblDuracao.setText(String.valueOf(filme.getDuracao()));
            lblRoteiro.setText(filme.getRoteiro());
            lblTituloOriginal.setText(filme.getTitulo_original());
            lblElenco.setText(filme.getElenco());
            lblOndeAssistir.setText(filme.getOnde_assistir());

            // Definir os rowIndex para os campos de Filme que estão visíveis
            GridPane.setRowIndex(lblDiretorLabel, 3);
            GridPane.setRowIndex(lblDiretor, 3);
            GridPane.setRowIndex(lblDuracaoLabel, 4);
            GridPane.setRowIndex(lblDuracao, 4);
            GridPane.setRowIndex(lblRoteiroLabel, 5);
            GridPane.setRowIndex(lblRoteiro, 5);
            GridPane.setRowIndex(lblElencoLabel, 6);
            GridPane.setRowIndex(lblElenco, 6);
            GridPane.setRowIndex(lblTituloOriginalLabel, 7);
            GridPane.setRowIndex(lblTituloOriginal, 7);
            GridPane.setRowIndex(lblOndeAssistirLabel, 8);
            GridPane.setRowIndex(lblOndeAssistir, 8);

        } else if (obraAtual instanceof Livro) {
            Livro livro = (Livro) obraAtual;
            setCamposVisiveis(true, lblAutorLabel, lblAutor, lblEditoraLabel, lblEditora, lblIsbnLabel, lblIsbn, lblExemplarLabel, lblExemplar);

            lblAutor.setText(livro.getAutor());
            lblEditora.setText(livro.getEditora());
            lblIsbn.setText(String.valueOf(livro.getIsbn()));
            lblExemplar.setText(livro.getExemplar());

            // Definir os rowIndex para os campos de Livro que estão visíveis
            GridPane.setRowIndex(lblAutorLabel, 3);
            GridPane.setRowIndex(lblAutor, 3);
            GridPane.setRowIndex(lblEditoraLabel, 4);
            GridPane.setRowIndex(lblEditora, 4);
            GridPane.setRowIndex(lblIsbnLabel, 5);
            GridPane.setRowIndex(lblIsbn, 5);
            GridPane.setRowIndex(lblExemplarLabel, 6);
            GridPane.setRowIndex(lblExemplar, 6);
        }
    }

    // Método auxiliar para controle dos campos visíveis
    private void setCamposVisiveis(boolean visible, Label... labels) {
        for (Label label : labels) {
            label.setVisible(visible);
            label.setManaged(visible); // Controla se o elemento ocupa espaço no layout
        }
    }

    @FXML
    private void handleSalvarAvaliacao() {
        if (obraAtual == null) {
            mostrarAlerta(AlertType.ERROR, "Erro", "Nenhuma obra selecionada.", "Por favor, selecione uma obra para avaliar.");
            return;
        }

        int novaNota = (int) sliderNota.getValue();
        String novoReview = txtAreaReview.getText();

        obraAtual.setNota(novaNota);
        obraAtual.setReview(novoReview);

        if (main_controller != null) {
            main_controller.salvarDados(); // Salva as alterações no arquivo
            mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Avaliação Salva", "Sua avaliação e review foram salvos com sucesso!");
        } else {
            mostrarAlerta(AlertType.ERROR, "Erro", "Controlador principal não disponível.", "Não foi possível salvar a avaliação.");
        }
    }

    @FXML
    private void handleExcluirObra() {
        if (obraAtual == null) {
            mostrarAlerta(AlertType.ERROR, "Erro", "Nenhuma obra selecionada.", "Por favor, selecione uma obra para excluir.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Obra: " + obraAtual.getTitulo());
        alert.setContentText("Tem certeza que deseja excluir esta obra? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (main_controller != null) {
                main_controller.getAcervo().removerItem(obraAtual); // Remove do acervo
                main_controller.salvarDados(); // Salva as alterações no arquivo
                mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Obra Excluída", "A obra '" + obraAtual.getTitulo() + "' foi excluída com sucesso!");
                // Fechar a janela de detalhes após a exclusão
                ((javafx.stage.Stage) lblTitulo.getScene().getWindow()).close();
            } else {
                mostrarAlerta(AlertType.ERROR, "Erro", "Controlador principal não disponível.", "Não foi possível excluir a obra.");
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
}