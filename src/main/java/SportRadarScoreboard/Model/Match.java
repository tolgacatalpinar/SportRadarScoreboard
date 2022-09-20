package SportRadarScoreboard.Model;

public class Match {
    private final int id;
    private final String homeName;
    private final String awayName;
    private int homeScore;
    private int awayScore;

    private boolean isFinished;

    private static int totalNumberOfMatches = 0;


    public Match(String homeName, String awayName)
    {
        this.homeName = homeName;
        this.awayName = awayName;
        homeScore = 0;
        awayScore = 0;
        id = totalNumberOfMatches;
        totalNumberOfMatches ++;
        isFinished = false;
    }

    public void updateScores(int homeScore, int awayScore)
    {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getId() {
        return id;
    }
    public int getSumOfScores()
    {
        return homeScore + awayScore;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
