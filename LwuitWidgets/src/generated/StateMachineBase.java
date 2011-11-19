/**
 * This class contains generated code from the LWUIT resource editor, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://lwuit.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
*/
package generated;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import com.sun.lwuit.util.UIBuilder;

public abstract class StateMachineBase extends UIBuilder {
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.open(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            return showForm("IndicatorDemoMain", null);
        } else {
            Form f = (Form)createContainer(resPath, "IndicatorDemoMain");
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.open(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "IndicatorDemoMain");
    }

    protected void initTheme(Resources res) {
            String[] themes = res.getThemeResourceNames();
            if(themes != null && themes.length > 0) {
                UIManager.getInstance().setThemeProps(res.getTheme(themes[0]));
            }
    }

    public StateMachineBase() {
    }

    public StateMachineBase(String resPath) {
        this(null, resPath, true);
    }

    public StateMachineBase(Resources res) {
        this(res, null, true);
    }

    public StateMachineBase(String resPath, boolean loadTheme) {
        this(null, resPath, loadTheme);
    }

    public StateMachineBase(Resources res, boolean loadTheme) {
        this(res, null, loadTheme);
    }

    public com.sun.lwuit.Button findImages(Container root) {
        return (com.sun.lwuit.Button)findByName("Images", root);
    }

    public com.sun.lwuit.Button findRaw(Container root) {
        return (com.sun.lwuit.Button)findByName("Raw", root);
    }

    public com.sun.lwuit.Form findIndicatorDemoMain(Container root) {
        return (com.sun.lwuit.Form)findByName("IndicatorDemoMain", root);
    }

    public static final int COMMAND_IndicatorDemoMainExit = 1;

    protected boolean onIndicatorDemoMainExit() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_IndicatorDemoMainExit:
                if(onIndicatorDemoMainExit()) {
                    ev.consume();
                }
                return;

        }
    }

    protected void exitForm(Form f) {
        if("IndicatorDemoMain".equals(f.getName())) {
            exitIndicatorDemoMain(f);
            return;
        }

    }


    protected void exitIndicatorDemoMain(Form f) {
    }

    protected void beforeShow(Form f) {
        if("IndicatorDemoMain".equals(f.getName())) {
            beforeIndicatorDemoMain(f);
            return;
        }

    }


    protected void beforeIndicatorDemoMain(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        if("IndicatorDemoMain".equals(c.getName())) {
            beforeContainerIndicatorDemoMain(c);
            return;
        }

    }


    protected void beforeContainerIndicatorDemoMain(Container c) {
    }

    protected void postShow(Form f) {
        if("IndicatorDemoMain".equals(f.getName())) {
            postIndicatorDemoMain(f);
            return;
        }

    }


    protected void postIndicatorDemoMain(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("IndicatorDemoMain".equals(c.getName())) {
            postContainerIndicatorDemoMain(c);
            return;
        }

    }


    protected void postContainerIndicatorDemoMain(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("IndicatorDemoMain".equals(rootName)) {
            onCreateIndicatorDemoMain();
            return;
        }

    }


    protected void onCreateIndicatorDemoMain() {
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        if(rootContainerName == null) return;
        if(rootContainerName.equals("IndicatorDemoMain")) {
            if("Images".equals(c.getName())) {
                onIndicatorDemoMain_ImagesAction(c, event);
                return;
            }
            if("Raw".equals(c.getName())) {
                onIndicatorDemoMain_RawAction(c, event);
                return;
            }
        }
    }

      protected void onIndicatorDemoMain_ImagesAction(Component c, ActionEvent event) {
      }

      protected void onIndicatorDemoMain_RawAction(Component c, ActionEvent event) {
      }

}
