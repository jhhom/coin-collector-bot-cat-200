package com.jeff.game.GameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Manager.GameManager;
import com.jeff.game.Manager.SoundManager;
import com.jeff.game.Util.CoordToPosition;

import java.util.ArrayList;

public class PlayScreen implements Screen {

    int level;
    int[][] map;
    int[] crusaderCoord;
    Vector2 crusaderPos;
    int coinCollected = 0;
    Instruction inst;
    ArrayList<InstType> expandedInst;
    Game game;

    float animTime = 0f;
    public static final float ANIM_DURATION = .5f, X_DIST = 50F, Y_DIST = 50f;

    public PlayScreen(Game game, int level, Instruction inst) {
        this.game = game;
        this.level = level;
        this.inst = inst;
        this.expandedInst = this.inst.getExpandedMainInst();

        crusaderCoord = new int[] { 0, 0};
        crusaderPos = CoordToPosition.getCrusaderPosFromCoord(crusaderCoord[0], crusaderCoord[1]);
    }

    @Override
    public void show() {
        this.map = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for  (int j = 0; j < 4; j++) {
                map[i][j] = GameManager.LEVEL_MAPS[level][i][j];
            }
        }
        AssetManager.UISetup(setupUI);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        AssetManager.batch.begin();
        AssetManager.batch.draw(AssetManager.currentGameBg, 0f, 0f, 1920f, 1280f);
        ArrayList<Vector2> tablesPos = AssetManager.renderMap(map, crusaderCoord);
        AssetManager.renderCrusaderAnimation(updateCrusaderPos(delta), animTime, crusaderPos.x, crusaderPos.y);
        AssetManager.renderOverlappingTables(tablesPos);
        AssetManager.batch.end();

        AssetManager.UIRender();

