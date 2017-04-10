package com.ruse.javabase;

import org.junit.Test;

public class JavaBaseTest {

	/**
	 * Test the availability of internal objects after instantiating a new
	 * JavaBase subclass (display, input).
	 */
	@Test
	public void javaBaseInstanstiationTest() {

		// Arrange
		final int WINDOW_WIDTH = 480;
		final int WINDOW_HEIGHT = 320;
		
		// Act
		final JavaBase lJavaBaseSubClass = new JavaBase("Sample Window", WINDOW_WIDTH, WINDOW_HEIGHT) {
			private static final long serialVersionUID = 1L;
		};

		// Assert
		assert(lJavaBaseSubClass.width == WINDOW_WIDTH) : "JavaBase subclass doesn't maintain correct width after creation";
		assert(lJavaBaseSubClass.height == WINDOW_HEIGHT) : "JavaBase subclass doesn't maintain correct height after creation";
		
		assert (lJavaBaseSubClass.display() != null) : " No Display instance after creating JavaBase subclass ";
		assert (lJavaBaseSubClass.input() != null) : "No input instance available after creating JavaBase subclass";

	}

	// TODO: Cannot test this really, takes too long for AWT to open window and
	// close ..
	@Test
	public void javaBaseWindowCreationTest() {

	}

}
