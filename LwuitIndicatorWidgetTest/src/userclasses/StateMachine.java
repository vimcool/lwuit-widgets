/**
 * Your application code goes here
 */

package userclasses;

import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import generated.StateMachineBase;
import com.sun.lwuit.demo.UIDemoMain;
import com.sun.lwuit.events.ActionListener;

/**
 *
 * 
 * @author Vimal
 */
public class StateMachine extends StateMachineBase {
    
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }

    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
        
    }

    protected void beforeIndicatorDemoMain(Form f) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.beforeIndicatorDemoMain(f);

        Container root = f.getContentPane();
        UIDemoMain.getInstance().setMainFormAndParams(f, new Button[]{findImages(root), findRaw(root)});
        UIDemoMain.getInstance().startApp();
        root = null;
    }

    protected void onIndicatorDemoMain_ImagesAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onIndicatorDemoMain_ImagesAction(c, event);
        
        UIDemoMain.getInstance().buttonActionPerformed(event);
    }

    protected void onIndicatorDemoMain_RawAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onIndicatorDemoMain_RawAction(c, event);
        
        UIDemoMain.getInstance().buttonActionPerformed(event);
    }
}
