package com.jeff.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Manager.SoundManager;

public class MenuScreen implements Screen {

    private Game game;
    public static Music bgm;

    public MenuScreen(Game game) {
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
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        AssetManager.batch.begin();
        AssetManager.menuBgSp.draw(AssetManager.batch);
        AssetManager.menuTitleSp.draw(AssetManager.batch);
        AssetManager.batch.end();

        AssetManager.UIRender();
    }

    @Override
    public void resize(int i, int i1) {

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

    // UI Setup
    Runnable uiSetup = new Runnable() {
        @Override
        public void run() {
            AssetManager.menuBgSp.setSize(1920, 1280);
            AssetManager.menuBgSp.setPosition(0, 0);

            AssetManager.menuTitleSp.setSize(1200, 1200);
            AssetManager.menuTitleSp.setPosition(350, -100);

            TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
            txtBtnStyle.up = AssetManager.jeffGameMenuSkin.getDrawable("blue_button00");
            txtBtnStyle.down = AssetManager.jeffGameMenuSkin.getDrawable("blue_button02");
            txtBtnStyle.pressedOffsetY = -1;
            txtBtnStyle.pressedOffsetX = 1;
            txtBtnStyle.font = AssetManager.fontWhite;

            TextButton playBtn, exitBtn, tutBtn;
            playBtn = new TextButton("Play", txtBtnStyle);
            playBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SoundManager.sButton.play(1);
                    game.setScreen(new LevelMenuScreen(game));
                }
            });
            tutBtn = new TextButton("Tutorial", txtBtnStyle);
            tutBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SoundManager.sButton.play(1);
                    game.setScreen(new TutorialScreen(game));
                }
            });
            exitBtn = new TextButton("Exit Game", txtBtnStyle);
            exitBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SoundManager.sButton.play(1);
                    Gdx.app.exit();
                }
            });
            Table table = new Table(AssetManager.jeffGameMenuSkin);
            table.setPosition(920, 350);
            table.add(playBtn).minSize(600, 120);
            playBtn.getLabel().setFontScale(2f);
            table.row();
            table.getCell(playBtn).spaceBottom(70);
            table.add(tutBtn).minSize(600, 120);
            tutBtn.getLabel().setFontScale(2f);
            table.row();
            table.getCell(tutBtn).spaceBottom(70);
            table.add(exitBtn).minSize(600, 120);
            exitBtn.getLabel().setFontScale(2f);

            AssetManager.stg.addActor(table);
        }
    };
}
