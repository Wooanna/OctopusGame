package nkichev.wooanna.octopusgameteamwork.HighscoresDB;

public class Entry {
    private long id;
    private String name;
    private long score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    // Will be used by the ArrayAdapter in the ListView
    /*@Override
    public String toString() {
        return name + ": " + score;
    }*/
}
