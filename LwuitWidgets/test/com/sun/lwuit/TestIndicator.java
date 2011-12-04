package com.sun.lwuit;

import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.plaf.IndicatorLookAndFeel;
import com.sun.lwuit.plaf.UIManager;
import java.io.IOException;

/**
 * Widget to show the navigation indicator.
 * <pre>
 * 
 *                Dimmed-----|   |---- Undimmed
 *                           |   |
 *              |-----------------------------------------|
 *              |   <<   <   *   @   *   *   *   >   >>   |
 *              |-----------------------------------------|
 *                  |    |  |                 |  |   |
 *                  |    |  |-----------------|  |   |
 *          |--------    |        Timeline       |   ------------|
 *          |            |                       |               |
 *          |       Navigation                Navigation         |
 *          |        Previous                   Next             |
 *          |                                                    |
 *          |                                                    |
 *      Navigation                                           Navigation
 *        First                                                 Last
 * </pre>
 * <br>
  * <b>NOTE:</b>
 * <ul>
 *  <li>Navigation First and Last constitute to be Navigation Ends
 *  <li>Navigation Previous and Next constitute to be Navigation Increment
 * </ul>
 * <br>
 * <b>Use Case:</b>
 * <ul>
 *  <li>To navigation indicators for a paginated view, like in RSS feed readers on Android's
 * </ul>
 * <br>
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
    
    public static final int TYPE_RAW_DOTS = 2;
    
    public static final int TYPE_RAW_BOX = 3;
    
    public static final int TYPE_RAW_OVALS = 4;
    
    public static final int TYPE_RAW_NUMBERS = 5;
    
    private Image[] nonFocusedImages = null;
    
    private Image[] focusedImages = null;
    
    private int valign = CENTER;
    
    /** Number of items in the time-line */
    private int maxTimelineItems = 5;

    private int gap = 2;

    private int naviToItemGap = 2;
    
    private int naviGap = 2;
    
    private int naviToBorderGap = 2;
    
    private int type = TYPE_RAW_BOX;
    
    private int behaviour = BEHAVIOUR_SHOW_NAVGATION_ALWAYS;
    
    /** Indicator quantum */
    private int quantum = 1;
    
    /** Indicator iteration */
    private int current = -1;
    
    /** Total indicator iterations */
    private int total = 1;
    
    /** Show 'previous' and 'next' navigation in the componenet */
    private boolean showIncrement = true;
    
    /** Show 'first' and 'last' navigation in the componenet */
    private boolean showEnds = true;
    
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
    public Indicator(int type) {
        this(type, BEHAVIOUR_SHOW_NAVGATION_ALWAYS);
    }
    
    /** 
     * Constructs a new indicator with given type.
     * 
     * @param type the type of the indicator
     */
    public Indicator(int type, int behaviour) {
        setType(type < TYPE_IMAGES ? TYPE_RAW_DOTS : type);
        setBehaviour(behaviour < BEHAVIOUR_SHOW_NAVGATION_NONE ? BEHAVIOUR_SHOW_NAVGATION_ALWAYS : behaviour);
        setVerticalAlignment(CENTER);
        setFocusable(false);
        localize();
        loadImages(null,null);
        
        setUIID("Indicator");
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
        this.behaviour = behaviour;
    }
    
    /**
     * Sets the focused images of the component. 
     * The order of the images is {dimmed, undimmed, previous, next, first, last}
     * 
     * @param imgs the array of images
     */
    public void setFocusedImages(Image[] imgs) {
        if(null == imgs) {
            return;
        }
        focusedImages = new Image[imgs.length];
        System.arraycopy(imgs, 0, focusedImages, 0, focusedImages.length);
    }

    /**
     * Sets the non focused images of the component. 
     * The order of the images is {dimmed, undimmed, previous, next, first, last}
     * 
     * @param the array of images
     */
    public void setNonFocusedImages(Image[] imgs) {
        if(null == imgs) {
            return;
        }
        nonFocusedImages = new Image[imgs.length];
        System.arraycopy(imgs, 0, nonFocusedImages, 0, nonFocusedImages.length);
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
    public void setMaxTimelineItems(int maxTimelineItems) {
        this.maxTimelineItems = maxTimelineItems;
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
     * Set the gap in pixels between the time-line item/text to the navigation increment
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationToItemGap(int navigationToItemGap) {
        this.naviToItemGap = navigationToItemGap;
    }

    /**
     * Set the gap in pixels between navigation increment and navigation ends
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationGap(int navigationGap) {
        this.naviGap = navigationGap;
    }

    /**
     * Set the gap in pixels between navigation ends and component border
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationToBorderGap(int navigationToBorderGap) {
        this.naviToBorderGap = navigationToBorderGap;
    }

    /**
     * Set the navigation increment state (TRUE/FALSE) in the component
     * 
     * @param show show the navigation
     */
    public void setNavigationIncrementShown(boolean show) {
        this.showIncrement = show;

        //Since navigation increment is not shown, is doesn't make sense
        //to keep the gapping between navigations 'ends' and 'increment'.
        setNavigationGap(0);
        
        //Since both navigation increment and navigation ends 
        //are not shown, so removing the navigation to item gapping.
        if(!showEnds) {
            setNavigationToBorderGap(0);
            setNavigationToItemGap(0);
	}
    }

    /**
     * Set the navigation ends state (TRUE/FALSE) in the component
     * 
     * @param show show the navigation
     */
    public void setNavigationEndsShown(boolean show) {
        this.showEnds = show;

        //Since navigation ends is not shown, is doesn't make sense
        //to keep the gapping between navigations 'ends' and 'increment'
        setNavigationGap(0);
        
        //Since both navigation increment and navigation ends
        //are not shown, so removing the navigation to item gapping.
        if(!showEnds) {
            setNavigationToBorderGap(0);
            setNavigationToItemGap(0);
	}
    }

    /**
     * Set the quantum for component
     * 
     * @param quantum the quantum to set
     */
    public void setQuantum(int quantum) {
        this.quantum = Math.max(0, quantum);
    }

    /**
     * Set the total for component
     * 
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = Math.max(0, total);
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
    	//FIXME Patch to set the type for IMAGES always, 
        //will change when RAW mode is implemented
        if(type != TYPE_IMAGES) {
            type = TYPE_IMAGES;
        }
        this.type = type;
    }

    /**
     * Set the vertical alignment of the component
     * 
     * @param valign the vertical align to set
     */
    public void setVerticalAlignment(int valign) {
    	//FIXME Patch to set the vertical aligment to CENTER always, 
        //will change when other vAlignments are implemented
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
     * Returns the behavior of Indicator
     * 
     * @return the behavior of Indicator
     */
    public int getBehaviour() {
        return behaviour;
    }

    /**
     * Returns the current indicator
     * 
     * @return the current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Set the number of items in time-line
     * 
     * @param gap the gap in pixels
     */
    public int getMaxTimelineItems() {
        return maxTimelineItems;
    }

    /**
     * Returns the gap in pixels between the time-line items
     * 
     * @return the gap in pixels
     */
    public int getGap() {
    	return gap;
    }

    /**
     * Returns the gap in pixels between the time-line item and the navigation increment
     * 
     * @return the gap in pixels
     */
    public int getNavigationToItemGap() {
        return naviToItemGap;
    }

    /**
     * Returns the gap between navigation increment and navigation ends
     * 
     * @return the gap in pixels
     */
    public int getNavigationGap() {
        return naviGap;
    }

    /**
     * Returns the gap between navigation ends and component border
     * 
     * @return the gap in pixels
     */
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
     * Returns the quantum of the component
     * 
     * @return the quantum
     */
    public int getQuantum() {
        return quantum;
    }

    /**
     * Returns the total of the component
     * 
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Returns the type of component
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }
    
    /**
     * Returns the navigation increment state of the component
     * 
     * @return the navigation increment state (TRUE / FALSE)
     */
    public boolean isNavigationIncrementShown() {
        return showIncrement;
    }

    /**
     * Returns the navigation ends state of the component
     * 
     * @return the navigation ends state (TRUE / FALSE)
     */
    public boolean isNavigationEndsShown() {
        return showEnds;
    }

    /**
     * @inheritDoc
     */
    public void paint(Graphics g) {
        ((IndicatorLookAndFeel) UIManager.getInstance().getLookAndFeel()).drawIndicator(g, this);
    }
    
    /**
     * Returns the focused images of the component. 
     * The order of the images is {dimmed, undimmed, previous, next, first, last}
     * 
     * @return the images array
     */
    public Image[] getFocusedImages() {
        return focusedImages;
    }

    /**
     * Returns the non focused images of the component. 
     * The order of the images is {dimmed, undimmed, previous, next, first, last}
     * 
     * @return the images array
     */
    public Image[] getNonFocusedImages() {
        return nonFocusedImages;
    }

    /**
     * @inheritDoc
     */
    protected String paramString() {
        return super.paramString() + ", type = " + getType() + ", behavior = " + getBehaviour() + ", quantum = " + getQuantum() + ", current = " + getCurrent() + ", total = " + getTotal();
    }

    /**
     * @inheritDoc
     */
    protected Dimension calcPreferredSize(){
        return ((IndicatorLookAndFeel) UIManager.getInstance().getLookAndFeel()).getIndicatorPreferredSize(this);
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
    

    /**
     * Load 'Indicator' images
     * <br><br>
     * <b>NOTE: </b>The resource location should have 6 (12 if focused images are included) files.
     * <br><br>
     * The files naming conventions is as follows
     * <br>
     * <b><i>NOTE: </i></b>In the filename '@' is not required, it should be replaced with your 'append' text
     * <table border="1">
     * <tr>
     *   <td><b>Image Type</b></td>
     *   <td><b>Image Non-Focused Filename</b></td>
     *   <td><b>Image Focused Filename</b></td>
     * </tr>
     * <tr>
     *   <td>Dimmed image</td>
     *   <td>'indicatorDimmed@Image.png'</td>
     *   <td>'indicatorDimmedFocus@Image.png'</td>
     * </tr>
     * <tr>
     *   <td>Undimmed image</td>
     *   <td>'indicatorUndimmed@Image.png'</td>
     *   <td>'indicatorUndimmedFocus@Image.png'</td>
     * </tr>
     * <tr>
     *   <td>Navigation First image</td>
     *   <td>'indicatorFirst@Image.png'</td>
     *   <td>'indicatorFirstFocus@Image.png'</td>
     * </tr>
     * <tr>
     *   <td>Navigation Last image</td>
     *   <td>'indicatorLast@Image.png'</td>
     *   <td>'indicatorLastFocus@Image.png'</td>
     * </tr>
     * <tr>
     *   <td>Navigation Previous image</td>
     *   <td>'indicatorPrevious@Image.png'</td>
     *   <td>'indicatorPreviousFocus@Image.png'</td>
     * </tr>
     * <tr>
     *   <td>Navigation Next image</td>
     *   <td>'indicatorNext@Image.png'</td>
     *   <td>'indicatorNextFocus@Image.png'</td>
     * </tr>
     * </table>
     * 
     */
    public void loadImages(String resPath, String append) {
        if(null == resPath) {
            loadImagesFromThemeConstant();
            return;
        }
        if(null == append) {
            append = "";
        }
        /* Load Non-Focused Images */
        try {
            Image dimmed = Image.createImage(resPath + "/indicatorDimmed" + append + "Image.png");
            if(dimmed != null) {
                Image undimmed = Image.createImage(resPath + "/indicatorUndimmed" + append + "Image.png");
                if(undimmed != null) {
                    //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
                    //as non-proportional images are currently not supported
                    if(dimmed.getWidth() <= undimmed.getWidth()) {
                        undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
                    } else {
                        dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
                    }

                    //FIXME Scale the navigation images to dimmed height to align them properly in the widget
                    //as non-proportional images are currently not supported
                    Image next = null;
                    try {
                        next = Image.createImage(resPath + "/indicatorNext" + append + "Image.png");
                        if (next != null) {
                            next.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Next image not found");
                    }
                    Image previous = null;
                    try {
                        previous = Image.createImage(resPath + "/indicatorPrevious" + append + "Image.png");
                        if (previous != null && next != null) {
                            previous.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Previous image not found");
                    }
                    Image first = null;
                    try {
                        first = Image.createImage(resPath + "/indicatorFirst" + append + "Image.png");
                        if (first != null) {
                            first.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator First image not found");
                    }
                    Image last = null;
                    try {
                        last = Image.createImage(resPath + "/indicatorLast" + append + "Image.png");
                        if (last != null && next != null) {
                            last.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Last image not found");
                    }
                    nonFocusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
                }
            }
        } catch (IOException e) {
            System.out.println("Indicator dimmed/undimmed focus image not found");
            e.printStackTrace();
        }
        /* Load Focused Images */
        try {
            Image dimmed = Image.createImage(resPath + "/indicatorDimmedFocus" + append + "Image.png");
            if(dimmed != null) {
                Image undimmed = Image.createImage(resPath + "/indicatorUndimmedFocus" + append + "Image.png");
                if(undimmed != null) {
                    //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
                    //as non-proportional images are currently not supported
                	if(dimmed.getWidth() <= undimmed.getWidth()) {
                		undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
                	} else {
                		dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
                	}

                    //FIXME Scale the navigation images to dimmed height to align them properly in the widget
                    //as non-proportional images are currently not supported
                    Image next = null;
                    try {
                        next = Image.createImage(resPath + "/indicatorNextFocus" + append + "Image.png");
                        if (next != null) {
                            next.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Next Focus image not found");
                    }
                    Image previous = null;
                    try {
                        previous = Image.createImage(resPath + "/indicatorPreviousFocus" + append + "Image.png");
                        if (previous != null && next != null) {
                            previous.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Previous Focus image not found");
                    }
                    Image first = null;
                    try {
                        first = Image.createImage(resPath + "/indicatorFirstFocus" + append + "Image.png");
                        if (first != null) {
                            first.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator First Focus image not found");
                    }
                    Image last = null;
                    try {
                        last = Image.createImage(resPath + "/indicatorLastFocus" + append + "Image.png");
                        if (last != null && next != null) {
                            last.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("Indicator Last Focus image not found");
                    }
                    focusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
                }
            }
        } catch (IOException e) {
            System.out.println("Indicator dimmed/undimmed focus image not found");
            e.printStackTrace();
        }
    }
    
    private void loadImagesFromThemeConstant() {
        UIManager m = UIManager.getInstance();
        /* Load Non-Focused Images */
        Image dimmed = m.getThemeImageConstant("Indicator.indicatorDimmedImage");
        if(dimmed != null) {
            Image undimmed = m.getThemeImageConstant("Indicator.indicatorUndimmedImage");
            if(undimmed != null) {
                //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
                //as non-proportional images are currently not supported
                if(dimmed.getWidth() <= undimmed.getWidth()) {
                    undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
                } else {
                    dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
                }

                //FIXME Scale the navigation images to dimmed height to align them properly in the widget
                //as non-proportional images are currently not supported
                Image next = m.getThemeImageConstant("Indicator.indicatorNextImage");
                if (next != null) {
                    next.scaledHeight(dimmed.getHeight());
                }
                Image previous = m.getThemeImageConstant("Indicator.indicatorPreviousImage");
                if (previous != null && next != null) {
                    previous.scaledHeight(next.getHeight());
                }
                Image first = m.getThemeImageConstant("Indicator.indicatorFirstImage");
                if (first != null) {
                    first.scaledHeight(dimmed.getHeight());
                }
                Image last = m.getThemeImageConstant("Indicator.indicatorLastImage");
                if (last != null && next != null) {
                    last.scaledHeight(next.getHeight());
                }
                nonFocusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
            }
        }
        /* Load Focused Images */
        dimmed = m.getThemeImageConstant("Indicator.indicatorDimmedFocusImage");
        if(dimmed != null) {
            Image undimmed = m.getThemeImageConstant("Indicator.indicatorUndimmedFocusImage");
            if(undimmed != null) {
                //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
                //as non-proportional images are currently not supported
                    if(dimmed.getWidth() <= undimmed.getWidth()) {
                            undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
                    } else {
                            dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
                    }

                //FIXME Scale the navigation images to dimmed height to align them properly in the widget
                //as non-proportional images are currently not supported
                Image next = m.getThemeImageConstant("Indicator.indicatorNextFocusImage");
                if (next != null) {
                    next.scaledHeight(dimmed.getHeight());
                }
                Image previous = m.getThemeImageConstant("Indicator.indicatorPreviousFocusImage");
                if (previous != null && next != null) {
                    previous.scaledHeight(next.getHeight());
                }
                Image first = m.getThemeImageConstant("Indicator.indicatorFirstFocusImage");
                if (first != null) {
                    first.scaledHeight(dimmed.getHeight());
                }
                Image last = m.getThemeImageConstant("Indicator.indicatorLastFocusImage");
                if (last != null && next != null) {
                    last.scaledHeight(next.getHeight());
                }
                focusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
            }
        }
    }
}
