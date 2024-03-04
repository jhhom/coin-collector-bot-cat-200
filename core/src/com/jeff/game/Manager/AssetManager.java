package com.jeff.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jeff.game.GameScreens.InstType;
import com.jeff.game.Util.CoordToPosition;
import com.jeff.game.Util.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class AssetManager {
    public static SpriteBatch batch;
    public static Stage stg;

    // all assets

    // minecraft skin
    public static Skin mcSkin;
    public static Skin jeffGameMenuSkin, jeffLevelMenuSkin;
    private static TextureAtlas jeffGameMenuAtlas, jeffLevelMenuAtlas;

    // all weijian assets

    // game menu assets
    public static BitmapFont fontWhite, fontBlack;
    public static Texture menuTitle, menuBg;
    public static Sprite menuTitleSp, menuBgSp;

    // level menu assets
    public static Sprite levelTitleSp, levelBgSp;
    public static TextureRegionDrawable[] levelBtnDrawables = new TextureRegionDrawable[9];

    // tutorial assets
    public static Sprite tutorialTitleSp;
    public static Sprite tutorialImage;

    // all block assets
    public static Texture[] grassBlocks = {
            resizeTexture("Blocks/Yellow Grass 1.png", 300, 300),
            resizeTexture("Blocks/Grass 1.png", 300, 300)
    };
    public static Texture[] obstacles = {
            resizeTexture("Blocks/Obstacles/Wood Table 1.png", 300, 300),
            resizeTexture("Blocks/Obstacles/Bookshelf 4.png", 300, 300),
            resizeTexture("Blocks/Obstacles/Crate 4.png", 300, 300),
            resizeTexture("Blocks/Obstacles/Container 1.png", 300, 300)
    };
    public static Texture coin = resizeTexture("Blocks/Gold Coin.png", 99, 123);
    public final static float ANIM_DURATION = .5f;
    public static Animation<TextureRegion> walkTopRightAnim, walkBotRightAnim, walkBotLeftAnim, walkTopLeftAnim;
    public static Animation<TextureRegion> jumpTopRightAnim, jumpBotRightAnim, jumpBotLeftAnim, jumpTopLeftAnim;

    // instruction assets
    public static Texture moveUp = new Texture(Gdx.files.internal("Instruction/New/inst-walk-up.jpg"));
    public static Texture moveDown = new Texture(Gdx.files.internal("Instruction/New/inst-walk-down.jpg"));
    public static Texture moveRight = new Texture(Gdx.files.internal("Instruction/New/inst-walk-right.jpg"));
    public static Texture moveLeft = new Texture(Gdx.files.internal("Instruction/New/inst-walk-left.jpg"));
    public static Texture empty = new Texture(Gdx.files.internal("Instruction/New/inst-empty.jpg"));
    public static Texture loop = new Texture(Gdx.files.internal("Instruction/New/inst-loop.jpg"));
    public static Texture function = new Texture(Gdx.files.internal("Instruction/New/inst-function.jpg"));
    public static Texture play = new Texture(Gdx.files.internal("Instruction/New/play.jpg"));
    public static Texture undo = new Texture(Gdx.files.internal("Instruction/New/undo.jpg"));
    public static HashMap<InstType, TextureRegionDrawable> instructionDrawableMap = new HashMap<InstType, TextureRegionDrawable>()
            {
                {
                    put(InstType.None, new TextureRegionDrawable(empty));
                    put(InstType.MoveUp, new TextureRegionDrawable(moveUp));
                    put(InstType.MoveDown, new TextureRegionDrawable(moveDown));
                    put(InstType.MoveRight, new TextureRegionDrawable(moveRight));
                    put(InstType.MoveLeft, new TextureRegionDrawable(moveLeft));
                    put(InstType.Loop, new TextureRegionDrawable(loop));
                    put(InstType.Function, new TextureRegionDrawable(function));
                }
            };
    public static HashMap<InstType, Animation<TextureRegion>> walkAnimationMap = new HashMap<InstType, Animation<TextureRegion>>();
    public static HashMap<InstType, Animation<TextureRegion>> jumpAnimationMap = new HashMap<InstType, Animation<TextureRegion>>();
         // public static Texture instList = new Texture(Gdx.files.internal("Instruction/inst-list.png"));

    public static Texture gameBackgrounds[] = new Texture[] {
            new Texture (Gdx.files.internal("Background N Character Weijian/game-bg-winter.jpg")),
            new Texture (Gdx.files.internal("Background N Character Weijian/game-bg-grassland.jpg")),
            new Texture (Gdx.files.internal("Background N Character Weijian/game-bg-fantasy.jpg")),
            new Texture (Gdx.files.internal("Background N Character Weijian/game-bg-castle.jpg"))
    };
    public static Texture currentGameBg = gameBackgrounds[gameBackgrounds.length - 1];

    // Finish Screen textures
    public static Texture failBadge = new Texture(Gdx.files.internal("FinishBadges/failedbadge_failed.png"));
    public static Texture successBadge = new Texture(Gdx.files.internal("FinishBadges/winnerbadge_success.png"));

    public static void initialize() {
        stg = new Stage();
        batch = new SpriteBatch();

        mcSkin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        // weijian assets
        // game menu assets
        menuBg = new Texture(Gdx.files.internal("Background N Character Weijian/game-bg-castle.jpg"));
        menuTitle = new Texture(Gdx.files.internal("Title/Jeff Coin Collector.png"));
        jeffGameMenuAtlas = new TextureAtlas("buttonA/button1.pack");
        jeffGameMenuSkin = new Skin(jeffGameMenuAtlas);
        fontBlack = new BitmapFont(Gdx.files.internal("font/blackfont.fnt"), false);
        fontWhite = new BitmapFont(Gdx.files.internal("font/whitefont.fnt"), false);
        menuBgSp = new Sprite(menuBg);
        menuTitleSp = new Sprite(menuTitle);
        tutorialTitleSp = new Sprite(new Texture(Gdx.files.internal("Title/Tutorial.png")));

        // level menu assets
        jeffLevelMenuAtlas = new TextureAtlas("buttonB/button3.pack");
        jeffLevelMenuSkin = new Skin(jeffLevelMenuAtlas);
        levelTitleSp = new Sprite(new Texture(Gdx.files.internal("Title/Level Menu.png")));
        levelBgSp = new Sprite(new Texture(Gdx.files.internal("Background N Character Weijian/mou.jpg")));
        initLevelMenuButtons();

        // tutorial assets
        tutorialTitleSp = new Sprite(new Texture(Gdx.files.internal("Title/Tutorial.png")));
        tutorialImage = new Sprite(new Texture(Gdx.files.internal("Tutorial/Tutorial.jpg")));

        // map assets
        TextureAtlas[] crusaderAtlas = {
            new TextureAtlas(Gdx.files.internal("crusader/walk-top.atlas")),
            new TextureAtlas(Gdx.files.internal("crusader/walk-bot.atlas")),
            new TextureAtlas(Gdx.files.internal("crusader/jump-top.atlas")),
            new TextureAtlas(Gdx.files.internal("crusader/jump-bot.atlas"))
        };
        walkTopLeftAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[0].findRegions("walk-top-left"), Animation.PlayMode.LOOP);
        walkTopRightAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[0].findRegions("walk-top-right"), Animation.PlayMode.LOOP);
        walkBotLeftAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[1].findRegions("walk-bot-left"), Animation.PlayMode.LOOP);
        walkBotRightAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[1].findRegions("walk-bot-right"), Animation.PlayMode.LOOP);
        jumpTopLeftAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[2].findRegions("jump-top-left"), Animation.PlayMode.LOOP);
        jumpTopRightAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[2].findRegions("jump-top-right"), Animation.PlayMode.LOOP);
        jumpBotLeftAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[3].findRegions("jump-bot-left"), Animation.PlayMode.LOOP);
        jumpBotRightAnim = new Animation<TextureRegion>(ANIM_DURATION / 15f, crusaderAtlas[3].findRegions("jump-bot-right"), Animation.PlayMode.LOOP);

        walkAnimationMap.put(InstType.MoveUp, walkTopRightAnim);
        walkAnimationMap.put(InstType.MoveDown, walkBotLeftAnim);
        walkAnimationMap.put(InstType.MoveRight, walkBotRightAnim);
        walkAnimationMap.put(InstType.MoveLeft, walkTopLeftAnim);
        jumpAnimationMap.put(InstType.MoveUp, jumpTopRightAnim);
        jumpAnimationMap.put(InstType.MoveDown, jumpBotLeftAnim);
        jumpAnimationMap.put(InstType.MoveRight, jumpBotRightAnim);
        jumpAnimationMap.put(InstType.MoveLeft, jumpTopLeftAnim);
    }

    private static Texture resizeTexture(String pathToPic, int width, int height) {
        Pixmap ori = new Pixmap(Gdx.files.internal(pathToPic));
        Pixmap afterScale = new Pixmap(width, height, ori.getFormat());
        afterScale.drawPixmap(ori,
                0, 0, ori.getWidth(), ori.getHeight(),
                0, 0, afterScale.getWidth(), afterScale.getHeight());
        ori.dispose();
        return new Texture(afterScale);
    }

    public static void UISetup(Runnable setup) {
        stg.clear();
        setup.run();
        Gdx.input.setInputProcessor(stg);
    }

    public static void UIRender() {
        batch.begin();
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
        batch.end();
    }

    public static void renderCrusaderAnimation(InstType direction, float animTime, float xPos, float yPos) {
        TextureRegion currentFrame = walkAnimationMap.get(direction).getKeyFrame(animTime, true);
        batch.draw(currentFrame, xPos, yPos, currentFrame.getRegionWidth() * 1.5f, currentFrame.getRegionHeight() * 1.5f);
    }

    public static ArrayList<Vector2> renderMap(int[][] map, int[] crusaderCoord) {
        final int EMPTY = 0, COIN = 1, OBSTACLE = -1;
        Texture baseTile = grassBlocks[1];
        ArrayList<Vector2> tablesInFront = new ArrayList<Vector2>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Texture topTile = null;
                Vector2 topTileCoord = null;
                switch(map[i][j]) {
                    case COIN: {
                        topTile = coin;
                        topTileCoord = CoordToPosition.getCoinPosFromCoord(i, j);
                        break;
                    }
                    case OBSTACLE: {
                        Vector2 obsPos = CoordToPosition.getBlockedTilePosFromCoord(i, j);
                        Vector2 crusaderPos = CoordToPosition.getCrusaderPosFromCoord(crusaderCoord[0], crusaderCoord[1]);
                        if (obsPos.y < crusaderPos.y) {
                            tablesInFront.add(CoordToPosition.getBlockedTilePosFromCoord(i, j));
                        } else {
                            topTile = obstacles[1];
                            topTileCoord = CoordToPosition.getBlockedTilePosFromCoord(i, j);
                        }
                        break;
                    }
                }
                Vector2 baseTileCoord = CoordToPosition.getEmptyTilePosFromCoord(i, j);
                batch.draw(baseTile, baseTileCoord.x, baseTileCoord.y);
                if (topTile != null) {
                    batch.draw(topTile, topTileCoord.x, topTileCoord.y);
                }
            }
        }
        return tablesInFront;
    }

    public static void renderOverlappingTables(ArrayList<Vector2> tablesPos) {
        for (int i = 0; i < tablesPos.size(); i++) {
            batch.draw(obstacles[1], tablesPos.get(i).x, tablesPos.get(i).y);
        }
    }

    public static void dispose() {
        batch.dispose();
        mcSkin.dispose();
    }

    private static void initLevelMenuButtons() {
        String[] fileNames = {
                "Background N Character Weijian/one.png",
                "Background N Character Weijian/two.png",
                "Background N Character Weijian/three.png",
                "Background N Character Weijian/four.png",
                "Background N Character Weijian/five.png",
                "Background N Character Weijian/six.png",
                "Background N Character Weijian/seven.png",
                "Background N Character Weijian/eight.png",
                "Background N Character Weijian/nine.png",
        };
        for (int i = 0; i < levelBtnDrawables.length; i++) {
            levelBtnDrawables[i] = new TextureRegionDrawable(
                    new TextureRegion(
                            new Texture(Gdx.files.internal(fileNames[i]))
                    )
            );
        }
    }



}
