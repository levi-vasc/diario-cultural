package diariocultural.controller;

import diariocultural.model.Filme;
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

public class BuscaFilmeController {

    @FXML
    private TableView<Filme> tabelaFilmes;
    @FXML
    private TableColumn<Filme, String> tituloColumn, elencoColumn, generoColumn, diretorColumn;
    @FXML
    private TableColumn<Filme, Integer> anoColumn, notaColumn;
    @FXML
    private TextField field_busca;

    private Controller mainAppController;

    public void setMainController(Controller mainAppController) {
        this.mainAppController = mainAppController;
        carregarFilmesNaTabela();
    }

    @FXML
    public void initialize() {
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        elencoColumn.setCellValueFactory(new PropertyValueFactory<>("elenco"));
        generoColumn.setCellValueFactory(new PropertyValueFactory<>("genero"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        diretorColumn.setCellValueFactory(new PropertyValueFactory<>("diretor"));

        tabelaFilmes.setOnMouseClicked(event -> {
            Filme filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
            if (filmeSelecionado != null) {
                abrirDetalhesObra(filmeSelecionado);
            }
        });
    }

    @FXML
    private void handleBuscaFilme(ActionEvent event) {
        String termoBusca = field_busca.getText().trim();
        List<Obra> resultados;

        if (termoBusca.isEmpty()) {
            resultados = mainAppController.getAcervo().listarItensPorTipo("filme");
        } else {
            resultados = mainAppController.getAcervo().buscarItem(termoBusca, "filme");
        }

        ObservableList<Filme> filmesEncontrados = FXCollections.observableArrayList();
        for (Obra obra : resultados) {
            if (obra instanceof Filme) {
                filmesEncontrados.add((Filme) obra);
            }
        }
        tabelaFilmes.setItems(filmesEncontrados);
    }

    @FXML
    public void carregarFilmesNaTabela() {
        List<Obra> obras = mainAppController.getAcervo().listarItensPorTipo("filme");
        ObservableList<Filme> filmes = FXCollections.observableArrayList();
        for (Obra obra : obras) {
            if (obra instanceof Filme) {
                filmes.add((Filme) obra);
            }
        }
        tabelaFilmes.setItems(filmes);
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

            // Após fechar a janela de detalhes, recarrega a tabela para refletir possíveis alterações
            carregarFilmesNaTabela();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir detalhes da obra");
            alert.setContentText("Não foi possível carregar a tela de detalhes. Verifique o arquivo FXML e o controlador.");
            alert.showAndWait();
        }
    }
}