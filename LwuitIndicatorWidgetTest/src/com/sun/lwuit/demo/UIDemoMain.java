/*
 * Copyright � 2008, 2010, Oracle and/or its affiliates. All rights reserved
 */
package com.sun.lwuit.demo;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.IndicatorDefaultLookAndFeel;
import com.sun.lwuit.plaf.UIManager;
import java.util.Hashtable;

/**
 * Bootstraps the UI toolkit demos 
 *
 * 
 * @author Shai Almog
 */
public class UIDemoMain  implements ActionListener {

    private static final int EXIT_COMMAND = 1;
    private static final int BACK_COMMAND = 3;
    private static final int ABOUT_COMMAND = 4;

    //private static final Command exitCommand = new Command("Exit", EXIT_COMMAND);
    static final Command backCommand = new Command("Back", BACK_COMMAND);
    private static final Command aboutCommand = new Command("About", ABOUT_COMMAND);

    static final Demo[] DEMOS = new Demo[]{
        new ImagesDemo(), new RawDemo()
    };
    private Demo currentDemo;
    private Hashtable demosHash = new Hashtable();
    private static Form mainMenu;
    private Button[] demoButtons;

    private static UIDemoMain instance;

    private Container demoPanel;

    public static UIDemoMain getInstance() {
        if(null == instance) {
            instance=new UIDemoMain();
        }
        return instance;
    }
    
    public UIDemoMain() {
        super();
    }

    public void startApp() {
        try {
            //although calling directly to setMainFormAndParams(res) will work on
            //most devices, a good coding practice will be to allow the midp 
            //thread to return and to do all the UI on the EDT.
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    buildMainForm();
                }
            });
            
        } catch (Throwable ex) {
            ex.printStackTrace();
            Dialog.show("Exception", ex.getMessage(), "OK", null);
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg0) {
    }

    public static void setTransition(Transition in, Transition out) {
        mainMenu.setTransitionInAnimator(in);
        mainMenu.setTransitionOutAnimator(out);
        if(in != null) {
            UIManager.getInstance().getLookAndFeel().setDefaultFormTransitionIn(null);
            UIManager.getInstance().getLookAndFeel().setDefaultFormTransitionOut(null);
        }
    }

    public static void setMenuTransition(Transition in, Transition out) {
        mainMenu.setMenuTransitions(in, out);
        UIManager.getInstance().getLookAndFeel().setDefaultMenuTransitionIn(null);
        UIManager.getInstance().getLookAndFeel().setDefaultMenuTransitionOut(null);
    }

    public static Form getMainForm() {
        return mainMenu;
    }
    
    public void setMainFormAndParams(Form main, Button[] demoButtons) {
        mainMenu = main;
        UIManager.getInstance().setLookAndFeel(new IndicatorDefaultLookAndFeel());
        this.demoButtons = new Button[demoButtons.length];
        System.arraycopy(demoButtons,0,this.demoButtons,0,this.demoButtons.length);
    }

    private void buildMainForm() {
        Font.setBitmapFontEnabled(false);
        
        mainMenu.addCommand(aboutCommand);
        mainMenu.addCommandListener(this);
        
        for (int i = 0; i < demoButtons.length; i++) {
            demosHash.put(demoButtons[i], DEMOS[i]);
            demoButtons[i] = null;
        }
        demoButtons = null;
    }

    /**
     * Invoked when a command is clicked. We could derive from Command but that would 
     * require 3 separate classes.
     */
    public void actionPerformed(ActionEvent evt) {
        Command cmd = evt.getCommand();
        switch (cmd.getId()) {
             case EXIT_COMMAND:
                Display.getInstance().exitApplication();
                break;
            case BACK_COMMAND:
                currentDemo.cleanup();
                mainMenu.showBack();
                break;
            case ABOUT_COMMAND:
                Form aboutForm = new Form("About");
                aboutForm.setScrollable(false);
                aboutForm.setLayout(new BorderLayout());
                TextArea aboutText = new TextArea(getAboutText(), 5, 10);
                aboutText.setEditable(false);
                aboutForm.addComponent(BorderLayout.CENTER, aboutText);
                Command backFromAbout = new Command("Back") {
                    public void actionPerformed(ActionEvent evt) {
                        if(demoPanel != null) {
                            demoPanel.replace(demoPanel.getComponentAt(0), mainMenu, UIManager.getInstance().getLookAndFeel().getDefaultFormTransitionOut().copy(true));
                        } else {
                            mainMenu.showBack();
                        }
                    }
                };
                aboutForm.addCommand(backFromAbout);
                aboutForm.setBackCommand(backFromAbout);
                if(demoPanel != null) {
                    demoPanel.replace(demoPanel.getComponentAt(0), aboutForm, UIManager.getInstance().getLookAndFeel().getDefaultFormTransitionOut());
                } else {
                    aboutForm.show();
                }
                break;
        }
    }


    private String getAboutText() {
        return UIManager.getInstance().localize("aboutString", "Developed By: Vimal, \n\nPlease feel free to send me your feedback, suggestions or queries at \nvimal.lwuit@ymail.com");
    }

    public void buttonActionPerformed(ActionEvent evt) {
        Form f = ((Button)evt.getSource()).getComponentForm();
        if(f instanceof Dialog) {
            ((Dialog)f).dispose();
        }
        currentDemo = ((Demo) (demosHash.get(evt.getSource())));
        currentDemo.run(backCommand, UIDemoMain.this, demoPanel);
    }

    static void executeDemo(int i) {
        DEMOS[i].run(backCommand, instance, instance.demoPanel);
    }
}
