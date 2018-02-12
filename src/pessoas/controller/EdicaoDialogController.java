package pessoas.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pessoas.model.Pessoa;

public class EdicaoDialogController implements Initializable {

    @FXML
    private TextField txNome;

    @FXML
    private TextField txCpf;

    @FXML
    private TextField txTelefone;

    @FXML
    private TextField txEmail;

    @FXML
    private TextField txCep;

    @FXML
    private TextField txLogradouro;

    @FXML
    private TextField txNumEndereco;

    @FXML
    private TextField txComplemento;

    @FXML
    private TextField txBairro;

    @FXML
    private TextField txCidade;

    private static final ObservableList<String> estados = FXCollections.
            observableArrayList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES",
                    "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
                    "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

    @FXML
    private ComboBox<String> txUf;

    private Stage stage;
    private boolean OkClicked = false;
    private Pessoa pessoa;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txUf.setItems(estados);
        mostrarPessoa(null);
    }

    @FXML
    private void acaoBotaoOK() {
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

            OkClicked = true;
            stage.close();
        }
    }

    @FXML
    private void acaoBotaoCancelar() {
        stage.close();
    }

    @FXML
    private void acaoBotaoCEP() {
        try {
            Map<String, String> dados = parseJSON(getInfosAPI(txCep.getText()));

            if (dados.containsKey("erro")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("CEP Incorreto");
                alert.setHeaderText("CEP digitado está incorreto");
                alert.setContentText("Verifique seu CEP e tente novamente");
                alert.showAndWait();
                return;
            }

            txLogradouro.setText(dados.get("logradouro") + " " + dados.get("complemento"));
            txBairro.setText(dados.get("bairro"));
            txCidade.setText(dados.get("localidade"));
            txUf.setValue(dados.get("uf"));
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de API");
            alert.setHeaderText("Ocorreu um erro na consulta dos dados");
            alert.setContentText("Tente novamente mais tarde");
        }
    }

    // Transforma a string estruturada em um HashMap.
    private HashMap<String, String> parseJSON(String json) throws Exception {

        json = json.replaceAll("[{}]", "");

        Pattern keyValue = Pattern.compile("\"\\w*\": ?\"(\\p{L}|\\s|-|\\d)*\"");
        Matcher m = keyValue.matcher(json);

        HashMap<String, String> properties = new HashMap<>();

        while (m.find()) {
            String result = m.group();
            result = result.replaceAll(": ", ":");
            String[] values = result.split(":");
            for(int i = 0; i < values.length; i++) {
                values[i]  = values[i].replaceAll("[\"|,]", "");
            }
            properties.put(values[0], values[1]);
        }

        return properties;
    }

    // Consulta a API pública. Retorna o JSON resultante ou uma exceção em caso de erro. 
    private String getInfosAPI(String cep) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL api = new URL("http://viacep.com.br/ws/" + cep + "/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(api.openStream()));
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private boolean verificaCampoVazio(TextField campo) {
        return campo.getText() == null || campo.getText().length() == 0;
    }

    private boolean validarDados() {
        String msgErro = "";

        if (verificaCampoVazio(txNome)) {
            msgErro += "Preencha o nome!\n";
        }

        if (verificaCampoVazio(txCpf)) {
            msgErro += "Preencha o CPF!\n";
        } else {
            try {
                Long.parseLong(txCpf.getText());
                if (validarCPF(txCpf.getText()) == false) {
                    msgErro += "CPF inválido!\n";
                } else if (txCpf.getText().length() != 11) {
                    msgErro += "CPF é composto por 11 algarismos; verifique o CPF!\n";
                }

            } catch (NumberFormatException e) {
                msgErro += "CPF deve ser composto por somente números!\n";
            }
        }

        if (verificaCampoVazio(txCep)) {
            msgErro += "Preencha o CEP!\n";
        } else if (txCep.getText().length() != 8) {
            msgErro += "CEP é composto por 8 algarismos. Verifique o CEP!\n";
        } else {
            try {
                Long.parseLong(txCep.getText());
            } catch (Exception ex) {
                msgErro += "CEP deve ser composto por somente números!\n";
            }
        }

        if (verificaCampoVazio(txLogradouro)) {
            msgErro += "Preencha o logradouro!\n";
        }

        if (verificaCampoVazio(txNumEndereco)) {
            msgErro += "Preencha o número do local!\n";
        } else {
            try {
                Integer.parseInt(txNumEndereco.getText());
            } catch (Exception ex) {
                msgErro += "Número do local deve ser composto por somente números!\n";
            }
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

    private void mostrarPessoa(Pessoa pessoa) {
        if (pessoa != null) {
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
        } else {
            txNome.setText("");
            txCpf.setText("");
            txTelefone.setText("");
            txEmail.setText("");
            txCep.setText("");
            txLogradouro.setText("");
            txNumEndereco.setText("");
            txComplemento.setText("");
            txBairro.setText("");
            txCidade.setText("");
        }
    }

    private boolean validarCPF(String cpf) {
        int acum1 = 0, acum2 = 0, dv1, dv2;

        String[] chars = cpf.split("");
        int[] valores = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            valores[i] = Integer.parseInt(chars[i]);
        }

        for (int i = 0, mult = 10; i <= 8; i++, mult--) {
            acum1 += mult * valores[i];
        }

        for (int i = 0, mult = 11; i <= 9; i++, mult--) {
            acum2 += mult * valores[i];
        }

        acum1 %= 11;
        dv1 = acum1 < 2 ? 0 : 11 - acum1;

        acum2 %= 11;
        dv2 = acum2 < 2 ? 0 : 11 - acum2;

        return (dv1 == valores[9] && dv2 == valores[10]);
    }

    public boolean isOkClicked() {
        return OkClicked;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
