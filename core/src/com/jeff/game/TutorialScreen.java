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

public class TutorialScreen implements Screen {

    Game game;
    Music bgm;

    public TutorialScreen(Game game) {
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
        AssetManager.tutorialImage.draw(AssetManager.batch);
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

            TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
            txtBtnStyle.up = AssetManager.jeffGameMenuSkin.getDrawable("blue_button00");
            txtBtnStyle.down = AssetManager.jeffGameMenuSkin.getDrawable("blue_button02");
            txtBtnStyle.pressedOffsetY = -1;
            txtBtnStyle.pressedOffsetX = 1;
            txtBtnStyle.font = AssetManager.fontWhite;

            TextButton menuBtn;
            menuBtn = new TextButton("Back to Main Menu", txtBtnStyle);
            menuBtn.getLabel().setFontScale(2);
            menuBtn.setSize(600, 110);
            menuBtn.setPosition(1200, 20);
            menuBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                }
            });

            AssetManager.stg.addActor(menuBtn);
        }
    };
}
