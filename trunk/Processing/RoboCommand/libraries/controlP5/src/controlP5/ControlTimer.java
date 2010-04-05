package controlP5;

/**
 * control timer is a timer that can be used e.g. as a stop watch.
 * 
 * @example ControlP5timer
 */
public class ControlTimer {

	long millisOffset;

	int ms, s, m, h, d;

	float _mySpeed = 1;

	int current, previous;

	/**
	 * create a new control timer, a timer that counts up in time.
	 */
	public ControlTimer() {
		reset();
	}

	/**
	 * return a string representation of the current status of the timer.
	 * 
	 * @return String
	 */
	public String toString() {
		update();
		return (((h < 10) ? "0" + h : "" + h) + " : " + ((m < 10) ? "0" + m : "" + m) + " : " + ((s < 10) ? "0" + s : ""
		  + s) // + " : " +
		// ((ms<100) ? "0" + ms: "" +ms)
		);
	}

	/**
	 * @invisible
	 */
	public void update() {
		current = (int) time();
		if (current > previous + 10) {
			ms = (int) (current * _mySpeed);
			s = (int) (((current * _mySpeed) / 1000));
			m = (int) (s / 60);
			h = (int) (m / 60);
			d = (int) (h / 24);
			ms %= 1000;
			s %= 60;
			m %= 60;
			h %= 24;
			previous = current;
		}

	}

	/**
	 * get the time in milliseconds since the timer was started.
	 * 
	 * @return long
	 */
	public long time() {
		return (System.currentTimeMillis() - millisOffset);
	}

	/**
	 * reset the timer.
	 */
	public void reset() {
		millisOffset = System.currentTimeMillis();
		current = previous = 0;
		s = 0; // Values from 0 - 59
		m = 0; // Values from 0 - 59
		h = 0; // Values from 0 - 23
		update();
	}

	/**
	 * set the speed of time, for slow motion or high speed.
	 * 
	 * @param theSpeed int
	 */
	public void setSpeedOfTime(float theSpeed) {
		_mySpeed = theSpeed;
		update();
	}

	/**
	 * Get the milliseconds of the timer.
	 */
	public int millis() {
		return ms;
	}

	/**
	 * Seconds position of the timer.
	 */
	public int second() {
		return s;
	}

	/**
	 * Minutes position of the timer.
	 */
	public int minute() {
		return m;
	}

	/**
	 * Hour position of the timer in international format (0-23).
	 */
	public int hour() {
		return h;
	}

	/**
	 * day position of the timer.
	 */
	public int day() {
		return d;
	}

}
