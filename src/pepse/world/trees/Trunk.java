package pepse.world.trees;


import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Trunk {
    public static void create(
            GameObjectCollection gameObjects,
            Vector2 topLeftCorner,
            Color color,
            int layer,
            double height ){

        int numOfBlocks = (int)(height / Block.SIZE);
        for (int i = numOfBlocks; i > 0; i--) {
            Renderable renderable =
                    new RectangleRenderable(ColorSupplier.approximateColor(color));
            Vector2 TLC = new Vector2(topLeftCorner.x(), topLeftCorner.y()  - (i * Block.SIZE));
            Block block = new Block(TLC, renderable);
            gameObjects.addGameObject(block, layer);
        }
    }
}
