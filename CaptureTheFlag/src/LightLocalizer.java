import lejos.hardware.Sound;

/**
 * @author Jiaao Guan
 * @version 1.2 Nov 12, 2015
 * */

public class LightLocalizer {
	private Odometer odo;
	private Navigation navi;
	private LightSensorPoller lsPoller;
	private final float gridLine = 30;	//the light value of gridline
	private final float ROTATION_SPEED = 50;
	private double offset = -4;	//offset from sensor to the wheel center
	
	
	/**
	 * Constructs a LightLocalizer object
	 * */
	public LightLocalizer(Odometer odo, LightSensorPoller lsPoller, Navigation navi) {
		this.odo = odo;
		this.lsPoller = lsPoller;
		this.navi = navi;
	}
	
	/**
	 * Performs light sensor localization
	 * */
	public void doLocalization() {

		navi.travelTo(-10, -10);
		navi.turnTo(90, true);
		Sound.beep();
		
		try {
			Thread.sleep(800); //wait 2 sec
		} catch (InterruptedException e) {
		}
		
		navi.setSpeeds(ROTATION_SPEED,ROTATION_SPEED);
		while(lsPoller.getRColorRed() > gridLine);
		odo.setY(-offset);
			
		navi.goForward(-5);
		navi.turnTo(0, true);
		
		navi.setSpeeds(ROTATION_SPEED,ROTATION_SPEED);
		while(lsPoller.getRColorRed() > gridLine);
		odo.setX(-offset);
		
		navi.stopMotors();
		Sound.beep();
		
		try {
			Thread.sleep(800); //wait 2 sec before next navigation
		} catch (InterruptedException e) {
		}
		
		// when done travel to (0,0) and turn to 0 degrees
		navi.travelTo(0, 0);
		Sound.buzz();
		navi.turnTo(0, true);
		Sound.beep();
	}

}
