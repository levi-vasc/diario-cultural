package diariocultural.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CadastroController {
    @FXML
    private AnchorPane painel_cadastro;
    @FXML
    private Button livro, filme, serie, voltar_menu;

    private Button ultimo_selecionado;
    private Controller main_controller;

    public void setMainController(Controller main_controller) {
        this.main_controller = main_controller;
        handleLivro();
    }

    @FXML
    private void handleLivro() {
        carregarPainel("/diariocultural/view/CadastroLivro.fxml");
        selecionarBotao(livro);
    }

    @FXML
    private void handleFilme() {
        carregarPainel("/diariocultural/view/CadastroFilme.fxml");
        selecionarBotao(filme);
    }

    @FXML
    private void handleSerie() {
        carregarPainel("/diariocultural/view/CadastroSerie.fxml");
        selecionarBotao(serie);
    }

    private void carregarPainel(String fxml_path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_path));
            Parent content = loader.load();

            // Seta controlador a depender do painel
            Object controller = loader.getController();
            if (controller instanceof CadastroLivroController) {
                CadastroLivroController livroController = (CadastroLivroController) controller;
                livroController.setMainController(this.main_controller);
                livroController.configurarMenuGeneros();
            } else if (controller instanceof CadastroFilmeController) {
                CadastroFilmeController filmeController = (CadastroFilmeController) controller;
                filmeController.setMainController(this.main_controller);
                filmeController.configurarMenuGeneros();
            } else if (controller instanceof CadastroSerieController) {
                CadastroSerieController serieController = (CadastroSerieController) controller;
                serieController.setMainController(this.main_controller);
                serieController.configurarMenuGeneros();
            }

            painel_cadastro.getChildren().clear();
            painel_cadastro.getChildren().add(content);

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