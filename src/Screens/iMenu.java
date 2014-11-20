package Screens;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

/**
 * Created by coco on 14-11-16.
 */
public interface iMenu {

    public int eventManager(RenderWindow App);

    public int mouseManager(Event event, RenderWindow App);

    public int keyboardManager(Event event, RenderWindow App);

    public void menuSelectionne(int numero);

}
