package at.swt6.driveanalytics.dashboard;

import java.nio.ByteBuffer;

import at.swt6.driveanalytics.sensor.contracts.ISensor;
import at.swt6.driveanalytics.sensor.contracts.ISensorListener;

/**
 * Mit der SensorListener Klasse können die einzelnen Sensoren
 * den aktuellen Wert senden. Wenn sich der Wert im Sensor ändert
 * wird die Methode valueChanged aufgerufen und der Wert auf der 
 * Console ausgegeben. Das Flag DashboardWindow.PrintSensorListenerToConsole
 * dient zu Testzwecken, sodass die Ausgabe an und ausgeschalten werden kann.
 * @author Stefan
 *
 */
public class SensorListener implements ISensorListener {
	@Override
	public void valueChanged(ISensor sensor) {
		if(DashboardWindow.PrintSensorListenerToConsole)
			System.out.println(sensor.getSensorName() + " changed its value to " + getValueAsStringForSensor(sensor));
	}

	/**
	 * Gibt die Daten des Sensors im Stringformat zurück
	 * @param sensor
	 * @return
	 */
	private String getValueAsStringForSensor(ISensor sensor) {
		ByteBuffer buffer = ByteBuffer.wrap(sensor.getData());
		switch (sensor.getDataFormat()) {
			case ABSOLUTE_VALUE_LONG:
				return String.valueOf(buffer.getLong());
			case PERCENT:
				return String.valueOf(buffer.getDouble());
		}
		return "No Value";
	}
}
