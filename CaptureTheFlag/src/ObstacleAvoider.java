import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.utility.TimerListener;

public class ObstacleAvoider implements TimerListener {

	public static boolean isAvoidingObstacles = true;
	private Navigation navigator;

	private double currentX;
	private double currentY;
	private static UltrasonicPoller usPoller;
	private Odometer odometer;
	private final int OFFSET = 30;
	private Display display = new Display();
	private float distance;
	private static final int safeDistance = 30;
	private static int direction;
	private static int path = 0;	//0 means no available path, 1 means left, 2 means right
	public static boolean atSide = false;
	public int USSENSOR_MOTOR_SPEED = 50;
	int bottomLeftX;
	int bottomLeftY;
	int topRightX;
	int topRightY;
	private static EV3MediumRegulatedMotor usMotor = new EV3MediumRegulatedMotor(LocalEV3.get().getPort("B"));

	public ObstacleAvoider(UltrasonicPoller usPoller, Navigation nav, Odometer odom, int[] coords) {
		this.usPoller = usPoller;
		this.navigator = nav;
		this.odometer = odom;
		this.bottomLeftX = coords[0];
		this.bottomLeftY = coords[1];
		this.topRightX = coords[2];
		this.topRightY = coords[3];
		usMotor.setSpeed(USSENSOR_MOTOR_SPEED);
	}

	public void avoidObstacles() {

		navigator.turnTo(90,true);
		currentX = odometer.getX();
		currentY = odometer.getY();

		while (destReached()) {
			if(!atSide)
			{
				if(odometer.getY() > 180)
				{
					navigator.turn(-90);
					atSide = true;
				}
				if(odometer.getX() > 180)
				{
					navigator.turn(90);
					atSide = true;
				}
			}
			if(!atSide)
			{
				if(path == 1)
				{
					navigator.turn(90);
					path = 0;
				}
				if(path == 2)
				{
					navigator.turn(-90);
					path = 0;
				}
			}
			direction = (((int)odometer.getAng()+45)%360)/90;	//0,1,2,3 means E,N,W,S respectively
			distance = usPoller.getFilteredData();
			display.print("Distance: ", distance + "", 4);
			if (distance > safeDistance) {
				switch(direction)
				{
					case 0:
						navigator.travelTo(currentX + OFFSET, currentY);
						currentX = currentX + OFFSET;
						break;
					case 1:
						navigator.travelTo(currentX, currentY + OFFSET);
						currentY = currentY + OFFSET;
						break;
					case 2:
						navigator.travelTo(currentX - OFFSET, currentY);
						currentX = currentX - OFFSET;
						break;
					case 3:
						navigator.travelTo(currentX, currentY - OFFSET);
						currentY = currentY - OFFSET;
						break;
				}
			} else {
				switch(direction)
				{
					case 0:
						navigator.turn(90);
						distance = usPoller.getFilteredData();
						if (distance < safeDistance)
						{
							navigator.turn(180);	
							distance = usPoller.getFilteredData();
							if (distance < safeDistance)
							{
								navigator.turn(-90);	
							}
							break;
						}
						else
							break;
					case 1:
						navigator.turn(-90);
						distance = usPoller.getFilteredData();
						if (distance < safeDistance)
						{
							navigator.turn(180);	
							distance = usPoller.getFilteredData();
							if (distance < safeDistance)
							{
								navigator.turn(90);	
							}
							break;
						}
						else
							break;
					case 2:
						navigator.turn(-90);
						distance = usPoller.getFilteredData();
						if (distance < safeDistance)
						{
							navigator.turn(180);	
							distance = usPoller.getFilteredData();
							if (distance < safeDistance)
							{
								navigator.turn(90);	
							}
							break;
						}
						else
							break;
					case 3:
						navigator.turn(90);
						distance = usPoller.getFilteredData();
						if (distance < safeDistance)
						{
							navigator.turn(180);	
							distance = usPoller.getFilteredData();
							if (distance < safeDistance)
							{
								navigator.turn(-90);	
							}
							break;
						}
						else
							break;
				}
			}
		}

	}

	public static void seekPath()
	{
		if(!atSide)
		{
			if(direction == 0)
			{
				usMotor.rotate(100);
				if(usPoller.getFilteredData() > safeDistance)
					path = 1;
				usMotor.rotate(-100);
			}
			else if(direction == 1)
			{
				usMotor.rotate(-100);
				if(usPoller.getFilteredData() > safeDistance)
					path = 2;
				usMotor.rotate(100);
			}
			else
				path = 0;
		}
		else
			path = 0;
	}
	
	//set to false when destination is reached
	private boolean destReached()
	{
		if(odometer.getX() > 180 && odometer.getX() < 210 && odometer.getY() > 180 && odometer.getY() < 210)
			return false;
		else
			return true;
	}

	private boolean searchForObject() {
		return false;
	}

	public void timedOut() {
		display.print("Distance: ", "" + distance, 4);
	}

}