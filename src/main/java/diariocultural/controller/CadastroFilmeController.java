package diariocultural.controller;

import diariocultural.model.Filme;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class CadastroFilmeController {
    @FXML
    private TextField field_titulo, field_genero, field_ano, field_duracao, field_direcao, field_roteiro, field_elenco, field_titulo_original, field_onde_assistir;
    @FXML
    private Button bt_genero, bt_salvar;

    private ContextMenu menu_generos;
    private Controller main_controller;

    public void setMainController(Controller controller) {this.main_controller = controller;}

    public void configurarMenuGeneros() {
        this.menu_generos = main_controller.criarMenuGeneros(field_genero);
        field_genero.setEditable(false);
        bt_genero.setOnAction(event -> {
            this.menu_generos.show(bt_genero, Side.BOTTOM, 0, 0);
        });
    }
    @FXML
    private void handleSalvarFilme(ActionEvent event) {
        List<String> erros = new ArrayList<>();

        String titulo = field_titulo.getText();
        if (titulo.trim().isEmpty()) {
            erros.add("O campo 'Título' não pode estar vazio.");
        }

        String genero = field_genero.getText();
        if (genero.trim().isEmpty()) {
            erros.add("O campo 'Gênero' não pode estar vazio. Por favor, selecione um gênero.");
        }

        int ano = 0;
        try {
            if (field_ano.getText().trim().isEmpty()) {
                erros.add("O campo 'Lançamento' não pode estar vazio.");
            } else {
                ano = Integer.parseInt(field_ano.getText());
                if (ano > 2025) {
                    erros.add("Insira um ano de lançamento menor que 2026.");
                }
            }
        } catch (NumberFormatException e) {
            erros.add("O campo 'Lançamento' contém um valor inválido. Por favor, insira apenas números.");
        }

        String duracao = main_controller.verificacaoDadosOpcionais(field_duracao);
        String direcao = main_controller.verificacaoDadosOpcionais(field_direcao);
        String roteiro = main_controller.verificacaoDadosOpcionais(field_roteiro);
        String elenco = main_controller.verificacaoDadosOpcionais(field_elenco);
        String titulo_original = main_controller.verificacaoDadosOpcionais(field_titulo_original);
        String onde_assistir = main_controller.verificacaoDadosOpcionais(field_onde_assistir);

        if (!erros.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Erro de Validação", "Por favor, corrija os seguintes erros:", String.join("\n", erros));
            return;
        }

        Filme novo_filme = new Filme(titulo, genero, ano, duracao, direcao, roteiro, elenco, titulo_original,onde_assistir);

        if (main_controller.getAcervo().existeItem(novo_filme)) {
            mostrarAlerta(AlertType.WARNING, "Filme já cadastrado", "Item Duplicado", "Um filme com o mesmo título e ano de lançamento já existe no acervo.");
        } else {
            main_controller.getAcervo().adicionarItem(novo_filme); // Adiciona ao acervo
            main_controller.salvarDados(); // Salva os dados no arquivo
            mostrarAlerta(AlertType.INFORMATION, "Sucesso!", "Filme Cadastrado", "O filme '" + titulo + "' foi cadastrado e salvo com sucesso!");
            limparCampos();
            }
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String cabecalho, String corpo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(corpo);
        alert.showAndWait();
    }

    private void limparCampos() {
        field_titulo.clear();
        field_ano.clear();
        field_direcao.clear();
        field_elenco.clear();
        field_genero.clear();
        field_onde_assistir.clear();
        field_titulo_original.clear();
    }
}