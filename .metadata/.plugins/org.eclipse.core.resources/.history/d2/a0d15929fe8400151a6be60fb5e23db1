
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

public class UltrasonicPoller{
	private static int distanceThreshold = 250;
	private static final Port usPort = LocalEV3.get().getPort("S1");
	private SensorModes usSensor;
	private SampleProvider usValue;
	private float[] usData;
	private final int FILTER_OUT = 20;
	private int filterControl;
	
	//Constructor
	Public UltrasonicPoller()
	{
		this.usSensor = new EV3UltrasonicSensor(usPort);
		this.usValue = usSensor.getMode("Distance");			// usValue provides samples from this instance
		this.usData = new float[usValue.sampleSize()];			// usData is the buffer in which data are returned
	}
	
	//filtered ultrasonic data getter
	public float getFilteredData() {
		float distance;
		do{
			usValue.fetchSample(usData, 0);
			distance = usData[0] * 100;
			
			//limit the max distance to the threshold
			if(distance > distanceThreshold)
				distance = distanceThreshold;
			
			//filter out spurious values
			if(distance == distanceThreshold && filterControl < FILTER_OUT)
			{
				filterControl++;
			}
			else if(distance < distanceThreshold)
			{
				filterControl = 0;
			}
		}while(filterControl < FILTER_OUT && filterControl > 0);
		
		return distance;
	}
}
