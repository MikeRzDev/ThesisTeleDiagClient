package co.edu.unab.gti.thesistelediagclient.data.model;

/**
 * Created by user on 3/06/2016.
 */
public class RawData {

    private String value;
    private int sensorType;

    public RawData(String value, int sensorType) {
        this.value = value;
        this.sensorType = sensorType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }
}
