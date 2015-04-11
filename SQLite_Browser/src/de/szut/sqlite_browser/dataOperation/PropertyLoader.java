package de.szut.sqlite_browser.dataOperation;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

	private static final String CONFIG_FILE_PATH = "config/config.ini";
	private static final String POSITION_X_KEY = "position.x";
	private static final String POSITION_Y_KEY = "position.y";
	private static final String SIZE_X_KEY = "size.x";
	private static final String SIZE_Y_KEY = "size.y";
	
	private Properties properties;
	private Rectangle windowDimension;
	private File file;
	private FileInputStream fileInputStream;
	
	public PropertyLoader() {
		properties = new Properties();
		file = new File(CONFIG_FILE_PATH);
		try {
			fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
			windowDimension = new Rectangle();
			windowDimension.setBounds(
					Double.valueOf(properties.getProperty(POSITION_X_KEY)).intValue(), 
					Double.valueOf(properties.getProperty(POSITION_Y_KEY)).intValue(),
					Integer.parseInt(properties.getProperty(SIZE_X_KEY)), 
					Integer.parseInt(properties.getProperty(SIZE_Y_KEY)));
			
		} catch (IOException e) {
			windowDimension = new Rectangle();
			windowDimension.setBounds(100, 100, 868, 582);
		} catch(NullPointerException e) {
			windowDimension = new Rectangle();
			windowDimension.setBounds(100, 100, 868, 582);
		}
	}
	
	public Rectangle getWindowDimension() {
		return windowDimension;
	}
	
	public void setWindowDimension(Rectangle dimension) {
		windowDimension = dimension;
		properties.setProperty(POSITION_X_KEY, String.valueOf(windowDimension.getX()));
		properties.setProperty(POSITION_Y_KEY, String.valueOf(windowDimension.getY()));
		properties.setProperty(SIZE_X_KEY, String.valueOf(windowDimension.width));
		properties.setProperty(SIZE_Y_KEY, String.valueOf(windowDimension.height));
		try {
			properties.store(new FileOutputStream(file), null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
