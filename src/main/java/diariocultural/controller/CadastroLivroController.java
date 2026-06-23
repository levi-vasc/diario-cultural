package diariocultural.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import diariocultural.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class CadastroLivroController {

    @FXML
    private TextField field_titulo, field_isbn, field_autor, field_editora, field_genero, field_ano;
    @FXML
    private CheckBox possui_exemplar;
    @FXML
    private Button bt_genero, bt_salvar;

    private ContextMenu menu_generos;
    private Controller main_controller;

    public void setMainController(Controller controller) {this.main_controller = controller;}

    @FXML
    public void configurarMenuGeneros() {
        this.menu_generos = main_controller.criarMenuGeneros(field_genero);
        field_genero.setEditable(false);
        bt_genero.setOnAction(event -> {
            this.menu_generos.show(bt_genero, Side.BOTTOM, 0, 0);
        });
    }

    @FXML
    private void handleSalvarLivro(ActionEvent event) {
        List<String> erros = new ArrayList<>();

        String titulo = field_titulo.getText();
        if (titulo.trim().isEmpty()) {
            erros.add("O campo 'Título' não pode estar vazio.");
        }

        String autor = main_controller.verificacaoDadosOpcionais(field_autor);

        String genero = field_genero.getText();
        if (genero.trim().isEmpty()) {
            erros.add("O campo 'Gênero' não pode estar vazio. Por favor, selecione um gênero.");
        }

        String editora = main_controller.verificacaoDadosOpcionais(field_editora);

        long isbn = 0;
        try {
            if (field_isbn.getText().trim().isEmpty()) {
                erros.add("O campo 'ISBN' não pode estar vazio.");
            } else {
                isbn = Long.parseLong(field_isbn.getText());
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'ISBN' contém um valor inválido. Por favor, insira apenas números.");
        }

        int ano = 0;
        try {
            if (field_ano.getText().trim().isEmpty()) {
                erros.add("O campo 'Publicação' não pode estar vazio.");
            } else {
                ano = Integer.parseInt(field_ano.getText());
                if (ano > 2025) {
                    erros.add("Insira um ano de publicação menor que 2026.");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Publicação' contém um valor inválido. Por favor, insira apenas números.");
        }

        String exemplar = possui_exemplar.isSelected() ? "Sim" : "Não";

        if (!erros.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Erro de Validação", "Por favor, corrija os seguintes erros:", String.join("\n", erros));
            return;
        }

        Livro novo_livro = new Livro(titulo, genero, ano, autor, editora, isbn, exemplar);

        if (main_controller.getAcervo().existeItem(novo_livro)) {
            mostrarAlerta(AlertType.WARNING, "Livro já Cadastrado", "Item Duplicado", "Um livro com o mesmo ISBN já existe no acervo.");
        } else {
            main_controller.getAcervo().adicionarItem(novo_livro);
            main_controller.salvarDados();
            mostrarAlerta(AlertType.INFORMATION, "Sucesso!", "Livro Cadastrado", "O livro '" + titulo + "' foi cadastrado e salvo com sucesso!");
            limparCampos();
        }
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    private void limparCampos() {
        field_titulo.clear();
        field_isbn.clear();
        field_autor.clear();
        field_editora.clear();
        field_genero.clear();
        field_ano.clear();
        possui_exemplar.setSelected(false);
    }
}