package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final String AVATAR_FILE = "i2.png";
    private static final Vector2 AVATAR_SIZE = Vector2.ONES.mult(100);
    private static UserInputListener inputListener;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    private Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);

    }

    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader)  {

        Avatar.inputListener = inputListener;

        Renderable avatarImage = imageReader.readImage(AVATAR_FILE,false);

        Avatar avatar = new Avatar(topLeftCorner, AVATAR_SIZE, avatarImage);

        gameObjects.addGameObject(avatar, layer);

        return avatar;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir  = Vector2.DOWN;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            movementDir = movementDir.add(Vector2.LEFT);

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT ))
            movementDir = movementDir.add(Vector2.RIGHT);

        setVelocity(movementDir.mult(300));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);


        //System.out.println(getVelocity().y());
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            setVelocity(Vector2.UP.mult(500));
        }

       if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            setVelocity(Vector2.UP.mult(300));
        }

    }
}
