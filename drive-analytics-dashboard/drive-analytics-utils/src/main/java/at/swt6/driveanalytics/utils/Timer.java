package at.swt6.driveanalytics.utils;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Timer {
	private AtomicInteger interval = new AtomicInteger(1000);
	private AtomicInteger tickCount = new AtomicInteger(0);
	private AtomicBoolean stopTimer = new AtomicBoolean(false);
	
	private AtomicReference<Thread> tickerThread = new AtomicReference<>(null);
	
	private Vector<TimerListener> listeners = new Vector<>();

	public Boolean isRunning() {
		return tickerThread.get() != null;
	}

	public void reset() {
		if (isRunning()) {
			throw new IllegalStateException("can't reset timer, already running");
		}
		tickCount.set(0);
	}

	protected void fireEvent(TimerEvent te) {
		@SuppressWarnings("unchecked")
		Vector<TimerListener> listenerClone = (Vector<TimerListener>) listeners.clone();
		for (TimerListener l : listenerClone) {
			l.expired(te);
		}
	}

	public void start() {
		if (isRunning())
			throw new IllegalStateException("Can't start because timer is already running");

		this.tickerThread.set(new Thread(() -> {
			int i = 0;
			while (!stopTimer.get()) {
				try {
					Thread.sleep(this.interval.get());
				} catch (Exception e) {
				}
				if (!stopTimer.get()) {
					this.tickCount.set(++i);
					fireEvent(new TimerEvent(Timer.this));
				}
			}
			stopTimer.set(false);
			this.tickerThread.set(null);
		}));
		tickerThread.get().start();
	}

	public void stop() {
		stopTimer.set(true);
	}

	public void setInterval(int interval) {
		int oldValue = this.interval.get();
		if (oldValue != interval) {
			this.interval.set(interval);
		}
	}

	public void addTimerListener(TimerListener listener) {
		listeners.add(listener);
	}

	public void removeTimerListener(TimerListener listener) {
		listeners.remove(listener);
	}
}