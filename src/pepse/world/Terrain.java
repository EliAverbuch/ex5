package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.PerslinNoise;

import java.awt.*;
import java.util.Vector;

public class Terrain {
    private GameObjectCollection gameObjects;
    private Vector2 windowDimensions;
    private float groundHeightAtX0;
    private PerslinNoise perslinNoise;
    private static final int TERRAIN_DEPTH = 20;
    private static final Color BASE_GROUND_COLOR =
                               new Color(212, 123, 74);

    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer, Vector2 windowDimensions,
                   int seed) {
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        groundHeightAtX0 = (windowDimensions.y() * 2) / 3;
        perslinNoise = new PerslinNoise(seed);
    }

    /**
     * This method return the ground height at a given location.
     *
     * @param x A number
     * @return The ground height at the given location.
     */
    public float groundHeightAt(float x) {
        return groundHeightAtX0 + (float) Math.sin(x / windowDimensions.y()) * (windowDimensions.y() / 5)
                + (float) perslinNoise.noise(x) * Block.SIZE;
    }

    public void createInRange(int minX, int maxX) {
        int actualMinX = getAprox(minX, false);
        int actualMaxX = getAprox(maxX, true);
        int numOfColumn = (actualMaxX - actualMinX) / Block.SIZE;
        for (int i = 0; i < numOfColumn; i++) {
            float columnHeight = (float) Math.floor(groundHeightAt(actualMinX + i * Block.SIZE));
            int numOfBlocksPerColumn = (int)columnHeight / Block.SIZE -24;
            for (int j = 0; j < numOfBlocksPerColumn; j++) {
                Renderable renderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Vector2 topLeftCorner= new Vector2(actualMinX + i * Block.SIZE,
                        windowDimensions.y() - j * Block.SIZE - 155);
                Block block = new Block(topLeftCorner,renderable);
                gameObjects.addGameObject(block, Layer.STATIC_OBJECTS);

            }

        }

    }


    static private int getAprox(int xCordinate, boolean shouldRoundUp){
        int res = xCordinate;

        while (res % Block.SIZE != 0) {
            if (!shouldRoundUp)
                res--;
            else
                res++;
        }

            return res;
    }





}
