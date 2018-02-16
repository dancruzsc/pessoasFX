package pessoas.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import pessoas.MainApp;
import pessoas.model.Pessoa;
import pessoas.util.JavaFXThreadingRule;

/**
 * Classe de testes unitários para a classe {@link EdicaoDialogController}.
 * @author Danilo Cruz
 */

public class EdicaoDialogControllerTest {

    private EdicaoDialogController controller;
    private MainApp mainApp;
    
    // Rule para inicializar threads JavaFX com os testes 
    // Código provem de http://andrewtill.blogspot.com.br/2012/10/junit-rule-for-javafx-controller-testing.html

    @Rule
    public JavaFXThreadingRule javaFXRule = new JavaFXThreadingRule();

    /**
     * Chamado antes de todo método de teste
     */
    @Before
    public void setUp() {
        try {
            Stage stage = new Stage();
            mainApp = new MainApp();
            mainApp.start(stage);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EdicaoDialog.fxml"));

            loader.load();

            controller = loader.getController();

            Pessoa pessoa = new Pessoa("15", "Teste JUnit", "36609312419",
                    "(47) 3344-2232", "nome@dominio.com", "01001001",
                    "Praça da Sé lado par", "1515", "", "Sé", "São Paulo", "SP");
            controller.setPessoa(pessoa);
            controller.setStage(new Stage());
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipalControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#parseJSON(java.lang.String)} em caso de
     * sucesso.
     */
    @Test
    public void testParseJSON() throws Exception {

        // JSON correto
        String json = "{"
                + "  \"cep\": \"01001-001\","
                + "  \"logradouro\": \"Praça da Sé\","
                + "  \"complemento\": \"lado par\","
                + "  \"bairro\": \"Sé\","
                + "  \"localidade\": \"São Paulo\","
                + "  \"uf\": \"SP\","
                + "  \"unidade\": \"\","
                + "  \"ibge\": \"3550308\","
                + "  \"gia\": \"1004\""
                + "}";

        HashMap<String, String> expResult = new HashMap<>();
        expResult.put("cep", "01001-001");
        expResult.put("logradouro", "Praça da Sé");
        expResult.put("complemento", "lado par");
        expResult.put("bairro", "Sé");
        expResult.put("localidade", "São Paulo");
        expResult.put("uf", "SP");
        expResult.put("unidade", "");
        expResult.put("ibge", "3550308");
        expResult.put("gia", "1004");

        HashMap<String, String> result = controller.parseJSON(json);
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#parseJSON(java.lang.String)} em caso de
     * erro.
     */
    @Test
    public void testParseJSONErro() throws Exception {

        // JSON correto
        String json = "{\"erro\": true}";

        HashMap<String, String> expResult = new HashMap<>();
        expResult.put("erro", "true");

        HashMap<String, String> result = controller.parseJSON(json);
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#getInfosAPI(java.lang.String)} em caso de
     * sucesso.
     */
    @Test
    public void testGetInfosAPI() throws Exception {
        String cep = "01001001";
        String expResult = "{"
                + "  \"cep\": \"01001-001\","
                + "  \"logradouro\": \"Praça da Sé\","
                + "  \"complemento\": \"lado par\","
                + "  \"bairro\": \"Sé\","
                + "  \"localidade\": \"São Paulo\","
                + "  \"uf\": \"SP\","
                + "  \"unidade\": \"\","
                + "  \"ibge\": \"3550308\","
                + "  \"gia\": \"1004\""
                + "}";
        String result = controller.getInfosAPI(cep);
        assertEquals(expResult, result);
    }

    /**
     * {@link EdicaoDialogController#getInfosAPI(java.lang.String)} em caso de
     * erro.
     */
    @Test(expected = Exception.class)
    public void testGetInfosAPIException() throws Exception {
        String cep = "0";
        String result = controller.getInfosAPI(cep);
    }

    /**
     * Teste para o método {@link EdicaoDialogController#validarDados()} em caso
     * de sucesso.
     */
    @Test
    public void testValidarDados() {
        boolean expResult = true;
        boolean result = controller.validarDados();
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método {@link EdicaoDialogController#validarDados()} em caso
     * de erro.
     */
    @Test
    public void testValidarDadosErro() {
        controller.setPessoa(new Pessoa());
        boolean expResult = false;
        boolean result = controller.validarDados();
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#validarCPF(java.lang.String)} em caso de
     * CPF válido.
     */
    @Test
    public void testValidarCPF() {
        //CPF válido
        String cpf = "68214000599";
        boolean expResult = true;
        boolean result = controller.validarCPF(cpf);
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#validarCPF(java.lang.String)} em caso de
     * CPF inválido.
     */
    @Test
    public void testValidarCPFInvalido() {
        //CPF válido
        String cpf = "68214000598";
        boolean expResult = false;
        boolean result = controller.validarCPF(cpf);
        assertEquals(expResult, result);
    }

    /**
     * Teste para o método
     * {@link EdicaoDialogController#validarCPF(java.lang.String)} em caso de
     * String contendo valores inválidos.
     */
    @Test(expected = Exception.class)
    public void testValidarCPFMStringInvalida() {
        // CPF com números a menos
        String cpf = "teste";
        controller.validarCPF(cpf);
    }
}
