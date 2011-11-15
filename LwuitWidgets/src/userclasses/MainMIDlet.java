/*
 * Feel free to update this class
 */

package userclasses;

import com.sun.lwuit.Display;
import javax.microedition.midlet.MIDlet;

/**
 * @author Vimal (vimal.lwuit@ymail.com)
 */
public class MainMIDlet extends MIDlet implements Runnable {
    public void startApp() {
        Display.init(this);
        Display.getInstance().callSerially(this);
    }

    public void run() {
        new StateMachine("/IndicatorDemo.res");
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
