package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.util.ArrayList;

public class Leaf {


    /**
     * this methode create one square leaf group in a specific index. each leaf group is made of blocks;
     *
     * @param xIndex      x index location
     * @param yIndex      y index location
     * @param gameObjects The collection of all participating game objects.
     * @param leafLayer   layer in which the leaf is created
     * @return array that save the leaves;
     */
    static public ArrayList<Block> create(float xIndex, float yIndex, GameObjectCollection gameObjects,
                                          int leafLayer) {

        ArrayList<Block> blockArrayList = new ArrayList<>((int) (SIZE * SIZE));

        for (float x = xIndex - (SIZE / 2); x < xIndex + (SIZE / 2); x += Block.SIZE)
            for (float y = yIndex - (SIZE / 2); y < yIndex + (SIZE / 2); y += Block.SIZE) {
                blockArrayList.add(createOneLeaf(x, y,
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR))
                        , gameObjects, leafLayer));
            }

        return blockArrayList;
    }



    /*
     * this methode create one leaf in a specific index and layer. Each leaf is made of block that moves in
     * the wind and fade out;
     * return leaf block;
     */

    private static Block createOneLeaf(float x, float y,
                                       Renderable renderable,
                                       GameObjectCollection gameObjects,
                                       int leafLayer) {

        Vector2 originTopLeftCorner = new Vector2(x, y);
        Block leaf = new Block(originTopLeftCorner, renderable);
        leaf.setLayer(leafLayer);
        gameObjects.addGameObject(leaf, leafLayer);
        leaf.setTag("leaf");

        new ScheduledTask(leaf, random.nextFloat(), false, () -> moveWind(leaf));
        new ScheduledTask(leaf, random.nextInt(MAX_TIME_UNTIL_FALL), false,
                () -> fall(leaf, originTopLeftCorner, gameObjects, leafLayer));

        return leaf;
    }
















}



