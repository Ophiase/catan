package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

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
        public static BufferedImage background_main;
        public static BufferedImage background_main_blur;
        public static BufferedImage background_menu;
        public static BufferedImage background_menu_2;

        public static BufferedImage digit_0;
        public static BufferedImage digit_1;
        public static BufferedImage digit_2;
        public static BufferedImage digit_3;
        public static BufferedImage digit_4;
        public static BufferedImage digit_5;
        public static BufferedImage digit_6;
        public static BufferedImage digit_7;
        public static BufferedImage digit_8;
        public static BufferedImage digit_9;

        public static BufferedImage btn_circle;
        public static BufferedImage btn_circle_click;
        public static BufferedImage btn_plus;
        public static BufferedImage btn_plus_click;
        public static BufferedImage btn_minus;
        public static BufferedImage btn_minus_click;
        public static BufferedImage btn_play;
        public static BufferedImage btn_play_click;

        public static BufferedImage text_logo;
        public static BufferedImage text_players;
        public static BufferedImage text_bots;
        public static BufferedImage text_number_of_dices;
        public static BufferedImage text_size_of_dices;

        static void load() {
            background_main         = loadImg("menu background main");
            background_main_blur    = loadImg("menu background main_blur");
            background_menu         = loadImg("menu background menu");
            background_menu_2       = loadImg("menu background menu_2");
    
            digit_0 = loadImg("menu digit d0");
            digit_1 = loadImg("menu digit d1");
            digit_2 = loadImg("menu digit d2");
            digit_3 = loadImg("menu digit d3");
            digit_4 = loadImg("menu digit d4");
            digit_5 = loadImg("menu digit d5");
            digit_6 = loadImg("menu digit d6");
            digit_7 = loadImg("menu digit d7");
            digit_8 = loadImg("menu digit d8");
            digit_9 = loadImg("menu digit d9");
    
            btn_circle          = loadImg("menu btn circle");
            btn_circle_click    = loadImg("menu btn circle_click");
            btn_plus            = loadImg("menu btn plus");
            btn_plus_click      = loadImg("menu btn plus_click");
            btn_minus           = loadImg("menu btn minus");
            btn_minus_click     = loadImg("menu btn minus_click");
            btn_play            = loadImg("menu btn play");
            btn_play_click      = loadImg("menu btn play_click");
    
            text_logo               = loadImg("menu text logo");
            text_players            = loadImg("menu text players");
            text_bots               = loadImg("menu text bots");
            text_number_of_dices    = loadImg("menu text number_of_dices");
            text_size_of_dices      = loadImg("menu text size_of_dices");

            menuLoaded = true;
        }
    }

    public static class Game {
        static void load() {


            gameLoaded = true;
        }
    }

    private static BufferedImage loadImg(String resource) {
        resource = resource.replace(" ", File.separator);

        try {
            String uri = 
                System.getProperty("user.dir") + File.separator +
                "resources"                    + File.separator +
                "image"                        + File.separator +
                resource                       + ".png";

            return ImageIO.read(new File(uri));
        } catch (Exception e) {
            cli.Utils.debug("Can't read : " + resource);
            return null;
        }
    }

}
