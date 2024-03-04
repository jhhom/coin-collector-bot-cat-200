package com.jeff.game.Util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.jeff.game.Manager.GameManager;

public class CoordToPosition {

    private static Vector2[][] baseBlockPos;
    private static Vector2[][] obsPos;
    private static Vector2[][] coinPos;
    private static Vector2[][] crusaderPos;

    public static void initCoords() {
        baseBlockPos = new Vector2[GameManager.MAP_SIZE][GameManager.MAP_SIZE];
        final float x = 150f, y = 75f;
        {
            final float firstX = 400f, firstY = 510f;
            float xOffset, yOffset;
            for (int i = 0; i < baseBlockPos.length; i++) {
                xOffset = i * (-x);
                yOffset = i * (-y);
                for (int j = 0; j < baseBlockPos[i].length; j++) {
                    xOffset += x;
                    yOffset += -y;
                    baseBlockPos[i][j] = new Vector2(firstX + xOffset, firstY + yOffset);
                }
            }
        }
        obsPos = new Vector2[GameManager.MAP_SIZE][GameManager.MAP_SIZE];
        {
            final float firstX = 400f, firstY = 650f;
            float xOffset, yOffset;
            for (int i = 0; i < obsPos.length; i++) {
                xOffset = i * (-x);
                yOffset = i * (-y);
                for (int j = 0; j < obsPos[i].length; j++) {
                    xOffset += x;
                    yOffset += -y;
                    obsPos[i][j] = new Vector2(firstX + xOffset, firstY + yOffset);
                }
            }
        }
        coinPos = new Vector2[GameManager.MAP_SIZE][GameManager.MAP_SIZE];
        {
            final float firstX = 510f, firstY = 700f;
            float xOffset, yOffset;
            for (int i = 0; i < coinPos.length; i++) {
                xOffset = i * (-x);
                yOffset = i * (-y);
                for (int j = 0; j < obsPos[i].length; j++) {
                    xOffset += x;
                    yOffset += -y;
                    coinPos[i][j] = new Vector2(firstX + xOffset, firstY + yOffset);
                }
            }
        }
        crusaderPos = new Vector2[GameManager.MAP_SIZE][GameManager.MAP_SIZE];
        {
            float xOffset = -50f, yOffset = 95f;
            for (int i = 0; i < crusaderPos.length; i++) {
                for (int j = 0; j < crusaderPos[i].length; j++) {
                    crusaderPos[i][j] = new Vector2(0f, 0f);
                    crusaderPos[i][j].x = baseBlockPos[i][j].x + xOffset;
                    crusaderPos[i][j].y = baseBlockPos[i][j].y + yOffset;
                }
            }
        }
    }

    public static Vector2 getEmptyTilePosFromCoord(int x, int y) {
        return baseBlockPos[x][y].cpy();
    }

    public static Vector2 getBlockedTilePosFromCoord(int x, int y) {
        return obsPos[x][y].cpy();
    }

    public static Vector2 getCoinPosFromCoord(int x, int y) {
        return coinPos[x][y].cpy();
    }

    public static Vector2 getCrusaderPosFromCoord(int x, int y) {
        return crusaderPos[x][y].cpy();
    }
}
