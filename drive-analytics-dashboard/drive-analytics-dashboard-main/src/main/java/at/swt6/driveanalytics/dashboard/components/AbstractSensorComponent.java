package at.swt6.driveanalytics.dashboard.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import java.nio.ByteBuffer;

import at.swt6.driveanalytics.sensor.contracts.ISensor;

/**
 * Die Basisklasse für UI Sensorendarstellungen. Alle Sensoren
 * befinden sich in einer horizotanlen Box. 
 * @author Stefan
 *
 */
public abstract class AbstractSensorComponent extends HBox {
	protected ISensor sensor;
	
	protected abstract void setCurrentValue(ByteBuffer value);
	protected abstract void setName(String name);
	
	protected AbstractSensorComponent(){
		this.setPadding(new Insets(20));
		this.setStyle("-fx-border-color: black;-fx-border-width: 2; -fx-padding: 10px; -fx-border-insets: 10px; -fx-background-insets: 10px;");
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Initialisiert die Komponente mit den Daten des übergebenen Sensors
	 * @param sensor
	 */
	public final void setSensor(ISensor sensor) {
		this.sensor = sensor;
		this.setId(sensor.getSensorId());
		this.setName(sensor.getSensorName());
	}
	
	/**
	 * Aktualisiert den angezeigten Wert.
	 */
	public final void refresh() {
		setCurrentValue(ByteBuffer.wrap(sensor.getData()));
	}
}
