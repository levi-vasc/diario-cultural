package diariocultural;

import diariocultural.controller.Controller;
import diariocultural.model.Acervo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Controller main_controller;

    public static Controller getMainController() {
        return main_controller;
    }

    @Override
    public void start(Stage janela) throws Exception {
        // 1. Inicializa o Acervo e o Controller principal para a parte da UI.
        Acervo acervo = new Acervo();
        main_controller = new Controller(acervo);
        main_controller.carregarDados(); // Carrega os dados existentes ao iniciar a aplicação.

        // 2. Carrega o FXML da tela de Menu (a primeira tela da UI).
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/Menu.fxml"));
        Parent root = loader.load();

        diariocultural.controller.MenuController menu_controller = loader.getController();
        menu_controller.setMainController(main_controller);

        // 4. Configura a janela principal.
        Scene scene = new Scene(root);
        janela.setTitle("Diário Cultural");
        janela.setScene(scene);
        janela.setWidth(800);
        janela.setHeight(600);
        janela.setResizable(false);
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}