package com.jeff.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Util.TitleFade;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class LogoScreen implements Screen {

    Game game;
    private final float FADE_DURATION = 0.5f;
    private TweenManager tweenManager;
    Sprite fadingTitle;


    public LogoScreen(Game game) {
        this.game = game;

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new TitleFade());
        fadingTitle = new Sprite(AssetManager.tutorialImage);
        fadingTitle.setPosition(0, 0);
        Tween.set(fadingTitle, TitleFade.ALPHA).target(0).start(tweenManager);
        Tween.to(fadingTitle, TitleFade.ALPHA, 2).target(1).repeatYoyo(1, 2)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Game jeffGame = (Game) Gdx.app.getApplicationListener();
                        jeffGame.setScreen(new MenuScreen(jeffGame));
                    }
                }).start(tweenManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);
        AssetManager.batch.begin();
        fadingTitle.draw(AssetManager.batch);
        AssetManager.batch.end();
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
}
