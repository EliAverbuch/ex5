package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PepseGameManager extends GameManager {
    private static final float NIGHT_CYCLE_LENGTH = 30;
    private static final float BOARD_WIDTH = 1800;
    private static final float BOARD_HEIGHT = 900;
    private static final int SUN_HALO_LAYER = Layer.BACKGROUND + 10;
    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    private static final int AVATAR_LAYER = Layer.DEFAULT;
    private static final Vector2 AVATAR_START_POS = Vector2.ONES.mult(400);
    private static final int SEED = 0;
    private final Color trunkColor = new Color(100, 50, 20);
    private final Color leafColor = new Color(50, 200, 30);
    private static final int TREE_LAYER = Layer.STATIC_OBJECTS  - 1;

    private UserInputListener inputListener;
    private Avatar avatar;
    private Terrain terrain;
    private Tree tree;

    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        terrain.createInRange( (int)avatar.getCenter().x() ,
                (int)avatar.getCenter().x() + 40*Block.SIZE);
        terrain.createInRange((int)avatar.getCenter().x() - 40*Block.SIZE  ,
                (int)avatar.getCenter().x());

        tree.createInRange( (int)avatar.getCenter().x() ,
                (int)avatar.getCenter().x() + 40*Block.SIZE);
        tree.createInRange((int)avatar.getCenter().x() - 40*Block.SIZE  ,
                (int)avatar.getCenter().x());

        terrain.removeInRange(avatar.getCenter().x() - BOARD_WIDTH / 2- 10* Block.SIZE,
                avatar.getCenter().x() - BOARD_WIDTH / 2- 15* Block.SIZE);

    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.inputListener = inputListener;

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimension = windowController.getWindowDimensions();

        Sky.create(gameObjects(), windowDimension, Layer.BACKGROUND);

        this.terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS,
                                                  windowDimension,SEED);
        terrain.createInRange(0, (int)windowController.getWindowDimensions().x());

        Night.create(gameObjects(), Layer.FOREGROUND,
                                   windowController.getWindowDimensions(),
                                    NIGHT_CYCLE_LENGTH);

        GameObject sun = Sun.create(gameObjects(),Layer.BACKGROUND + 1,
                                                    windowController.getWindowDimensions(),
                                                   NIGHT_CYCLE_LENGTH);

        SunHalo.create(gameObjects(),SUN_HALO_LAYER, sun, SUN_HALO_COLOR);


        this.tree  = new Tree(gameObjects(), terrain,
                windowController.getWindowDimensions(),
                TREE_LAYER, trunkColor,  leafColor, SEED);
        tree.createInRange(0, (int)windowController.getWindowDimensions().x());

         this.avatar = Avatar.create(gameObjects(), AVATAR_LAYER,
                 new Vector2(0,terrain.groundHeightAt(0) -67), inputListener, imageReader);

        setCamera(new Camera(avatar, Vector2.ZERO, windowDimension, windowDimension));



    }


    public static void main(String[] args) {
//        Random random1 = new Random(5);
//        Random random2 = new Random(6);
//        for (int i = 0; i < 100; i++) {
//            System.out.println("random1 is: " + random1.nextInt());
//            System.out.println("random2 is: " + random2.nextInt());
//            System.out.println();
//            System.out.println();
//        }
        new PepseGameManager("Pepse", new Vector2(BOARD_WIDTH, BOARD_HEIGHT)).run();
    }

}
