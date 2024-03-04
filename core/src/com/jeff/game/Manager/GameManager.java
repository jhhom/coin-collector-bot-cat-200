package com.jeff.game.Manager;

public class GameManager {
    public static boolean isSoundOn = true;
    public static boolean isSfxOn = true;

    public static int levelsUnlocked = 2;

    public static final int[][][] LEVEL_MAPS = {
            // level 1
            {
                    {0,0,0,1},
                    {0,0, 0,0},
                    {1,0, 1,0},
                    {0,0, 0,0},

            },
            // level 2
            {
                    {0,0,1,0},
                    {0,0,-1,0},
                    {0,0,-1,0},
                    {0,1,-1,1},
            },
            // level 3
            {
                    {0,0,-1,0},
                    {0,0,1,0},
                    {1,0,-1,0},
                    {-1,-1,-1,1},
            },
            // level 4
            {
                    {0,0,-1,-1},
                    {-1,1,0,-1},
                    {-1,-1,1,0},
                    {-1,-1,-1,1},
            },
            // level 5
            {
                    {0,-1,1,-1},
                    {0,0,0,0},
                    {-1,0,0,0},
                    {-1,1,-1,1},
            },
            // level 6
            {
                    {0,0,1,0},
                    {0,-1,0,0},
                    {0,0,-1,0},
                    {1,0,0,1},
            },
            // level 7
            {
                    {0,0,0,0},
                    {0,-1,1,0},
                    {0,1,-1,0},
                    {0,0,0,1},
            },
            // level 8
            {
                    {0,0,-1,-1},
                    {-1,0,0,1},
                    {1,0,0,0},
                    {-1,-1,1,0},
            },
            // level 9
            {
                    {0, 1, -1, 0},
                    {-1, 0, 1, -1},
                    {-1, 1, 0, 0},
                    {0, 0, 0, -1},
            }
    };

    public static final int MAP_SIZE = LEVEL_MAPS[0].length;
}
