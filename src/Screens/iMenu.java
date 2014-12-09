package Screens;

import org.jsfml.graphics.RenderWindow;

import org.jsfml.window.event.Event;


/**
 * @Interface iMenu
 * @author Corentin RAOULT, Yannis M'RAD, Steven FOUGERON
 * 
 * Interface représentant un Menu
 *
 */
public interface iMenu {


    public int eventManager(RenderWindow App);

    public int mouseManager(Event event, RenderWindow App);

    public int keyboardManager(Event event, RenderWindow App);

    public void menuSelectionne(int numero);

}
