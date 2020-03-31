package im.tox.toktok.app.domain;

public final class File {
    public static final File file = new File("Movie_2015-02-01.mp4", "Downloaded 2015-05-21");

    public final String name;
    public final String date;

    public File(
            String name,
            String date
    ) {
        this.name = name;
        this.date = date;
    }
}
