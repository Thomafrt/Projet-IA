import java.util.List;
import java.util.ArrayList;
import lejos.hardware.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
/**
 * La class Strat�gie met en lien les diff�rentes classes du projet pour faire fonctionner le robot de mani�re autonome
 *
 * @author Groupe3
 */
public class Strategie{
	TouchSensor t;
	DifferentialDrive m;
	Ultrasonic uS;
	Pince p;
	ColorSensor c;
	private int angle;

	/**
	 * Le constructeur initialise des objets pour chaque capteurs du robot et une variable angle qui permettra de connaitre la position du robot par rapport au but
	 * @see TouchSensor
	 * @see DifferentialDrive
	 * @see Ultrasonic
	 * @see Pince
	 * @see ColorSensor
	 */
	public Strategie() {
		t = new TouchSensor(SensorPort.S4);
		m = new DifferentialDrive(MotorPort.A,MotorPort.B);
		uS = new Ultrasonic(SensorPort.S2);
		p = new Pince (MotorPort.C);
		c = new ColorSensor(SensorPort.S1);
		angle=0;
	}

	/**
	 * Le robot avance et recupere un palet sur sa route et le met derriere la ligne blanche
	 * @see Touch#isPressed()
	 * @see DifferentialDrive#isMoving()
	 * @see DifferentialDrive#stop()
	 * @see DifferentialDrive#esquive()
	 * @see DifferentialDrive#forward()
	 * @see Pince#open()
	 * @see Pince#close()
	 * @see Delay#msDelay(long)
	 * @see Strategie#apres()
	 */
	public void premierPalet() {
		System.out.println("Mode : Recherche du premier palet");
		int i=0;
		while(i<1) { //tant que i inf�rieur � 1

			while (! t.isPressed()){ //tant que le touch nest pas touch�
				m.forward();
				if(m.isMoving())  //ouvre les pinces lorsque le robot avance
					p.open();
			}
			m.stop();
			Delay.msDelay(1000);
			p.close();
			Delay.msDelay(1000);
			System.out.println("Ramene le premier palet");
			m.esquive();
			System.out.println("D�tection ligne blanche...");
			while(c.getColorID()!= 6) { //tant qu'on ne d�tecte pas de blanc
				m.forward();
			}
			m.stop();
			p.open();
			i=1;
		}
		apres();
	}

	/**
	 * Le robot fait un tour sur lui m�me pour detecter l'objet le plus proche et se place face � lui
	 * 
	 * @see DifferentialDrive#demiTour()
	 * @see DifferentialDrive#isMoving()
	 * @see DifferentialDrive#vitesse()
	 * @see DifferentialDrive#pivote()
	 * @see Ultrasonic#getDistance()
	 * @see Delay#msDelay(long)
	 * @see Strategie#vaChercher()
	 */
	public void detecte() {
		System.out.println("Mode : Detection du palet le plus proche");
		List <Float> liste= new ArrayList<Float> ();
		m.demiTour();
		while(m.isMoving()==true) { //tant que le demi tour n'est pas fini
			float i = uS.getDistance();
			liste.add(i);
			Delay.msDelay(10);
		}
		int indice=0;
		float val=1000;
		for(int i=0; i<liste.size()-1;i++) { //tant qu'on a pas parcouru toute la liste
			if (liste.get(i)>0.35 && liste.get(i)<val) { //si l'indice i est supp � 0.3 ET inf�rieur � val et pas de val infini
				val=liste.get(i);						         
				indice=i;
			}
		}
		angle=(int) (1050/liste.size()*indice);
		m.vitesse();
		m.pivote(angle);
		Delay.msDelay(3000);
		vaChercher();
	}

