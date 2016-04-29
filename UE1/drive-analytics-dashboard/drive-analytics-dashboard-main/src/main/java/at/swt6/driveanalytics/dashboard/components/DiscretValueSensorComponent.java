package at.swt6.driveanalytics.dashboard.components;

import java.nio.ByteBuffer;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Komponente für die Sensoren welche das Datenformat
 * ABSOLUTE_VALUE_LONG unterstützen. Es wird ein Textfeld
 * angezeigt, welches den aktuell gemessenen Wert des Sensors
 * anzeigt.
 * 
 * @author Stefan
 */
public class DiscretValueSensorComponent extends AbstractSensorComponent {
	private Text valueText;
	private Text sensorNameText;

	public DiscretValueSensorComponent() {
		VBox box = new VBox();
		this.sensorNameText = new Text();
		this.sensorNameText.setFont(new Font(15));
		box.getChildren().add(this.sensorNameText);
		box.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(box, Priority.ALWAYS);
		this.valueText = new Text();
		this.valueText.setFont(new Font(20));
		this.getChildren().addAll(box, this.valueText);
	}
	
	@Override
	protected void setName(String name) {
		this.sensorNameText.setText(name);
	}

	@Override
	protected void setCurrentValue(ByteBuffer value) {
		this.valueText.setText(String.valueOf(value.getLong()));
	}
}
