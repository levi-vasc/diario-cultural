package diariocultural.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button cadastro, busca;

    private Controller main_controller;

    public void setMainController(Controller controller) {
        this.main_controller = controller;
    }

    @FXML
    private void irCadastro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/Cadastro.fxml"));
        Parent root = loader.load();

        // Injeta controller principal para CadastroController
        CadastroController cadastro_controller = loader.getController();
        cadastro_controller.setMainController(this.main_controller);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void irBusca(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/Busca.fxml"));
        Parent root = loader.load();

        // Injeta controller principal para BuscaController
        BuscaController buscaController = loader.getController();
        buscaController.setMainController(this.main_controller);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}