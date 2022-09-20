package SportRadarScoreboard;


import javafx.scene.Scene;
import javafx.stage.Stage;
import SportRadarScoreboard.Controller.ScoreBoardController;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {

        ScoreBoardController scoreBoardController = new ScoreBoardController();
        Scene scene = new Scene(scoreBoardController.getInitialBoard());

        primaryStage.setScene(scene);
        primaryStage.setTitle("SportRadar Score Board");
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}