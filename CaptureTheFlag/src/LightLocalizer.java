import lejos.hardware.Sound;

/**
 * @author Jiaao Guan
 * @version 1.2 Nov 12, 2015
 * */

public class LightLocalizer {
	private Odometer odo;
	private Navigation navi;
	private LightSensorPoller lsPoller;
	private final float gridLine = 40;	//the light value of gridline
	private final float ROTATION_SPEED = 200;
	private final static int TILE_WIDTH = 30;
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

		//navi.travelTo(-10, -10);
		navi.turnTo(90, true);
		Sound.beep();
		
		try {
			Thread.sleep(800); //wait 2 sec
		} catch (InterruptedException e) {
		}
		
		navi.setSpeeds(ROTATION_SPEED,ROTATION_SPEED);
		while(lsPoller.getLColorRed() > AutonomousRobot.samples[0] - 20);
		odo.setY(-offset+TILE_WIDTH);
			
		navi.goForward(-5);
		navi.turnTo(0, true);
		
		navi.setSpeeds(ROTATION_SPEED,ROTATION_SPEED);
		while(lsPoller.getLColorRed() > AutonomousRobot.samples[0] - 20);
		odo.setX(-offset+TILE_WIDTH);
		
		navi.stopMotors();
		Sound.beep();
		
		navi.travelToLocalization(15, 15);
		
		Sound.beepSequence();
		
		try {
			Thread.sleep(800); //wait 2 sec before next navigation
		} catch (InterruptedException e) {
		}
		
		// when done travel to (0,0) and turn to 0 degrees
	}

}
