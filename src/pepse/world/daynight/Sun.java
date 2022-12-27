package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.security.PublicKey;

public class Sun{

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength){
        Renderable renderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(new Vector2(0,250)
                ,new Vector2(200,200),renderable);
        gameObjects.addGameObject(sun, layer);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        new Transition<Float>(
                sun,
                angelToCal -> sun.setCenter(new Vector2(windowDimensions.x() / 100 * angelToCal-
                        (float) Math.sin(Math.toRadians(angelToCal))*(windowDimensions.x()/2),
                        windowDimensions.y() / 100 * angelToCal-
                                (float) Math.sin(Math.toRadians(angelToCal  ))*(windowDimensions.y()/2))),
                0F,
                360F,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength
                ,Transition.TransitionType.TRANSITION_LOOP,
                null
        );

        return sun;
    }
}
