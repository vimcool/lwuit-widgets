/*
 * Copyright � 2008, 2010, Oracle and/or its affiliates. All rights reserved
 */
package com.sun.lwuit.demo;

import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.IndicatorEventListener;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.plaf.UIManager;

/**
 */
public class RawDemo extends Demo {
	
    private IndicatorEventListener indiListener;

    public String getName() {
        return "Raw";
    }

    protected String getHelp() {
        return UIManager.getInstance().localize("buttonHelp", "Help description");
    }

    protected void executeDemo(final Form f) {
        final Container ctn = f.getContentPane();
    	ctn.addComponent(new TextArea("Raw mode indicator is under development, please visit back or write to me vimal.lwuit@ymail.com"));
//        ctn.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//
//        Indicator in = new Indicator();
//        in.setType(Indicator.TYPE_RAW_BOX);
//        in.setGap(4);
//        in.setQuantum(5);
//        in.setTotal(50);
//        
//        Style s = in.getUnselectedStyle(); 
//        s.setAlignment(Component.CENTER);
//        s.setBgColor(0xE1E1E1, false);
//        s.setBgTransparency(0, false);
//        s.setFgColor(0x666666, false);
//        s.setMargin(0, 5, 0, 0);
//        
//        s = in.getSelectedStyle();
//        s.setAlignment(Component.CENTER);
//        s.setBgColor(0xE1E1E1, false);
//        s.setBgTransparency(0, false);
//        s.setFgColor(0x666666, false);
//        s.setMargin(0, 5, 0, 0);
//        
//        s = in.getPressedStyle();
//        s.setAlignment(Component.CENTER);
//        s.setBgColor(0xE1E1E1, false);
//        s.setBgTransparency(0, false);
//        s.setFgColor(0x666666, false);
//        s.setMargin(0, 5, 0, 0);
//        
//        s = in.getDisabledStyle();
//        s.setAlignment(Component.CENTER);
//        s.setBgColor(0xE1E1E1, false);
//        s.setBgTransparency(0, false);
//        s.setFgColor(0x666666, false);
//        s.setMargin(0, 5, 0, 0);
//
//        indiListener = in.getIndicatorEventListener();
//        ctn.addComponent(in);
//
//        s = null;
//        in = null;
    }
}
