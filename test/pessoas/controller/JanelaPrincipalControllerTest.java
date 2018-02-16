package pessoas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import pessoas.MainApp;
import pessoas.util.JavaFXThreadingRule;

/**
 * Classe de testes unitários para a classe {@link JanelaPrincipalController}
 * @author Danilo Cruz
 */
public class JanelaPrincipalControllerTest {
    
    // Rule para inicializar threads JavaFX com os testes 
    // Código provem de http://andrewtill.blogspot.com.br/2012/10/junit-rule-for-javafx-controller-testing.html
    
    @Rule
    public JavaFXThreadingRule javaFXRule = new JavaFXThreadingRule();

    private MainApp mainApp;
    private JanelaPrincipalController controller;

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
            loader.setLocation(MainApp.class.getResource("view/JanelaPrincipal.fxml"));
            
            loader.load();
            
            controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipalControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#initialize(java.net.URL, java.util.ResourceBundle)}.
     */
    @Test
    public void testInitialize() {
        URL url = null;
        ResourceBundle rb = null;
        controller.initialize(url, rb);
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#acaoAdicionarPessoa()}.
}     */
    @Test
    public void testAcaoAdicionarPessoa() {
        controller.acaoAdicionarPessoa();
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#acaoEditarPessoa()}.
     */
    @Test
    public void testAcaoEditarPessoa() {
        controller.acaoEditarPessoa();
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#acaoRemoverPessoa()}.
     */
    @Test
    public void testAcaoRemoverPessoa() {
        controller.acaoRemoverPessoa();
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#acaoSalvarTxt()}.
     */
    @Test
    public void testAcaoSalvarTxt() {
        controller.acaoSalvarTxt();
    }

    /**
     * Teste para o método {@link JanelaPrincipalController#atualizaTabela()}.
     */
    @Test
    public void testAtualizaTabela() {
        controller.atualizaTabela();
    }
}
