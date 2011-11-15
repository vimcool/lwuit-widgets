package com.sun.lwuit;

import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.plaf.IndicatorDefaultLookAndFeel;
import com.sun.lwuit.plaf.UIManager;

/**
 * Widget to show the navigation indicator.
 * <br>
 * <b>Use Case:</b>
 * <ul>
 *  <li>To navigation indicators for a paginated view, like in RSS feed readers on Android's
 * </ul>
 * <b>NOTE:</b>
 * <ul>
 *  <li>Non navigation is an item, and group of items make a timeline.
 *  <li>Terminology for left / right page arrows in this component is previous and next navigation respectively
 *  <li>Terminology for first / last page arrows in this component is first and last navigation respectively
 * </ul>
 * <br>
 * <b>TODO:</b> 
 * <ul>
 *  <li>Support focused state image rendering
 *  <li>Support more Indicator Non-Image Types: NUMBERS, OVALS, ARCS
 *  <li>Support more Indicator Image Type: IMAGE
 *  <li>Support more vertical aligments: TOP and BOTTOM
 *  <li>Support variable dimensioned images for Dimmed, Undimmed, First, Last Previous and Next
 *  <li>Upgrade the base support to LWUIT 1.5
 * </ul>
 * <br>
 * <b>ROADMAP:</b> 
 * <ul>
 *  <li>Support for Bidi support for texts in the Indicator
 *  <li>Support for Localization, e.g. texts in indicator in NUMBERS mode
 *  <li>Support for user actions on the indicator timeline
 *  <li>Support to move to arbitrary position within the indicator timeline
 *  <li>Support animation for indicator timeline and navigation, e.g. like WP7 loading animation
 *  <li>Add 'Indicator' component and its style in the LWUIT GUI Builder source [AMBITIOUS :) BUT POSSIBLE]
 * </ul>
 * 
 * @since LWUIT 1.4
 * 
 * @version 1.0
 * 
 * @author Vimal ( vimal.lwuit@ymail.com )
 */
public class Indicator extends Component implements IndicatorEventListener {

	/** Show no navigation */ 
	public static final int BEHAVIOUR_SHOW_NAVGATION_NONE = 1;
	
    /** Show navigation always when more than the group count */
    public static final int BEHAVIOUR_SHOW_NAVGATION_ALWAYS = 2;
    
    /** Show navigation only when 1st or last dot in the indicator is selected */
    public static final int BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE = 3;
    
    public static final int TYPE_IMAGES = 1;
    
    public static final int TYPE_RAW_DOTS = 1;
    
    public static final int TYPE_RAW_BOX = 2;
    
    public static final int TYPE_RAW_OVALS = 3;
    
    public static final int TYPE_RAW_NUMBERS = 4;
    
    private int valign;
    
    /** Number of visible indicators in the component timeline */
    private int maxIndicatorsPerGroup = 5;

    private int gap = 2;

    private int naviToItemGap = 2;
    
    private int naviGap = 2;
    
    private int naviToBorderGap = 2;
    
    private int type = TYPE_RAW_BOX;
    
    private int behavior = BEHAVIOUR_SHOW_NAVGATION_ALWAYS;
    
    /** Indicator quantum */
    private int quantum = 0;
    
    /** Indicator iteration */
    private int current = 0;
    
    /** Total indicator iterations */
    private int total = 0;
    
    /** Show 'previous' and 'next' navigation in the componenet */
    private boolean showNaviIncrement = true;
    
    /** Show 'first' and 'last' navigation in the componenet */
    private boolean showNaviEnds = true;
    
    /** 
     * Constructs a new indicator with default type.
     */
    public Indicator() {
        this(TYPE_RAW_DOTS, BEHAVIOUR_SHOW_NAVGATION_ALWAYS);
    }
    
    /** 
     * Constructs a new indicator with given type.
     * 
     * @param type the type of the indicator
     */
    public Indicator(int type, int behaviour) {
        setType(type < 1 ? TYPE_RAW_DOTS : type);
        setBehaviour(behaviour < 1 ? BEHAVIOUR_SHOW_NAVGATION_ALWAYS : behaviour);
        setVerticalAlignment(CENTER);
        setFocusable(false);
        localize();
        loadImages();
        
        setUIID("Indicator");
    }
    
    /** 
     * Constructs a new indicator with given type.
     * 
     * @param type the type of the indicator
     */
    public Indicator(int type) {
        this(type, BEHAVIOUR_SHOW_NAVGATION_ALWAYS);
    }
    
    /**
     * Set the behavior of the component.
     * 
     * @param behaviour the behavior of the component
     * 
     * @see #BEHAVIOUR_SHOW_NAVGATION_NONE
     * @see #BEHAVIOUR_SHOW_NAVGATION_ALWAYS
     * @see #BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE
     */
    public void setBehaviour(int behaviour) {
        this.behavior = behaviour;
    }
    
    /**
     * Set the number of visible indicators in the component timeline
     * 
     * @param behaviour the behavior of the component
     * 
     * @see #BEHAVIOUR_SHOW_NAVGATION_NONE
     * @see #BEHAVIOUR_SHOW_NAVGATION_ALWAYS
     * @see #BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE
     */
    public void setMaxIndicatorsPerGroup(int maxIndicatorsPerGroup) {
		this.maxIndicatorsPerGroup = maxIndicatorsPerGroup;
	}

	/**
     * Set the gap in pixels between the icon/text to the navigation in the component
     * 
     * @param gap the gap in pixels
     */
    public void setGap(int gap) {
        this.gap = gap;
    }

