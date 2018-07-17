package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/7/16.
 */

public class Moment {

    public String momentID;
    public String description;

    public Moment(String momentID, String description) {
        this.momentID = momentID;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "momentID='" + momentID + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
