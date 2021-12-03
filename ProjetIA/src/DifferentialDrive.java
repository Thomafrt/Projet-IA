import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Cette class gère toutes les actions en lien avec les moteurs des roues du robot
 * <p>
 * Elle est réalisée sur la base de la classe du meme nom du fichier exemple sur le site du projet
 *
 * @author Groupe3
 */
public class DifferentialDrive {
	public EV3LargeRegulatedMotor mLeftMotor;
	public EV3LargeRegulatedMotor mRightMotor;
	private final static int SPEED = 500;
	private final static int SPEED1 = 250;

	/**
	 * Le constructeur de la class DifferentialDrive
	 * <p>
	 * On initialise les moteurs des deux roues qui sont du type EV3LargeRegulatedMotor
	 * <p>
	 * 
	 * @see EV3LargeRegulatedMotor
	 * 
	 * @param left_port
	 * Le port sur lequel est branché la roue gauche
	 * @param right_port
	 * Le port sur lequel est branché la roue droite
	 */
	public DifferentialDrive (Port left_port, Port right_port){
		mLeftMotor = new EV3LargeRegulatedMotor(left_port);
		mRightMotor = new EV3LargeRegulatedMotor(right_port);
	}

	/**
	 * Fait avancer le robot, les deux roues sont actionné simultanément vers l'avant
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 * @see EV3LargeRegulatedMotor#forward()
	 */
	public void forward(){
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.forward();
		mRightMotor.forward();
	}

	/**
	 * Le robot s'arrête (la vitesse des moteur est mise à 0)
	 * 
	 * @see EV3LargeRegulatedMotor#rotate(int, boolean)
	 */
	public void stop(){ //arrête le robot
		System.out.println("Stop");
		mLeftMotor.rotate(0,true);
		mRightMotor.rotate(0,true);

	}

	/**
	 * Le robot tourne sur lui même dans les sens des aiguilles d'une montre
	 * 
	 * @see EV3LargeRegulatedMotor#forward()
	 * @see EV3LargeRegulatedMotor#backward()
	 */
	public void rotateClockwise(){
		mLeftMotor.forward();
		mRightMotor.backward();
	}

	/**
	 * Le robot tourne sur lui même dans le sen inverse des aiguilles d'une montre
	 * 
	 * @see EV3LargeRegulatedMotor#forward()
	 * @see EV3LargeRegulatedMotor#backward()
	 */
	public void rotateCounterClockwise(){ //le robot fait un tour sur lui même dans le sens inverse des aiguilles
		mLeftMotor.backward();
		mRightMotor.forward();
	}

	/**
	 * Le robot fait un tour COMPLET sur lui même
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 * @see EV3LargeRegulatedMotor#rotate(int, boolean)
	 */
	public void demiTour() {
		mLeftMotor.setSpeed(SPEED1);
		mRightMotor.setSpeed(SPEED1);
		System.out.println("Tour sur lui meme");	
		mLeftMotor.rotate(782,true);
		mRightMotor.rotate(-782,true);

	}

	/**
	 * La vitesse des moteur est défini à SPEED (500)
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 */
	public void vitesse() {
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
	}

	/**
	 * Le robot tourne sur lui même par la gauche
	 * 
	 * @param a
	 * La rotation que doivent effectuer les moteurs (un dans un sens et l'autre dans l'autre)
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 * @see EV3LargeRegulatedMotor#rotate(int, boolean)
	 */
	public void pivote(int a) {
		mLeftMotor.setSpeed(SPEED1);
		mRightMotor.setSpeed(SPEED1);
		mLeftMotor.rotate(a,true);
		mRightMotor.rotate(-a,true);
	}

	/**
	 * Indique si le robot se déplace
	 * 
	 * @see EV3LargeRegulatedMotor#isMoving()
	 * 
	 * @return true si il se déplace, false sinon
	 */
	public boolean isMoving() {
		if(this.mLeftMotor.isMoving()==true && this.mRightMotor.isMoving()==true)
			return true;
		return false;
	}

	/**
	 * Le robot recule pendant 3000ms
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 * @see EV3LargeRegulatedMotor#backward()
	 * @see EV3LargeRegulatedMotor#forward()
	 * @see Delay#msDelay(long)
	 * @see DifferentialDrive#stop()
	 */
	public void recule() {
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.backward();
		mRightMotor.backward();
		Delay.msDelay(3000);
		stop();
	}

	/**
	 * Le robot recule pendant a*1000ms
	 * 
	 * @param a Le multiplicateur pour le nombre de ms
	 * 
	 * @see EV3LargeRegulatedMotor#setSpeed(int)
	 * @see EV3LargeRegulatedMotor#backward()
	 * @see EV3LargeRegulatedMotor#forward()
	 * @see Delay#msDelay(long)
	 */
	public void legerRecule(int a) {
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.backward();
		mRightMotor.backward();
		Delay.msDelay(a*1000);
	}

	/**
	 * Le robot effectue une esquive par la gauche
	 * 
	 * @see DifferentialDrive#pivote(int)
	 * @see DifferentialDrive#forward()
	 * @see Delay#msDelay(long)
	 */
	public void esquive() {
		System.out.println("Esquive");
		pivote(100);
		Delay.msDelay(500);
		forward();
		Delay.msDelay(1000);
		pivote(-80);
		Delay.msDelay(500);
	}
}