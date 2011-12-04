/*
 * Copyright � 2008, 2010, Oracle and/or its affiliates. All rights reserved
 */
package com.sun.lwuit.demo;

import com.sun.lwuit.Button;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Component;
import com.sun.lwuit.ComponentGroup;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Indicator;
import com.sun.lwuit.IndicatorEventListener;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.spinner.Spinner;

/**
 *
 */
public class ImagesDemo extends Demo {
	
    Container indicator;
    Indicator in;
    private IndicatorEventListener indiListener;
	
    public String getName() {
        return "Images";
    }

    protected void executeDemo(Form f) {
        final Container ctn = f.getContentPane();
        ctn.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        ctn.setScrollable(false);
        Border border = Border.createLineBorder(1, 0x000000);
        Style style = ctn.getUnselectedStyle(); 
        style.setBorder(border, false);
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;

        in = new Indicator();
        in.setType(Indicator.TYPE_RAW_BOX);
        in.setQuantum(1);
        in.setTotal(7);
        in.setBehaviour(Indicator.BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE);
        
        Style s = in.getUnselectedStyle(); 
        s.setAlignment(Component.CENTER);
        s.setBorder(border, false);
        s.setBgColor(0xE1E1E1, false);
        s.setBgTransparency(255, false);
        s.setFgColor(0x666666, false);
        s.setMargin(0, 0, 0, 0);
        s.setPadding(4, 4, 4, 4);
        
        s = in.getSelectedStyle();
        s.setAlignment(Component.CENTER);
        s.setBorder(border, false);
        s.setBgColor(0xE1E1E1, false);
        s.setBgTransparency(255, false);
        s.setFgColor(0x666666, false);
        s.setMargin(0, 0, 0, 0);
        s.setPadding(4, 4, 4, 4);
        
        s = in.getPressedStyle();
        s.setAlignment(Component.CENTER);
        s.setBorder(border, false);
        s.setBgColor(0xE1E1E1, false);
        s.setBgTransparency(255, false);
        s.setFgColor(0x666666, false);
        s.setMargin(0, 0, 0, 0);
        s.setPadding(4, 4, 4, 4);
        
        s = in.getDisabledStyle();
        s.setAlignment(Component.CENTER);
        s.setBorder(border, false);
        s.setBgColor(0xE1E1E1, false);
        s.setBgTransparency(255, false);
        s.setFgColor(0x666666, false);
        s.setMargin(0, 0, 0, 0);
        s.setPadding(4, 4, 4, 4);

        in.setNavigationEndsShown(true);
        
        indiListener = in.getIndicatorEventListener();
        
        indicator = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        style = indicator.getUnselectedStyle(); 
        style.setBorder(border, false);
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;
        indicator.addComponent(in);
        ctn.addComponent(indicator);

        Container controls = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        controls.setScrollableX(false);
        controls.setScrollableY(true);
        style = controls.getUnselectedStyle(); 
        style.setBorder(border, false);
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;
        ctn.addComponent(controls);
        
        Button message = new Button("            Press 4 to navigate left in the indicator,            Press 6 to navigate right in the indicator.");
        message.setUIID("Label");
        message.setTickerEnabled(true);
        controls.addComponent(message);
        
        CheckBox cbxNaviEnds = new CheckBox("Show Navigation Ends");
        cbxNaviEnds.setSelected(true);
        cbxNaviEnds.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		in.setNavigationEndsShown(((CheckBox)arg0.getSource()).isSelected());
        		in.setShouldCalcPreferredSize(true);
        		indicator.revalidate();
        	}
        });
        controls.addComponent(cbxNaviEnds);
        
        CheckBox cbxNaviIncrement = new CheckBox("Show Navigation Increment");
        cbxNaviIncrement.setSelected(true);
        cbxNaviIncrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				in.setNavigationIncrementShown(((CheckBox)arg0.getSource()).isSelected());
				in.setShouldCalcPreferredSize(true);
				indicator.revalidate();
			}
		});
        controls.addComponent(cbxNaviIncrement);

        ComboBox behaviour = new ComboBox(new Object[]{
        		"Navigation None",
        		"Navigation Always",
        		"Navigation When Besides"
        });
        behaviour.setSelectedIndex(in.getBehaviour());
        behaviour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
        		in.setBehaviour(((ComboBox)arg0.getSource()).getSelectedIndex()+1);
        		in.setShouldCalcPreferredSize(true);
        		indicator.revalidate();
			}
		});
        controls.addComponent(createPair("Behaviour", behaviour, BorderLayout.CENTER));
        
        final String[] hAlignements = new String[] {
    		"Left", "Right", "Center"
        };
        int alignVal = in.getStyle().getAlignment();
        if (alignVal == Component.LEFT) {
        	alignVal = 0;
        } else if (alignVal == Component.RIGHT) {
        	alignVal = 1;
        } else if (alignVal == Component.CENTER) {
        	alignVal = 2;
        }
        ComboBox hori = new ComboBox(hAlignements);
        hori.setSelectedIndex(alignVal);
        hori.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
				final String val = ((String)((ComboBox)arg0.getSource()).getSelectedItem()).toUpperCase();
				int alignVal = 0;
				if (val.compareTo(hAlignements[0].toUpperCase()) == 0) {
					alignVal = Component.LEFT;
				} else if (val.compareTo(hAlignements[1].toUpperCase()) == 0) {
					alignVal = Component.RIGHT;
				} else if (val.compareTo(hAlignements[2].toUpperCase()) == 0) {
					alignVal = Component.CENTER;
				}
        		in.getUnselectedStyle().setAlignment(alignVal);
        		in.getSelectedStyle().setAlignment(alignVal);
        		in.getPressedStyle().setAlignment(alignVal);
        		in.getDisabledStyle().setAlignment(alignVal);
        		in.setShouldCalcPreferredSize(true);
        		indicator.revalidate();
        	}
        });
        final String[] vAlignments = new String[] {
    		"Top", "Bottom", "Center"
        };
        alignVal = in.getVerticalAlignment();
        if (alignVal == Component.TOP) {
        	alignVal = 0;
        } else if (alignVal == Component.BOTTOM) {
        	alignVal = 1;
        } else if (alignVal == Component.CENTER) {
        	alignVal = 2;
        }
        ComboBox vert = new ComboBox(vAlignments);
        vert.setSelectedIndex(alignVal);
        vert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String val = ((String)((ComboBox)arg0.getSource()).getSelectedItem()).toUpperCase();
				if (val.compareTo(vAlignments[0].toUpperCase()) == 0) {
					in.setVerticalAlignment(Component.TOP);
				} else if (val.compareTo(vAlignments[1].toUpperCase()) == 0) {
					in.setVerticalAlignment(Component.BOTTOM);
				} else if (val.compareTo(vAlignments[2].toUpperCase()) == 0) {
					in.setVerticalAlignment(Component.CENTER);
				}
				in.setShouldCalcPreferredSize(true);
        		indicator.revalidate();
			}
		});
        controls.addComponent(createPair("Horizontal Align", hori, BorderLayout.CENTER));
        controls.addComponent(createPair("Vertical Align", vert, BorderLayout.CENTER));

        Container gapping = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        gapping.setScrollable(false);
        style = gapping.getUnselectedStyle(); 
        style.setBorder(border, false);
        style.setMargin(0, 5, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;
        Container gaps = new Container(new BoxLayout(BoxLayout.X_AXIS));
        gaps.setScrollable(false);
        style = gaps.getUnselectedStyle(); 
        //style.setBorder(border, false);
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;        
        gapping.addComponent(new Label("Gapping"));
        gapping.addComponent(gaps);
        
        Spinner gapSpinner = Spinner.create(0, 20, in.getGap(), 1);
        gapSpinner.setValue(new Integer(in.getGap()));
        gapSpinner.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		in.setGap(((Integer)((Spinner)arg0.getSource()).getValue()).intValue());
        		in.setShouldCalcPreferredSize(true);
        		indicator.requestFocus();
        		indicator.revalidate();
        	}
        });
        gaps.addComponent(createPair("Item", gapSpinner, BorderLayout.SOUTH));
        
        Spinner naviToItemGapSpinner = Spinner.create(0, 20, in.getNavigationToItemGap(), 1);
        naviToItemGapSpinner.setValue(new Integer(in.getNavigationToItemGap()));
        naviToItemGapSpinner.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		in.setNavigationToItemGap(((Integer)((Spinner)arg0.getSource()).getValue()).intValue());
        		in.setShouldCalcPreferredSize(true);
        		indicator.requestFocus();
        		indicator.revalidate();
        	}
        });
        gaps.addComponent(createPair("2Item", naviToItemGapSpinner, BorderLayout.SOUTH));
        
        Spinner naviGapSpinner = Spinner.create(0, 20, in.getNavigationGap(), 1);
        naviGapSpinner.setValue(new Integer(in.getNavigationGap()));
        naviGapSpinner.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		in.setNavigationGap(((Integer)((Spinner)arg0.getSource()).getValue()).intValue());
        		in.setShouldCalcPreferredSize(true);
        		indicator.requestFocus();
        		indicator.revalidate();
        	}
        });
        gaps.addComponent(createPair("2Navi", naviGapSpinner, BorderLayout.SOUTH));
        
        Spinner naviToBorderGapSpinner = Spinner.create(0, 20, in.getNavigationToBorderGap(), 1);
        naviToBorderGapSpinner.setValue(new Integer(in.getNavigationToBorderGap()));
        naviToBorderGapSpinner.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		in.setNavigationToBorderGap(((Integer)((Spinner)arg0.getSource()).getValue()).intValue());
        		in.setShouldCalcPreferredSize(true);
        		indicator.requestFocus();
        		indicator.revalidate();
        	}
        });
        gaps.addComponent(createPair("2Bdr", naviToBorderGapSpinner, BorderLayout.SOUTH));
        
        Spinner maxIndiPerGroupSpinner = Spinner.create(0, 20, in.getMaxTimelineItems(), 1);
        maxIndiPerGroupSpinner.setValue(new Integer(in.getMaxTimelineItems()));
        maxIndiPerGroupSpinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				in.setMaxTimelineItems(((Integer)((Spinner)arg0.getSource()).getValue()).intValue());
				in.setShouldCalcPreferredSize(true);
				indicator.requestFocus();
				indicator.revalidate();
			}
		});
        gaps.addComponent(createPair("Group", maxIndiPerGroupSpinner, BorderLayout.SOUTH));
        controls.addComponent(gapping);
        
        ctn.revalidate();
        
        final ActionListener leftKey = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                indiListener.previous();
            }
        };
        final ActionListener rightKey = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                indiListener.next();
            }
        };
        
        f.addKeyListener(52, leftKey);
        f.addKeyListener(54, rightKey);
        f.addKeyListener(Display.GAME_LEFT, leftKey);
        f.addKeyListener(Display.GAME_RIGHT, rightKey);
        
        style = null;
        border = null;
        s = null;
    }
    
    public void revalidateIndicator() {
    	indicator.revalidate();
    }
    
    protected Container createPair(String label, Component c, String layout) {
    	return createPair(label,c,layout,0);
    }
    
    /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout with a minimum label width
     */
    protected Container createPair(String label, Component c, String layout, int minWidth) {
        Container pair;
        Label l =  new Label(label);
        Dimension d = l.getPreferredSize();
        d.setWidth(Math.max(d.getWidth(), minWidth));
        l.setPreferredSize(d);
        c.setLabelForComponent(l);
        if(UIManager.getInstance().isThemeConstant("ComponentGroupBool", false)) {
            pair = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            pair.addComponent(l);
            l.setUIID("TitleLabel");
            ComponentGroup g = new ComponentGroup();
            g.addComponent(c);
            pair.addComponent(g);
        } else {
            pair = new Container(new BorderLayout());
            pair.addComponent(BorderLayout.WEST,l);
            pair.addComponent(layout, c);
        }
        pair.setScrollable(false);
        Style style = pair.getUnselectedStyle(); 
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
        style = null;

        return pair;
    }
    
      /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout
     */
     protected Container createPair(String label, Component c) {
         return createPair(label,c,BorderLayout.CENTER,0);
     }
}