package at.swt6.driveanalytics.dashboard.components;

import java.nio.ByteBuffer;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Komponente für die Sensoren welche das Datenformat
 * PERCENT unterstützen. Es wird ein Textfeld mit dem Namen
 * des Sensors, sowie ein Kreisdiagramm mit dem aktuellen
 * Prozentwert angezeigt.
 * 
 * @author Stefan
 */
public class PercentageSensorComponent extends AbstractSensorComponent {
	private ProgressIndicator progressIndicator;
	private Text sensorNameText;

	public PercentageSensorComponent() {
		this.sensorNameText = new Text();
		this.sensorNameText.setFont(new Font(15));
		HBox.setHgrow(this.sensorNameText, Priority.ALWAYS);
		VBox.setVgrow(this.sensorNameText, Priority.ALWAYS);
		this.progressIndicator = new ProgressIndicator();
		this.progressIndicator.setPrefHeight(300);
		this.progressIndicator.setProgress(0);
		HBox.setHgrow(this.progressIndicator, Priority.ALWAYS);
		VBox.setVgrow(this.progressIndicator, Priority.ALWAYS);
		this.getChildren().addAll(this.sensorNameText, this.progressIndicator);
	}

	@Override
	protected void setCurrentValue(ByteBuffer buffer) {
		progressIndicator.progressProperty().set(buffer.getDouble());
	}

	@Override
	protected void setName(String name) {
		sensorNameText.setText(name);		
	}
}
