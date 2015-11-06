import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class USLocalizer {
	public enum LocalizationType {
		FALLING_EDGE, RISING_EDGE
	};

	public static double ROTATION_SPEED = 30;

	private Odometer odo;
	private SampleProvider usSensor;
	private float[] usData;
	private LocalizationType locType;
	private boolean isWallClear;
	private Navigation nav;

	public USLocalizer(Odometer odo, SampleProvider usSensor, float[] usData, LocalizationType locType,
			Navigation nav) {
		this.odo = odo;
		this.usSensor = usSensor;
		this.usData = usData;
		this.locType = locType;
		this.nav = nav;
	}

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
			
			nav.setSpeeds(70, -70);
			while (getFilteredData() < 35);
			
			while (getFilteredData() > 35);
			
			//nav.stopMotors();
		
			// nav.stopMotors();
			angleA = odo.getAng();
			Sound.beep();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// switch direction and wait until it sees no wall
			nav.setSpeeds(-70, 70);
			while (getFilteredData() < 35);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (getFilteredData() > 35);
			//nav.stopMotors();

			angleB = odo.getAng();
			Sound.beep();
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			double avAng = (angleA + angleB) / 2;
			double correctionAngle = 225 - avAng;
			
		
			
			// formula in tutorial:
			if (angleA > angleB) {
				angleB += 360;
			}
			odo.fixTheta(correctionAngle);

			nav.stopMotors();
			nav.turnTo(270, true);
			Sound.beepSequence();
			double x = getFilteredData();

			nav.turnTo(180, true);
			Sound.beepSequence();
			double y = getFilteredData();

			odo.setX(-30+x);
			odo.setY(-30+y);
			
			nav.travelTo(0, 0);
			nav.turnTo(0, true);
		}
	}

	private float getFilteredData() {
		usSensor.fetchSample(usData, 0);
		float distance = usData[0]*100;

		return distance;
	}

}
