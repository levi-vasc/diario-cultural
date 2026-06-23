package diariocultural.controller;

import diariocultural.Main;
import diariocultural.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONException;

public class Controller {
    private Acervo acervo;
    private static final String arquivo = "save.json";

    public Controller(Acervo acervo) {
        this.acervo = acervo;
    }

    // NOVO: Getter para o Acervo, permitindo que outros controladores o acessem.
    public Acervo getAcervo() {
        return acervo;
    }

    public void salvarDados() {
        JSONArray jsonAcervo = acervo.salvarArq();
        try (FileWriter file = new FileWriter(arquivo)) {
            file.write(jsonAcervo.toString(2));
        } catch (IOException e) {
            System.out.print("Erro ao salvar arquivo.");
        }
    }

    public void carregarDados() {
        try {
            Path path = Paths.get(arquivo);
            String conteudo = new String(Files.readAllBytes(path));
            JSONArray jsonAcervo = new JSONArray(conteudo);
            acervo = Acervo.lerArq(jsonAcervo);
        } catch (IOException | JSONException e) {
            acervo = new Acervo(); // Inicializa um novo acervo se o arquivo não existe ou está corrompido
        }
    }

    public void excluirDados() {
        int confirmacao = 0;

        if (confirmacao == 1) {
            File arquivo = new File(Controller.arquivo);
            if (arquivo.delete()) {
                acervo = new Acervo();
            }
        }
    }

    // Metodo auxiliar para CadastroController e BuscaController
    public void voltarMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/diariocultural/view/Menu.fxml"));
        Parent root = loader.load();

        diariocultural.controller.MenuController menu_controller = loader.getController();
        menu_controller.setMainController(Main.getMainController());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    // Metodo auxiliar para os paineis de cadastro
    public ContextMenu criarMenuGeneros(TextField txt_genero) {
        ContextMenu menu_generos = new ContextMenu();

        MenuItem itemAcao = new MenuItem("Ação");
        MenuItem itemAventura = new MenuItem("Aventura");
        MenuItem itemComedia = new MenuItem("Comédia");
        MenuItem itemTerror = new MenuItem("Terror");
        MenuItem itemRomance = new MenuItem("Romance");
        MenuItem itemFiccao = new MenuItem("Ficção científica");
        MenuItem itemSuspense = new MenuItem("Suspense");
        MenuItem itemDrama = new MenuItem("Drama");

        menu_generos.getItems().addAll(itemAcao, itemAventura, itemComedia, itemTerror, itemRomance, itemFiccao, itemSuspense, itemDrama);

        itemAcao.setOnAction(event -> txt_genero.setText("Ação"));
        itemAventura.setOnAction(event -> txt_genero.setText("Aventura"));
        itemComedia.setOnAction(event -> txt_genero.setText("Comédia"));
        itemTerror.setOnAction(event -> txt_genero.setText("Terror"));
        itemRomance.setOnAction(event -> txt_genero.setText("Romance"));
        itemFiccao.setOnAction(event -> txt_genero.setText("Ficção científica"));
        itemSuspense.setOnAction(event -> txt_genero.setText("Suspense"));
        itemDrama.setOnAction(event -> txt_genero.setText("Drama"));

        return menu_generos;
    }

    // Metodo auxiliar para validação de dados opcionais
    public String verificacaoDadosOpcionais(TextField txt_field) {
        String retorno = txt_field.getText();
        if (retorno.trim().isEmpty()) {
            retorno = "Não informado";
        }
        return retorno;
    }
}