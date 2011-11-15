package com.sun.lwuit.plaf;

import java.io.IOException;

import com.sun.lwuit.Component;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Indicator;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.geom.Dimension;

/**
 * Used to extend the rendering of default look and feel with indicator widget
 *
 * @version 1.0
 * 
 * @since LWUIT 1.4
 * 
 * @author Vimal, vimal.lwuit@ymail.com
 */
public class IndicatorDefaultLookAndFeel extends DefaultLookAndFeel implements FocusListener {
    private Image[] indicatorImages = null;
    private Image[] indicatorImagesFocus = null;

    /** Creates a new instance of DefaultLookAndFeel */
    public IndicatorDefaultLookAndFeel() {
    	this.refreshTheme();
    }

    /**
     * Sets images for Indicator dimmed/undimmed modes
     * 
     * @param dimmed the image to draw in order to represent a dimmed Indicator
     * @param undimmed the image to draw in order to represent an undimmed Indicator
     * @param previous the image to draw in order to represent an previous navigation Indicator
     * @param next the image to draw in order to represent an next navigation Indicator
     * @param first the image to draw in order to represent an first navigation Indicator
     * @param last the image to draw in order to represent an last navigation Indicator
     */
    public void setIndicatorImages(Image dimmed, Image undimmed, Image previous, Image next, Image first, Image last) {
        if (dimmed == null || undimmed == null) {
            indicatorImages = null;
        } else {
            indicatorImages = new Image[]{dimmed, undimmed, previous, next, first, last};
        }
    }

    /**
     * Sets focus images for Indicator dimmed/undimmed modes
     * 
     * @param dimmed the image to draw in order to represent a dimmed Indicator
     * @param undimmed the image to draw in order to represent an undimmed Indicator
     * @param previous the image to draw in order to represent an previous navigation Indicator
     * @param next the image to draw in order to represent an next navigation Indicator
     * @param first the image to draw in order to represent an first navigation Indicator
     * @param last the image to draw in order to represent an last navigation Indicator
     */
    public void setIndicatorFocusImages(Image dimmed, Image undimmed, Image previous, Image next, Image first, Image last) {
        if (dimmed == null || undimmed == null) {
            indicatorImagesFocus = null;
        } else {
            indicatorImagesFocus = new Image[]{dimmed, undimmed, previous, next, first, last};
        }
    }

    /**
     * Returns the images used to represent the Indicator images.
     *
     * @return images representing the Indicator or null for using the default drawing
     */
    public Image[] getIndicatorImages() {
        return indicatorImages;
    }

    /**
     * Returns the images used to represent the Indicator focused images.
     *
     * @return images representing the Indicator or null for using the default drawing
     */
    public Image[] getIndicatorFocusImages() {
        return indicatorImagesFocus;
    }
    
