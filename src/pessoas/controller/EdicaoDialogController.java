package pessoas.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import pessoas.MainApp;
import pessoas.model.Pessoa;

/**
 * Classe responsável pela funcionalidade da janela de adição / edição de
 * entidades.
 *
 * @author Danilo Cruz
 */
public class EdicaoDialogController implements Initializable {

    /**
     * Campo de texto responsável pelo campo 'nome' da pessoa
     */
    @FXML
    private TextField txNome;

    /**
     * Campo de texto responsável pelo campo 'cpf' da pessoa
     */
    @FXML
    private TextField txCpf;

    /**
     * Campo de texto responsável pelo campo 'telefone' da pessoa
     */
    @FXML
    private TextField txTelefone;

    /**
     * Campo de texto responsável pelo campo 'email' da pessoa
     */
    @FXML
    private TextField txEmail;

    /**
     * Campo de texto responsável pelo campo 'cep' da pessoa
     */
    @FXML
    private TextField txCep;

    /**
     * Campo de texto responsável pelo campo 'logradouro' da pessoa
     */
    @FXML
    private TextField txLogradouro;

    /**
     * Campo de texto responsável pelo campo 'numEndereco' da pessoa
     */
    @FXML
    private TextField txNumEndereco;

    /**
     * Campo de texto responsável pelo campo 'complemento' da pessoa
     */
    @FXML
    private TextField txComplemento;

    /**
     * Campo de texto responsável pelo campo 'bairro' da pessoa
     */
    @FXML
    private TextField txBairro;

    /**
     * Campo de texto responsável pelo campo 'cidade' da pessoa
     */
    @FXML
    private TextField txCidade;

    /**
     * Lista responsável por conter as opcoes selecionáveis de
     * {@link EdicaoDialogController#txUf}
     */
    private static final ObservableList<String> estados = FXCollections.
            observableArrayList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES",
                    "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
                    "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

    /**
     * Campo de seleção responsável pelo campo 'uf' da pessoa
     */
    @FXML
    private ComboBox<String> txUf;

    /**
     * Palco contendo os elementos visíveis da GUI
     */
    private Stage stage;

    /**
     * campo contendo o resultado da ação do usuário
     */
    private boolean OkClicked = false;

    /**
     * instância da entidade modelo para realizar manipulações
     */
    private Pessoa pessoa;

