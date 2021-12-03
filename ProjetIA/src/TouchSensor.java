import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

/**
 * Extends EV3TouchSensor afin d'ajouter la m�thode isPressed()
 * 
 * Cette class provient du fichier exemple sur le site du projet
 * 
 * @author Groupe3
 */
public class TouchSensor extends EV3TouchSensor{

	/**
	 * Le constructeur de la class TouchSensor
	 * <p>
	 * On appel le constructeur de la super class sans modification
	 * @param port le port sur lequel le cpateur Touch est branch�
	 */
	public TouchSensor(Port port){
		super(port);
	}

	/**
	 * D�tecte si le capteur Touch est press�
	 * 
	 * @see SampleProvider#fetchSample(float[], int)
	 * 
	 * @return true si le premier �l�ment du tableau est diff�rent de 0 (le capteur est press�), false sinon
	 */
	public boolean isPressed(){
		float[] sample = new float[1];
		fetchSample(sample, 0);
		return sample[0] != 0;
	}
}