    /**
     * Invoked for drawing the indicator widget
     * 
     * @param g graphics context
     * @param in component to draw
     */
    public void drawIndicator(Graphics g, Indicator in) {
    	if (indicatorImages != null) {
    		drawIndicatorImages(g, in);
    		return;
    	}
    	
    	Style s = in.getStyle();
    	
    	int indiLeftPadding = s.getPadding(Component.LEFT);
    	int indiRightPadding = s.getPadding(Component.RIGHT);
    	int indiTopPadding = s.getPadding(Component.TOP);
    	int indiBottomPadding = s.getPadding(Component.BOTTOM);
    	
    	int indiXpos = in.getX();
    	int indiYpos = in.getY();
    	int indiWidth = in.getWidth();
    	int indiHeight = in.getHeight();
    	
    	int intType = in.getType();
    	int indiItemGap = in.getGap();
    	int indiHalign = s.getAlignment();
    	int indiValign = in.getVerticalAlignment();
    	int indiBehavior = in.getBehavior();
    	int indiMaxIndicatorsPerGroup = in.getMaxIndicatorsPerGroup();
    	
    	int indiDimmedItemWidth = 0;
    	int indiDimmedItemHeight = 0;
    	int indiUndimmedItemWidth = 0;
    	int indiUndimmedItemHeight = 0;
    	
    	int naviPreviousWidth   = 0;
    	int naviPreviousHeight  = 0;
    	int naviNextWidth   = 0;
    	int naviNextHeight  = 0;
    	int naviFirstWidth   = 0;
    	int naviFirstHeight  = 0;
    	int naviLastWidth   = 0;
    	int naviLastHeight  = 0;
    	int naviToIndiGap = 3;
    	int naviToNaviGap = indiItemGap;
    	
    	int indiQuantum = in.getQuantum();
    	int indiCurrent = in.getCurrent();
    	int indiTotal = in.getTotal();
    	
    	int totalIndis = indiQuantum > 0 && indiTotal > 0 ? indiTotal / indiQuantum : -1;
    	int currentIndi = indiQuantum > 0 && indiCurrent >= 0 ? indiCurrent / indiQuantum : -1;
    	int relativeCurIndi = currentIndi < 0 ? currentIndi : currentIndi % indiMaxIndicatorsPerGroup;
    	int numOfIndisOnAScreen = totalIndis < 0 ? totalIndis : totalIndis % indiMaxIndicatorsPerGroup;
    	numOfIndisOnAScreen = numOfIndisOnAScreen < 0 ? 1 :
    		currentIndi / indiMaxIndicatorsPerGroup == totalIndis / indiMaxIndicatorsPerGroup
    		? numOfIndisOnAScreen : indiMaxIndicatorsPerGroup;
    	
    	boolean hasNaviPrevious = false;
    	boolean hasNaviNext = false;
    	boolean hasNaviFirst = false;
    	boolean hasNaviLast = false;
    	if(indiBehavior == Indicator.BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE) {
    		boolean check = 0 == relativeCurIndi && currentIndi != 0;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviPrevious = check;
    		} else {
    			hasNaviFirst = check;
    		}
    		
    		check = indiMaxIndicatorsPerGroup - 1 == relativeCurIndi && currentIndi != totalIndis;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviNext = check;
    		} else {
    			hasNaviLast = check;
    		}
    	} else {
    		boolean check = currentIndi / indiMaxIndicatorsPerGroup > 0;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviPrevious = check;
    		} else {
    			hasNaviFirst = check;
    		}
    		check = currentIndi / indiMaxIndicatorsPerGroup != (totalIndis - 1) / indiMaxIndicatorsPerGroup;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviNext = check;
    		} else {
    			hasNaviLast = check;
    		}
    	}
    	hasNaviFirst = (hasNaviPrevious || hasNaviFirst) && in.isNavigationEndsShown();
    	hasNaviLast = (hasNaviNext || hasNaviLast) && in.isNavigationEndsShown();
    	
    	naviPreviousWidth   = naviNextWidth = 4;
    	naviPreviousHeight  = naviNextHeight = 7;
    	naviFirstWidth   = naviPreviousWidth + naviPreviousWidth / 2 + 1;
    	naviFirstHeight  = naviPreviousHeight;
    	naviLastWidth   = naviNextWidth + naviNextWidth / 2 + 1;
    	naviLastHeight  = naviNextHeight;
    	
    	//Set width and height of indicator
    	switch(intType) {
    	case Indicator.TYPE_RAW_BOX:
    		indiDimmedItemWidth = indiUndimmedItemWidth = 6;
    		indiDimmedItemHeight = indiUndimmedItemHeight = 6;
    		break;
    	case Indicator.TYPE_RAW_OVALS:
    		indiDimmedItemWidth = indiUndimmedItemWidth = 10;
    		indiDimmedItemWidth = indiUndimmedItemWidth = 6;
    		break;
    	case Indicator.TYPE_RAW_NUMBERS:
    		break;
    	case Indicator.TYPE_RAW_DOTS:
    	default:
    		intType = Indicator.TYPE_RAW_DOTS;
    		indiDimmedItemWidth = indiUndimmedItemWidth = 6;
    		indiDimmedItemWidth = indiUndimmedItemWidth = 6;
    		break;
    	}
    	
    	// H-Align the indicator
    	switch(indiHalign) {
    	case Component.RIGHT:
    		indiXpos = in.getX() + indiWidth - indiRightPadding
			    		- (indiDimmedItemWidth * numOfIndisOnAScreen) - (indiItemGap * (numOfIndisOnAScreen - 1))
			    		/*Reduce next navi length*/
			    		- (hasNaviNext ? naviNextWidth + naviToIndiGap : 0)
			    		/*Reduce previous navi length*/
			    		- (hasNaviPrevious ? naviPreviousWidth + naviToIndiGap : 0)
			    		/*Reduce first navi length*/
			    		- (hasNaviFirst ? naviFirstWidth + naviToNaviGap : 0)
			    		/*Reduce last navi length*/
			    		- (hasNaviLast ? naviLastWidth + naviToNaviGap : 0);
    		break;
    	case Component.CENTER:
    		indiXpos = in.getX() + indiWidth / 2 - (indiDimmedItemWidth * numOfIndisOnAScreen / 2)
			    		- (indiItemGap * (numOfIndisOnAScreen - 1) / 2)
			    		/*Reduce previous navi length*/
			    		- (hasNaviPrevious ? naviPreviousWidth + naviToIndiGap : 0)
			    		/*Reduce first navi length*/
			    		- (hasNaviFirst ? naviFirstWidth + naviToNaviGap : 0);
    		break;
    	case Component.LEFT:
    	default:
    		indiXpos = in.getX() + indiLeftPadding 
			    		/*Reduce previous navi length*/
			    		+ (hasNaviPrevious ? naviPreviousWidth + naviToIndiGap : 0)
			    		/*Reduce first navi length*/
			    		+ (hasNaviFirst ? naviFirstWidth + naviToNaviGap : 0);
    		break;
    	}
    	
    	int naviYpos = indiYpos;
    	// V-Align the navigation
    	switch(indiValign) {
    	case Component.BOTTOM:
    		naviYpos = naviYpos + indiHeight - indiBottomPadding - naviPreviousHeight;
    		break;
    	case Component.TOP:
    		naviYpos = naviYpos + indiTopPadding;
    		break;
    	case Component.CENTER:
    	default:
    		naviYpos = naviYpos + Math.max(0, indiHeight / 2 - naviPreviousHeight / 2);
    		break;
    	}
    	
    	//Navigation First
    	if (hasNaviFirst) {
    		g.setColor(s.getFgColor());
    		
    		int x = indiXpos;
    		g.fillTriangle(x, naviYpos + naviPreviousHeight / 2,
		    				x + naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
		    				x + naviPreviousWidth, naviYpos + naviPreviousHeight);
    		x = x + naviPreviousWidth / 2 + 1;
    		g.fillTriangle(x, naviYpos + naviPreviousHeight / 2,
		    				x + naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
		    				x + naviPreviousWidth, naviYpos + naviPreviousHeight);
    		
    		indiXpos = indiXpos + naviFirstWidth + naviToNaviGap;
    	}
    	
    	//Navigation Previous
    	if (hasNaviPrevious) {
    		g.setColor(s.getFgColor());
    		g.fillTriangle(indiXpos, naviYpos + naviPreviousHeight / 2,
		    				indiXpos + naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
		    				indiXpos + naviPreviousWidth, naviYpos + naviPreviousHeight);
    		
    		indiXpos = indiXpos + naviPreviousWidth + naviToIndiGap;
    	}
    	
    	// V-Align the indicator
    	switch(indiValign) {
    	case Component.BOTTOM:
    		indiYpos = in.getY() + indiHeight - indiBottomPadding - indiDimmedItemHeight;
    		break;
    	case Component.TOP:
    		indiYpos = in.getY() + indiTopPadding;
    		break;
    	case Component.CENTER:
    	default:
    		indiYpos = in.getY() + Math.max(0, indiHeight / 2 - indiDimmedItemHeight / 2 - (0 < indiDimmedItemHeight % 2 ? 1 : 0));
    		break;
    	}
    	
    	//g.setColor(s.getFgColor());
    	////top pad
    	//g.drawLine(in.getX(), in.getY() + topPadding, in.getX() + indiWidth, in.getY() + topPadding);
    	////bottom pad
    	//g.drawLine(in.getX(), in.getY() + indiHeight - bottomPadding, in.getX() + indiWidth, in.getY() + indiHeight - bottomPadding);
    	////left pad
    	//g.drawLine(in.getX() + leftPadding, in.getY(), in.getX() + leftPadding, in.getY() + indiHeight);
    	////right pad
    	//g.drawLine(in.getX() + indiWidth - rightPadding, in.getY(), in.getX() + indiWidth - rightPadding, in.getY() + indiHeight);
    	////center
    	//g.drawLine(in.getX() + indiWidth / 2, in.getY(), in.getX() + indiWidth / 2, in.getY() + indiHeight);
    	//
    	//g.drawRect(in.getX(), in.getY(), indiWidth - 1, indiHeight - 1);
    	
    	int indiCurveArcWidth = 1;
    	int indiCurveArcHeight = 1;
    	switch(intType) {
    	case Indicator.TYPE_RAW_DOTS:
    		indiCurveArcWidth = indiDimmedItemWidth / 2 + Math.max(0, indiDimmedItemWidth % 2);
    		indiCurveArcHeight = indiDimmedItemHeight / 2 + Math.max(0, indiDimmedItemHeight % 2);
    		break;
    	case Indicator.TYPE_RAW_BOX:
    		indiCurveArcWidth = 0;
    		indiCurveArcHeight = 0;
    		break;
    	case Indicator.TYPE_RAW_OVALS:
    		indiCurveArcWidth = indiDimmedItemWidth / 4 + Math.max(0, indiDimmedItemWidth % 4);
    		indiCurveArcHeight = indiDimmedItemHeight / 4 + Math.max(0, indiDimmedItemWidth % 4);
    		break;
    	case Indicator.TYPE_RAW_NUMBERS:
    		break;
    	}
    	
    	if (intType == Indicator.TYPE_RAW_DOTS || intType == Indicator.TYPE_RAW_BOX || intType == Indicator.TYPE_RAW_OVALS) {
    		for (int i = 0; i < numOfIndisOnAScreen; i++) {
    			if(i == relativeCurIndi) {
    				g.setColor(s.getFgColor());
    				g.fillRoundRect(indiXpos, indiYpos, indiDimmedItemWidth, indiDimmedItemHeight, indiCurveArcWidth, indiCurveArcHeight);
    			} else {
    				g.setColor(s.getFgColor());
    				g.drawRoundRect(indiXpos, indiYpos, indiDimmedItemWidth - 1, indiDimmedItemHeight - 1, indiCurveArcWidth, indiCurveArcHeight);
    			}
    			indiXpos = indiXpos + indiDimmedItemWidth;
    			if (i < (numOfIndisOnAScreen - 1)) {
    				indiXpos = indiXpos + indiItemGap;
    			}
    		}
    	} else if (intType == Indicator.TYPE_RAW_NUMBERS) {
    	}
    	
    	//Navigation Next
    	if (hasNaviNext) {
    		indiXpos = indiXpos + naviToIndiGap;
    		g.setColor(s.getFgColor());
    		g.fillTriangle(indiXpos, naviYpos, indiXpos, naviYpos + naviPreviousHeight,
    						indiXpos + naviPreviousWidth, naviYpos + (int)Math.floor(naviPreviousHeight / 2));
    		
    		indiXpos = indiXpos + naviNextWidth;
    	}
    	
    	//Navigation Last
    	if (hasNaviLast) {
    		indiXpos = indiXpos + naviToNaviGap;
    		g.setColor(s.getFgColor());
    		g.fillTriangle(indiXpos, naviYpos, indiXpos, naviYpos + naviPreviousHeight,
    						indiXpos + naviPreviousWidth, naviYpos + (int)Math.floor(naviPreviousHeight / 2));
    		indiXpos = indiXpos + naviNextWidth / 2 + 1;
    		g.fillTriangle(indiXpos, naviYpos, indiXpos, naviYpos + naviPreviousHeight,
    						indiXpos + naviPreviousWidth, naviYpos + (int)Math.floor(naviPreviousHeight / 2));
    	}
    	
    	s = null;
    }
    
    /**
     * Invoked for drawing the indicator widget with images
     * 
     * @param g graphics context
     * @param in component to draw
     */
    public void drawIndicatorImages(Graphics g, Indicator in) {
    	Style s = in.getStyle();
    	
    	int indiPaddingLeft = s.getPadding(Component.LEFT);
    	int indiPaddingRight = s.getPadding(Component.RIGHT);
    	int indiPaddingTop = s.getPadding(Component.TOP);
    	int indiPaddingBottom = s.getPadding(Component.BOTTOM);
    	
    	int indiPosX = in.getX();
    	int indiPosY = in.getY();
    	int indiWidth = in.getWidth();
    	int indiHeight = in.getHeight();
    	
    	int intType = in.getType();
    	int indiItemGap = in.getGap();
    	int indiHalign = s.getAlignment();
    	int indiValign = in.getVerticalAlignment();
    	int indiBehavior = in.getBehavior();
    	int indiMaxIndicatorsPerGroup = in.getMaxIndicatorsPerGroup();
    	
    	int indiDimmedItemWidth = 0;
    	int indiDimmedItemHeight = 0;
    	int indiUndimmedItemWidth = 0;
    	int indiUndimmedItemHeight = 0;
    	
    	int naviPreviousWidth   = 0;
    	int naviPreviousHeight  = 0;
    	int naviNextWidth   = 0;
    	int naviNextHeight  = 0;
    	int naviFirstWidth   = 0;
    	int naviFirstHeight  = 0;
    	int naviLastWidth   = 0;
    	int naviLastHeight  = 0;
    	int naviToIndiGap = in.getNavigationToItemGap();
    	int naviToNaviGap = in.getNavigationGap();
    	int naviToBorderGap = in.getNavigationToBorderGap();
    	
    	int indiQuantum = in.getQuantum();
    	int indiCurrent = in.getCurrent();
    	int indiTotal = in.getTotal();
    	
    	int totalIndis = indiQuantum > 0 && indiTotal > 0 ? indiTotal / indiQuantum : -1;
    	int currentIndi = indiQuantum > 0 && indiCurrent >= 0 ? indiCurrent / indiQuantum : -1;
    	int relativeCurIndi = currentIndi < 0 ? currentIndi : currentIndi % indiMaxIndicatorsPerGroup;
    	int numOfIndisOnAScreen = totalIndis < 0 ? totalIndis : totalIndis % indiMaxIndicatorsPerGroup;
    	numOfIndisOnAScreen = numOfIndisOnAScreen < 0 ? 1 :
    		currentIndi / indiMaxIndicatorsPerGroup == totalIndis / indiMaxIndicatorsPerGroup
    		? numOfIndisOnAScreen : indiMaxIndicatorsPerGroup;
    	
    	boolean hasNaviPrevious = false;
    	boolean hasNaviNext = false;
    	boolean hasNaviFirst = false;
    	boolean hasNaviLast = false;
    	if(indiBehavior == Indicator.BEHAVIOUR_SHOW_NAVGATION_WHEN_BESIDE) {
    		boolean check = 0 == relativeCurIndi && currentIndi != 0;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviPrevious = check;
    		} else {
    			hasNaviFirst = check;
    		}
    		
    		check = indiMaxIndicatorsPerGroup - 1 == relativeCurIndi && currentIndi != totalIndis;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviNext = check;
    		} else {
    			hasNaviLast = check;
    		}
    	} else if(indiBehavior == Indicator.BEHAVIOUR_SHOW_NAVGATION_ALWAYS) {
    		boolean check = currentIndi / indiMaxIndicatorsPerGroup > 0;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviPrevious = check;
    		} else {
    			hasNaviFirst = check;
    		}
    		check = currentIndi / indiMaxIndicatorsPerGroup != (totalIndis - 1) / indiMaxIndicatorsPerGroup;
    		if(in.isNavigationIncrementShown()) {
    			hasNaviNext = check;
    		} else {
    			hasNaviLast = check;
    		}
    	} else if(indiBehavior == Indicator.BEHAVIOUR_SHOW_NAVGATION_NONE) {
    		hasNaviFirst = false;
    		hasNaviPrevious = false;
    		hasNaviNext = false;
    		hasNaviLast = false;
    	}
    	hasNaviFirst = (hasNaviPrevious || hasNaviFirst) && in.isNavigationEndsShown();
    	hasNaviLast = (hasNaviNext || hasNaviLast) && in.isNavigationEndsShown();
    	
        if (indiBehavior == Indicator.TYPE_IMAGES && 
        		indicatorImages != null && indicatorImagesFocus != null) {
            indiDimmedItemWidth = indicatorImages[0].getWidth();
            indiDimmedItemHeight = indicatorImages[0].getHeight();
            
            indiUndimmedItemWidth = indicatorImages[1].getWidth();
            indiUndimmedItemHeight = indicatorImages[1].getHeight();

            naviPreviousWidth   = indicatorImages[2].getWidth();
            naviPreviousHeight  = indicatorImages[2].getHeight();
            
            naviNextWidth   = indicatorImages[3].getWidth();
            naviNextHeight  = indicatorImages[3].getHeight();
            
            naviFirstWidth   = indicatorImages[4].getWidth();
            naviFirstHeight  = indicatorImages[4].getWidth();
            
            naviLastWidth   = indicatorImages[5].getWidth();
            naviLastHeight  = indicatorImages[5].getWidth();
            
        } else {
			//Set width and height of indicator
			switch(intType) {
			case Indicator.TYPE_RAW_BOX:
				indiDimmedItemWidth = indiUndimmedItemWidth = 6;
				indiDimmedItemHeight = indiUndimmedItemHeight = 6;
				break;
			case Indicator.TYPE_RAW_OVALS:
				indiDimmedItemWidth = indiUndimmedItemWidth = 10;
				indiDimmedItemWidth = indiUndimmedItemWidth = 6;
				break;
			case Indicator.TYPE_RAW_NUMBERS:
				break;
			case Indicator.TYPE_RAW_DOTS:
			default:
				intType = Indicator.TYPE_RAW_DOTS;
				indiDimmedItemWidth = indiUndimmedItemWidth = 6;
				indiDimmedItemWidth = indiUndimmedItemWidth = 6;
				break;
			}
			
			naviPreviousWidth   = naviNextWidth = 4;
			naviPreviousHeight  = naviNextHeight = 7;
			
			naviFirstWidth   = naviPreviousWidth + naviPreviousWidth / 2 + 1;
			naviFirstHeight  = naviPreviousHeight;
			
			naviLastWidth   = naviNextWidth + naviNextWidth / 2 + 1;
			naviLastHeight  = naviNextHeight;
        }
    	
    	// H-Align the indicator
    	switch(indiHalign) {
	    	case Component.RIGHT:
	    		indiPosX = in.getX() + indiWidth - in.getPreferredW() - indiPaddingRight; 
	    		break;
	    	case Component.CENTER:
	    		indiPosX = in.getX() + indiPaddingLeft + Math.max(0, (in.getWidth() - in.getPreferredW()) / 2);
	    		break;
	    	case Component.LEFT:
	    	default:
	    		indiPosX = in.getX() + indiPaddingLeft;
	    		break;
    	}
    	
    	int naviYpos = indiPosY;
    	// V-Align the navigation
    	switch(indiValign) {
	    	case Component.BOTTOM:
	    		naviYpos += (indiHeight - in.getPreferredH() - indiPaddingBottom);
	    		break;
	    	case Component.TOP:
	    		naviYpos += indiPaddingTop;
	    		break;
	    	case Component.CENTER:
	    	default:
	    		naviYpos += indiPaddingTop + Math.max(0, (indiHeight - in.getPreferredH()) / 2);
	    		break;
    	}
    	
    	//Navigation First
    	if (in.isNavigationEndsShown()) {
    		if(hasNaviFirst) {
    			if (indicatorImages != null) {
    				g.drawImage(indicatorImages[4], indiPosX, naviYpos);
    			} else {
    				int[] poss = new int[]{0, naviYpos + (int)Math.ceil(naviPreviousHeight / 2),
    						naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
    						naviPreviousWidth, naviYpos + naviPreviousHeight};
    				int x = indiPosX;
    				g.setColor(s.getFgColor());
    				g.fillTriangle(x + poss[0], poss[1], x + poss[2], poss[3], x + poss[4], poss[5]);
    				x = x + naviPreviousWidth / 2 + 1;
    				g.fillTriangle(x + poss[0], poss[1], x + poss[2], poss[3], x + poss[4], poss[5]);
    			}
    		}
    		
    		indiPosX = indiPosX + naviToBorderGap + naviFirstWidth + naviToNaviGap;
    	}
    	
    	//Navigation Previous
    	if (in.isNavigationIncrementShown()) {
    		if(hasNaviPrevious) {
    			if (indicatorImages != null) {
    				g.drawImage(indicatorImages[2], indiPosX, naviYpos);
    			} else {
    				g.setColor(s.getFgColor());
    				g.fillTriangle(indiPosX, naviYpos + (int)Math.ceil(naviPreviousHeight / 2),
    						indiPosX + naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
    						indiPosX + naviPreviousWidth, naviYpos + naviPreviousHeight);
    			}
    		}
    			
    		indiPosX = indiPosX + naviPreviousWidth + naviToIndiGap;
    	}
    	
    	// V-Align the indicator
    	switch(indiValign) {
	    	case Component.BOTTOM:
	    		indiPosY += (indiHeight - in.getPreferredH() - indiPaddingBottom);
	    		break;
	    	case Component.TOP:
	    		indiPosY += indiPaddingTop;
	    		break;
	    	case Component.CENTER:
	    	default:
	    		indiPosY += indiPaddingTop + Math.max(0, (indiHeight - in.getPreferredH()) / 2);
	    		break;
    	}
    	
    	//g.setColor(s.getFgColor());
    	////top pad
    	//g.drawLine(in.getX(), in.getY() + topPadding, in.getX() + indiWidth, in.getY() + topPadding);
    	////bottom pad
    	//g.drawLine(in.getX(), in.getY() + indiHeight - bottomPadding, in.getX() + indiWidth, in.getY() + indiHeight - bottomPadding);
    	////left pad
    	//g.drawLine(in.getX() + leftPadding, in.getY(), in.getX() + leftPadding, in.getY() + indiHeight);
    	////right pad
    	//g.drawLine(in.getX() + indiWidth - rightPadding, in.getY(), in.getX() + indiWidth - rightPadding, in.getY() + indiHeight);
    	////center
    	//g.drawLine(in.getX() + indiWidth / 2, in.getY(), in.getX() + indiWidth / 2, in.getY() + indiHeight);
    	//
    	//g.drawRect(in.getX(), in.getY(), indiWidth - 1, indiHeight - 1);
    	
        if (indicatorImages != null) {
            for (int i = 0; i < numOfIndisOnAScreen; i++) {
                g.drawImage(indicatorImages[i == relativeCurIndi ? 1 : 0], indiPosX, indiPosY);
                indiPosX = indiPosX + indiDimmedItemWidth;
                if (i < (numOfIndisOnAScreen - 1)) {
                    indiPosX = indiPosX + indiItemGap;
                }
            }
        } else {
	    	int indiCurveArcWidth = 1;
	    	int indiCurveArcHeight = 1;
	    	switch(intType) {
	    	case Indicator.TYPE_RAW_DOTS:
	    		indiCurveArcWidth = indiDimmedItemWidth / 2 + Math.max(0, indiDimmedItemWidth % 2);
	    		indiCurveArcHeight = indiDimmedItemHeight / 2 + Math.max(0, indiDimmedItemHeight % 2);
	    		break;
	    	case Indicator.TYPE_RAW_BOX:
	    		indiCurveArcWidth = 0;
	    		indiCurveArcHeight = 0;
	    		break;
	    	case Indicator.TYPE_RAW_OVALS:
	    		indiCurveArcWidth = indiDimmedItemWidth / 4 + Math.max(0, indiDimmedItemWidth % 4);
	    		indiCurveArcHeight = indiDimmedItemHeight / 4 + Math.max(0, indiDimmedItemWidth % 4);
	    		break;
	    	case Indicator.TYPE_RAW_NUMBERS:
	    		break;
	    	}
	    	
	    	if (intType == Indicator.TYPE_RAW_DOTS || intType == Indicator.TYPE_RAW_BOX || intType == Indicator.TYPE_RAW_OVALS) {
	    		for (int i = 0; i < numOfIndisOnAScreen; i++) {
	    			if(i == relativeCurIndi) {
	    				g.setColor(s.getFgColor());
	    				g.fillRoundRect(indiPosX, indiPosY, indiDimmedItemWidth, indiDimmedItemHeight, indiCurveArcWidth, indiCurveArcHeight);
	    			} else {
	    				g.setColor(s.getFgColor());
	    				g.drawRoundRect(indiPosX, indiPosY, indiDimmedItemWidth - 1, indiDimmedItemHeight - 1, indiCurveArcWidth, indiCurveArcHeight);
	    			}
	    			indiPosX = indiPosX + indiDimmedItemWidth;
	    			if (i < (numOfIndisOnAScreen - 1)) {
	    				indiPosX = indiPosX + indiItemGap;
	    			}
	    		}
	    	} else if (intType == Indicator.TYPE_RAW_NUMBERS) {
	    	}
        }
    	
    	//Navigation Next
        if(in.isNavigationIncrementShown()) {
	    	if (hasNaviNext) {
	    		indiPosX = indiPosX + naviToIndiGap;
	            if (indicatorImages != null) {
	                g.drawImage(indicatorImages[3], indiPosX, naviYpos);
	            } else {
		    		g.setColor(s.getFgColor());
		    		g.fillTriangle(indiPosX, naviYpos, indiPosX, naviYpos + naviPreviousHeight,
		    						indiPosX + naviPreviousWidth, naviYpos + (int)Math.floor(naviPreviousHeight / 2));
	            }
	    	}
	    	
	    	indiPosX = indiPosX + naviNextWidth;
    	}
    
    	//Navigation Last
    	if(in.isNavigationEndsShown()) {
	    	if (hasNaviLast) {
	    		indiPosX = indiPosX + naviToNaviGap;
	            if (indicatorImages != null) {
	            	g.drawImage(indicatorImages[5], indiPosX, naviYpos);
	            } else {
					int[] poss = new int[]{0, naviYpos,
							naviPreviousWidth, naviYpos + naviPreviousHeight,
							naviPreviousWidth, naviYpos + (int)Math.ceil(naviPreviousHeight / 2)};
					g.setColor(s.getFgColor());
					g.fillTriangle(indiPosX + poss[0], poss[1], indiPosX + poss[2], poss[3], indiPosX + poss[4], poss[5]);
					indiPosX = indiPosX + naviNextWidth / 2 + 1;
					g.fillTriangle(indiPosX + poss[0], poss[1], indiPosX + poss[2], poss[3], indiPosX + poss[4], poss[5]);
	            }
	    	}
		}
    	
    	s = null;
    }
    
    /**
     * Calculate the preferred size of the component
     * 
     * @param in component whose size should be calculated
     * @return the preferred size for the component
     */
    public Dimension getIndicatorPreferredSize(Indicator in) {
        if (indicatorImages != null && indicatorImagesFocus!= null) {
            return getIndicatorPreferredSizeImage(in);
        }
        return new Dimension(20, 10);
    }
    
    /**
     * Calculate the preferred size of the component with images
     * 
     * @param in component whose size should be calculated
     * @return the preferred size for the component
     */
    private Dimension getIndicatorPreferredSizeImage(Indicator in) {

        Style style = in.getStyle();
        
    	int indiMaxIndicatorsPerGroup = in.getMaxIndicatorsPerGroup();
    	
    	int gap = in.getGap();
        int naviToNaviGap = in.getNavigationGap();
    	int naviToItemGap = in.getNavigationToItemGap();
    	int naviToBorderGap = in.getNavigationToBorderGap();

        int prefW = 0;
        int prefH = 0;
        int naviIncrementPrefW = 0;
        int naviEndsPrefW = 0;

    	if (indicatorImages[0] != null) {
    		prefW = Math.max(prefW, indicatorImages[0].getWidth());
    		prefH = Math.max(prefH, indicatorImages[0].getHeight());
    	}
    	if (indicatorImagesFocus[0] != null) {
    		prefW = Math.max(prefW, indicatorImagesFocus[0].getWidth());
    		prefH = Math.max(prefH, indicatorImagesFocus[0].getHeight());
    	}
    	if (indicatorImages[1] != null) {
    		prefW = Math.max(prefW, indicatorImages[1].getWidth());
    		prefH = Math.max(prefH, indicatorImages[1].getHeight());
    	}
    	if (indicatorImagesFocus[1] != null) {
    		prefW = Math.max(prefW, indicatorImagesFocus[1].getWidth());
    		prefH = Math.max(prefH, indicatorImagesFocus[1].getHeight());
    	}

    	if(in.isNavigationIncrementShown()) {
	        if (indicatorImages[2] != null) {
	        	naviIncrementPrefW = Math.max(naviIncrementPrefW, indicatorImages[2].getWidth());
	        	prefH = Math.max(prefH, indicatorImages[2].getHeight());
	        }
	        if (indicatorImagesFocus[2] != null) {
	        	naviIncrementPrefW = Math.max(naviIncrementPrefW, indicatorImagesFocus[2].getWidth());
	        	prefH = Math.max(prefH, indicatorImagesFocus[2].getHeight());
	        }
	        if (indicatorImages[3] != null) {
	        	naviIncrementPrefW = Math.max(naviIncrementPrefW, indicatorImages[3].getWidth());
	        	prefH = Math.max(prefH, indicatorImages[3].getHeight());
	        }
	        if (indicatorImagesFocus[3] != null) {
	        	naviIncrementPrefW = Math.max(naviIncrementPrefW, indicatorImagesFocus[3].getWidth());
	        	prefH = Math.max(prefH, indicatorImagesFocus[3].getHeight());
	        }
        }

    	if(in.isNavigationEndsShown()) {
        	if (indicatorImages[4] != null) {
        		naviEndsPrefW = Math.max(naviEndsPrefW, indicatorImages[4].getWidth());
        		prefH = Math.max(prefH, indicatorImages[4].getHeight());
        	}
        	if (indicatorImagesFocus[4] != null) {
        		naviEndsPrefW = Math.max(naviEndsPrefW, indicatorImagesFocus[4].getWidth());
        		prefH = Math.max(prefH, indicatorImagesFocus[4].getHeight());
        	}
        	if (indicatorImages[5] != null) {
        		naviEndsPrefW = Math.max(naviEndsPrefW, indicatorImages[5].getWidth());
        		prefH = Math.max(prefH, indicatorImages[5].getHeight());
        	}
        	if (indicatorImagesFocus[5] != null) {
        		naviEndsPrefW = Math.max(naviEndsPrefW, indicatorImagesFocus[5].getWidth());
        		prefH = Math.max(prefH, indicatorImagesFocus[5].getHeight());
        	}
        }
        
        //Calculate indicator items width's and gaping
        if (indicatorImages[0] != null && indicatorImagesFocus[0] != null 
        		&& indicatorImages[1] != null && indicatorImagesFocus[1] != null) {
        	int indiQuantum = in.getQuantum();
        	int indiCurrent = in.getCurrent();
        	int indiTotal = in.getTotal();

        	int totalIndis = indiQuantum > 0 && indiTotal > 0 ? indiTotal / indiQuantum : -1;
        	int currentIndi = indiQuantum > 0 && indiCurrent >= 0 ? indiCurrent / indiQuantum : -1;
        	int numberOfIndiItemsOnAScreen = totalIndis < 0 ? totalIndis : totalIndis % indiMaxIndicatorsPerGroup;
        	numberOfIndiItemsOnAScreen = numberOfIndiItemsOnAScreen < 0 ? 1 :
        		currentIndi / indiMaxIndicatorsPerGroup == totalIndis / indiMaxIndicatorsPerGroup
        		? numberOfIndiItemsOnAScreen : indiMaxIndicatorsPerGroup;
        	
        	prefW = (numberOfIndiItemsOnAScreen * (prefW + gap)) - gap;
        }
        //Calculate indicator increment navigation width's and gaping
    	if (in.isNavigationIncrementShown() && indicatorImages[2] != null && indicatorImagesFocus[2] != null 
    			&& indicatorImages[3] != null && indicatorImagesFocus[3] != null) {
    		prefW += ((naviIncrementPrefW + naviToItemGap) * 2); 
    	}
    	//Calculate indicator end navigation width's and gaping
    	if (in.isNavigationEndsShown() && indicatorImages[4] != null && indicatorImagesFocus[4] != null 
    			&& indicatorImages[5] != null && indicatorImagesFocus[5] != null) {
    		
    		prefW += ((naviEndsPrefW + naviToNaviGap) * 2); 
    	}
    	//Calculate indicator end navigation gaping with indicator border
    	if (in.isNavigationEndsShown() && indicatorImages[4] != null && indicatorImagesFocus[4] != null 
    			&& indicatorImages[5] != null && indicatorImagesFocus[5] != null) {
    		prefW += (naviToBorderGap * 2); 
    	}

    	if (prefW != 0) {
    		prefW += (style.getPadding(in.isRTL(), Component.RIGHT) + style.getPadding(in.isRTL(), Component.LEFT));
    	}
        if (prefH != 0) {
            prefH += (style.getPadding(false, Component.TOP) + style.getPadding(false, Component.BOTTOM));
        }

        if(style.getBorder() != null && in.isVisible()) {
            prefW = Math.max(style.getBorder().getMinimumWidth(), prefW);
            prefH = Math.max(style.getBorder().getMinimumHeight(), prefH);
        }

        style = null;
        
        return new Dimension(prefW, prefH);

    }

    /**
     * Update 'Indicator' component
     * 
     * @inheritDoc
     */
    public void refreshTheme() {
    	super.refreshTheme();
    	UIManager m = UIManager.getInstance();
        updateIndicatorConstants(m, false, "");
        updateIndicatorConstants(m, true, "Focus");
    }

    /**
     * Load 'Indicator' images
     */
    private void updateIndicatorConstants(UIManager m, boolean focus, String append) {
        try {
			Image dimmed = Image.createImage("/indicatorDimmed" + append + "Image.png");
            if(dimmed != null) {
                Image undimmed = Image.createImage("/indicatorUndimmed" + append + "Image.png");
                if(undimmed != null) {
                    //Scale the undimmed/dimmed image to dimmed/undimmed image dimension
                    //as non-proportional images are currently not supported
                	if(dimmed.getWidth() <= undimmed.getWidth()) {
                		undimmed = undimmed.scaledSmallerRatio(dimmed.getWidth(), dimmed.getHeight());
                	} else {
                		dimmed = dimmed.scaledSmallerRatio(undimmed.getWidth(), undimmed.getHeight());
                	}

                    //Scale the navigation images to dimmed height to align them properly in the widget
                	//as non-proportional images are currently not supported
                	Image next = Image.createImage("/indicatorNext" + append + "Image.png").scaledHeight(dimmed.getHeight());
                	Image previous = Image.createImage("/indicatorPrevious" + append + "Image.png").scaledHeight(next.getHeight());
                    Image first = Image.createImage("/indicatorFirst" + append + "Image.png").scaledHeight(dimmed.getHeight());
                    Image last = Image.createImage("/indicatorLast" + append + "Image.png").scaledHeight(next.getHeight());
                    if(focus) {
                        setIndicatorFocusImages(dimmed, undimmed, previous, next, first, last);
                    } else {
                        setIndicatorImages(dimmed, undimmed, previous, next, first, last);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