    /**
     * Método responsável pela inicialização do controlador. Invocado após o
     * carregamento do arquivo FXML em {@link MainApp#iniciaJanelaPrincipal}. Os
     * argumentos do método não são utilizados na implementação desta aplicação.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
            Inicialização dos campos TextField. 
            Cada campo recebe um TextFormatter responsável por garantir a integridade
            dos dados a serem recebidos; em campos com texto existem limitadores
            de caracteres, e em campos somente números tanto a condição supracitada 
            quanto a condição de haver apenas números são verificadas.
         */
        txNome.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 100) {
                return change;
            }
            return null;
        }));

        txCpf.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (text.matches("[0-9]*")
                    && change.getControlNewText().length() <= 11) {
                return change;
            }
            return null;
        }));

        txTelefone.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 20) {
                return change;
            }
            return null;
        }));

        txEmail.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 100) {
                return change;
            }
            return null;
        }));

        txCep.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (text.matches("[0-9]*")
                    && change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        }));

        txLogradouro.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 255) {
                return change;
            }
            return null;
        }));

        txNumEndereco.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (text.matches("[0-9]*")
                    && change.getControlNewText().length() <= 10) {
                return change;
            }
            return null;
        }));

        txComplemento.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 255) {
                return change;
            }
            return null;
        }));

        txBairro.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 100) {
                return change;
            }
            return null;
        }));

        txCidade.setTextFormatter(new TextFormatter<>((change) -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (change.getControlNewText().length() <= 50) {
                return change;
            }
            return null;
        }));

        txUf.setItems(estados); // Inicializa o ComboBox com a lista de estados
    }

    /**
     * Método responsável pela ação do botão OK.
     */
    @FXML
    private void acaoBotaoOK() {
        /*
            Se todos os dados estão dentro do padrão exigido transfere os dados
            para a instância de Pessoa
         */
        if (validarDados()) {
            pessoa.setNome(txNome.getText());
            pessoa.setCpf(txCpf.getText());
            pessoa.setTelefone(txTelefone.getText());
            pessoa.setEmail(txEmail.getText());
            pessoa.setCep(txCep.getText());
            pessoa.setLogradouro(txLogradouro.getText());
            pessoa.setNumEndereco(txNumEndereco.getText());
            pessoa.setComplemento(txComplemento.getText());
            pessoa.setBairro(txBairro.getText());
            pessoa.setCidade(txCidade.getText());
            pessoa.setUf(txUf.getSelectionModel().getSelectedItem());

            OkClicked = true; // Informa à janela principal que o usuário clicou em OK
            stage.close(); // fecha a janela
        }
    }

    /**
     * Método responsável pela ação do botão Cancelar.
     */
    @FXML
    private void acaoBotaoCancelar() {
        stage.close();
    }

    /**
     * Método responsável pela ação do botão de consulta do CEP.
     */
    @FXML
    private void acaoBotaoCEP() {
        try {
            if (txCep.getText().length() != 8) {
                // Verifica se o CEP está com menos de 8 caracteres
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campo inválido");
                alert.setHeaderText("Por favor, corrija o campo");
                alert.setContentText("CEP deve conter 8 algarismos. Verifique seu CEP!");
                alert.showAndWait();
            }

            // Coleta os dados da API pública
            Map<String, String> dados = parseJSON(getInfosAPI(txCep.getText()));

            // Se a API retornar erro, exibe uma mensagem de erro ao usuário
            if (dados.containsKey("erro")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("CEP Incorreto");
                alert.setHeaderText("CEP digitado está incorreto");
                alert.setContentText("Verifique seu CEP e tente novamente");
                alert.showAndWait();
                return;
            }

            // Preenche os campos utilizando os dados retornados
            /*
                A API retorna o campo 'complemento' como complemento de 
                logradouro (ex. lado par, CEP até num. X); no campo logradouro 
                estou concatenando o campo logradouro e complemento para oferecer
                um resultado mais íntegro.
            
                O campo 'complemento' da GUI é utilizado para observações (ex. 
                2o andar, loja comercial etc.)
             */
            txLogradouro.setText(dados.get("logradouro") + " " + dados.get("complemento"));
            txBairro.setText(dados.get("bairro"));
            txCidade.setText(dados.get("localidade"));
            txUf.setValue(dados.get("uf"));
        } catch (Exception ex) {
            // Exibe alerta em caso de erro na API.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de API");
            alert.setHeaderText("Ocorreu um erro na consulta dos dados");
            alert.setContentText("Tente novamente mais tarde");
        }
    }

    /**
     * Método responsável por realizar o parse do JSON retornado pela API.
     *
     * @param json a {@link String} contendo o JSON retornado pela API
     * @return {@link HashMap} contendo as duplas chave-valor do JSON
     * @throws Exception em caso de erro no webservice (ex. site fora do ar)
     */
    private HashMap<String, String> parseJSON(String json) throws Exception {

        // remove as chaves que delimitam o JSON
        json = json.replaceAll("[{}]", "");

        // Expressão regular localizando os pares chave-valor no JSON
        Pattern keyValue = Pattern.compile("\"\\w*\": ?\"?(\\p{L}|\\s|-|\\d)*\"?");
        Matcher m = keyValue.matcher(json);

        HashMap<String, String> properties = new HashMap<>();

        // Iteração nas strings encontradas pela expressão regular
        while (m.find()) {
            String result = m.group();
            result = result.replaceAll(": ", ":"); // Ajustes na string
            String[] values = result.split(":"); // Separa chave e valor
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replaceAll("[\"|,]", ""); // Ajustes na string
            }
            properties.put(values[0], values[1]); // Insere a dupla no HashMap
        }

        return properties;
    }

    /**
     * Método responsável pela consulta à API pública.
     *
     * @param cep {@link String} contendo o CEP a ser consultado na API.
     * @return {@link String} contendo o JSON com as informações resultantes.
     * @throws Exception em caso de erro no webservice (ex. site fora do ar)
     */
    private String getInfosAPI(String cep) throws Exception {

        // Conexão com o webservice
        StringBuilder sb = new StringBuilder();
        URL api = new URL("http://viacep.com.br/ws/" + cep + "/json");
        
        // parâmetro UTF-8 necessário para exibir acentos na aplicação corretamente
        BufferedReader br = new BufferedReader(new InputStreamReader(api.openStream(), "UTF-8"));
        String line;

        // Iteração coletando os dados retornados
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        return sb.toString();
    }

    /**
     * Método verificando se o texto no {@link TextField} é vazio
     *
     * @param campo {@link TextField} a ser verificado
     * @return {@link Boolean} {@code true} se o campo está vazio, {@code false}
     * caso contrário
     */
    private boolean verificaCampoVazio(TextField campo) {
        return campo.getText() == null || campo.getText().length() == 0;
    }

    /**
     * Método responsável pela validação dos dados.
     *
     * @return {@link Boolean} {@code true} se os dados estiverem dentro do
     * padrão exigido, {@code false} caso contrário
     */
    private boolean validarDados() {
        String msgErro = "";

        if (verificaCampoVazio(txNome)) {
            msgErro += "Preencha o nome!\n";
        }

        if (verificaCampoVazio(txCpf)) {
            msgErro += "Preencha o CPF!\n";
        } else {

            if (validarCPF(txCpf.getText()) == false) {
                msgErro += "CPF inválido!\n";
            } else if (txCpf.getText().length() != 11) {
                msgErro += "CPF é composto por 11 algarismos; verifique o CPF!\n";
            }
        }

        if (verificaCampoVazio(txCep)) {
            msgErro += "Preencha o CEP!\n";
        } else if (txCep.getText().length() != 8) {
            msgErro += "CEP é composto por 8 algarismos. Verifique o CEP!\n";
        }

        if (verificaCampoVazio(txLogradouro)) {
            msgErro += "Preencha o logradouro!\n";
        }

        if (verificaCampoVazio(txNumEndereco)) {
            msgErro += "Preencha o número do local!\n";
        }

        if (verificaCampoVazio(txBairro)) {
            msgErro += "Preencha o bairro!\n";
        }

        if (verificaCampoVazio(txCidade)) {
            msgErro += "Preencha a cidade!\n";
        }

        if (msgErro.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campo(s) inválido(s)");
            alert.setHeaderText("Por favor, corrija os campos listados abaixo:");
            alert.setContentText(msgErro);
            alert.showAndWait();

            return false;
        }
    }

    /**
     * Método responsável pela validação do CPF informado.
     *
     * @param cpf {@link String} contendo o CPF
     * @return {@code true} se o CPF estiver válido
     */
    private boolean validarCPF(String cpf) {
        int acum1 = 0, acum2 = 0, dv1, dv2;

        String[] chars = cpf.split("");
        int[] valores = new int[chars.length];

        // Parse da String para valores inteiros
        for (int i = 0; i < chars.length; i++) {
            valores[i] = Integer.parseInt(chars[i]);
        }

        // Cálculo inicial do primeiro dígito verificador
        for (int i = 0, mult = 10; i <= 8; i++, mult--) {
            acum1 += mult * valores[i];
        }

        // Cálculo inicial do segundo dígito verificador
        for (int i = 0, mult = 11; i <= 9; i++, mult--) {
            acum2 += mult * valores[i];
        }

        // Cálculo final do primeiro dígito verificador
        acum1 %= 11;
        dv1 = acum1 < 2 ? 0 : 11 - acum1;

        // Cálculo final do segundo dígito verificador
        acum2 %= 11;
        dv2 = acum2 < 2 ? 0 : 11 - acum2;

        return (dv1 == valores[9] && dv2 == valores[10]);
    }

    /**
     * Método getter do atributo {@link EdicaoDialogController#OkClicked}.
     *
     * @return atributo {@link EdicaoDialogController#OkClicked}
     */
    public boolean isOkClicked() {
        return OkClicked;
    }

    /**
     * Método getter do atributo {@link EdicaoDialogController#stage}.
     *
     * @return atributo {@link EdicaoDialogController#stage}
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Método setter do atributo {@link EdicaoDialogController#stage}.
     *
     * @param stage {@link Stage} a ser atribuído ao atributo
     * {@link EdicaoDialogController#stage}
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método setter do atributo {@link EdicaoDialogController#pessoa}. Neste
     * atributo os campos de texto também são iniciados com os dados da entidade
     * {@link EdicaoDialogController#pessoa}.
     *
     * @param pessoa {@link Pessoa} a ser atribuído ao atributo
     * {@link EdicaoDialogController#pessoa}
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;

        txNome.setText(pessoa.getNome());
        txCpf.setText(pessoa.getCpf());
        txTelefone.setText(pessoa.getTelefone());
        txEmail.setText(pessoa.getEmail());
        txCep.setText(pessoa.getCep());
        txLogradouro.setText(pessoa.getLogradouro());
        txNumEndereco.setText(pessoa.getNumEndereco());
        txComplemento.setText(pessoa.getComplemento());
        txBairro.setText(pessoa.getBairro());
        txCidade.setText(pessoa.getCidade());
        txUf.setValue(pessoa.getUf());
    }
}
