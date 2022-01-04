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

        // -----------------------------------------

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

        // -----------------------------------------

        public static BufferedImage char_s_032;
        public static BufferedImage char_s_033;
        public static BufferedImage char_s_046;
        public static BufferedImage char_s_058;
        public static BufferedImage char_s_063;
        public static BufferedImage char_s_124; 

        public static BufferedImage char_u_a; 
        public static BufferedImage char_u_b;
        public static BufferedImage char_u_c;
        public static BufferedImage char_u_d;
        public static BufferedImage char_u_e;
        public static BufferedImage char_u_f;
        public static BufferedImage char_u_g;
        public static BufferedImage char_u_h;
        public static BufferedImage char_u_i;
        public static BufferedImage char_u_j;
        public static BufferedImage char_u_k;
        public static BufferedImage char_u_l;
        public static BufferedImage char_u_m;
        public static BufferedImage char_u_n;
        public static BufferedImage char_u_o;
        public static BufferedImage char_u_p;
        public static BufferedImage char_u_q;
        public static BufferedImage char_u_r;
        public static BufferedImage char_u_s;
        public static BufferedImage char_u_t;
        public static BufferedImage char_u_u;
        public static BufferedImage char_u_v;
        public static BufferedImage char_u_w;
        public static BufferedImage char_u_x;
        public static BufferedImage char_u_y;
        public static BufferedImage char_u_z;

        public static BufferedImage char_l_a; 
        public static BufferedImage char_l_b;
        public static BufferedImage char_l_c;
        public static BufferedImage char_l_d;
        public static BufferedImage char_l_e;
        public static BufferedImage char_l_f;
        public static BufferedImage char_l_g;
        public static BufferedImage char_l_h;
        public static BufferedImage char_l_i;
        public static BufferedImage char_l_j;
        public static BufferedImage char_l_k;
        public static BufferedImage char_l_l;
        public static BufferedImage char_l_m;
        public static BufferedImage char_l_n;
        public static BufferedImage char_l_o;
        public static BufferedImage char_l_p;
        public static BufferedImage char_l_q;
        public static BufferedImage char_l_r;
        public static BufferedImage char_l_s;
        public static BufferedImage char_l_t;
        public static BufferedImage char_l_u;
        public static BufferedImage char_l_v;
        public static BufferedImage char_l_w;
        public static BufferedImage char_l_x;
        public static BufferedImage char_l_y;
        public static BufferedImage char_l_z;

        // -----------------------------------------

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

        // -----------------------------------------

        static void load() {
            background_main         = loadImg("menu background main");
            background_main_blur    = loadImg("menu background main_blur");
            background_menu         = loadImg("menu background menu");
            background_menu_2       = loadImg("menu background menu_2");

            // -------------------------------
    
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

            char_s_032 = loadImg("menu char s_032");
            char_s_033 = loadImg("menu char s_033");
            char_s_046 = loadImg("menu char s_046");
            char_s_058 = loadImg("menu char s_058");
            char_s_063 = loadImg("menu char s_063");
            char_s_124 = loadImg("menu char s_124"); 

            char_u_a = loadImg("menu char u_a");
            char_u_b = loadImg("menu char u_b");
            char_u_c = loadImg("menu char u_c");
            char_u_d = loadImg("menu char u_d");
            char_u_e = loadImg("menu char u_e");
            char_u_f = loadImg("menu char u_f");
            char_u_g = loadImg("menu char u_g");
            char_u_h = loadImg("menu char u_h");
            char_u_i = loadImg("menu char u_i");
            char_u_j = loadImg("menu char u_j");
            char_u_k = loadImg("menu char u_k");
            char_u_l = loadImg("menu char u_l");
            char_u_m = loadImg("menu char u_m");
            char_u_n = loadImg("menu char u_n");
            char_u_o = loadImg("menu char u_o");
            char_u_p = loadImg("menu char u_p");
            char_u_q = loadImg("menu char u_q");
            char_u_r = loadImg("menu char u_r");
            char_u_s = loadImg("menu char u_s");
            char_u_t = loadImg("menu char u_t");
            char_u_u = loadImg("menu char u_u");
            char_u_v = loadImg("menu char u_v");
            char_u_w = loadImg("menu char u_w");
            char_u_x = loadImg("menu char u_x");
            char_u_y = loadImg("menu char u_y");
            char_u_z = loadImg("menu char u_z");
    
            char_l_a = loadImg("menu char l_a");
            char_l_b = loadImg("menu char l_b");
            char_l_c = loadImg("menu char l_c");
            char_l_d = loadImg("menu char l_d");
            char_l_e = loadImg("menu char l_e");
            char_l_f = loadImg("menu char l_f");
            char_l_g = loadImg("menu char l_g");
            char_l_h = loadImg("menu char l_h");
            char_l_i = loadImg("menu char l_i");
            char_l_j = loadImg("menu char l_j");
            char_l_k = loadImg("menu char l_k");
            char_l_l = loadImg("menu char l_l");
            char_l_m = loadImg("menu char l_m");
            char_l_n = loadImg("menu char l_n");
            char_l_o = loadImg("menu char l_o");
            char_l_p = loadImg("menu char l_p");
            char_l_q = loadImg("menu char l_q");
            char_l_r = loadImg("menu char l_r");
            char_l_s = loadImg("menu char l_s");
            char_l_t = loadImg("menu char l_t");
            char_l_u = loadImg("menu char l_u");
            char_l_v = loadImg("menu char l_v");
            char_l_w = loadImg("menu char l_w");
            char_l_x = loadImg("menu char l_x");
            char_l_y = loadImg("menu char l_y");
            char_l_z = loadImg("menu char l_z");

            // -------------------------------
    
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
