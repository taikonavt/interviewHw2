import java.time.*;
import java.util.Calendar;
import java.util.TimeZone;

public class SensorTempAdapter implements MeteoSensor {

    private SensorTemperature sensorTemperature;

    SensorTempAdapter(SensorTemperature sensorTemperature){
        this.sensorTemperature = sensorTemperature;
    }

    @Override
    public int getId() {
        return sensorTemperature.identifier();
    }

    @Override
    public Float getTemperature() {
        return (float) sensorTemperature.temperature();
    }

    @Override
    public Float getHumidity() {
        return null;
    }

    @Override
    public Float getPressure() {
        return null;
    }

    @Override
    public LocalDateTime getDateTime() {
        int HOUR_IN_DAY = 24;
        int MINUTES_IN_HOUR = 60;
        int SECONDS_IN_MINUTE = 60;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, sensorTemperature.year());
        calendar.set(Calendar.DAY_OF_YEAR, sensorTemperature.day());
        int hourOfDay = sensorTemperature.second() / (MINUTES_IN_HOUR * SECONDS_IN_MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        if (hourOfDay == 0){
            hourOfDay = 24;
        }
        int minuteOfHour = sensorTemperature.second() % (hourOfDay * MINUTES_IN_HOUR) / SECONDS_IN_MINUTE;
        calendar.set(Calendar.MINUTE, minuteOfHour);
        int secondOfMinute = sensorTemperature.second() % (hourOfDay * MINUTES_IN_HOUR) % SECONDS_IN_MINUTE;
        calendar.set(Calendar.SECOND, secondOfMinute);
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
