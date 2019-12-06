package utilities;

import java.io.Serializable;
import java.util.Objects;
import jade.core.AID;


public class Message implements Serializable {

    private int timeToRespond;
    private AID senderID;
    private int priority;
    private int x;
    private int y;
    private int costPerKm;
    private int speed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getTimeToRespond() == message.getTimeToRespond() &&
                getPriority() == message.getPriority() &&
                getX() == message.getX() &&
                getY() == message.getY() &&
                getCostPerKm() == message.getCostPerKm() &&
                getSpeed() == message.getSpeed() &&
                Objects.equals(getSenderID(), message.getSenderID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeToRespond(), getSenderID(), getPriority(), getX(), getY(), getCostPerKm(), getSpeed());
    }

    public int getTimeToRespond() {
        return timeToRespond;
    }

    public void setTimeToRespond(int timeToRespond) {
        this.timeToRespond = timeToRespond;
    }

    public Message(int priority, int x ,int y, int costPerKm, int speed, AID senderID) {
        this.priority = priority;
        this.x = x;
        this.y = y;
        this.costPerKm = costPerKm;
        this.speed = speed;
        this.senderID = senderID;
    }

    public Message() {
        this.priority = 0;
        this.x = 0;
        this.y = 0;
        this.costPerKm = 0;
        this.speed = 0;
        this.senderID = null;
    }

    public AID getSenderID() { return senderID; }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCostPerKm() {
        return costPerKm;
    }

    public void setCostPerKm(int costPerKm) {
        this.costPerKm = costPerKm;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
