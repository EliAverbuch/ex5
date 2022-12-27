package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Sky;
import pepse.world.Terrain;

public class PepseGameManager extends GameManager {
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);
        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions(),24);
        terrain.createInRange(200, 400);
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
        new PepseGameManager().run();
    }

}
