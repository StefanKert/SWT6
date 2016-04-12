package at.swt6.driveanalytics.dashboard;

import java.util.ArrayList;
import java.util.List;
import at.swt6.driveanalytics.dashboard.components.AbstractSensorComponent;
import at.swt6.driveanalytics.dashboard.components.DiscretValueSensorComponent;
import at.swt6.driveanalytics.dashboard.components.PercentageSensorComponent;
import at.swt6.driveanalytics.sensor.contracts.ISensor;
import at.swt6.driveanalytics.utils.Timer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * Diese Klasse repräsentiert das Dashboard, die Hauptanzeige. Hier werden die einzelnen Sensoren angezeigt
 * und es kann konfiguriert werden in welchem Interval sich die Sensoren updaten sollten.
 * 
 * @author Stefan
 */
public class DashboardWindow {
	private Stage stage;
	private List<EventHandler<WindowEvent>> onCloseHandlers = new ArrayList<>();
	private Slider slider;
	private VBox rootPane;
	private VBox componentContainer;
	private List<ISensor> sensorList = new ArrayList<>();
	private Timer timer;
	
	/**
	 * Flag das festlegt, ob der SensorListener die aktuell gesendeten Werte aus den Senosren auf der Konsole ausgibt oder nicht.
	 */
	public static boolean PrintSensorListenerToConsole = false;

	public DashboardWindow() {
		this.rootPane = new VBox();
		this.slider = new Slider();
		this.slider.setShowTickLabels(true);
		this.slider.setSnapToTicks(true);
		this.slider.setMajorTickUnit(500);
		this.slider.setBlockIncrement(100);
		this.slider.setValue(100);
		this.slider.setMin(100);
		this.slider.setMax(5000);
		this.slider.valueProperty().addListener((arg0, arg1, arg2) -> {
			timer.setInterval((int) slider.getValue());
		});
		this.slider.setStyle("-fx-padding: 10px; -fx-border-insets: 10px; -fx-background-insets: 10px;");
		Button button = new Button("Activate Output to console.");
		button.setOnMouseClicked(ev -> {
			DashboardWindow.PrintSensorListenerToConsole = !DashboardWindow.PrintSensorListenerToConsole;
			if(	DashboardWindow.PrintSensorListenerToConsole)
				button.setText("Deactivate Output to console.");
			else
				button.setText("Activate Output to console.");
		});
		button.setStyle("-fx-padding: 10px; -fx-border-insets: 10px;");
		this.componentContainer = new VBox();
		this.componentContainer.setSpacing(10);
		this.rootPane.getChildren().add(slider);
		this.rootPane.getChildren().add(button);
		this.rootPane.getChildren().add(componentContainer);
		timer = new Timer();
		timer.addTimerListener(x -> {
			try {
				JavaFxUtils.runLater(() -> updateSensorComponents());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		timer.start();
	}

	private void updateSensorComponents() {
		for (ISensor sensor : sensorList) {
			Node component = getSensorComponentById(sensor.getSensorId());
			if (component != null)
				((AbstractSensorComponent) component).refresh();
		}
	}

	private Node getSensorComponentById(String id) {
		return componentContainer.getChildren().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
	}
	
/**
 * Entfernt den Sensor aus der Liste, sowie die dazugehörige Komponente vom dashboard
 * @param sensor
 */
	public void removeSensor(ISensor sensor) {
		Node node = getSensorComponentById(sensor.getSensorId());
		ISensor sensorInList = sensorList.stream().filter(x -> x.getSensorId().equals(sensor.getSensorId())).findFirst().orElse(null);
		if (node != null) {
			componentContainer.getChildren().remove(node);
		}
		if(!sensorList.remove(sensorInList)){
			throw new RuntimeException("Failed to remove sensor");
		}
	}

	/**
	 * Hinzufügen eines Sensors und erzeugen der zugehörigen Sensorkomponente
	 * abhängig vom Datentyp.
	 * @param sensor
	 */
	public void addSensor(ISensor sensor) {
		sensorList.add(sensor);
		AbstractSensorComponent sensorComponent = null;
		switch (sensor.getDataFormat()) {
		case PERCENT:
			sensorComponent = new PercentageSensorComponent();
			break;
		case ABSOLUTE_VALUE_LONG:
			sensorComponent = new DiscretValueSensorComponent();
			break;
		}
		sensorComponent.setSensor(sensor);
		componentContainer.getChildren().add(sensorComponent);
	}

	public void show() {
		if (stage == null) {
			stage = new Stage();
			stage.setScene(new Scene(rootPane, 500, 500));
			stage.setMinWidth(250);
			stage.setMinHeight(250);
			stage.setOnCloseRequest(evt -> {
				onCloseHandlers.forEach(h -> h.handle(evt));
			});
			stage.setTitle("Drive Analytics Dashboard App");
		}
		stage.show();
	}

	public void close() {
		PrintSensorListenerToConsole = false; // Wird wieder auf den Stanardwert zurückgesetzt.
		timer.stop();
		if (stage != null)
			stage.close();
	}

	public void addOnCloseEventHandler(EventHandler<WindowEvent> event) {
		onCloseHandlers.add(event);
	}

	public void removeOnCloseEventHandler(EventHandler<WindowEvent> event) {
		onCloseHandlers.remove(event);
	}
}
