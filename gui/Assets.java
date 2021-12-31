package gui;

public class Assets {
    static public boolean loading = false;
    static public boolean loaded  = false;
    static public boolean menuLoaded = false;
    static public boolean gameLoaded = false;
    
    // --------------------

    static void load() {
        if (loaded) return;
        loading = true;
        cli.Utils.debug("Loading assets..");
        // --------------------
        Menu.load();
        Game.load();
        // --------------------
        cli.Utils.debug("Finish to load assets.");
        loaded  = true;
        loading = false;
    }

    // --------------------

    public static class Menu {
        static void load() {
            menuLoaded = true;
        }
    }

    public static class Game {
        static void load() {
            gameLoaded = true;
        }
    }

}
