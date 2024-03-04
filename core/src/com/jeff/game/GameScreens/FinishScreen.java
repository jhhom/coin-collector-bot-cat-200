package com.jeff.game.GameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Manager.GameManager;
import com.jeff.game.Manager.SoundManager;
import com.jeff.game.MenuScreen;

import java.util.ArrayList;

public class FinishScreen implements Screen {

    int[][] map;
    int level;
    boolean isGameWon;
    Vector2 crusaderLastPos;

    Game game;

    public FinishScreen(Game game, int coinCollected, int level, Vector2 crusaderLastPos) {
        this.map = GameManager.LEVEL_MAPS[level].clone();
        this.game = game;
        this.level = level;
        this.isGameWon = coinCollected == 3;
        this.crusaderLastPos = crusaderLastPos;
    }

    @Override
    public void show() {
        AssetManager.UISetup(setupUI);
        if (isGameWon)
            SoundManager.sfxWin.play();
        else {
            SoundManager.sfxLose.setVolume(1);
            SoundManager.sfxLose.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        AssetManager.batch.begin();
        AssetManager.batch.draw(AssetManager.currentGameBg, 0f, 0f, 1920f, 1280f);
        ArrayList<Vector2> tablesPos = AssetManager.renderMap(map, new int[] {0, 0});
        AssetManager.renderCrusaderAnimation(InstType.MoveDown, 0f, crusaderLastPos.x, crusaderLastPos.y);
        AssetManager.renderOverlappingTables(tablesPos);
        AssetManager.batch.end();

        AssetManager.UIRender();
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
        ProgramScreen.gameBgm.dispose();
        ProgramScreen.gameBgm.stop();
    }

    @Override
    public void dispose() {
        ProgramScreen.gameBgm.dispose();
        ProgramScreen.gameBgm.stop();
    }

    Runnable setupUI = new Runnable() {
        @Override
        public void run() {
            Window window = new Window("", AssetManager.mcSkin);
            int nextLevel = level;

            TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
            txtBtnStyle.up = AssetManager.jeffGameMenuSkin.getDrawable("blue_button00");
            txtBtnStyle.down = AssetManager.jeffGameMenuSkin.getDrawable("blue_button02");
            txtBtnStyle.pressedOffsetY = -1;
            txtBtnStyle.pressedOffsetX = 1;
            txtBtnStyle.font = AssetManager.fontWhite;
            TextButton menuBtn = new TextButton("Back to Main Menu", txtBtnStyle);

            menuBtn.setPosition(650f, 40f);
            menuBtn.setWidth(550);
            menuBtn.setHeight(150);
            menuBtn.getLabel().setFontScale(2);

            if (isGameWon) {
                window.setBackground(new TextureRegionDrawable(AssetManager.successBadge));
                window.setSize(AssetManager.successBadge.getWidth() * 1.5f, AssetManager.successBadge.getHeight() * 1.5f);
                window.setPosition(380f, 500f);
                nextLevel = (level + 1) % 9;
            } else {
                window.setBackground(new TextureRegionDrawable(AssetManager.failBadge));
                window.setSize(AssetManager.failBadge.getWidth() * 1.6f, AssetManager.failBadge.getHeight() * 1.6f);
                window.setPosition(350f, 500f);
                nextLevel = level;
            }
            {
                final int constFinalLevel = nextLevel;
                window.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new ProgramScreen(game, constFinalLevel));
                    }
                });
            }
            menuBtn.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                }
            });

            AssetManager.stg.addActor(window);
            AssetManager.stg.addActor(menuBtn);
        }
    };
}
