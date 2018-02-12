package pessoas.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pessoas.MainApp;
import pessoas.model.Pessoa;

public class JanelaPrincipalController implements Initializable {

    @FXML
    private TableView<Pessoa> tabelaPessoas;

    @FXML
    private TableColumn<Pessoa, String> colunaId;

    @FXML
    private TableColumn<Pessoa, String> colunaNome;

    @FXML
    private TableColumn<Pessoa, String> colunaCpf;

    @FXML
    private TableColumn<Pessoa, String> colunaTelefone;

    @FXML
    private TableColumn<Pessoa, String> colunaEmail;

    @FXML
    private TableColumn<Pessoa, String> colunaCep;

    @FXML
    private TableColumn<Pessoa, String> colunaLogradouro;

    @FXML
    private TableColumn<Pessoa, String> colunaNumEndereco;

    @FXML
    private TableColumn<Pessoa, String> colunaComplemento;

    @FXML
    private TableColumn<Pessoa, String> colunaBairro;

    @FXML
    private TableColumn<Pessoa, String> colunaCidade;

    @FXML
    private TableColumn<Pessoa, String> colunaUf;

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btEditar;

    @FXML
    private Button btRemover;

    @FXML
    private Button btSalvar;
    
    @FXML
    private TextField txPesquisa;

    private ObservableList<Pessoa> listaPessoas = FXCollections.observableArrayList();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabelaPessoas.setItems(listaPessoas);
        colunaId.setCellValueFactory(dadoCelula -> dadoCelula.getValue().idProperty());
        colunaNome.setCellValueFactory(dadoCelula -> dadoCelula.getValue().nomeProperty());
        colunaCpf.setCellValueFactory(dadoCelula -> dadoCelula.getValue().cpfProperty());
        colunaTelefone.setCellValueFactory(dadoCelula -> dadoCelula.getValue().telefoneProperty());
        colunaEmail.setCellValueFactory(dadoCelula -> dadoCelula.getValue().emailProperty());
        colunaCep.setCellValueFactory(dadoCelula -> dadoCelula.getValue().cepProperty());
        colunaLogradouro.setCellValueFactory(dadoCelula -> dadoCelula.getValue().logradouroProperty());
        colunaNumEndereco.setCellValueFactory(dadoCelula -> dadoCelula.getValue().numEnderecoProperty());
        colunaComplemento.setCellValueFactory(dadoCelula -> dadoCelula.getValue().complementoProperty());
        colunaBairro.setCellValueFactory(dadoCelula -> dadoCelula.getValue().bairroProperty());
        colunaCidade.setCellValueFactory(dadoCelula -> dadoCelula.getValue().cidadeProperty());
        colunaUf.setCellValueFactory(dadoCelula -> dadoCelula.getValue().ufProperty());
        
        txPesquisa.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    acaoPesquisa();
                });

        atualizaTabela();
    }

    @FXML
    private void acaoAdicionarPessoa() {
        try {
            Pessoa pessoa = new Pessoa();
            boolean ok = MainApp.iniciaEdicaoDialog(pessoa);
            if (ok) {
                pessoa.adicionaPessoa();
                listaPessoas.add(pessoa);
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro no cadastro");
            alert.setContentText("Houve um erro no cadastro dos novos dados.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void acaoEditarPessoa() {
        try {
            Pessoa pessoa = tabelaPessoas.getSelectionModel().getSelectedItem();
            if (pessoa != null) {
                boolean ok = MainApp.iniciaEdicaoDialog(pessoa);
                if (ok) {
                    pessoa.editaPessoa();
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro na edição");
            alert.setContentText("Houve um erro na edição da pessoa.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void acaoRemoverPessoa() {
        try {
            Pessoa pessoa = tabelaPessoas.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de exclusão");
            alert.setHeaderText("Alerta de exclusão de pessoa");
            alert.setContentText("Tem certeza de que deseja remover esta pessoa?\n"
                    + "Esta ação é irreversível");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                pessoa.removePessoa();
                listaPessoas.remove(pessoa);
            }
        } catch (Exception ex) {

        }
    }

    @FXML
    private void acaoSalvarTxt() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Escolha o caminho para salvar o arquivo");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de texto (.txt)", "*.txt"));
            File arquivo = chooser.showSaveDialog(MainApp.getPrimaryStage());

            if (arquivo != null) {
                BufferedWriter bw = new BufferedWriter(
                        Files.newBufferedWriter(arquivo.toPath(), Charset.forName("UTF-8")));
                for (Pessoa p : tabelaPessoas.getItems()) {
                    bw.write(p.pessoaString() + System.lineSeparator());
                }
                bw.close();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Arquivo salvo com sucesso");
                alert.setHeaderText("O arquivo de texto foi salvo com sucesso");
                alert.setContentText("Caminho do arquivo: " + arquivo.getAbsolutePath());
                alert.showAndWait();

            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na gravação do arquivo!");
            alert.setHeaderText("Ocorreu um erro na gravação do arquivo de texto.");
            alert.setContentText("Mensagem de erro: " + ex.getMessage());

        }
    }
    
    @FXML
    private void acaoPesquisa() {
        if(txPesquisa.getText().equals("")) {
            tabelaPessoas.setItems(listaPessoas);
        } else {
            tabelaPessoas.setItems(encontrarPessoas());
        }
    }
    
    private ObservableList<Pessoa> encontrarPessoas() {
        ObservableList<Pessoa> pessoasEncontradas = 
                FXCollections.observableArrayList();
        
        for(Pessoa pessoa : listaPessoas) {
            if(pessoa.getNome().contains(txPesquisa.getText())) {
                pessoasEncontradas.add(pessoa);
            }
        }
        
        return pessoasEncontradas;
    }

    private void atualizaTabela() {
        try {
            listaPessoas.clear();
            listaPessoas.addAll(Pessoa.getAll());
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro de atualização da tabela");
            alert.setContentText("Houve um erro na atualização da tabela.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    public ObservableList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }
}
