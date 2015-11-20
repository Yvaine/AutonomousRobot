import lejos.utility.TimerListener;

public class ObstacleAvoider implements TimerListener {

	public static boolean isAvoidingObstacles = true;
	private Navigation navigator;

	private double currentX;
	private double currentY;
	private UltrasonicPoller usPoller;
	private Odometer odometer;
	private final int OFFSET = 10;
	private Display display = new Display();
	private float distance;

	public ObstacleAvoider(UltrasonicPoller usPoller, Navigation nav, Odometer odom) {
		this.usPoller = usPoller;
		this.navigator = nav;
		this.odometer = odom;
	}

	public void avoidObstacles() {
		
		currentX = odometer.getX();
		currentY = odometer.getY();

		while (true) {
			distance = usPoller.getFilteredData();
			display.print("Distance: ", distance + "", 4);
			if (distance > 20) {
				double currentAngle = odometer.getAng();
				display.print("Angle: ", currentAngle + "", 5);
				
				// heading north
				if (currentAngle > 45.0 && currentAngle < 135.0) {
					navigator.travelTo(currentX, currentY + OFFSET);
					currentY = currentY + OFFSET;
				}

				// heading east
				else if (currentAngle < 45.0 || currentAngle > 315.0) {
					navigator.travelTo(currentX + OFFSET, currentY);
					currentX = currentX + OFFSET;
				}

				// heading west
				else if (currentAngle > 135.0 && currentAngle < 225.0) {
					navigator.travelTo(currentX - OFFSET, currentY);
					currentX = currentX - OFFSET;
				}

				// heading south
				else if (currentAngle > 225 && currentAngle < 315.0) {
					navigator.travelTo(currentX, currentY - OFFSET);
					currentY = currentY - OFFSET;
				}
			}
			else{
				navigator.turnTo(odometer.getAng() + 90, true);
			}
		}
		
	}

	private float getFilteredData() {
		return usPoller.getFilteredData();
	}

	public void timedOut() {
		display.print("Distance: ", "" + distance, 4);
	}

}
