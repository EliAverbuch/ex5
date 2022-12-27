package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final float HALO_RADIUS = 500;

    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer, GameObject sun , Color color)  {
        Renderable renderable = new OvalRenderable(color);
        GameObject halo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(HALO_RADIUS), renderable);
        halo.setTag("halo");
        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(halo);
        //halo.addComponent(deltaTime -> halo.setCenter());
        return halo;
    }

}
