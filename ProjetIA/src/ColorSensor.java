import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

/**
 * Cette class permet d'utliser le capteur de couleur du robot
 * <p>
 * Elle est r�alis� � partir du fichier exemple fournit sur le site du projet
 * 
 * *
 * @author Groupe3
 */
public class ColorSensor {
	private SensorModes sensor;
	private Port port;
	private float [] sample;
	private SampleProvider color;

	/**
	 * Le constructeur de ColorSensor
	 * 
	 * @param sensorPort Le port sur lequel le capeteur de couleur est branch�
	 * 
	 * @see SensorModes#getMode(String)
	 * @see SampleProvider#sampleSize()
	 * @see Brick#getPort(String)
	 * @see LocalEV3#get()
	 */
	public ColorSensor(Port sensorPort) {
		port = LocalEV3.get().getPort("S1");
		sensor = new EV3ColorSensor(port);
		color= sensor.getMode("ColorID");
		sample = new float[color.sampleSize()];
	}

	/**
	 * Renvoie un int correspondant � une couleur d�tect�e
	 *<p>
	 *Nous ne l'utiliserons que pour le blanc (code : 6) du fait de son manque de pr�cision
	 * 
	 * @see SampleProvider#fetchSample(float[], int)
	 * 
	 * @return sample[0] le premier �l�ment du tableau (la derni�re valeur capt�e)
	 */
	public float getColorID() {
		while(true) {
			color.fetchSample(sample, 0);
			return sample[0];
		}
	}
}
