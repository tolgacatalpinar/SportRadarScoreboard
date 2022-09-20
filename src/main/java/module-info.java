module tolga.sportradarscoreboard {
    requires javafx.controls;
    requires javafx.fxml;


    opens SportRadarScoreboard to javafx.fxml;
    exports SportRadarScoreboard;
    exports SportRadarScoreboard.Model;
    opens SportRadarScoreboard.Model to javafx.fxml;
    exports SportRadarScoreboard.Controller;
    opens SportRadarScoreboard.Controller to javafx.fxml;
}