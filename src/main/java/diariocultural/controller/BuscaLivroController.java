package diariocultural.controller;

import diariocultural.model.Livro;
import diariocultural.model.Obra;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BuscaLivroController {

    @FXML
    private TableView<Livro> tabelaLivros;
    @FXML
    private TableColumn<Livro, String> tituloColumn, autorColumn, generoColumn;
    @FXML
    private TableColumn<Livro, Integer> anoColumn, notaColumn;
    @FXML
    private TableColumn<Livro, Long> isbnColumn;
    @FXML
    private TextField field_busca;

    private Controller mainAppController;

    public void setMainController(Controller mainAppController) {
        this.mainAppController = mainAppController;
        carregarLivrosNaTabela();
    }

    @FXML
    public void initialize() {
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        generoColumn.setCellValueFactory(new PropertyValueFactory<>("genero"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        tabelaLivros.setOnMouseClicked(event -> {
            Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
            if (livroSelecionado != null) {
                abrirDetalhesObra(livroSelecionado);
            }
        });
    }

    @FXML
    private void handleBuscaLivro(ActionEvent event) {
        String termoBusca = field_busca.getText().trim();
        List<Obra> resultados;

        if (termoBusca.isEmpty()) {
            resultados = mainAppController.getAcervo().listarItensPorTipo("livro");
        } else {
            resultados = mainAppController.getAcervo().buscarItem(termoBusca, "livro");
        }

        ObservableList<Livro> livrosEncontrados = FXCollections.observableArrayList();
        for (Obra obra : resultados) {
            if (obra instanceof Livro) {
                livrosEncontrados.add((Livro) obra);
            }
        }
        tabelaLivros.setItems(livrosEncontrados);
    }

    @FXML
    public void carregarLivrosNaTabela() {
        List<Obra> obras = mainAppController.getAcervo().listarItensPorTipo("livro");
        ObservableList<Livro> livros = FXCollections.observableArrayList();
        for (Obra obra : obras) {
            if (obra instanceof Livro) {
                livros.add((Livro) obra);
            }
        }
        tabelaLivros.setItems(livros);
    }

    private void abrirDetalhesObra(Obra obra) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/DetalhesObra.fxml"));
            Parent root = loader.load();

            DetalhesObraController detalhesController = loader.getController();
            detalhesController.setMainController(mainAppController);
            detalhesController.setObra(obra);

            Stage stage = new Stage();
            stage.setTitle("Detalhes da Obra: " + obra.getTitulo());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            carregarLivrosNaTabela();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir detalhes da obra");
            alert.setContentText("Não foi possível carregar a tela de detalhes.");
            alert.showAndWait();
        }
    }

}