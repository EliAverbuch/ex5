package pepse.world.trees;


import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Trunk {
    private static HashMap<Float, ArrayList<Block>> trunkArray = new HashMap<>();
    public static void create(
            GameObjectCollection gameObjects,
            Vector2 topLeftCorner,
            Color color,
            int layer,
            double height ){
        if(!trunkArray.containsKey(topLeftCorner.x()))
        {
            System.out.println("creating tree");
            ArrayList<Block> trunk = new ArrayList<>();
            int numOfBlocks = (int) (height / Block.SIZE);

            for (int i = numOfBlocks; i > 0; i--) {
                Renderable renderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(color));
                Vector2 TLC = new Vector2(topLeftCorner.x(), topLeftCorner.y() - (i * Block.SIZE));
                Block block = new Block(TLC, renderable);
                gameObjects.addGameObject(block, layer);
                trunk.add(block);
            }
            trunkArray.put(topLeftCorner.x(), trunk);
        }
    }

    public static void removeInRange(GameObjectCollection gameObjects,float xCoor)  {
        if(trunkArray.containsKey(xCoor))  {
            for (int i = 0; i < trunkArray.get(xCoor).size(); i++) {
                gameObjects.removeGameObject(trunkArray.get(xCoor).remove(i));
            }
            trunkArray.remove(xCoor);

        }
        }

}
