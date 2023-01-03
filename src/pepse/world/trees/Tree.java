package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tree {

    private ArrayList<Block> leafs;
    private final Random random = new Random();
    private static final double CREATE_TREE_COIN = 0.1;

    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimension;
    private final Terrain terrain;
    private final int treeLayer;
    private final Color trunkColor;
    private final Color leafColor;

    public Tree(GameObjectCollection gameObjects, Terrain terrain,
                                                Vector2 windowDimension,
                                                int treeLayer,
                                                Color trunkColor,
                                                Color leafColor,
                                                int seed)  {

        this.gameObjects = gameObjects;
        this.windowDimension = windowDimension;
        this.terrain = terrain;
        this.treeLayer = treeLayer;
        this.trunkColor = trunkColor;
        this.leafColor = leafColor;
    }

    public void createInRange(int minX, int maxX) {

        int actualMinX = Terrain.getAprox(minX, true);
        int actualMaxX = Terrain.getAprox(maxX, false);

        for (int i = actualMinX; i < actualMaxX; i += Block.SIZE) {
            if(random.nextDouble() <= CREATE_TREE_COIN){

                System.out.println("creating tree");

                float heightAtI = terrain.groundHeightAt(i);

                double randomHeightOfTree1 = windowDimension.y() * (0.35 + random.nextDouble() * 0.55);
                        //windowDimension.y() - heightAtI + heightAtI * (0.3333 + random.nextDouble());
                double randomHeightOfTree = Math.min(windowDimension.y() - heightAtI * 0.5, heightAtI * 2.5);

                Trunk.create(gameObjects, new Vector2(i, windowDimension.y()),
                             trunkColor, treeLayer, randomHeightOfTree1);

                Leaves.create(gameObjects, 8,
                             new Vector2( i + 4 * Block.SIZE, (float) (windowDimension.y() - randomHeightOfTree1 + 2 * Block.SIZE)),
                             leafColor, treeLayer);
            }
        }
    }
}

