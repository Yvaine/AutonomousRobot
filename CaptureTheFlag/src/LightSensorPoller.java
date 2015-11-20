
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;


/**
 * @author Jiaao Guan
 * @author Ralph Bou Samra
 * 
 * @version 1.0 Nov 1, 2015
 * 
 * */

public class LightSensorPoller{
	/**The left color sensor port*/
	private static final Port lColorPort = LocalEV3.get().getPort("S2");		//The port for left light sensor
	/**The right color sensor port*/
	private static final Port rColorPort = LocalEV3.get().getPort("S3");		//The port for right light sensor
	/**The front color sensor port*/
	//private static final Port fColorPort = LocalEV3.get().getPort("S4");		//The port for front light sensor
	/**The left color sensor mode*/
	private static SensorModes lColorSensor = new EV3ColorSensor(lColorPort);	// The corresponding SensorModes
	/**The right color sensor mode*/
	private static SensorModes rColorSensor = new EV3ColorSensor(rColorPort);
	/**The front color sensor mode*/
	//private static SensorModes fColorSensor = new EV3ColorSensor(fColorPort);
	/**The left sensor red mode sample provider*/
	private SampleProvider lColorRed;
	/**The right sensor red mode sample provider*/
	private SampleProvider rColorRed;
	/**The front sensor colorID sample provider*/
	//private SampleProvider fColorID;
	/**The front sensor RGB sample provider*/
	//private SampleProvider fColorRGB;
	/**Holds values of sample collected by lColorRed*/
	private float[] lColorData;
	/**Holds values of sample collected by rColorRed*/
	private float[] rColorData;
	/**Holds values of sample collected by fColorID*/
	//private float[] fColorIDData;
	/**Holds values of sample collected by fColorData*/
	//private float[] fColorData;
	
	//Constructor
	/**Constructs a LightSensorPoller, initializes the sample providers, and fills the buffers with their 
	 * appropriate samples*/
	public LightSensorPoller()
	{
		this.lColorRed = lColorSensor.getMode("Red");				// LcolorR provides Red samples from this instance
		this.rColorRed = rColorSensor.getMode("Red");				// RcolorR provides Red samples from this instance
		//this.fColorID = fColorSensor.getMode("ColorID");		// FcolorID provides one of the 8 color ID's corresponding integers
		//this.fColorRGB = fColorSensor.getMode("RGB");			// FcolorRGB provides RGB samples from this instance
		this.lColorData = new float[lColorRed.sampleSize()];		//The data buffers
		this.rColorData = new float[rColorRed.sampleSize()];
		//this.fColorIDData = new float[fColorID.sampleSize()];
		//this.fColorData = new float[fColorRGB.sampleSize()];
	}
	
	//Data getters for the front light sensor
	//	The colorID getter
	/**
	 * Gets the color ID collected from the front sensor
	 * @return colorID
	 * */
	/*public int getFColorID()
	{
		fColorID.fetchSample(fColorIDData, 0);
		int color = (int)fColorIDData[0];
		return color;
	}*/
	// The color RGB value getter (returns an float array of length 3)
	/**
	 * Gets an array of size 3 filled with color sensor samples in RGB mode
	 * @return tempRGB
	 * */
	/*public float[] getFColorRGB()
	{
		float[] tempRGB = new float[3];
		fColorRGB.fetchSample(fColorData, 0);
		tempRGB[0] = fColorData[0];
		tempRGB[1] = fColorData[1];
		tempRGB[2] = fColorData[2];
		return tempRGB;
	}*/
	
	// Data getter for the left light sensor
	// The red color getter
	/**
	 * Gets the value collected from the left sensor in red mode
	 * @return color
	 * */
	public float getLColorRed()
	{
		lColorRed.fetchSample(lColorData, 0);
		float color = 100*lColorData[0];
		return color;
	}
	
	//Data getter for the right light sensor
	// The red color getter
	/**
	 * Gets the value collected from the right sensor in red mode
	 * @return color 
	 * */
	public float getRColorRed()
	{
		rColorRed.fetchSample(rColorData, 0);
		float color = 100*rColorData[0];
		return color;
	}
}
