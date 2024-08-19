package pl.edu.pg.eti.ksg.po.project2.game;

public class Narrator {
    private static String text = "";

    public static void addComment(String comment) {
        text += comment + "\n";
    }

    public static String getText() {
        return text;
    }

    public static void clearComments() {
        text = "";
    }
}
