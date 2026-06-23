package diariocultural.controller;

import diariocultural.model.Serie;
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

public class BuscaSerieController {

    @FXML
    private TableView<Serie> tabelaSeries;
    @FXML
    private TableColumn<Serie, String> tituloColumn, elencoColumn, generoColumn;
    @FXML
    private TableColumn<Serie, Integer> anoColumn, notaColumn, numTemporadasColumn;
    @FXML
    private TextField field_busca;

    private Controller mainAppController;

    public void setMainController(Controller mainAppController) {
        this.mainAppController = mainAppController;
        carregarSeriesNaTabela();
    }

    @FXML
    public void initialize() {
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        elencoColumn.setCellValueFactory(new PropertyValueFactory<>("elenco"));
        generoColumn.setCellValueFactory(new PropertyValueFactory<>("genero"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        numTemporadasColumn.setCellValueFactory(new PropertyValueFactory<>("numTemporadas"));

        tabelaSeries.setOnMouseClicked(event -> {
            Serie serieSelecionada = tabelaSeries.getSelectionModel().getSelectedItem();
            if (serieSelecionada != null) {
                abrirDetalhesObra(serieSelecionada);
            }
        });
    }

    @FXML
    private void handleBuscaSerie(ActionEvent event) {
        String termoBusca = field_busca.getText().trim();
        List<Obra> resultados;

        if (termoBusca.isEmpty()) {
            resultados = mainAppController.getAcervo().listarItensPorTipo("serie");
        } else {
            resultados = mainAppController.getAcervo().buscarItem(termoBusca, "serie");
        }

        ObservableList<Serie> seriesEncontradas = FXCollections.observableArrayList();
        for (Obra obra : resultados) {
            if (obra instanceof Serie) {
                seriesEncontradas.add((Serie) obra);
            }
        }
        tabelaSeries.setItems(seriesEncontradas);
    }

    @FXML
    public void carregarSeriesNaTabela() {
        List<Obra> obras = mainAppController.getAcervo().listarItensPorTipo("serie");
        ObservableList<Serie> series = FXCollections.observableArrayList();
        for (Obra obra : obras) {
            if (obra instanceof Serie) {
                series.add((Serie) obra);
            }
        }
        tabelaSeries.setItems(series);
    }

    private void abrirDetalhesObra(Obra obra) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/DetalhesObra.fxml"));
            Parent root = loader.load();

            DetalhesObraController detalhesController = loader.getController();
            detalhesController.setMainController(mainAppController);
            detalhesController.setObra(obra); // Passa a obra selecionada

            Stage stage = new Stage();
            stage.setTitle("Detalhes da Obra: " + obra.getTitulo());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal
            stage.showAndWait(); // Espera a janela de detalhes ser fechada

            // Após fechar a janela de detalhes, recarrega a tabela para refletir possíveis alterações (nota, review, exclusão)
            carregarSeriesNaTabela();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao abrir detalhes da obra");
            alert.setContentText("Não foi possível carregar a tela de detalhes. Verifique o arquivo FXML e o controlador.");
            alert.showAndWait();
        }
    }
}