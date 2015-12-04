// Auther: Jiaao Guan
import java.rmi.RemoteException;
import lejos.hardware.Sound;
import lejos.remote.ev3.RMIRegulatedMotor;

public class BlockCatcher extends Thread{
	private RMIRegulatedMotor catcherArm;
	private RMIRegulatedMotor catcherHand;
	private boolean toRelease;		//true if a signal is received to ask the catcher to release the block
	private static boolean done;			//true if the current task of catcher is done
	private final int catchSpeed = 40;	//the rotation speed of catcher motors
	private final int handOpenAngle = -60;
	private final int handCloseAngle = 100;
	private final int armUpAngle = 230;
	private final int armDownAngle = -180;
	
	public BlockCatcher(RMIRegulatedMotor catcherArm, RMIRegulatedMotor catcherHand)
	{
		this.catcherArm = catcherArm;
		this.catcherHand = catcherHand;
		this.toRelease = false;
		done = false;
	}
	
	//it is a one time process
	public void run()
	{
		Sound.beepSequence();
		try {
			this.catcherArm.setSpeed(catchSpeed);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.catcherHand.setSpeed(catchSpeed);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.catcherHand.rotate(handOpenAngle,false);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.catcherArm.rotate(armDownAngle,false);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			this.catcherHand.rotate(handCloseAngle,false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.catcherArm.rotate(armUpAngle,false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		done = true;
		Sound.beep();
		
		while(!toRelease);
		
		done = false;
		try {
			this.catcherArm.rotate(armDownAngle,false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.catcherHand.rotate(-handCloseAngle,false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		done = true;
	}
	
	//ask the catcher to release the block
	public void Release()
	{
		this.toRelease = true;
	}
	
	// Returns true if the current move is done.
	/*	Since there is a delay in the second brick,
	 * this method is to make sure the current catching or releasing process is done
	 * in order to jump to the next step.
	 * I am not sure about how the delay will occur, and this method may be replaced by
	 * a simple sleep.
	 */
	public static boolean currentMoveDone()
	{
		return done;
	}
}
