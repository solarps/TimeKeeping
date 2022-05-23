package com.my.timekeeping.dto;

import com.my.timekeeping.entity.State;

import java.io.Serializable;

/**
 * This class for activity dto.
 * Have inner class {@link Time} to implement spent time operrations.
 *
 * @author Andrey
 * @version 1.0
 */
public class ActivityDTO implements Serializable {
    private Long id;
    private String name;
    private String category;
    private State state;
    private final Time spentTime = new Time();


    public ActivityDTO() {
    }

    public ActivityDTO(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setSpentTime(String string) {
        this.spentTime.setTime(string);
    }

    public Time getSpentTime() {
        return this.spentTime;
    }

    public static class Time {

        private Short hours;
        private Byte minutes;
        private Byte seconds;
        private String time;

        private Time() {
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            if (time != null) {
                this.time = time;
                String[] times = time.split(":");
                this.hours = Short.parseShort(times[0]);
                this.minutes = Byte.parseByte(times[1]);
                this.seconds = Byte.parseByte(times[2]);
            }
        }

        public short getHours() {
            return hours;
        }

        public byte getMinutes() {
            return minutes;
        }

        public byte getSeconds() {
            return seconds;
        }

    }
}
