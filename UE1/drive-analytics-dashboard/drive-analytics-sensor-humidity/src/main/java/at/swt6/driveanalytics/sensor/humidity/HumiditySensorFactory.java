package at.swt6.driveanalytics.sensor.humidity;

import org.osgi.util.tracker.ServiceTracker;

import at.swt6.driveanalytics.sensor.contracts.ISensor;
import at.swt6.driveanalytics.sensor.contracts.ISensorFactory;
import at.swt6.driveanalytics.sensor.contracts.ISensorListener;

public class HumiditySensorFactory implements ISensorFactory {
	private ServiceTracker<ISensorListener, ISensorListener> listenerServiceTracker;

	public HumiditySensorFactory(ServiceTracker<ISensorListener, ISensorListener> listenerServiceTracker){
		this.listenerServiceTracker = listenerServiceTracker;
	}
	
	@Override
	public ISensor createSensor() {
		return new HumiditySensor(listenerServiceTracker);
	}
}
