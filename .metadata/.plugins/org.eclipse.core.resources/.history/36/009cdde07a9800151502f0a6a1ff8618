import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

/**
 * 
 * @author Ralph Bou Samra
 * @version 1.0 Nov 1, 2015
 * 
 */

public class USLocalizer {
	

	 enum LocalizationType {
		FALLING_EDGE, 
		RISING_EDGE
	};
	

	/**Speed of wheel rotation */
	public static double ROTATION_SPEED = 30;
	/** Odomter object passed by instantiater of USLocalizer*/
	private Odometer odo;
	/** UltrasonicPoller object passed by instantiater of USLocalizer */
	private UltrasonicPoller usPoller;
	/** LocalizationType object passed by instantiater of USLocalizer */
	private LocalizationType locType;
	/** Navigation object passed by instantiater of USLocalizer */
	private Navigation nav;
	
	private int robotOffsetToMid = 9;
	private int distanceA = 35;
	private int dDistance = 1;

	
	/**
	 * Constructs a USLocalizer with initial field values
	 * 
	 * @param odo (required)
	 * @param usPoller (required)
	 * @param locType (required)
	 * @param nav (required)
	 * */
	public USLocalizer(Odometer odo, UltrasonicPoller usPoller, LocalizationType locType,
			Navigation nav) {
		this.odo = odo;
		this.usPoller = usPoller;
		this.locType = locType;
		this.nav = nav;
	}

	
	/**
	 * Performs ultrasonic localization
	 * by collecting and processing data from USPoller
	 * */
	public void doLocalization() {
		double angleA, angleB;

		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			nav.setSpeeds(100, -100);
			while (getFilteredData() < distanceA);
			
			while (getFilteredData() > distanceA + dDistance);
			angleA = odo.getAng();
			while (getFilteredData() > distanceA - dDistance);
			angleA = 0.5*(odo.getAng() + angleA);
			nav.stopMotors();
			Sound.beep();
			
			// switch direction and wait until it sees no wall
			nav.setSpeeds(-100, 100);
			while (getFilteredData() < distanceA);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (getFilteredData() > distanceA + dDistance);
			angleB = odo.getAng();
			while (getFilteredData() > distanceA - dDistance);
			angleB = 0.5*(odo.getAng() + angleB);
			nav.stopMotors();
			Sound.beep();
			
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			double avAng = (angleA + angleB) / 2;
			double correctionAngle;

			
			// formula in tutorial:
			if (angleA > angleB) {
				angleB += 360;
				correctionAngle = 225 - avAng;
			}
			else{
				correctionAngle = 45 - avAng;
			}
			odo.fixTheta(correctionAngle);

			/*nav.turnToLocalization(270, true);
			Sound.beepSequence();
			try {
				Thread.sleep(400); //wait 1 sec
			} catch (InterruptedException e) {
			}
			double x = getFilteredData();

			nav.turnToLocalization(180, true);
			Sound.beepSequence();
			try {
				Thread.sleep(400); //wait 1 sec
			} catch (InterruptedException e) {
			}
			double y = getFilteredData();
			
			odo.setX(-30 + x + robotOffsetToMid);
			odo.setY(-30 + y + robotOffsetToMid);
			*/
			
			//nav.travelTo(0, 0);
			//nav.turnToLocalization(0, true);
			try {
				Thread.sleep(800); //wait 2 sec
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 *	Gets the filtered value of distance collected
	 *  from the ultrasonic sensor
	 *  
	 *  @return distance collected from ultrasonic sensor
	 * */
	private float getFilteredData() {
		return usPoller.getFilteredData();
	}

}
