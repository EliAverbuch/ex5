package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun{

    private static final float SUN_RADIUS = 200;
    private static final Vector2 SUN_RADIUS_VEC = new Vector2(SUN_RADIUS, SUN_RADIUS);
    private static final float TOP_LEFT_CORNER_Y = 250;

    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimensions,
                                    float cycleLength){

        Renderable renderable = new OvalRenderable(Color.YELLOW);

        GameObject sun = new GameObject(new Vector2(0, 0)
                                     ,SUN_RADIUS_VEC,renderable);

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        sun.setTag("sun");

        changeTransparency(sun, cycleLength, windowDimensions);

        gameObjects.addGameObject(sun, layer); //נשלח עם  1+ Layer.BACKGROUND בתור layer.


        return sun;
    }

    private static void changeTransparency(GameObject sun, float cycleLength, Vector2 windowDimensions)  {
        new Transition<Float>(
                sun,
                angleToCalc -> sun.setCenter(calcSunPosition(windowDimensions, angleToCalc)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength
                ,Transition.TransitionType.TRANSITION_LOOP,
                null
        );

    }
    private static Vector2 calcSunPosition(Vector2 windowDimension, Float angleToCalc) {

        float multX = windowDimension.x() * 0.45f;
        float multY = windowDimension.y() * 0.45f;

        return new Vector2(windowDimension.mult(0.5f).add(Vector2.UP.rotated(angleToCalc).
                                                multX(multX).multY(multY)));

//        return  new Vector2(windowDimension.x() / 100 * angleToCalc -
//                (float) Math.sin(Math.toRadians(angleToCalc))*(windowDimension.x()/2),
//                windowDimension.y() / 100 * angleToCalc -
//                        (float) Math.sin(Math.toRadians(angleToCalc  ))*(windowDimension.y()/2));
    }
}

//    //        private static final float SUN_PERCENTAGE_SCREEN_FROM_MIDDLE = 0.45f;
//    float screenX = windowDimensions.x() * SUN_PERCENTAGE_SCREEN_FROM_MIDDLE;
//    float screenY = windowDimensions.y() * SUN_PERCENTAGE_SCREEN_FROM_MIDDLE;
//
//        new Transition<>(
//        sun, //the game object being changed
//        (angle -> sun.setCenter(
//        windowDimensions.mult(0.5f).add(Vector2.UP.rotated(angle).
//        multX(screenX).multY(screenY)))),/*
// *Changes the location of the sun relative to the angle it should be taking relative to the
// *center of the screen.
// */
