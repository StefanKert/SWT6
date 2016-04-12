package at.swt6.driveanalytics.utils;

import java.util.EventListener;

public interface TimerListener extends EventListener {

	public void expired(TimerEvent e);	
}
