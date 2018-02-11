package pessoas.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    @FXML
    private TextField txUf;

    private Stage stage;
    private boolean OkClicked = false;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarPessoa(null);
    }

    @FXML
    private void acaoBotaoOK() {
        if (validarDados()) {
            try {
                Pessoa pessoa = new Pessoa(txNome.getText(), txCpf.getText(),
                        txTelefone.getText(), txEmail.getText(), txCep.getText(),
                        txLogradouro.getText(), txNumEndereco.getText(),
                        txComplemento.getText(), txBairro.getText(),
                        txCidade.getText(), txUf.getText());
                Pessoa.adicionaPessoa(pessoa);
                OkClicked = true;
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de atualização da tabela");
                alert.setHeaderText("Erro no cadastro");
                alert.setContentText("Houve um erro no cadastro dos novos dados.\n"
                        + "Mensagem de erro: " + ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void acaoBotaoCancelar() {
        stage.close();
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

        } else if (txCpf.getText().length() != 11) {
            msgErro += "CPF é composto por 11 algarismos; verifique o CPF!\n";
        } else {
            try {
                Long.parseLong(txCpf.getText());
                if (validarCPF(txCpf.getText()) == false) {
                    msgErro += "CPF inválido!\n";
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

        if (verificaCampoVazio(txUf)) {
            msgErro += "Preencha o estado!\n";
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
            txUf.setText(pessoa.getUf());
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
            txUf.setText("");
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

        if (dv1 == valores[9] && dv2 == valores[10]) {
            return true;
        } else {
            return false;
        }

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
}
