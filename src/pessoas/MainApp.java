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

public class MainApp extends Application {

    private static Stage primaryStage;
    private static BorderPane janelaPrincipal;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gerenciamento de Pessoas");

        iniciaJanelaPrincipal();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void iniciaJanelaPrincipal() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/JanelaPrincipal.fxml"));
            janelaPrincipal = loader.load();

            Scene scene = new Scene(janelaPrincipal);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean iniciaEdicaoDialog(Pessoa pessoa) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EdicaoDialog.fxml"));
            AnchorPane pane = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Editar Pessoa");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            dialog.setScene(scene);

            EdicaoDialogController controller = loader.getController();
            controller.setStage(dialog);
            controller.setPessoa(pessoa);

            dialog.showAndWait();
            return controller.isOkClicked();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
}
