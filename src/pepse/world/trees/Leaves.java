package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Leaves {
    private static final float ANGLE = 120;

    public static void create(GameObjectCollection gameObjects,
                              double square, Vector2 center,
                              Color color, int layer) {
        Vector2 startPoint = findTopLeftCorner(center, square);
        for (int i = 0; i < square; i++) {
            for (int j = 0; j < square; j++) {
                Renderable renderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(color));
                Vector2 TLC = startPoint.add(new Vector2(j * Block.SIZE, i * Block.SIZE));
                GameObject leaf = new Block(TLC, renderable);
                new ScheduledTask(leaf, (float) (i), false, () -> Leaves.leafMovement(leaf));
                gameObjects.addGameObject(leaf, layer);
                leaf.renderer().fadeOut(10);
                leaf.transform().setVelocityY(23);

            }
        }
    }
    private static void leafMovement(GameObject leaf)  {

        new Transition<Float>(
                leaf,
                leaf.renderer()::setRenderableAngle,
                90F,
                110F,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        new Transition<Vector2>(
                leaf,
                leaf::setDimensions,
                Vector2.ONES.mult(Block.SIZE),
                Vector2.ONES.mult((float) (Block.SIZE * 1.22)),
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

    }

    private static Vector2 findTopLeftCorner(Vector2 center, double square) {
        return center.subtract(Vector2.ONES.mult((float) square * Block.SIZE));
    }
}
