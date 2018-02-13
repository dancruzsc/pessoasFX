package pessoas.controller;

import java.io.BufferedWriter;
import java.io.File;
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

/**
 * Classe responsável pela funcionalidade da janela principal.
 * @author Danilo Cruz
 */
public class JanelaPrincipalController implements Initializable {

    /**
     * Tabela responsável por conter o registro das pessoas
     */
    @FXML
    private TableView<Pessoa> tabelaPessoas;

    /**
     * Coluna representando o ID da pessoa
     */
    @FXML
    private TableColumn<Pessoa, String> colunaId;

    /**
     * Coluna representando o nome da pessoa
     */
    @FXML
    private TableColumn<Pessoa, String> colunaNome;

    /**
     * Coluna representando o CPF da pessoa
     */
    @FXML
    private TableColumn<Pessoa, String> colunaCpf;

    /**
     * Coluna representando o telefone da pessoa
     */
    @FXML
    private TableColumn<Pessoa, String> colunaTelefone;

    /**
     * Coluna representando o email da pessoa
     */
    @FXML
    private TableColumn<Pessoa, String> colunaEmail;

    /**
     * Coluna representando o CEP onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaCep;

    /**
     * Coluna representando o logradouro onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaLogradouro;

    /**
     * Coluna representando o número da residência onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaNumEndereco;

    /**
     * Coluna representando o complemento da residência da pessoa (exemplo: casa
     * 2, 3º andar etc.)
     */
    @FXML
    private TableColumn<Pessoa, String> colunaComplemento;

    /**
     * Coluna representando o bairro onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaBairro;

    /**
     * Coluna representando a cidade onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaCidade;

    /**
     * Coluna representando o estado onde a pessoa reside
     */
    @FXML
    private TableColumn<Pessoa, String> colunaUf;

    /**
     * Botão responsável pela ação de adicionar novos dados à aplicação.
     */
    @FXML
    private Button btAdicionar;

    /**
     * Botão responsável pela ação de editar dados existentes.
     */
    @FXML
    private Button btEditar;

    /**
     * Botão responsável pela remoção dos dados selecionados.
     */
    @FXML
    private Button btRemover;

    /**
     * Botão responsável pela criação e gravação do arquivo .txt contendo os
     * dados.
     */
    @FXML
    private Button btSalvar;

    /**
     * Campo de texto responsável pela pesquisa de tuplas de dados na aplicação.
     */
    @FXML
    private TextField txPesquisa;

    /**
     * Lista interna do JavaFX responsável por popular a tabela com as tuplas de
     * dados. Qualquer alteração que ocorre na lista ou em seus elementos é
     * automaticamente refletida na tabela
     */
    private ObservableList<Pessoa> listaPessoas = FXCollections.observableArrayList();

    /**
     * Método responsável pela inicialização do controlador. Invocado após o
     * carregamento do arquivo FXML em {@link MainApp#iniciaJanelaPrincipal() }.
     * Os argumentos do método não são utilizados na implementação desta
     * aplicação.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
            Inicialização da tabela.
            O binding da tabela com a lista observável e o binding das colunas
            com os properties da entidade modelo são efetuados neste trecho.
         */
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