        if (expandedInst.size() == 0) {
            game.setScreen(new FinishScreen(this.game, this.coinCollected, this.level, new Vector2(crusaderPos.x, crusaderPos.y)));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    boolean isInPlaceMovementFinished = true;
    InstType updateCrusaderPos(float dt) {
        InstType currentInst = InstType.MoveDown;
        if (expandedInst.size() != 0) {
            currentInst = expandedInst.get(0);
            int[] nextCoord = getNextCoord(crusaderCoord, currentInst);
            Vector2 nextPos = CoordToPosition.getCrusaderPosFromCoord(nextCoord[0], nextCoord[1]);
            if (!(nextCoord == crusaderCoord) && isInPlaceMovementFinished) {
                SoundManager.sfxWalk.play();
                switch (currentInst) {
                    case MoveDown: {
                        if (crusaderPos.y > nextPos.y || crusaderPos.x > nextPos.x) {
                            if (crusaderPos.y > nextPos.y)
                                crusaderPos.y -= Y_DIST * (dt / ANIM_DURATION);
                            if (crusaderPos.x > nextPos.x)
                                crusaderPos.x -= X_DIST * (dt / ANIM_DURATION);
                        } else {
                                crusaderCoord = nextCoord;
                                expandedInst.remove(0);
                        }
                        break;
                    }
                    case MoveUp: {
                        if (crusaderPos.y < nextPos.y || crusaderPos.x < nextPos.x) {
                            if (crusaderPos.y < nextPos.y)
                                crusaderPos.y += Y_DIST * (dt / ANIM_DURATION);
                            if (crusaderPos.x < nextPos.x)
                                crusaderPos.x += X_DIST * (dt / ANIM_DURATION);
                        } else {
                            crusaderCoord = nextCoord;
                            expandedInst.remove(0);
                        }
                        break;
                    }
                    case MoveRight: {
                        if (crusaderPos.y > nextPos.y || crusaderPos.x < nextPos.x) {
                            if (crusaderPos.y > nextPos.y)
                                crusaderPos.y -= Y_DIST * (dt / ANIM_DURATION);
                            if (crusaderPos.x < nextPos.x)
                                crusaderPos.x += X_DIST * (dt / ANIM_DURATION);
                        } else {
                            crusaderCoord = nextCoord;
                            expandedInst.remove(0);
                        }
                        break;
                    }
                    case MoveLeft: {
                        if (crusaderPos.y < nextPos.y || crusaderPos.x > nextPos.x) {
                            if (crusaderPos.y < nextPos.y)
                                crusaderPos.y += Y_DIST * (dt / ANIM_DURATION);
                            if (crusaderPos.x > nextPos.x)
                                crusaderPos.x -= X_DIST * (dt / ANIM_DURATION);
                        } else {
                            crusaderCoord = nextCoord;
                            expandedInst.remove(0);
                        }
                        break;
                    }
                }
                animTime += dt;
            } else {
                isInPlaceMovementFinished = false;
                animTime += dt;
                if (animTime > ANIM_DURATION) {
                    animTime = 0f;
                    isInPlaceMovementFinished = true;
                    expandedInst.remove(0);
                }
            }
        }
        return currentInst;
    }

    public int[] getNextCoord(int[] currentCoord, InstType nextInst) {
        int[] nextCoord = currentCoord;
        switch (nextInst) {
            case MoveDown:
                nextCoord = new int[] { currentCoord[0] + 1, currentCoord[1] };
                break;
            case MoveUp:
                nextCoord = new int[] { currentCoord[0] - 1, currentCoord[1] };
                break;
            case MoveLeft:
                nextCoord = new int[] { currentCoord[0], currentCoord[1] - 1 };
                break;
            case MoveRight:
                nextCoord = new int[] { currentCoord[0], currentCoord[1] + 1};
                break;
        }
        // if next coord is off the map
        if (nextCoord[0] < 0 || nextCoord[0] > 3 || nextCoord[1] < 0 || nextCoord[1] > 3) {
            nextCoord = currentCoord;
        // if next coord is obstacle
        } else if (map[nextCoord[0]][nextCoord[1]] == -1) {
            nextCoord = currentCoord;
        // if next coord is coin
        } else if(map[nextCoord[0]][nextCoord[1]] == 1) {
            map[nextCoord[0]][nextCoord[1]] = 0;
            coinCollected++;
            ((Label) (AssetManager.stg.getRoot().findActor("coin-label"))).setText("Coins collected: " + coinCollected);
            SoundManager.sfxCoin.play();
        }
        return nextCoord;
    }

    Runnable setupUI = new Runnable() {
        @Override
        public void run() {
            ArrayList<InstType> mainInst = inst.getMainInstList();
            ArrayList<InstType> funcInst = inst.getFuncInstList(), loopInst = inst.getLoopInstList();

            Image[] mainInstList = new Image[16];
            Image[] loopInstList = new Image[4], funcInstList = new Image[4];
            for (int i = 0, x = 1345, y = 975; i < mainInst.size(); i++, x += 145) {
                if (i % 4 == 0) {
                    x = 1345;
                    y -= 145;
                }
                mainInstList[i] = new Image(AssetManager.instructionDrawableMap.get(mainInst.get(i)));
                mainInstList[i].setPosition(x, y);
                mainInstList[i].setName("main-inst#" + i);
                AssetManager.stg.addActor(mainInstList[i]);
            }
            for (int i = 0, loopX = 0, funcX = 1345; i < loopInstList.length; i++, loopX += 145, funcX += 145) {
                loopInstList[i] = new Image(new TextureRegionDrawable(AssetManager.instructionDrawableMap.get(loopInst.get(i))));
                loopInstList[i].setName("loop-inst#" + i);
                loopInstList[i].setPosition(loopX, 0);
                funcInstList[i] = new Image(new TextureRegionDrawable(AssetManager.instructionDrawableMap.get(funcInst.get(i))));
                funcInstList[i].setName("func-inst#" + i);
                funcInstList[i].setPosition(funcX, 0);
                AssetManager.stg.addActor(loopInstList[i]);
                AssetManager.stg.addActor(funcInstList[i]);
            }

            Label coinLabel = new Label("Coins Collected: 0", AssetManager.mcSkin);
            coinLabel.setFontScale(1.5f, 1.5f);
            coinLabel.setPosition(20, 1000);
            coinLabel.setName("coin-label");

            Label funcLabel = new Label("Function", AssetManager.mcSkin);
            funcLabel.setFontScale(1.5f, 1.5f);
            funcLabel.setPosition(1345, 150);
            Label loopLabel = new Label("Loop", AssetManager.mcSkin);
            loopLabel.setPosition(20, 150);
            loopLabel.setFontScale(1.5f, 1.5f);
            Label mainLabel = new Label("Main", AssetManager.mcSkin);
            mainLabel.setFontScale(1.5f, 1.5f);
            mainLabel.setPosition(1345, 1000);

            AssetManager.stg.addActor(coinLabel);
            AssetManager.stg.addActor(funcLabel);
            AssetManager.stg.addActor(loopLabel);
            AssetManager.stg.addActor(mainLabel);

        }
    };
}
