package at.swt6.driveanalytics.dashboard;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import at.swt6.driveanalytics.sensor.contracts.ISensorFactory;
import at.swt6.driveanalytics.sensor.contracts.ISensorListener;

/**
 * Die Activator Klasse für das Dashboard
 * @author Stefan
 *
 */
public class DashboardActivator implements BundleActivator {
	private DashboardWindow dashboardWindow;
	private ServiceTracker<ISensorFactory, ISensorFactory> sensorTracker;

	/**
	 * Starten und initialisieren der Tracker. 
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(ISensorListener.class, new SensorListener(), null);
		JavaFxUtils.initJavaFx();
		dashboardWindow = new DashboardWindow();
		JavaFxUtils.runAndWait(() -> startUI(context));

		sensorTracker = new ServiceTracker<>(context, ISensorFactory.class, new SensorTrackerCustomizer(context, dashboardWindow));
		sensorTracker.open();
	}

	/**
	 * Stoppen des Bundles.
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		JavaFxUtils.runAndWait(() -> stopUI(context));
	}

	/**
	 * Öffnen der UI und registrieren der Events
	 * @param context
	 */
	private void startUI(BundleContext context) {
		dashboardWindow.show();
		dashboardWindow.addOnCloseEventHandler(evt -> {
			try {
				context.getBundle().stop();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	/**
	 * Schließen der UI und beenden der relevanten Tracker
	 * @param context
	 */
	private void stopUI(BundleContext context) {
		dashboardWindow.close();
		sensorTracker.close();
	}
}
