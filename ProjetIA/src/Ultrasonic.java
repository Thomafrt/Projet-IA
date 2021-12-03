import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

/**
 * Cette class gère le capteur ultrasonic
 * <p>
 * Elle provient du fichier exemple sur le site du projet
 * 
 * @author Groupe3
 */
public class Ultrasonic{

	private SensorModes sensor;
	private Port port;
	private float [] sample;
	private SampleProvider distance;

	/**
	 * Le constructeur de la class Ultrasonic
	 * <p>
	 * Le port est récupéré, une instance EV3UltrasonicSensor est créée, initialise une instance du capteur en mode Distance, un tableau de float est créée de la taille de l'échantillon
	 * 
	 * @param sensorPort Le port sur lequel est branché le capeteur ultrasonic
	 * 
	 * @see EV3UltrasonicSensor
	 * @see SensorModes#getMode(String)
	 * @see SampleProvider#sampleSize()
	 * @see Brick#getPort(String)
	 * @see LocalEV3#get()
	 */
	public Ultrasonic(Port sensorPort) {
		port = LocalEV3.get().getPort("S2");
		sensor = new EV3UltrasonicSensor(port);
		distance= sensor.getMode("Distance");
		sample = new float[distance.sampleSize()];
	}

	/**
	 * Renvoie la distance avec le premier obstacle capté par le capteur ultrason
	 * 
	 * @see SampleProvider#fetchSample(float[], int)
	 * 
	 * @return sample[0] le premier élément du tableau (la dernière valeur captée)
	 */
	public float getDistance() {
		while(true) {
			distance.fetchSample(sample, 0);
			return sample[0];
		}
	}
}




