**************************************************************************
*********************************README***********************************
**************************************************************************

By Vimal (vimal.lwuit@ymail.com)


USAGE:
---------------
To complete !!





Change History:
---------------

Version 1.1.1
	1) Moved widget testing code into new project 'LwuitIndicatorWidgetTest'
	2) Renamed class 'IndicatorDefaultLookAndFeel' to 'IndicatorLookAndFeel' for better understandability
	3) [Feature] Add orientation (horizontal / vertical) support for this component
	4) [Feature] Move the default indicator widget resource into 'Vim_Widget_Indicator.res'

Version 1.1.0
	1) [Feature] Reduced the library size to 9Kb. Working hard to further reduce it :)
	2) [Feature] Auto-removed the gapping around navigation increment and/or navigation ends when they are made FALSE 
	3) [Bug Fix] Supports rendering component loaded images (focused and non-focused). Added methods to component, loadImages(...), getFocusedImages(...), getNonFocusedImages(...)
	4) [Bug Fix] Show the first indicator item as undimmed when the indicator is just initialized with default settings

Version 1.0.0
	Initial version



TODO:
-----

1) Support focused state image rendering
2) Support more Indicator Non-Image Types: NUMBERS, OVALS, ARCS
3) Support more Indicator Image Type: IMAGE
4) Support more vertical aligments: TOP and BOTTOM
5) Support variable dimensioned images for Dimmed, Undimmed, First, Last Previous and Next
6) Upgrade the LWUIT base support to version 1.5


