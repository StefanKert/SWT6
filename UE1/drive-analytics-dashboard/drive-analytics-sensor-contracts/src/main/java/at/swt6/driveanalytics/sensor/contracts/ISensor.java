package at.swt6.driveanalytics.sensor.contracts;


public interface ISensor {
	
    public enum SensorDataFormat {
      PERCENT,
      ABSOLUTE_VALUE_LONG
    }

    String getSensorId();
    
    String getSensorName();
    
    byte[] getData();

    SensorDataFormat getDataFormat();
}