	/**
	 * Le robot avance vers l'objet d�tect� en ouvrant les pinces, si c'est un palet il l'attrappe et se remet face au but adverse
	 * <p>
	 * Sinon il repasse en mode detection
	 * 
	 * @see DifferentialDrive#forward()
	 * @see DifferentialDrive#isMoving()
	 * @see DifferentialDrive#stop()
	 * @see Pince#open()
	 * @see Pince#close()
	 * @see Ultrasonic#getDistance()
	 * @see Touch#isPressed()
	 * @see Strategie#vaMarquer
	 */
	public void vaChercher() {
		System.out.println("Mode : Recuperer le palet");
		List <Float> liste= new ArrayList<Float> ();
		float v=0;
		liste.add(v);

		m.forward();
		while(m.isMoving()==true) { //tant que le robot avance
			p.open();

			float i = uS.getDistance();
			if(i>0.2){// si on ne detetecte pas de mur ni de valeur infini
				liste.add(i);
				float val1=liste.get(0);
				float val2=liste.get(1);
				if( t.isPressed() || val1-val2>0 ) { //on d�tecte un palet (il y a un saut dans les valeurs)
					if (t.isPressed()) {//si le touch est touch�
						m.stop();
						p.close();
						vaMarquer();
					}
				}
			}
			else { //sinon on s'arrete et on s'�carte et repasse en detection
				m.stop();
				m.legerRecule(2);
				angle=-angle;
				p.close();
				m.pivote(angle);
				Delay.msDelay(3000);
				angle=0;
				detecte();
			}
		}
	}

	/**
	 * Le robot se place face � la ligne pouis avance vers celle-ci
	 * <p>
	 * Si un objet est d�tect� sur le chemin, il l'esquive
	 * <p>
	 * Le robot laisse le palet derri�re la ligne blanche, puis se replace au centre du plateau
	 * 
	 * @see DifferentialDrive#legerRecule()
	 * @see DifferentialDrive#pivote()
	 * @see DifferentialDrive#vitesse()
	 * @see DifferentialDrive#forward()
	 * @see DifferentialDrive#esquive()
	 * @see DifferentialDrive#stop
	 * @see ColorSensor#getColorID()
	 * @see Pince#open()
	 * @see Delay#msDelay(long)
	 * @see Strategie#apres()
	 */
	public void vaMarquer() {
		System.out.println("Mode : Marquer un point");
		m.legerRecule(2);
		angle=-angle;
		System.out.println(angle);
		m.pivote(angle);
		Delay.msDelay(3000);
		m.vitesse();
		angle=0;
		while(c.getColorID()!= 6) {//tant qu'on ne d�tecte pas de blanc
			m.forward();
			if(uS.getDistance()<0.2)
				m.esquive();
		}
		m.stop();
		p.open();
		apres();
	}

	/**
	 * Ce replace au centre du plateau en fermant les pinces et relance la d�tection
	 * 
	 * @see DifferentialDrive#recule()
	 * @see Pince#close()
	 * @see Strategie#detecte()
	 */
	public void apres() {
		System.out.println("Replacement");
		m.recule();
		p.close();
		detecte();		
	}

	/**
	 * Lance le programme apr�s avoir press� un bouton
	 * <p>
	 * Cela permet de mettre en route les capteurs et d'avoir un d�marrage imm�diat avec un bouton
	 * 
	 * @see Button#waitForAnyPress()
	 * @see Strategie#premierPalet()
	 */
	public void run() {
		System.out.println("Pressez un bouton pour lancer le robot");
		Button.waitForAnyPress();
		premierPalet();
	}

	/**
	 * Lance le programme apres avoir press� un bouton
	 * <p>
	 * C'est un d�marrage alternatif � run() en allant directement se placer au centre du plateau
	 * 
	 * @see Button#waitForAnyPress()
	 * @see DifferentialDrive#forward()
	 * @see Delay#msDelay(long)
	 * @see Strategie#detecte()
	 */
	public void run2() {
		System.out.println("Pressez un bouton pour lancer le robot");
		Button.waitForAnyPress();
		m.forward();
		Delay.msDelay(2000);
		detecte();
	}

	/**
	 * Le main � lancer pour d�marrer le robot
	 * On peut utiliser run() ou run2()
	 * 
	 * @param args
	 * 
	 * @see Strategie#run()
	 * @see Strategie#run2()
	 */
	public static void main(String[] args) {
		Strategie s=new Strategie();
		s.run();
		//s.run2();
	}
}