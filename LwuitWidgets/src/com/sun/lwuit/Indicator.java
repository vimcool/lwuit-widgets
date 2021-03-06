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

    private static final int DEFAULT_GAP = 2;
    
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
    
    public static final int ORIENTATION_HORIZONTAL = 1;
    
    public static final int ORIENTATION_VERTICAL = 2;
    
    private Image[] nonFocusedImages = null;
    
    private Image[] focusedImages = null;
    
    private int behaviour;
    
    private int type;
    
    private int orientation;
    
    private int valign = CENTER;
    
    /** Number of items in the time-line */
    private int maxTimelineItems = 5;

    private int gap = DEFAULT_GAP;

    private int naviToItemGap = DEFAULT_GAP;
    
    private int naviGap = DEFAULT_GAP;
    
    private int naviToBorderGap = DEFAULT_GAP;
    
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
        this(TYPE_RAW_DOTS, BEHAVIOUR_SHOW_NAVGATION_ALWAYS, null, null);
    }
    
    /** 
     * Constructs a new indicator with given type.
     * 
     * @param type the type of the indicator
     */
    public Indicator(int type) {
        this(type, BEHAVIOUR_SHOW_NAVGATION_ALWAYS, null, null);
    }
    
    /** 
     * Constructs a new indicator with given type.
     * 
     * @param type the type of the indicator
     */
    public Indicator(int type, int behaviour, String imagesFolderPath, String append) {
        //setUIID("Indicator");
        setFocusable(false);
        setType(type);
        setBehaviour(behaviour);
        setVerticalAlignment(CENTER);
        setOrientation(ORIENTATION_HORIZONTAL);
        localize();
        loadImages(imagesFolderPath,append);
        
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
        if(behaviour < BEHAVIOUR_SHOW_NAVGATION_NONE 
                || behaviour > BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE) {
            behaviour = BEHAVIOUR_SHOW_NAVGATION_NONE;
        }
        boolean change = this.behaviour != behaviour;
        this.behaviour = behaviour;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }
    
    /**
     * Set the orientation of the component.
     * 
     * @param orientation the orientation of the component
     * 
     * @see #ORIENTATION_HORIZONTAL
     * @see #ORIENTATION_VERTICAL
     */
    public void setOrientation(int orientation) {
        boolean change = this.orientation != orientation;
        this.orientation = orientation;
        if(change) {
            shouldCalcPreferredSize = true;
        }
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
        shouldCalcPreferredSize = true;
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
        shouldCalcPreferredSize = true;
    }
    
    /**
     * Set the number of visible indicators in the component timeline.
     * <b>NOTE:</b> If timeline items are greater than {@link #getTotal()}
     * than timeline will be reduced to the latter's value.
     * 
     * @param maxTimelineItems the number of visible indicators in timeline
     */
    public void setMaxTimelineItems(int maxTimelineItems) {
        boolean change = this.maxTimelineItems != maxTimelineItems
                && this.total != maxTimelineItems && this.maxTimelineItems != this.total;
        this.maxTimelineItems = this.total < maxTimelineItems ? this.total : maxTimelineItems;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the gap in pixels between the icon/text to the navigation in the component
     * 
     * @param gap the gap in pixels
     */
    public void setGap(int gap) {
        boolean change = this.gap != gap;
        this.gap = gap;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the gap in pixels between the time-line item/text to the navigation increment
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationToItemGap(int navigationToItemGap) {
        boolean change = this.naviToItemGap != navigationToItemGap;
        this.naviToItemGap = navigationToItemGap;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the gap in pixels between navigation increment and navigation ends
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationGap(int navigationGap) {
        boolean change = this.naviGap != navigationGap;
        this.naviGap = navigationGap;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the gap in pixels between navigation ends and component border
     * 
     * @param gap the gap in pixels
     */
    public void setNavigationToBorderGap(int navigationToBorderGap) {
        boolean change = this.naviToBorderGap != navigationToBorderGap;
        this.naviToBorderGap = navigationToBorderGap;
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the navigation increment state (TRUE/FALSE) in the component
     * 
     * @param show show the navigation
     */
    public void setNavigationIncrement(boolean show) {
        boolean change = this.showIncrement != show;
        this.showIncrement = show;

        //Since navigation increment is not shown, is doesn't make sense
        //to keep the gapping between navigations 'ends' and 'increment'.
        if(!this.showIncrement) {
            setNavigationGap(0);

            //Since both navigation increment and navigation ends 
            //are not shown, so removing the navigation to item gapping.
            if(!this.showEnds) {
                setNavigationToBorderGap(0);
                setNavigationToItemGap(0);
            }

        } else {
            //Since navigation increment is shown but navigation ends is not shown,
            //so it doesn't make sense to keep the inbetween navigation gap.
            if(!this.showEnds) {
                setNavigationGap(0);
            }
        }
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the navigation ends state (TRUE/FALSE) in the component
     * 
     * @param show show the navigation
     */
    public void setNavigationEnds(boolean show) {
        boolean change = this.showEnds != show;
        this.showEnds = show;

        //Since navigation ends is not shown, is doesn't make sense
        //to keep the gapping between navigations 'ends' and 'increment'
        if (!this.showEnds) {
            setNavigationGap(0);

            //Since both navigation increment and navigation ends
            //are not shown, so removing the navigation to item gapping.
            if (!showIncrement) {
                setNavigationToBorderGap(0);
                setNavigationToItemGap(0);
            }

        } else {
            //Since navigation increment is shown but navigation ends is not shown,
            //so it doesn't make sense to keep the inbetween navigation gap.
            if(!this.showIncrement) {
                setNavigationGap(0);
            }
        }
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the quantum for component
     * 
     * @param quantum the quantum to set
     */
    public void setQuantum(int quantum) {
        boolean change = this.quantum != quantum;
        this.quantum = Math.max(0, quantum);
        if(change) {
            shouldCalcPreferredSize = true;
        }
    }

    /**
     * Set the total for component
     * 
     * @param total the total to set
     */
    public void setTotal(int total) {
        boolean change = this.total != total;
        this.total = Math.max(0, total);
        if (this.total < this.quantum) {
            this.quantum = this.total;
        }
        if(change) {
            shouldCalcPreferredSize = true;
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
        if(type < TYPE_IMAGES || type > TYPE_RAW_NUMBERS) {
            type = TYPE_IMAGES;
        }
        
    	//FIXME Patch to set the type for IMAGES always, 
        //will change when RAW mode is implemented
        if(type != TYPE_IMAGES) {
            type = TYPE_IMAGES;
        }
        boolean change = this.type != type;
        this.type = type;
        if(change) {
            shouldCalcPreferredSize = true;
        }
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
        boolean change = this.valign != valign;
        this.valign = valign;
        if(change) {
            shouldCalcPreferredSize = true;
        }
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
     * Returns the behavior of the component
     * 
     * @return the behavior of the component
     */
    public int getBehaviour() {
        return behaviour;
    }
    
    /**
     * Returns the orientation of the component
     * 
     * @return the orientation of the component
     */
    public int getOrientation() {
        return orientation;
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
    public String paramString() {
        return super.paramString() + ", type = " + getType() + ", orientation = " + getOrientation() + ", behavior = " + getBehaviour() + ", quantum = " + getQuantum() + ", current = " + getCurrent() + ", total = " + getTotal() + ", gap = " + getGap() + ", item2naviGap = " + getNavigationToItemGap() + ", navi2naviGap = " + getNavigationGap() + ", naviToBorderGap = " + getNavigationToBorderGap();
    }

    /**
     * @inheritDoc
     */
    protected Dimension calcPreferredSize(){
        return ((IndicatorLookAndFeel) UIManager.getInstance().getLookAndFeel()).getIndicatorPreferredSize(this);
    }
    
    private void checkNext() {
        if (total /*- 1*/ > current + quantum) {
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
            if (type == TYPE_IMAGES) {
                //loadImagesFromThemeConstant();
                
                //UIManager m = UIManager.getInstance();
                //IndicatorLookAndFeel laf = (IndicatorLookAndFeel) m.getLookAndFeel();
                //
                //laf.updateIndicatorConstants(m, false, "");
                //laf.updateIndicatorConstants(m, true, "Focus");
                //
                //laf = null;
                //m = null;
            }
            
            return;
        }
        if(null == append) {
            append = "";
        }
        String path = null;
        /* Load Non-Focused Images */
        try {
            path = resPath + "/indicatorDimmed" + append + "Image.png";
            Image dimmed = Image.createImage(path);
            if(dimmed != null) {
                path = resPath + "/indicatorUndimmed" + append + "Image.png";
                Image undimmed = Image.createImage(path);
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
                        path = resPath + "/indicatorNext" + append + "Image.png";
                        next = Image.createImage(path);
                        if (next != null) {
                            next.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image previous = null;
                    try {
                        path = resPath + "/indicatorPrevious" + append + "Image.png";
                        previous = Image.createImage(path);
                        if (previous != null && next != null) {
                            previous.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image first = null;
                    try {
                        path = resPath + "/indicatorFirst" + append + "Image.png";
                        first = Image.createImage(path);
                        if (first != null) {
                            first.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image last = null;
                    try {
                        path = resPath + "/indicatorLast" + append + "Image.png";
                        last = Image.createImage(path);
                        if (last != null && next != null) {
                            last.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    nonFocusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
                }
            }
        } catch (IOException e) {
            System.out.println("("+path+") image not found");
        }
        /* Load Focused Images */
        try {
            path = resPath + "/indicatorDimmedFocus" + append + "Image.png";
            Image dimmed = Image.createImage(path);
            if(dimmed != null) {
                path = resPath + "/indicatorUndimmedFocus" + append + "Image.png";
                Image undimmed = Image.createImage(path);
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
                        path = resPath + "/indicatorNextFocus" + append + "Image.png";
                        next = Image.createImage(path);
                        if (next != null) {
                            next.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image previous = null;
                    try {
                        path = resPath + "/indicatorPreviousFocus" + append + "Image.png";
                        previous = Image.createImage(path);
                        if (previous != null && next != null) {
                            previous.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image first = null;
                    try {
                        path = resPath + "/indicatorFirstFocus" + append + "Image.png";
                        first = Image.createImage(path);
                        if (first != null) {
                            first.scaledHeight(dimmed.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    Image last = null;
                    try {
                        path = resPath + "/indicatorLastFocus" + append + "Image.png";
                        last = Image.createImage(path);
                        if (last != null && next != null) {
                            last.scaledHeight(next.getHeight());
                        }
                    } catch (IOException iOException) {
                        System.out.println("("+path+") image not found");
                    }
                    focusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
                }
            }
        } catch (IOException e) {
            System.out.println("("+path+") image not found");
        }
    }
    
//    private void loadImagesFromThemeConstant() {
//        UIManager m = UIManager.getInstance();
//        /* Load Non-Focused Images */
//        Image dimmed = m.getThemeImageConstant("Indicator.indicatorDimmedImage");
//        if(dimmed != null) {
//            Image undimmed = m.getThemeImageConstant("Indicator.indicatorUndimmedImage");
//            if(undimmed != null) {
//                //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
//                //as non-proportional images are currently not supported
//                if(dimmed.getWidth() <= undimmed.getWidth()) {
//                    undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
//                } else {
//                    dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
//                }
//    
//                //FIXME Scale the navigation images to dimmed height to align them properly in the widget
//                //as non-proportional images are currently not supported
//                Image next = m.getThemeImageConstant("Indicator.indicatorNextImage");
//                if (next != null) {
//                    next.scaledHeight(dimmed.getHeight());
//                }
//                Image previous = m.getThemeImageConstant("Indicator.indicatorPreviousImage");
//                if (previous != null && next != null) {
//                    previous.scaledHeight(next.getHeight());
//                }
//                Image first = m.getThemeImageConstant("Indicator.indicatorFirstImage");
//                if (first != null) {
//                    first.scaledHeight(dimmed.getHeight());
//                }
//                Image last = m.getThemeImageConstant("Indicator.indicatorLastImage");
//                if (last != null && next != null) {
//                    last.scaledHeight(next.getHeight());
//                }
//                nonFocusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
//            }
//        }
//        /* Load Focused Images */
//        dimmed = m.getThemeImageConstant("Indicator.indicatorDimmedFocusImage");
//        if(dimmed != null) {
//            Image undimmed = m.getThemeImageConstant("Indicator.indicatorUndimmedFocusImage");
//            if(undimmed != null) {
//                //FIXME Scale the undimmed/dimmed image to dimmed/undimmed image dimension
//                //as non-proportional images are currently not supported
//                    if(dimmed.getWidth() <= undimmed.getWidth()) {
//                            undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
//                    } else {
//                            dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
//                    }
//    
//                //FIXME Scale the navigation images to dimmed height to align them properly in the widget
//                //as non-proportional images are currently not supported
//                Image next = m.getThemeImageConstant("Indicator.indicatorNextFocusImage");
//                if (next != null) {
//                    next.scaledHeight(dimmed.getHeight());
//                }
//                Image previous = m.getThemeImageConstant("Indicator.indicatorPreviousFocusImage");
//                if (previous != null && next != null) {
//                    previous.scaledHeight(next.getHeight());
//                }
//                Image first = m.getThemeImageConstant("Indicator.indicatorFirstFocusImage");
//                if (first != null) {
//                    first.scaledHeight(dimmed.getHeight());
//                }
//                Image last = m.getThemeImageConstant("Indicator.indicatorLastFocusImage");
//                if (last != null && next != null) {
//                    last.scaledHeight(next.getHeight());
//                }
//                focusedImages = new Image[]{dimmed, undimmed, previous, next, first, last};
//            }
//        }
//    }
}
