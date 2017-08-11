package bus.maxime.steganographie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("layout.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Steganography Demo");
        stage.setScene(scene);
        stage.show();
    }
}