	/**
     * Set the gap in pixels between the icon/text to the component boundaries
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationToItemGap(int navigationToItemGap) {
		this.naviToItemGap = navigationToItemGap;
	}

	public void setNavigationGap(int navigationGap) {
		this.naviGap = navigationGap;
	}

	public void setNavigationToBorderGap(int navigationToBorderGap) {
		this.naviToBorderGap = navigationToBorderGap;
	}

	public void setNavigationIncrement(boolean showNaviFirst) {
		this.showNaviIncrement = showNaviFirst;
	}

	public void setNavigationEnds(boolean showNaviLast) {
		this.showNaviEnds = showNaviLast;
	}

	/**
     * @param quantum the quantum to set
     */
    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
        if (this.total < this.quantum) {
            this.quantum = this.total;
        }
    }

    /**
     * Set the type of component: 
     * {@link #TYPE_IMAGES}
     * 
     * @param type the type to set
     * 
     * @see #TYPE_IMAGES
     */
    public void setType(int type) {
    	//Patch to set the type for IMAGES always, will change when RAW mode is implemented
        if(type != TYPE_IMAGES) {
            type = TYPE_IMAGES;
        }
        this.type = type;
    }

    /**
     * @param valign the vertical align to set
     */
    public void setVerticalAlignment(int valign) {
    	//Patch to set the vertical aligment to CENTER always, will change when other vAlignments are implemented
        if(valign != Component.CENTER) {
            valign = Component.CENTER;
        }
        this.valign = valign;
    }
    
    /**
     * Returns the vertical alignment of the Indicator.
     * 
     * @return the vertical alignment of the component one of: CENTER
     * @see #CENTER
     */
    public int getVerticalAlignment(){
        return valign;
    }
    
    /**
     * Returns the alignment of the Indicator
     * 
     * @return the alignment of the component one of: CENTER, LEFT, RIGHT
     * @see #CENTER
     * @see #LEFT
     * @see #RIGHT
     * @deprecated use Style.getAlignment instead
     */
    public int getAlignment(){
        return getStyle().getAlignment();
    }
    
    /**
     * Returns the behavior of Indicator
     * 
     * @return the behavior of Indicator
     */
    public int getBehavior() {
        return behavior;
    }

    /**
     * @return the current
     */
    public int getCurrent() {
        return current;
    }
    
    public int getMaxIndicatorsPerGroup() {
		return maxIndicatorsPerGroup;
	}

	/**
     * Returns the gap in pixels between the icon/text to the component boundaries
     * 
     * @return the gap in pixels between the icon/text to the component boundaries
     */
    public int getGap() {
    	return gap;
    }
    
    public int getNavigationToItemGap() {
		return naviToItemGap;
	}

	public int getNavigationGap() {
		return naviGap;
	}

	public int getNavigationToBorderGap() {
		return naviToBorderGap;
	}

	/**
     * Returns the component event listener
     * 
     * @return the component event listener
     */
    public IndicatorEventListener getIndicatorEventListener() {
        return (IndicatorEventListener) this;
    }

    /**
     * @return the quantum
     */
    public int getQuantum() {
        return quantum;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Returns the type of Indicator
     * 
     * @return the type of Indicator
     */
    public int getType() {
        return type;
    }
    
    public boolean isNavigationIncrementShown() {
		return showNaviIncrement;
	}

	public boolean isNavigationEndsShown() {
		return showNaviEnds;
	}

	/**
     * @inheritDoc
     */
    public void paint(Graphics g) {
		((IndicatorDefaultLookAndFeel) UIManager.getInstance().getLookAndFeel()).drawIndicator(g, this);
    }
    
    /**
     * @inheritDoc
     */
    protected String paramString() {
        return super.paramString() + ", type = " + getType() + ", behavior = " + getBehavior() + ", quantum = " + getQuantum() + ", current = " + getCurrent() + ", total = " + getTotal();
    }

    /**
     * @inheritDoc
     */
    protected Dimension calcPreferredSize(){
		return ((IndicatorDefaultLookAndFeel) UIManager.getInstance().getLookAndFeel()).getIndicatorPreferredSize(this);
    }
    
    private void checkNext() {
        if (total - 1 > current + quantum) {
            current = current + quantum;
        }
    }
    
    private void checkPrevious() {
        if (-1 < current - quantum) {
            current = current - quantum;
        }
    }
    
    private void checkFirst() {
        current = 0;
    }
    
    private void checkLast() {
        current = ((total - 1) / quantum) * quantum;
    }
    
    /**
     * @nheritDoc
     */
    public void next() {
        if(isRTL()) {
            checkPrevious();
        } else {
            checkNext();
        }
        repaint(this);
    }
    
    /**
     * @nheritDoc
     */
    public void previous() {
        if(isRTL()) {
            checkNext();
        } else {
            checkPrevious();
        }
        repaint(this);
    }
    
    /**
     * @nheritDoc
     */
    public void first() {
        if(isRTL()) {
            checkLast();
        } else {
            checkFirst();
        }
        repaint(this);
    }
    
    /**
     * @nheritDoc
     */
    public void last() {
        if(isRTL()) {
            checkFirst();
        } else {
            checkLast();
        }
        repaint(this);
    }
    
    private void localize() {
        //this.text =  UIManager.getInstance().localize(text, text);
    }
    
    private void loadImages() {
        //this.text =  UIManager.getInstance().localize(text, text);
    }
}