        /*
            Binding do evento de edição do campo de pesquisa. Toda vez que o
            campo é editado o método de pesquisa é executado.
            Não foi possível efetuar esse tipo de bind através do FXML, portanto
            ele está sendo efetuado desta maneira. 
         */
        txPesquisa.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    acaoPesquisa();
                });

        // Coleta as entradas na tabela SQL e as insere na lista da tabela GUI.
        atualizaTabela();
    }

    /**
     * Método responsável pela adição de novas entidades à aplicação.
     */
    @FXML
    private void acaoAdicionarPessoa() {
        try {
            /*
                Devido às properties utilizadas na classe do modelo, uma espécie
                de "passagem por referência" ocorre no trecho abaixo. Se todos 
                os dados forem validados no controller da janela de inserção
                de dados, estes já estarão acessíveis ao objeto 'pessoa' abaixo,
                permitindo sua posterior manipulação.
             */

            Pessoa pessoa = new Pessoa();

            // Retorna positivo se o usuário clicou em OK e se os dados forem validados corretamente
            boolean ok = MainApp.iniciaEdicaoDialog(pessoa);
            if (ok) {

                // Adiciona a pessoa à tabela SQL
                pessoa.adicionaPessoa();

                // Adiciona a pessoa à lista com bind na tabela GUI
                listaPessoas.add(pessoa);
            }
        } catch (Exception ex) {
            /*
                Caso ocorra algum problema, uma mensagem de erro aparecerá para o
                usuário alertando o que ocorreu. 
             */

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro no cadastro");
            alert.setContentText("Houve um erro no cadastro dos novos dados.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Método responsável pela edição de entradas existentes na aplicação.
     */
    @FXML
    private void acaoEditarPessoa() {
        try {

            /*
                Coleta a pessoa selecionada na tabela através da lista de elementos. 
            
                Devido às properties utilizadas na classe do modelo, uma espécie
                de "passagem por referência" ocorre no trecho abaixo. Se todos 
                os dados forem validados no controller da janela de edição
                de dados, estes já estarão acessíveis ao objeto 'pessoa' abaixo,
                permitindo sua posterior manipulação.
            
                Devido a característica de reflexão da lista da tabela GUI não
                há necessidade de chamar o método set() desta lista.
             */
            Pessoa pessoa = tabelaPessoas.getSelectionModel().getSelectedItem();
            if (pessoa != null) {
                boolean ok = MainApp.iniciaEdicaoDialog(pessoa);
                if (ok) {

                    // Atualiza as informações da pessoa na tabela SQL
                    pessoa.editaPessoa();
                }
            }
        } catch (Exception ex) {
            /*
                Caso ocorra algum problema, uma mensagem de erro aparecerá para o
                usuário alertando o que ocorreu. 
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro na edição");
            alert.setContentText("Houve um erro na edição da pessoa.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Método responsável pela remoção de entradas existentes na aplicação.
     */
    @FXML
    private void acaoRemoverPessoa() {
        try {
            /*
                Devido às properties utilizadas na classe do modelo, uma espécie
                de "passagem por referência" ocorre no trecho abaixo. Se todos 
                os dados forem validados no controller da janela de inserção
                de dados, estes já estarão acessíveis ao objeto 'pessoa' abaixo,
                permitindo sua posterior manipulação.
             */
            Pessoa pessoa = tabelaPessoas.getSelectionModel().getSelectedItem();

            if (pessoa != null) {
                // Alerta questionando se o usuário realmente deseja remover o usuário. 

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
            }

        } catch (Exception ex) {
            /*
                Caso ocorra algum problema, uma mensagem de erro aparecerá para o
                usuário alertando o que ocorreu. 
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro na exclusão");
            alert.setContentText("Houve um erro na exclusão da pessoa.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Método responsável por gerar e salvar o arquivo .txt no caminho especificado pelo usuário.
     */

    @FXML
    private void acaoSalvarTxt() {
        try {
            // Escolhe o local onde o caminho será salvo.
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Escolha o caminho para salvar o arquivo");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de texto (.txt)", "*.txt"));
            File arquivo = chooser.showSaveDialog(MainApp.getPrimaryStage());
            
            
            // Escreve as informações de cada entrada no arquivo
            if (arquivo != null) {
                BufferedWriter bw = new BufferedWriter(
                        Files.newBufferedWriter(arquivo.toPath(), Charset.forName("UTF-8")));
                for (Pessoa p : tabelaPessoas.getItems()) {
                    bw.write(p.toString() + System.lineSeparator());
                }
                bw.close();

                // Avisa o usuário que houve sucesso na gravação do arquivo.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Arquivo salvo com sucesso");
                alert.setHeaderText("O arquivo de texto foi salvo com sucesso");
                alert.setContentText("Caminho do arquivo: " + arquivo.getAbsolutePath());
                alert.showAndWait();

            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            /*
                Caso ocorra algum problema, uma mensagem de erro aparecerá para o
                usuário alertando o que ocorreu. 
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na gravação do arquivo!");
            alert.setHeaderText("Ocorreu um erro na gravação do arquivo de texto.");
            alert.setContentText("Mensagem de erro: " + ex.getMessage());

        }
    }
    
    /**
     * Método responsável por executar a pesquisa de entradas na aplicação.
     */

    @FXML
    private void acaoPesquisa() {
        if (txPesquisa.getText().equals("")) {
            tabelaPessoas.setItems(listaPessoas);
        } else {
            tabelaPessoas.setItems(encontrarPessoas());
        }
    }
    
    /**
     * Método responsável por atualizar a lista da tabela GUI referente ao texto inserido no {@link TextField} de pesquisa.
     * @return lista contendo os resultados da pesquisa
     */

    private ObservableList<Pessoa> encontrarPessoas() {
        ObservableList<Pessoa> pessoasEncontradas
                = FXCollections.observableArrayList();

        for (Pessoa pessoa : listaPessoas) {
            if (pessoa.getNome().contains(txPesquisa.getText())) {
                pessoasEncontradas.add(pessoa);
            }
        }

        return pessoasEncontradas;
    }

    /**
     * Método responsável por atualizar a tabela através de uma query SQL.
     */
    
    private void atualizaTabela() {
        try {
            listaPessoas.clear();
            listaPessoas.addAll(Pessoa.getAll());
        } catch (Exception ex) {
            /*
                Caso ocorra algum problema, uma mensagem de erro aparecerá para o
                usuário alertando o que ocorreu. 
             */
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de atualização da tabela");
            alert.setHeaderText("Erro de atualização da tabela");
            alert.setContentText("Houve um erro na atualização da tabela.\n"
                    + "Mensagem de erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Método getter da lista da tabela GUI.
     * @return lista da tabela GUI
     */
    public ObservableList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }
}
