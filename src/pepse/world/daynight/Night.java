package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimension,
                                    float cycleLength) {

        Renderable renderable = new RectangleRenderable(Color.BLACK);

        GameObject night = new GameObject(Vector2.ZERO, windowDimension, renderable);

        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        night.setTag("night");

        gameObjects.addGameObject(night, layer);   // נשלח עם Layer.FOREGROUND בתור layer.

        changeTransparency(night, cycleLength);

        return night;
    }
    private static void changeTransparency(GameObject night, float cycleLength){
        new Transition<Float>(
                night,
                night.renderer()::setOpaqueness,
                0f,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }
}
