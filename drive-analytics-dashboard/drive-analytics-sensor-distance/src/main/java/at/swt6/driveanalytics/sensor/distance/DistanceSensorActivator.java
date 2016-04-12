package at.swt6.driveanalytics.sensor.distance;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import at.swt6.driveanalytics.sensor.contracts.ISensorFactory;
import at.swt6.driveanalytics.sensor.contracts.ISensorListener;

public class DistanceSensorActivator implements BundleActivator {
	 private ServiceTracker<ISensorListener,ISensorListener> listenerServiceTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {		
		listenerServiceTracker = new ServiceTracker<ISensorListener, ISensorListener>(context,ISensorListener.class,null);
		listenerServiceTracker.open();
		context.registerService(ISensorFactory.class, new DistanceSensorFactory(listenerServiceTracker), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		listenerServiceTracker.close();
	}
}
