package com.jeff.game.GameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.jeff.game.Manager.AssetManager;
import com.jeff.game.Manager.GameManager;
import com.jeff.game.Manager.SoundManager;
import com.jeff.game.Util.CoordToPosition;
import com.jeff.game.Util.MathUtil;

import java.util.ArrayList;

import javax.xml.soap.Text;

public class ProgramScreen implements Screen {

    int level;
    int[][] map;
    Game game;
    Instruction instruction = new Instruction();
    DragAndDrop dnd = new DragAndDrop();

    public static Music gameBgm;

    public ProgramScreen(Game game, int level) {
        this.game = game;
        this.level = level;
        this.map = GameManager.LEVEL_MAPS[level].clone();
        crusaderInitPos = CoordToPosition.getCrusaderPosFromCoord(0, 0);
        gameBgm = Gdx.audio.newMusic(Gdx.files.internal("SFX/bgm-game.mp3"));

        gameBgm.setLooping(true);
        gameBgm.setVolume(0.5f);
        gameBgm.play();
    }

    @Override
    public void show() {

        AssetManager.UISetup(setupUI);
        AssetManager.currentGameBg = AssetManager.gameBackgrounds[MathUtil.randomInt(AssetManager.gameBackgrounds.length)];
        this.map = GameManager.LEVEL_MAPS[level].clone();
    }

