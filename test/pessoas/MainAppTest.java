package pessoas;

import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import pessoas.model.Pessoa;
import pessoas.util.JavaFXThreadingRule;

/**
 * Classe de testes unitários para a classe {@link MainApp}
 * @author Danilo Cruz
 */

public class MainAppTest {
    
    // Rule para inicializar threads JavaFX com os testes 
    // Código provem de http://andrewtill.blogspot.com.br/2012/10/junit-rule-for-javafx-controller-testing.html
    @Rule
    public JavaFXThreadingRule javaFXRule = new JavaFXThreadingRule();
    
    public MainApp mainApp;
    
    /**
     * Chamado antes de todo método de teste
     */
    @Before
    public void iniciaTeste() {
        mainApp = new MainApp();
    }

    /**
     * Teste para o método {@link MainApp#start(javafx.stage.Stage) }.
     */
    @Test
    public void testStart() {
        // Teste com um novo stage
        Stage stage = new Stage();
        mainApp.start(stage);
    }

    /**
     * Teste para o método {@link MainApp#iniciaJanelaPrincipal()}.
     */
    @Test
    public void testIniciaJanelaPrincipal() {
        mainApp.iniciaJanelaPrincipal();
    }

    /**
     * Teste para o método {@link MainApp#iniciaEdicaoDialog(pessoas.model.Pessoa)}.
     */
    @Test
    public void testIniciaEdicaoDialog() {
        
        // Teste com nova pessoa
        Pessoa pessoa = new Pessoa();
        boolean result = MainApp.iniciaEdicaoDialog(pessoa);
        Assert.assertEquals(false, result);
        
        // Teste com pessoa nula
        pessoa = null;
        MainApp.iniciaEdicaoDialog(pessoa);
    }
}
