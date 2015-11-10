

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class ColorTest {

	private static final Port colorPort = LocalEV3.get().getPort("S2");
	private static TextLCD LCD = LocalEV3.get().getTextLCD();
	private static SensorModes colorSensor = new EV3ColorSensor(colorPort);
	private static SampleProvider colorID = colorSensor.getMode("ColorID");		//colorID provides one of the 8 color ID's corresponding integers
	//private static SampleProvider colorValue = colorSensor.getMode("RGB");			// colorValue provides samples from this instance
	//private static float[] colorData = new float[colorValue.sampleSize()];			// colorData is the buffer in which data are returned
	private static SampleProvider colorRed = colorSensor.getMode("Red");
	private static float[] colorDataRed = new float[colorRed.sampleSize()];
	
	public static void main(String[] args) {
		while(true)
		{
			int color = getColorID();
			
			LCD.clear();
			LCD.drawInt(color, 0, 0);
			/*LCD.drawString(""+colorData[0], 0, 1);
			LCD.drawString(""+colorData[1], 0, 2);
			LCD.drawString(""+colorData[2], 0, 3);*/
			LCD.drawString(""+getColorRed(), 0, 1);
		}
	}
	
	/*//get the color sensor value in RGB mode
		private static void getColorRGB()
		{
			colorValue.fetchSample(colorData, 0);
		}
	*/	
		
		//get the color sensor value in red mode
		private static float getColorRed()
		{
			float red;
			colorRed.fetchSample(colorDataRed, 0);
			red = 100*colorDataRed[0];
			return red;
		}
		
		//get one of the 8 color ID integers
		private static int getColorID()
		{
			float[] tempID = new float[colorID.sampleSize()];
			colorID.fetchSample(tempID, 0);
			int color = (int)tempID[0];
			
			return color;
		}

}