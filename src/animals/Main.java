package animals;

public class Main {
    private static String fileType = "";

    // Filetype is set as a command line argument e.g. "-type json"
    // It can be "json", "xml" or "yaml"
    public static void main(String[] args) {
        if (args.length >= 2 && args[0].equals("-type")) {
            fileType = args[1];
        }
        new Game().start();
    }

    public static String getFileType() {
        return fileType;
    }
}