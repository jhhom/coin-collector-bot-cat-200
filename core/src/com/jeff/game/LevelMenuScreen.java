package com.jeff.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jeff.game.GameScreens.ProgramScreen;
import com.jeff.game.Manager.AssetManager;

public class LevelMenuScreen implements Screen {

    private Game game;
    Music bgm;


    public LevelMenuScreen (Game game) {
        this.game = game;
        bgm = Gdx.audio.newMusic(Gdx.files.internal("SFX/bgm-menu.mp3"));
    }

    @Override
    public void show() {
        AssetManager.UISetup(uiSetup);
        bgm.setLooping(true);
        bgm.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        AssetManager.batch.begin();
        AssetManager.levelBgSp.draw(AssetManager.batch);
        AssetManager.levelTitleSp.draw(AssetManager.batch);
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
        bgm.dispose();
    }

    @Override
    public void dispose() {

    }

    Runnable uiSetup = new Runnable() {
        @Override
        public void run() {
            Table table = new Table(AssetManager.jeffLevelMenuSkin);
            table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            AssetManager.levelBgSp.setSize(1920, 1280);
            AssetManager.levelBgSp.setPosition(0, 0);

            AssetManager.levelTitleSp.setSize(1200, 1200);
            AssetManager.levelTitleSp.setPosition(350, -80);

            TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
            txtBtnStyle.up = AssetManager.jeffLevelMenuSkin.getDrawable("green_button00");
            txtBtnStyle.down = AssetManager.jeffLevelMenuSkin.getDrawable("green_button03");
            txtBtnStyle.pressedOffsetY = -1;
            txtBtnStyle.pressedOffsetX = 1;
            txtBtnStyle.font = AssetManager.fontWhite;

            ImageButton[] levelBtns = new ImageButton[9];
            for (int i = 0; i < levelBtns.length; i++) {
                levelBtns[i] = new ImageButton(AssetManager.levelBtnDrawables[i]);
                levelBtns[i].setSize(252, 198.6f);
                final int level = i;
                levelBtns[i].addListener(new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        game.setScreen(new ProgramScreen(game, level));
                    }
                });
            }
            // top row
            {
                float x = 120f;
                for (int i = 0; i < 5; i++) {
                    levelBtns[i].setPosition(x, 500);
                    x += 350f;
                }
            }
            // bottom row
            {
                float x = 200f;
                for (int i = 5; i < 9; i++) {
                    levelBtns[i].setPosition(x, 200);
                    x += 385f;
                }
            }

            TextButton backToMenuBtn = new TextButton("Main Menu", txtBtnStyle);
            backToMenuBtn.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                }
            });
            backToMenuBtn.getLabel().setFontScale(2);
            table.add(backToMenuBtn).minSize(400, 100);
            table.right().bottom().setPosition(-100, 50);

            for (int i = 0; i < levelBtns.length; i++)
                AssetManager.stg.addActor(levelBtns[i]);
            AssetManager.stg.addActor(table);

        }
    };
}