    Vector2 crusaderInitPos = CoordToPosition.getCrusaderPosFromCoord(0, 0);
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateInstList();
        AssetManager.batch.begin();
        AssetManager.batch.draw(AssetManager.currentGameBg, 0f, 0f, 1920f, 1280f);
        ArrayList<Vector2> tablesPos = AssetManager.renderMap(this.map, new int[] {0, 0});
        AssetManager.renderCrusaderAnimation(InstType.MoveDown, 0f, crusaderInitPos.x, crusaderInitPos.y);
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

    }

    @Override
    public void dispose() {

    }

    void updateInstList() {
        Group root = AssetManager.stg.getRoot();
        ArrayList<InstType> mainInstList = instruction.getMainInstList();
        ArrayList<InstType> loopInstList = instruction.getLoopInstList();
        ArrayList<InstType> funcInstList = instruction.getFuncInstList();
        for (int i = 0; i < Instruction.MAIN_INST_COUNT; i++) {
            Image img = root.findActor("main-inst#" + i);
            img.setDrawable(AssetManager.instructionDrawableMap.get(mainInstList.get(i)));
        }
        for (int i = 0; i < Instruction.LOOP_FUNC_INST_COUNT; i++) {
            Image loopImg = root.findActor("loop-inst#" + i);
            Image funcImg = root.findActor("func-inst#" + i);
            loopImg.setDrawable(AssetManager.instructionDrawableMap.get(loopInstList.get(i)));
            funcImg.setDrawable(AssetManager.instructionDrawableMap.get(funcInstList.get(i)));
        }
    }

    Runnable setupUI = new Runnable() {
        @Override
        public void run() {
            ImageButton upCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.moveUp));
            ImageButton downCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.moveDown));
            ImageButton leftCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.moveLeft));
            ImageButton rightCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.moveRight));
            ImageButton funcCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.function));
            ImageButton loopCmdBtn = new ImageButton(new TextureRegionDrawable(AssetManager.loop));
            ImageButton undoBtn = new ImageButton(new TextureRegionDrawable(AssetManager.undo));
            ImageButton playBtn = new ImageButton(new TextureRegionDrawable(AssetManager.play));

            upCmdBtn.setPosition(0f, 935); downCmdBtn.setPosition(145f, 935);
            rightCmdBtn.setPosition(290, 935); leftCmdBtn.setPosition(435, 935);
            funcCmdBtn.setPosition(580, 935); loopCmdBtn.setPosition(725, 935);
            undoBtn.setPosition(870, 935); playBtn.setPosition(1015, 935);

            Image[] mainInstList = new Image[16];
            Image[] loopInstList = new Image[4], funcInstList = new Image[4];

            for (int i = 0, x = 1345, y = 975; i < mainInstList.length; i++, x += 145) {
                if (i % 4 == 0) {
                    x = 1345;
                    y -= 145;
                }
                mainInstList[i] = new Image(new TextureRegionDrawable(AssetManager.empty));
                mainInstList[i].setPosition(x, y);
                mainInstList[i].setName("main-inst#" + i);
                AssetManager.stg.addActor(mainInstList[i]);
            }
            for (int i = 0, loopX = 0, funcX = 1345; i < loopInstList.length; i++, loopX += 145, funcX += 145) {
                loopInstList[i] = new Image(new TextureRegionDrawable(AssetManager.empty));
                loopInstList[i].setName("loop-inst#" + i);
                loopInstList[i].setPosition(loopX, 0);
                funcInstList[i] = new Image(new TextureRegionDrawable(AssetManager.empty));
                funcInstList[i].setName("func-inst#" + i);
                funcInstList[i].setPosition(funcX, 0);
                setupLoopFuncDndTarget(loopInstList[i], Instruction.LOOP, loopX, 0f, 145, 145);
                setupLoopFuncDndTarget(funcInstList[i], Instruction.FUNCTION, funcX, 0f, 145, 145);
                AssetManager.stg.addActor(loopInstList[i]);
                AssetManager.stg.addActor(funcInstList[i]);
            }
            Label funcLabel = new Label("Function", AssetManager.mcSkin);
            funcLabel.setFontScale(1.5f, 1.5f);
            funcLabel.setPosition(1345, 150);
            Label loopLabel = new Label("Loop", AssetManager.mcSkin);
            loopLabel.setPosition(20, 150);
            loopLabel.setFontScale(1.5f, 1.5f);
            Label mainLabel = new Label("Main", AssetManager.mcSkin);
            mainLabel.setFontScale(1.5f, 1.5f);
            mainLabel.setPosition(1345, 1000);

            Label levelLabel = new Label("Level: " + (level + 1), AssetManager.mcSkin);
            levelLabel.setFontScale(1.5f, 1.5f);
            levelLabel.setPosition(20, 800);
            /* SETUP BUTTON LISTENERS */
            upCmdBtn.addListener(getInstBtnClickListener(InstType.MoveUp));
            downCmdBtn.addListener(getInstBtnClickListener(InstType.MoveDown));
            rightCmdBtn.addListener(getInstBtnClickListener(InstType.MoveRight));
            leftCmdBtn.addListener(getInstBtnClickListener(InstType.MoveLeft));
            loopCmdBtn.addListener(getInstBtnClickListener(InstType.Loop));
            funcCmdBtn.addListener(getInstBtnClickListener(InstType.Function));
            playBtn.addListener(new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int ptr, int btn) {
                    game.setScreen(new PlayScreen(game, level, instruction));
                    SoundManager.sButton.play(1);
                }
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int ptr, int btn) {
                    return true;
                }
            });
            undoBtn.addListener(new InputListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int ptr, int btn) {
                    SoundManager.sButton.play(1);

                    instruction.resetInstList();
                }
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int ptr, int btn) {
                    return true;
                }
            });
            setLoopFuncDndSource(upCmdBtn, InstType.MoveUp, upCmdBtn.getX(), upCmdBtn.getY(), upCmdBtn.getWidth(), upCmdBtn.getHeight());
            setLoopFuncDndSource(downCmdBtn, InstType.MoveDown, downCmdBtn.getX(), downCmdBtn.getY(), downCmdBtn.getWidth(), downCmdBtn.getHeight());
            setLoopFuncDndSource(rightCmdBtn, InstType.MoveRight, rightCmdBtn.getX(), rightCmdBtn.getY(), rightCmdBtn.getWidth(), rightCmdBtn.getHeight());
            setLoopFuncDndSource(leftCmdBtn, InstType.MoveLeft, leftCmdBtn.getX(), leftCmdBtn.getY(), leftCmdBtn.getWidth(), leftCmdBtn.getHeight());

            AssetManager.stg.addActor(loopLabel); AssetManager.stg.addActor(mainLabel); AssetManager.stg.addActor(funcLabel); AssetManager.stg.addActor(levelLabel);
            AssetManager.stg.addActor(upCmdBtn); AssetManager.stg.addActor(downCmdBtn);
            AssetManager.stg.addActor(rightCmdBtn); AssetManager.stg.addActor(leftCmdBtn);
            AssetManager.stg.addActor(funcCmdBtn); AssetManager.stg.addActor(loopCmdBtn);
            AssetManager.stg.addActor(undoBtn);
            AssetManager.stg.addActor(playBtn);

        }
    };

    InputListener getInstBtnClickListener(final InstType inst) {
        return new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int ptr, int btn) {
                SoundManager.sButton.play(1);

                if (!Gdx.input.isTouched()) {
                    if (inst == InstType.None) {
                        assert false;
                    } else {
                        instruction.setNextInst(inst, Instruction.MAIN);
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int ptr, int btn) {

                return true;
            }
        };
    }

    void setupLoopFuncDndTarget(Image target, final int codeBlockTarget, float boundX, float boundY, float boundWidth, float boundHeight) {
        target.setBounds(boundX, boundY, boundWidth, boundHeight);
        dnd.addTarget(new DragAndDrop.Target(target) {
            public boolean drag(DragAndDrop.Source src, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }
            public void reset(DragAndDrop.Source src, DragAndDrop.Payload payload, float x, float y, int pointer) {

            }
            public void drop(DragAndDrop.Source src, DragAndDrop.Payload payload, float x, float y, int pointer) {
                InstType inst = (InstType) payload.getObject();
                if (codeBlockTarget == Instruction.FUNCTION) {
                    instruction.setNextInst(inst, Instruction.FUNCTION);
                } else if (codeBlockTarget == Instruction.LOOP) {
                    instruction.setNextInst(inst, Instruction.LOOP);
                } else assert false;

                System.out.println("dropped");
            }
        });
    }

    void setLoopFuncDndSource(final ImageButton source, final InstType inst, float boundX, float boundY, float boundWidth, float boundHeight) {
        source.setBounds(boundX, boundY, boundWidth, boundHeight);
        dnd.addSource(new DragAndDrop.Source(source) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(inst);
                Image img = new Image(source.getImage().getDrawable());
                payload.setDragActor(img);
                return payload;
            }
        });
    }

}
