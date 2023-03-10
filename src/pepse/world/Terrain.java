package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.PerslinNoise;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Terrain {
    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final float groundHeightAtX0;
    private final PerslinNoise perslinNoise;
    private HashMap<Integer, ArrayList<Block>> currTerrain = new HashMap<>();
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer, Vector2 windowDimensions,
                   int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
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
        return groundHeightAtX0 + (float) Math.sin(x / windowDimensions.y()) *
                                                    (windowDimensions.y() / 5)
                                  + (float) perslinNoise.noise(x) * Block.SIZE;
    }

    public void createInRange(int minX, int maxX) {

        int actualMinX = getAprox(minX, false);
        int actualMaxX = getAprox(maxX, true);

        int numOfColumn = (actualMaxX - actualMinX) / Block.SIZE;


            for (int i = 0; i < numOfColumn; i++) {

                ArrayList<Block> arrayCol = new ArrayList<>();

                float columnHeight = (float) Math.floor(groundHeightAt(actualMinX + i * Block.SIZE));

                int numOfBlocksPerColumn = (int) (windowDimensions.y() - columnHeight) / Block.SIZE;
                if(!this.currTerrain.containsKey(actualMinX + i * Block.SIZE))
                {
                    for (int j = 0; j < numOfBlocksPerColumn; j++) {

                        Renderable renderable =
                                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));

                        Vector2 topLeftCorner = new Vector2(actualMinX + i * Block.SIZE,
                                windowDimensions.y() - (j + 1) * Block.SIZE);

                        Block block = new Block(topLeftCorner, renderable);

                        block.setTag("ground");

                        gameObjects.addGameObject(block, groundLayer); // ?????????? ???????? ???? Layer.STATIC_OBJECTS.

                        arrayCol.add(block);

                    }
                }

                this.currTerrain.put(actualMinX + i * Block.SIZE, arrayCol);
            }

    }

    public void removeInRange(double right, double left){
        int actualRight = (int) right;
        int actualLeft = (int) left;

        while (actualLeft<actualRight){
            if(this.currTerrain.containsKey(actualLeft)){
                for (int i = 0; i < currTerrain.get(actualLeft).size(); i++) {
                    this.gameObjects.removeGameObject(currTerrain.get(actualLeft).remove(i));
                }
                this.currTerrain.remove(actualLeft);
            }
            actualLeft++;
        }

    }


    public static int getAprox(int xCordinate, boolean shouldRoundUp){
        int res = xCordinate;

        while (res % Block.SIZE != 0) {
            if (shouldRoundUp)
                res++;
            else
                res--;
        }

            return res;
    }
}
