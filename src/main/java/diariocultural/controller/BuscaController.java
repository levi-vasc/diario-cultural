package diariocultural.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class BuscaController {

    @FXML
    private Button livro, filme, serie;
    @FXML
    private StackPane painel_busca;

    private Button ultimo_selecionado;
    private Controller main_controller;

    public void setMainController(Controller main_controller) {
        this.main_controller = main_controller;
        mostrarPainelLivro();
    }

    @FXML
    public void initialize() {}

    @FXML
    private void mostrarPainelLivro() {
        selecionarBotao(livro);
        carregarPainel("/diariocultural/view/BuscaLivro.fxml");
    }

    @FXML
    private void mostrarPainelFilme() {
        selecionarBotao(filme);
        carregarPainel("/diariocultural/view/BuscaFilme.fxml");
    }

    @FXML
    private void mostrarPainelSerie() {
        selecionarBotao(serie);
        carregarPainel("/diariocultural/view/BuscaSerie.fxml");
    }

    private void carregarPainel(String fxml_path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_path));
            Parent content = loader.load();

            // Seta controlador principal a depender do painel
            Object controller = loader.getController();
            if (controller instanceof BuscaLivroController) {
                ((BuscaLivroController) controller).setMainController(this.main_controller);

            } else if (controller instanceof BuscaFilmeController) {
                ((BuscaFilmeController) controller).setMainController(this.main_controller);
            } else if (controller instanceof BuscaSerieController) {
                ((BuscaSerieController) controller).setMainController(this.main_controller);
            }

            painel_busca.getChildren().clear();
            painel_busca.getChildren().add(content);

            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
        } catch (IOException e) {
            System.err.println("Erro ao carregar painel");
        }
    }

    private void selecionarBotao(Button atual) {
        if (ultimo_selecionado != null && ultimo_selecionado != atual) {
            ultimo_selecionado.getStyleClass().remove("button-selected");
            ultimo_selecionado.getStyleClass().add("button");
        }
        if (!atual.getStyleClass().contains("button-selected")) {
            atual.getStyleClass().add("button-selected");
            atual.getStyleClass().remove("button");
        }
        ultimo_selecionado = atual;
    }

    @FXML
    private void voltarMenu(ActionEvent event) throws IOException { main_controller.voltarMenu(event); }
}