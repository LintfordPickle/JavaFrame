package com.ruse.javabase;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ruse.javabase.graphics.Display;
import com.ruse.javabase.input.Input;

public abstract class JavaBase extends Canvas implements Runnable {

	// ---------------------------------------
	// Constants
	// ---------------------------------------

	private static final long serialVersionUID = 1L;

	// ---------------------------------------
	// Variables
	// ---------------------------------------

	/**  */
	private Thread mGameThread;
	private String mWindowTitle;
	private boolean mIsRunning;
	private boolean mWindowOpen;

	private Display mDisplay;
	private Input mInput;

	/** The width of the window (and {@link BufferedImage}. */
	public final int width;

	/** The height of the window (and {@link BufferedImage}. */
	public final int height;

	/**
	 * The {@link BufferedImage} we draw onto the screen at the end of each
	 * render.
	 */
	private BufferedImage mBufferedImage;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	/**
	 * Returns the {@link Display} instance created with teh {@link JavaBase}.
	 * {@link Display} is a bitmap class, which we draw into during the render
	 * method.
	 */
	public Display display() {
		return mDisplay;
	}

	/**
	 * Returns the {@link Input} class, which tracks user input from the mouse
	 * and keyboard over time.
	 */
	public Input input() {
		return mInput;
	}

	/**
	 * Returns true if the game has been started, false otherwise. Use start()
	 * to start() the game, and stop() to stop it.
	 */
	public boolean isGameRunning() {
		return mIsRunning;
	}

	// ---------------------------------------
	// Constructor
	// ---------------------------------------

	public JavaBase(String pWindowTitle, final int pWindowWidth, final int pWindowHeight) {
		mWindowTitle = pWindowTitle;
		width = pWindowWidth;
		height = pWindowHeight;

		Dimension lSize = new Dimension(width, height);
		setSize(lSize);
		setPreferredSize(lSize);
		setMinimumSize(lSize);
		setMaximumSize(lSize);

		mDisplay = new Display(width, height);

		mBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		mDisplay.pixels = ((DataBufferInt) mBufferedImage.getRaster().getDataBuffer()).getData();

		mInput = new Input();

		addKeyListener(mInput);
		addFocusListener(mInput);
	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------

	public void openWindow() {
		if (mWindowOpen)
			return;

		JFrame lFrame = new JFrame(mWindowTitle);

		JPanel lPanel = new JPanel(new BorderLayout());
		lPanel.add(this, BorderLayout.CENTER);

		lFrame.setContentPane(lPanel);
		lFrame.pack();
		lFrame.setLocationRelativeTo(null);
		lFrame.setResizable(true);
		lFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lFrame.setVisible(true);

		mWindowOpen = true;

		start();

	}

	@Override
	public void run() {

		loadResources();

		while (mIsRunning) {

			handleInput();

			update();

			render();

		}

	}

	protected void loadResources() {

	}

	protected void handleInput() {

	}

	protected void update() {
		mDisplay.clear(0xff000000);

	}

	protected void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(mBufferedImage, 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

	}

	public void start() {

		if (mIsRunning)
			return;
		mIsRunning = true;
		mGameThread = new Thread(this);
		mGameThread.start();

	}

	public void stop() {
		if (!mIsRunning)
			return;
		mIsRunning = false;

		try {
			mGameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
