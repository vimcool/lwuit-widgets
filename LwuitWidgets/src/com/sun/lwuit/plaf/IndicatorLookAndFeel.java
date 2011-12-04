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
public class IndicatorLookAndFeel extends DefaultLookAndFeel implements FocusListener {
    private Image[] indicatorImages = null;
    private Image[] indicatorImagesFocus = null;

    /** Creates a new instance of DefaultLookAndFeel */
    public IndicatorLookAndFeel() {
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
    	if (indicatorImages != null || in.getNonFocusedImages() != null) {
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
    	int indiBehavior = in.getBehaviour();
    	int indiMaxIndicatorsPerGroup = in.getMaxTimelineItems();
    	
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
        Image[] images = null;
        if(in.hasFocus() && in.isFocusable()) {
            images = in.getFocusedImages();
            if(null == images) {
                images = indicatorImagesFocus;
            }
        } else {
            images = in.getNonFocusedImages();
            if(null == images) {
                images = indicatorImages;
            }
        }
        
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
    	int indiBehavior = in.getBehaviour();
    	int indiMaxTimelineItems = in.getTotal() < in.getMaxTimelineItems() ? in.getTotal() : in.getMaxTimelineItems();
        boolean isOrientationHorizontal = in.getOrientation() == Indicator.ORIENTATION_HORIZONTAL;
    	
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
    	int relativeCurIndi = currentIndi < 0 ? currentIndi : currentIndi % indiMaxTimelineItems;
    	int numOfIndisOnAScreen = totalIndis < 0 ? totalIndis : totalIndis % indiMaxTimelineItems;
    	numOfIndisOnAScreen = numOfIndisOnAScreen < 0 ? 1 :
    		currentIndi / indiMaxTimelineItems == totalIndis / indiMaxTimelineItems
    		? numOfIndisOnAScreen : indiMaxTimelineItems;
    	
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

            check = indiMaxTimelineItems - 1 == relativeCurIndi && currentIndi != totalIndis;
            if(in.isNavigationIncrementShown()) {
                hasNaviNext = check;
            } else {
                hasNaviLast = check;
            }
    	} else if(indiBehavior == Indicator.BEHAVIOUR_SHOW_NAVGATION_ALWAYS) {
            boolean check = currentIndi / indiMaxTimelineItems > 0;
            if(in.isNavigationIncrementShown()) {
                hasNaviPrevious = check;
            } else {
                hasNaviFirst = check;
            }
            check = currentIndi / indiMaxTimelineItems != (totalIndis - 1) / indiMaxTimelineItems;
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
    	
        if (in.getType() == Indicator.TYPE_IMAGES) {
            if(images != null) {
                if (null != images[0]) {
                    indiDimmedItemWidth = images[0].getWidth();
                    indiDimmedItemHeight = images[0].getHeight();
                }

                if (null != images[1]) {
                    indiUndimmedItemWidth = images[1].getWidth();
                    indiUndimmedItemHeight = images[1].getHeight();
                }

                if (null != images[2]) {
                    naviPreviousWidth = images[2].getWidth();
                    naviPreviousHeight = images[2].getHeight();
                }

                if (null != images[3]) {
                    naviNextWidth = images[3].getWidth();
                    naviNextHeight = images[3].getHeight();
                }

                if (null != images[4]) {
                    naviFirstWidth = images[4].getWidth();
                    naviFirstHeight = images[4].getWidth();
                }

                if (null != images[5]) {
                    naviLastWidth = images[5].getWidth();
                    naviLastHeight = images[5].getWidth();
                }
            }
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
                if (isOrientationHorizontal) {
                    if(numOfIndisOnAScreen < indiMaxTimelineItems) {
                        indiPosX += (indiDimmedItemWidth + indiItemGap) * (indiMaxTimelineItems - numOfIndisOnAScreen);
                    }
                }
                break;
            case Component.CENTER:
                indiPosX = in.getX() + indiPaddingLeft + Math.max(0, (indiWidth - in.getPreferredW()) / 2); 
                if (isOrientationHorizontal) {
                    if(numOfIndisOnAScreen < indiMaxTimelineItems) {
                        indiPosX += ((indiDimmedItemWidth + indiItemGap) * (indiMaxTimelineItems - numOfIndisOnAScreen)) / 2;
                    }
                }
                break;
            case Component.LEFT:
            default:
                indiPosX = in.getX() + indiPaddingLeft;
                break;
    	}
    	
    	// V-Align the indicator
    	switch(indiHalign) {
            case Component.BOTTOM:
                indiPosY = in.getY() + indiHeight - in.getPreferredH() - indiPaddingBottom; 
                if (!isOrientationHorizontal) {
                    if(numOfIndisOnAScreen < indiMaxTimelineItems) {
                        indiPosY += (indiDimmedItemHeight + indiItemGap) * (indiMaxTimelineItems - numOfIndisOnAScreen);
                    }
                }
                break;
            case Component.CENTER:
                indiPosY = in.getY() + indiPaddingTop + Math.max(0, (indiHeight - in.getPreferredH()) / 2);
                if (!isOrientationHorizontal) {
                    if(numOfIndisOnAScreen < indiMaxTimelineItems) {
                        indiPosY += ((indiDimmedItemHeight + indiItemGap) * (indiMaxTimelineItems - numOfIndisOnAScreen)) / 2;
                    }
                }
                break;
            case Component.TOP:
            default:
                indiPosY = in.getY() + indiPaddingTop;
                break;
    	}
    	
    	int naviYpos = indiPosY;
    	int naviXpos = indiPosX;
    	// V-Align the navigation
    	switch(indiValign) {
            case Component.BOTTOM:
                if (isOrientationHorizontal) {
                    naviYpos += (indiHeight - in.getPreferredH() - indiPaddingBottom);
                } else {
                    naviXpos += (indiWidth - in.getPreferredW() - indiPaddingRight);
                }
                break;
            case Component.TOP:
                if (isOrientationHorizontal) {
                    naviYpos += indiPaddingTop;
                } else {
                    naviXpos += indiPaddingLeft;
                }
                break;
            case Component.CENTER:
            default:
                if (isOrientationHorizontal) {
                    naviYpos += indiPaddingTop + Math.max(0, (indiHeight - in.getPreferredH()) / 2);
                } else {
                    naviXpos += indiPaddingLeft + Math.max(0, (indiWidth - in.getPreferredW()) / 2);
                }
                break;
    	}
    	
    	//Navigation First
    	if (in.isNavigationEndsShown()) {
            if(hasNaviFirst) {
                if(intType == Indicator.TYPE_IMAGES) {
                    if (images[4] != null) {
                        g.drawImage(images[4], naviXpos, naviYpos);
                    } 
                } else {
                    int[] poss = new int[]{0, naviYpos + (int)Math.ceil(naviPreviousHeight / 2),
                                    naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
                                    naviPreviousWidth, naviYpos + naviPreviousHeight};
                    int x = naviXpos;
                    g.setColor(s.getFgColor());
                    g.fillTriangle(x + poss[0], poss[1], x + poss[2], poss[3], x + poss[4], poss[5]);
                    x = x + naviPreviousWidth / 2 + 1;
                    g.fillTriangle(x + poss[0], poss[1], x + poss[2], poss[3], x + poss[4], poss[5]);
                }
            }

            if (isOrientationHorizontal) {
                indiPosX = indiPosX + naviToBorderGap + naviFirstWidth + naviToNaviGap;
                naviXpos = indiPosX;
            } else {
                indiPosY = indiPosY + naviToBorderGap + naviFirstHeight + naviToNaviGap;
                naviYpos = indiPosY;
            }
    	}
    	
    	//Navigation Previous
    	if (in.isNavigationIncrementShown()) {
            if(hasNaviPrevious) {
                if(intType == Indicator.TYPE_IMAGES) {
                    if (images[2] != null) {
                        g.drawImage(images[2], naviXpos, naviYpos);
                    }
                } else {
                    g.setColor(s.getFgColor());
                    g.fillTriangle(naviXpos, naviYpos + (int)Math.ceil(naviPreviousHeight / 2),
                                    naviXpos + naviPreviousWidth, naviYpos - (0 < naviPreviousHeight % 2 ? 1 : 0),
                                    naviXpos + naviPreviousWidth, naviYpos + naviPreviousHeight);
                }
            }

            if (isOrientationHorizontal) {
                indiPosX = indiPosX + naviPreviousWidth + naviToIndiGap;
                naviXpos = indiPosX;
            } else {
                indiPosY = indiPosY + naviPreviousHeight + naviToIndiGap;
                naviYpos = indiPosY;
            }
    	}
    	
    	// V-Align the indicator
    	switch(indiValign) {
            case Component.BOTTOM:
                if (isOrientationHorizontal) {
                    indiPosY += (indiHeight - in.getPreferredH() - indiPaddingBottom);
                } else {
                    indiPosX += (indiWidth - in.getPreferredW() - indiPaddingRight);
                }
                break;
            case Component.TOP:
                if (isOrientationHorizontal) {
                    indiPosY += indiPaddingTop;
                } else {
                    indiPosX += indiPaddingLeft;
                }
                break;
            case Component.CENTER:
            default:
                if (isOrientationHorizontal) {
                    indiPosY += indiPaddingTop + Math.max(0, (indiHeight - in.getPreferredH()) / 2);
                } else {
                    indiPosX += indiPaddingLeft + Math.max(0, (indiWidth - in.getPreferredW()) / 2);
                }
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
    	
        if (images != null) {
            for (int i = 0; i < numOfIndisOnAScreen; i++) {
                g.drawImage(images[i == relativeCurIndi ? 1 : 0], indiPosX, indiPosY);
                if (isOrientationHorizontal) {
                    indiPosX = indiPosX + indiDimmedItemWidth;
                    if (i < (numOfIndisOnAScreen - 1)) {
                        indiPosX = indiPosX + indiItemGap;
                    }
                } else {
                    indiPosY = indiPosY + indiDimmedItemHeight;
                    if (i < (numOfIndisOnAScreen - 1)) {
                        indiPosY = indiPosY + indiItemGap;
                    }
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
                if (isOrientationHorizontal) {
                    indiPosX = indiPosX + naviToIndiGap;
                    naviXpos = indiPosX;
                } else {
                    indiPosY = indiPosY + naviToIndiGap;
                    naviYpos = indiPosY;
                }
                if(intType == Indicator.TYPE_IMAGES) {
                    if (images[3] != null) {
                        g.drawImage(images[3], naviXpos, naviYpos);
                    }
                } else {
                    g.setColor(s.getFgColor());
                    g.fillTriangle(naviXpos, naviYpos, naviXpos, naviYpos + naviPreviousHeight,
                                                    naviXpos + naviPreviousWidth, naviYpos + (int)Math.floor(naviPreviousHeight / 2));
                }
            }

            if (isOrientationHorizontal) {
                indiPosX = indiPosX + naviNextWidth;
            } else {
                indiPosY = indiPosY + naviNextHeight;
            }
    	}
    
    	//Navigation Last
    	if(in.isNavigationEndsShown()) {
            if (hasNaviLast) {
                if (isOrientationHorizontal) {
                    indiPosX = indiPosX + naviToNaviGap;
                    naviXpos = indiPosX;
                } else {
                    indiPosY = indiPosY + naviToNaviGap;
                    naviYpos = indiPosY;
                }
                if(intType == Indicator.TYPE_IMAGES) {
                    if (images[5] != null) {
                        g.drawImage(images[5], naviXpos, naviYpos);
                    }
                } else {
                    int[] poss = new int[]{0, naviYpos,
                                    naviPreviousWidth, naviYpos + naviPreviousHeight,
                                    naviPreviousWidth, naviYpos + (int)Math.ceil(naviPreviousHeight / 2)};
                    g.setColor(s.getFgColor());
                    g.fillTriangle(naviXpos + poss[0], poss[1], naviXpos + poss[2], poss[3], naviXpos + poss[4], poss[5]);
                    naviXpos = naviXpos + naviNextWidth / 2 + 1;
                    g.fillTriangle(naviXpos + poss[0], poss[1], naviXpos + poss[2], poss[3], naviXpos + poss[4], poss[5]);
                }
            }
        }
    	
    	s = null;
        images = (Image[])null;
    }
    
    /**
     * Calculate the preferred size of the component
     * 
     * @param in component whose size should be calculated
     * @return the preferred size for the component
     */
    public Dimension getIndicatorPreferredSize(Indicator in) {
        if (indicatorImages != null || indicatorImagesFocus != null ||
                in.getNonFocusedImages() != null || in.getFocusedImages() != null) {
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

        Image[] nonFocusedImages = in.getNonFocusedImages();
        if(null == nonFocusedImages) {
            nonFocusedImages = indicatorImages;
        }
        Image[] focusedImages = in.getFocusedImages();
        if(null == focusedImages) {
            focusedImages = indicatorImagesFocus;
        }

        Style style = in.getStyle();
        
    	int indiMaxTimelineItems = in.getTotal() < in.getMaxTimelineItems() ? in.getTotal() : in.getMaxTimelineItems();
        boolean isOrientationHorizontal = in.getOrientation() == Indicator.ORIENTATION_HORIZONTAL;
    	
    	int gap = in.getGap();
        int naviToNaviGap = in.getNavigationGap();
    	int naviToItemGap = in.getNavigationToItemGap();
    	int naviToBorderGap = in.getNavigationToBorderGap();

        int prefW = 0;
        int prefH = 0;
        int naviIncrementPrefW = 0;
        int naviIncrementPrefH = 0;
        int naviEndsPrefW = 0;
        int naviEndsPrefH = 0;

        if (nonFocusedImages != null) {
            if (nonFocusedImages[0] != null) {
                prefW = Math.max(prefW, nonFocusedImages[0].getWidth());
                prefH = Math.max(prefH, nonFocusedImages[0].getHeight());
            }
            if (nonFocusedImages[1] != null) {
                prefW = Math.max(prefW, nonFocusedImages[1].getWidth());
                prefH = Math.max(prefH, nonFocusedImages[1].getHeight());
            }
        }
        if (focusedImages != null) {
            if (focusedImages[0] != null) {
                prefW = Math.max(prefW, focusedImages[0].getWidth());
                prefH = Math.max(prefH, focusedImages[0].getHeight());
            }
            if (focusedImages[1] != null) {
                prefW = Math.max(prefW, focusedImages[1].getWidth());
                prefH = Math.max(prefH, focusedImages[1].getHeight());
            }
        }

    	if(in.isNavigationIncrementShown()) {
            if (nonFocusedImages != null) {
                if (nonFocusedImages[2] != null) {
                    if (isOrientationHorizontal) {
                        naviIncrementPrefW = Math.max(naviIncrementPrefW, nonFocusedImages[2].getWidth());
                        prefH = Math.max(prefH, nonFocusedImages[2].getHeight());
                    } else {
                        prefW = Math.max(prefW, nonFocusedImages[2].getWidth());
                        naviIncrementPrefH = Math.max(naviIncrementPrefH, nonFocusedImages[2].getHeight());
                    }
                }
                if (nonFocusedImages[3] != null) {
                    if (isOrientationHorizontal) {
                        naviIncrementPrefW = Math.max(naviIncrementPrefW, nonFocusedImages[3].getWidth());
                        prefH = Math.max(prefH, nonFocusedImages[3].getHeight());
                    } else {
                        prefW = Math.max(prefW, nonFocusedImages[3].getWidth());
                        naviIncrementPrefH = Math.max(naviIncrementPrefH, nonFocusedImages[3].getHeight());
                    }
                }
            }
            if (focusedImages != null) {
                if (focusedImages[2] != null) {
                    if (isOrientationHorizontal) {
                        naviIncrementPrefW = Math.max(naviIncrementPrefW, focusedImages[2].getWidth());
                        prefH = Math.max(prefH, focusedImages[2].getHeight());
                    } else {
                        prefW = Math.max(prefW, focusedImages[2].getWidth());
                        naviIncrementPrefH = Math.max(naviIncrementPrefH, focusedImages[2].getHeight());
                    }
                }
                if (focusedImages[3] != null) {
                    if (isOrientationHorizontal) {
                        naviIncrementPrefW = Math.max(naviIncrementPrefW, focusedImages[3].getWidth());
                        prefH = Math.max(prefH, focusedImages[3].getHeight());
                    } else {
                        prefW = Math.max(prefW, focusedImages[3].getWidth());
                        naviIncrementPrefH = Math.max(naviIncrementPrefH, focusedImages[3].getHeight());
                    }
                }
            }
        }

    	if(in.isNavigationEndsShown()) {
            if (nonFocusedImages != null) {
                if (nonFocusedImages[4] != null) {
                    if (isOrientationHorizontal) {
                        naviEndsPrefW = Math.max(naviEndsPrefW, nonFocusedImages[4].getWidth());
                        prefH = Math.max(prefH, nonFocusedImages[4].getHeight());
                    } else {
                        prefW = Math.max(prefW, nonFocusedImages[4].getWidth());
                        naviEndsPrefH = Math.max(naviEndsPrefH, nonFocusedImages[4].getHeight());
                    }
                }
                if (nonFocusedImages[5] != null) {
                    if (isOrientationHorizontal) {
                        naviEndsPrefW = Math.max(naviEndsPrefW, nonFocusedImages[5].getWidth());
                        prefH = Math.max(prefH, nonFocusedImages[5].getHeight());
                    } else {
                        prefW = Math.max(prefW, nonFocusedImages[5].getWidth());
                        naviEndsPrefH = Math.max(naviEndsPrefH, nonFocusedImages[5].getHeight());
                    }
                }
            }
            if (focusedImages != null) {
                if (focusedImages[4] != null) {
                    if (isOrientationHorizontal) {
                        naviEndsPrefW = Math.max(naviEndsPrefW, focusedImages[4].getWidth());
                        prefH = Math.max(prefH, focusedImages[4].getHeight());
                    } else {
                        prefW = Math.max(prefW, focusedImages[4].getWidth());
                        naviEndsPrefH = Math.max(naviEndsPrefH, focusedImages[4].getHeight());
                    }
                }
                if (focusedImages[5] != null) {
                    if (isOrientationHorizontal) {
                        naviEndsPrefW = Math.max(naviEndsPrefW, focusedImages[5].getWidth());
                        prefH = Math.max(prefH, focusedImages[5].getHeight());
                    } else {
                        prefW = Math.max(prefW, focusedImages[5].getWidth());
                        naviEndsPrefH = Math.max(naviEndsPrefH, focusedImages[5].getHeight());
                    }
                }
            }
        }
        
        //Calculate indicator items width's and gaping
        if ((nonFocusedImages != null && nonFocusedImages[0] != null && nonFocusedImages[1] != null) 
                || (focusedImages != null && focusedImages[0] != null && focusedImages[1] != null)){
            
            int indiQuantum = in.getQuantum();
            int indiCurrent = in.getCurrent();
            int indiTotal = in.getTotal();

            int totalIndis = indiQuantum > 0 && indiTotal > 0 ? indiTotal / indiQuantum : -1;
            int currentIndi = indiQuantum > 0 && indiCurrent >= 0 ? indiCurrent / indiQuantum : -1;
            int numberOfIndiItemsOnAScreen = totalIndis < 0 ? totalIndis : totalIndis % indiMaxTimelineItems;
            numberOfIndiItemsOnAScreen = numberOfIndiItemsOnAScreen < 0 ? 1 :
                currentIndi / indiMaxTimelineItems == totalIndis / indiMaxTimelineItems
                ? numberOfIndiItemsOnAScreen : indiMaxTimelineItems;

            if (isOrientationHorizontal) {
                prefW = (numberOfIndiItemsOnAScreen * (prefW + gap)) - gap;
            } else {
                prefH = (numberOfIndiItemsOnAScreen * (prefH + gap)) - gap;
            }
        }
        //Calculate indicator increment navigation width's and gaping
    	if (in.isNavigationIncrementShown() 
                && ((nonFocusedImages != null && nonFocusedImages[2] != null && nonFocusedImages[3] != null) 
                    || (focusedImages != null || focusedImages[2] != null && focusedImages[3] != null))) {
            if (isOrientationHorizontal) {
                prefW += ((naviIncrementPrefW + naviToItemGap) * 2); 
            } else {
                prefH += ((naviIncrementPrefH + naviToItemGap) * 2); 
            }
    	}
    	//Calculate indicator end navigation width's and gaping
    	if (in.isNavigationEndsShown() 
                && ((nonFocusedImages != null && nonFocusedImages[4] != null && nonFocusedImages[5] != null) 
                    || (focusedImages != null || focusedImages[4] != null && focusedImages[5] != null))) {
            if (isOrientationHorizontal) {
                prefW += ((naviEndsPrefW + naviToNaviGap) * 2); 
            } else {
                prefH += ((naviEndsPrefH + naviToNaviGap) * 2); 
            }
    	}
    	//Calculate indicator end/increment navigation gaping with indicator border
    	if ((in.isNavigationEndsShown()  || in.isNavigationIncrementShown())
                && ((nonFocusedImages != null && nonFocusedImages[4] != null && nonFocusedImages[5] != null) 
                    || (focusedImages != null || focusedImages[4] != null && focusedImages[5] != null))) {
            if (isOrientationHorizontal) {
                prefW += (naviToBorderGap * 2); 
            } else {
                prefH += (naviToBorderGap * 2); 
            }
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
        
        System.out.println("Indi="+in.paramString());
        System.out.println("prefW="+prefW+" | prefH="+prefH);

        style = null;
        nonFocusedImages = (Image[])null;
        focusedImages = (Image[])null;
        
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
            Image dimmed = getImage(m, "indicatorDimmed" + append + "Image");
            if(dimmed != null) {
                Image undimmed = getImage(m, "indicatorUndimmed" + append + "Image");
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
                    Image next = getImage(m, "indicatorNext" + append + "Image").scaledHeight(dimmed.getHeight());
                    Image previous = getImage(m, "indicatorPrevious" + append + "Image").scaledHeight(next.getHeight());
                    Image first = getImage(m, "indicatorFirst" + append + "Image").scaledHeight(dimmed.getHeight());
                    Image last = getImage(m, "indicatorLast" + append + "Image").scaledHeight(next.getHeight());
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
    
    private Image getImage(UIManager m, String imageName) throws IOException {
        //#if ForResouceEditor == 1
//#             return m.getThemeImageConstant("Indicator."+imageName);
        //#else
            return Image.createImage("/res/default/" + imageName + ".png");
        //#endif
    }
}
