package com.sun.lwuit;

/**
 * Controls the Indicator navigation
 * 
 * @since LWUIT 1.4
 * 
 * @version 1.0
 * 
 * @author Vimal, vimal.lwuit@ymail.com
 */
public interface IndicatorEventListener {
    /**
     * Move a position forward
     */
    public void next();
    
    /**
     * Move a position back
     */
    public void previous();
    
    /**
     * Move to first position
     */
    public void first();
    
    /**
     * Move to last position
     */
    public void last();
}
