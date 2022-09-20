package SportRadarScoreboard.Controller;

import SportRadarScoreboard.Model.Match;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ScoreBoardController {
    VBox scoreBoard;
    Pane matchPane;
    SplitPane splitPane;
    ArrayList<Match> matches = new ArrayList<>();

    public SplitPane getInitialBoard() {
        splitPane = new SplitPane();

        ScrollPane scoreBoard  = getScoreBoard();
        matchPane = getMatchBoard();

        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(scoreBoard, matchPane);
        return splitPane;
    }
    public ScrollPane getScoreBoard()
    {
        ScrollPane scrollPane = new ScrollPane();
        scoreBoard = new VBox();
        HBox titleAndSummaryBox = new HBox();
        titleAndSummaryBox.setSpacing(50);
        Button viewSummaryButton = new Button("View Summary");
        onViewSummaryButtonClicked(viewSummaryButton);
        titleAndSummaryBox.getChildren().addAll(new Label("--Score Board--"), viewSummaryButton);

        scoreBoard.getChildren().add(titleAndSummaryBox);
        scrollPane.setContent(scoreBoard);
        return scrollPane;
    }
    public void onViewSummaryButtonClicked(Button viewSummaryButton)
    {
        viewSummaryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ScrollPane pane = new ScrollPane();
                VBox summaryBox = new VBox();
                for(int i = 0; i < matches.size(); i ++)
                {
                    Match match = matches.get(i);
                    if(match.isFinished())
                        summaryBox.getChildren().add(new Label(match.getHomeName() + " " + match.getHomeScore() + " - " + match.getAwayName() + " " + match.getAwayScore()));
                }
                pane.setContent(summaryBox);
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.setWidth(300);
                stage.setHeight(300);
                stage.setTitle("Summary of games");
                stage.setScene(scene);
                stage.show();
            }
        });
    }
    public Pane getMatchBoard()
    {
        Pane pane = new Pane();
        VBox vbox = new VBox();

        Label homeLabel = new Label("Home: ");
        TextField homeTextField = new TextField ();
        HBox hb = new HBox();
        Label awayLabel = new Label("Away: ");
        TextField awayTextField = new TextField ();
        hb.getChildren().addAll(homeLabel, homeTextField, awayLabel, awayTextField);
        hb.setSpacing(20);


        Button startMatchButton = new Button("Start match");
        startMatchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Label scoresLabel = new Label();
                startMatch(homeTextField, awayTextField, hb, homeLabel, awayLabel, scoresLabel);
                if(hb.getChildren().contains(homeTextField) && hb.getChildren().contains(awayTextField))
                {
                    hb.getChildren().removeAll(homeTextField, awayTextField);
                }
                vbox.getChildren().remove(startMatchButton);
                addScoresPanel(scoresLabel, vbox);
                addFinishMatchButton(homeTextField, awayTextField, vbox);
            }
        });
        vbox.getChildren().addAll(hb, startMatchButton);
        vbox.setSpacing(30);

        pane.getChildren().addAll(vbox);
        return pane;
    }

    private void addFinishMatchButton(TextField homeTextField, TextField awayTextField, VBox vbox) {
        Button finishMatchButton = new Button("Finish match");
        finishMatchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String homeName = homeTextField.getText();
                String awayName = awayTextField.getText();
                Match match = getMatchWithId(matches.size() - 1);
                match.setFinished(true);
                orderMatch(match);

                scoreBoard.getChildren().add(new Label(homeName + " - " + awayName + ": " + match.getHomeScore() + " - " + match.getAwayScore()));
                splitPane.getItems().remove(matchPane);
                matchPane = getMatchBoard();
                splitPane.getItems().add(matchPane);
            }
        });
        vbox.getChildren().add(finishMatchButton);
    }

    private void addScoresPanel(Label scoresLabel, VBox vbox) {
        Label updateHomeScoreLabel = new Label("Update Home Score: ");
        Label updateAwayScoreLabel = new Label("Update Away Score: ");
        TextField updateHomeScoreField = new TextField();
        TextField updateAwayScoreField = new TextField();
        Button updateScores = new Button("Update Scores");
        updateScores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    updateScores(Integer.parseInt(updateHomeScoreField.getText()), Integer.parseInt(updateAwayScoreField.getText()), scoresLabel);

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter a number", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
        HBox scoresHBox = new HBox();
        scoresHBox.getChildren().addAll(updateHomeScoreLabel, updateHomeScoreField, updateAwayScoreLabel, updateAwayScoreField);
        vbox.getChildren().addAll(scoresHBox, updateScores);
    }

    public void updateScores(int newHomeScore, int newAwayScore, Label scoresLabel)
    {
        Match currentMatch = getMatchWithId(matches.size() - 1);
        currentMatch.updateScores(newHomeScore, newAwayScore);
        scoresLabel.setText("" + currentMatch.getHomeScore() + " - " + currentMatch.getAwayScore());
    }
    public void startMatch(TextField homeTextField, TextField awayTextField, HBox hbox, Label homeLabel, Label awayLabel, Label scoresLabel)
    {
        String homeName = homeTextField.getText();
        String awayName = awayTextField.getText();
        Match match = new Match(homeName, awayName);
        matches.add(match);
        String scores = "" + match.getHomeScore() + " - " + match.getAwayScore();
        scoresLabel.setText(scores);
        hbox.getChildren().add(hbox.getChildren().indexOf(awayLabel), scoresLabel);
        homeLabel.setText(homeLabel.getText() + homeName);
        awayLabel.setText(awayLabel.getText() + awayName);
    }
    public Match getMatchWithId(int id)
    {
        for(int i = 0; i < matches.size(); i ++)
        {
            if(matches.get(i).getId() == id)
                return matches.get(i);
        }
        return null;
    }
    public void orderMatch(Match unorderedMatch)
    {
        matches.remove(unorderedMatch);
        int insertionIndex = 0;
        while(insertionIndex < matches.size()  && unorderedMatch.getSumOfScores() < matches.get(insertionIndex).getSumOfScores())
        {
            insertionIndex ++;
        }
        matches.add(insertionIndex, unorderedMatch);
    }
}