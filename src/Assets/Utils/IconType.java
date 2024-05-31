package Assets.Utils;

public enum IconType {
    ADD("heavy_plus_sign.png"),
    PACKAGE("package.png"),
    EDIT("pencil2.png"),
    UNLOCK("unlock.png"),
    DELETE("wastebasket.png"),

    USER("bust_in_silhouette.png"),

    USERS("busts_in_silhouette.png"),


    USERADD("bust_in_silhouette_add.png"),
    CANCEL("x.png");

    private final String filename;

    IconType(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
