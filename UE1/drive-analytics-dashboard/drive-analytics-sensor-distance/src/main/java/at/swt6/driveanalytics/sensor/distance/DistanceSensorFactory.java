package at.swt6.driveanalytics.sensor.distance;

import org.osgi.util.tracker.ServiceTracker;

import at.swt6.driveanalytics.sensor.contracts.ISensor;
import at.swt6.driveanalytics.sensor.contracts.ISensorFactory;
import at.swt6.driveanalytics.sensor.contracts.ISensorListener;

public class DistanceSensorFactory implements ISensorFactory {
	private ServiceTracker<ISensorListener, ISensorListener> listenerServiceTracker;

	public DistanceSensorFactory(ServiceTracker<ISensorListener, ISensorListener> listenerServiceTracker){
		this.listenerServiceTracker = listenerServiceTracker;
	}
	
	@Override
	public ISensor createSensor() {
		return new DistanceSensor(listenerServiceTracker);
	}
}
