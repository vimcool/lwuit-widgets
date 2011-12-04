//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is part of J2MEUnit, a Java 2 Micro Edition unit testing framework.
//
// J2MEUnit is free software distributed under the Common Public License (CPL).
// It may be redistributed and/or modified under the terms of the CPL. You 
// should have received a copy of the license along with J2MEUnit. It is also 
// available from the website of the Open Source Initiative at 
// http://www.opensource.org.
//
// J2MEUnit is distributed in the hope that it will be useful, but WITHOUT ANY 
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
// FOR A PARTICULAR PURPOSE.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestSuite;

import j2meunit.tests.TestTest;
import j2meunit.tests.TestTestCase;


/********************************************************************
 * TestSuite that runs all tests for J2MEUnit.
 */
public class AllTests extends TestCase
{
	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new AllTests object.
	 */
	public AllTests()
	{
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * Creates a test suite containing all J2MEUnit tests. 
	 *
	 * @return A new test suite
	 */
	public Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new TestTest().suite());
		suite.addTest(new TestTestCase().suite());

		return suite;
	}
}
