package pessoas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pessoas.controller.EdicaoDialogController;
import pessoas.model.Pessoa;

/** 
 * Classe inicializadora da aplicação e das janelas do sistema.
 * @author Danilo Cruz
 */

public class MainApp extends Application {
    
    /** Stage da janela principal */
    private static Stage primaryStage;
    
    /** Scene da janela principal */
    private static BorderPane janelaPrincipal;
    
    /**
     * Método padrão do JavaFX para iniciar a GUI.
     * @param stage o palco (stage) principal onde os componentes visíveis são posicionados.
     */

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gerenciamento de Pessoas");

        iniciaJanelaPrincipal();
    }
    
    /**
     * Método inicializador da aplicação.
     * @param args argumentos opcionais
     */

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Método inicializador da janela principal do sistema.
     */

    public void iniciaJanelaPrincipal() {
        try {
            
            // Inicialização do arquivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/JanelaPrincipal.fxml"));
            janelaPrincipal = loader.load();

            // Inicialização dos objetos necessários à GUI
            Scene scene = new Scene(janelaPrincipal);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método inicializador da janela utilizada na adição e edição de entidades do sistema.
     * @param pessoa Pessoa a ser adicionada ou editada
     * @return retorna verdadeiro caso o usuário clique no botão OK para confirmar a adição ou alteração; retorna falso em caso contrário 
     */

    public static boolean iniciaEdicaoDialog(Pessoa pessoa) {
        try {
            // Inicialização do arquivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EdicaoDialog.fxml"));
            AnchorPane pane = loader.load();

            // Inicialização dos objetos necessários à GUI
            Stage dialog = new Stage();
            dialog.setTitle("Editar Pessoa");
            dialog.initModality(Modality.WINDOW_MODAL); // Opcao adicionada para tornar a janela em modal
            dialog.initOwner(primaryStage); // Define a janela-mãe da modal

            Scene scene = new Scene(pane);
            dialog.setScene(scene);

            // Inicialização do controller 
            EdicaoDialogController controller = loader.getController();
            controller.setStage(dialog);
            controller.setPessoa(pessoa);

            
            // Exibição da GUI
            dialog.showAndWait();
            return controller.isOkClicked();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Método getter do stage da janela principal.
     * @return {@link MainApp#primaryStage}
     */

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
