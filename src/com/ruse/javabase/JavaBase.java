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

	private Thread mGameThread;
	private boolean mIsRunning;
	private boolean mWindowOpen;
	private String mWindowTitle;

	private Display mDisplay;
	private Input mInput;

	private final int width;
	private final int height;

	private BufferedImage mBufferedImage;

	// ---------------------------------------
	// Properties
	// ---------------------------------------

	public Display display() {
		return mDisplay;
	}

	// ---------------------------------------
	// Constructor
	// ---------------------------------------

	public JavaBase(String pWindowTitle) {
		mWindowTitle = pWindowTitle;
		width = ConstantsTable.WINDOW_WIDTH * ConstantsTable.WINDOW_SCALE;
		height = ConstantsTable.WINDOW_HEIGHT * ConstantsTable.WINDOW_SCALE;

		Dimension lSize = new Dimension(width, height);
		setSize(lSize);
		setPreferredSize(lSize);
		setMinimumSize(lSize);
		setMaximumSize(lSize);

		mDisplay = new Display(width, height);

		mBufferedImage = new BufferedImage(ConstantsTable.WINDOW_WIDTH * ConstantsTable.WINDOW_SCALE, ConstantsTable.WINDOW_HEIGHT * ConstantsTable.WINDOW_SCALE, BufferedImage.TYPE_INT_RGB);
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
