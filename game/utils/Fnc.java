package game.utils;

// UTILITY FUNCTIONS

import java.util.Random;

public class Fnc {
    public static Random rnd = new Random();
    public static int rand(int max) { return rnd.nextInt(max); } 

    // --------------------

    public static int conv2dto1d (int x, int y, int size) {
        return x + (y*size);
    }

    public static int conv1dto2d_x (int w, int size) {
        return w%size;
    }

    public static int conv1dto2d_y (int w, int size) {
        return w/size;
    }

    // --------------------

    public static int[] randomIndexArray(int length) {
        int[] arr = new int[length];

        for (int i = 0; i < length; i++)
            arr[i] = i;

        // shuffle
        for (int i = 0; i < length; i++)
        {
            // swap avec un index random
            int j = Fnc.rand(length);
            
            int ti = arr[i];
            arr[i] = arr[j];
            arr[j] = ti;
        }

        return arr;
    }

}