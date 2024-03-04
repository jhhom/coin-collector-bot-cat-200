package com.jeff.game.Manager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class SoundManager {
    public static final Sound sButton = Gdx.audio.newSound(Gdx.files.internal("SFX/button.mpeg"));

    public static final Music sfxLose = Gdx.audio.newMusic(Gdx.files.internal("SFX/lose.mpeg"));
    public static final Music sfxWin = Gdx.audio.newMusic(Gdx.files.internal("SFX/win.mpeg"));
    public static final Music sfxWalk = Gdx.audio.newMusic(Gdx.files.internal("SFX/walk.mpeg"));
    public static final Music sfxCoin = Gdx.audio.newMusic(Gdx.files.internal("SFX/coin.mpeg"));

}
