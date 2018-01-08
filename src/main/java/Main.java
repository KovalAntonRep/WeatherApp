import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.City;
import model.OpenWeatherAppClient;
import service.CityWeatherDataRecorder;
import service.PropertyReader;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    private static String cityId = new PropertyReader("cities.properties").getProperty("cityid.kyiv");
    private static OpenWeatherAppClient openWeatherAppClient = new OpenWeatherAppClient();

    private static void recordToUSBIfPossible(City city) {
        boolean saved = false;

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        for (File root : roots) {
            if (fsv.getSystemTypeDescription(root) != null && fsv.getSystemTypeDescription(root).contains("USB")) {
                try {
                    CityWeatherDataRecorder.recordCity(city, root.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saved = true;
                break;
            }
        }

        if(!saved) {
            System.out.println("No USB driver available, choose another path: ");

            while(!saved) {
                Scanner reader = new Scanner(System.in);
                try {
                    CityWeatherDataRecorder.recordCity(city, reader.next());
                    saved = true;
                } catch (IOException e) {
                    System.out.println("The specified path is inappropriate, choose another path: ");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        City city = openWeatherAppClient.getInformationAboutCity(cityId);

        primaryStage.setTitle("Kyiv Weather Info");
        primaryStage.setResizable(false);
        FlowPane flowPane = new FlowPane();

        Label[] labels = {
                new Label("City: " + city.getName()),
                new Label("Temperature (Celsius degree): " + city.getTemperature()),
                new Label("Humidity (%): " + city.getHumidity()),
                new Label("Atmospheric pressure (MoM): " + city.getPressure()),
                new Label("Wind speed (m/s): " + city.getWindSpeed()),
                new Label("Wind direction (degree): " + city.getWindDirection())
        };

        for (Label label : labels ) {
            label.setPrefSize(200, 20);
        }

        flowPane.getChildren().addAll(labels);

        Scene mainScene = new Scene(flowPane, 200, 130);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        recordToUSBIfPossible(city);
    }
}
