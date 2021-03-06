package at.swt6.driveanalytics.sensor.distance;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

import org.osgi.util.tracker.ServiceTracker;

import at.swt6.driveanalytics.sensor.contracts.*;
import at.swt6.driveanalytics.utils.Timer;

public class DistanceSensor implements ISensor {
	private ServiceTracker<ISensorListener, ISensorListener> listenerServiceTracker;
	private Timer timer;

	public DistanceSensor(){}
	
	public DistanceSensor(ServiceTracker<ISensorListener, ISensorListener> listenerTracker) {
		this.listenerServiceTracker = listenerTracker;
		timer = new Timer();
		timer.setInterval(1000);
		timer.addTimerListener((x) -> {
			valueChanged();
		});
		timer.start();
	}

	@Override
	public byte[] getData() {
		long random = ThreadLocalRandom.current().nextLong(1, 10000);
		return ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(random).array();
	}

	@Override
	public SensorDataFormat getDataFormat() {
		return SensorDataFormat.ABSOLUTE_VALUE_LONG;
	}

	@Override
	public String getSensorId() {
		return "a0ce5db6-3c96-4162-8e34-49d157375eda";
	}

	@Override
	public String getSensorName() {
		return "Distance Sensor";
	}
	
	private void valueChanged() {
		Object[] listeners = listenerServiceTracker.getServices();
		if (listeners == null)
			return;
		for (Object listener : listeners) {
			if (listener instanceof ISensorListener) {
				((ISensorListener) listener).valueChanged(this);
			}
		}
	}
}
