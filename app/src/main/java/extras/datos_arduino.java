package extras;

public class datos_arduino {

    String ip="";
    long timestamp=0;

    public datos_arduino(){
    }

    public datos_arduino(String ip, long timestamp) {

        this.ip = ip;
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
