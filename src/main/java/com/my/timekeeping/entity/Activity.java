package com.my.timekeeping.entity;


/**
 * This entity class for activity with Builder class for builder pattern
 *
 * @author Andrey
 * @version 1.0
 */
public class Activity {
    private Long id;
    private String name;
    private String category;
    private State state;

    private Activity() {
    }

    public Long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public static Builder newBuilder() {
        return new Activity().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(Long id) {
            Activity.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Activity.this.name = name;
            return this;
        }

        public Builder setCategory(String category) {
            Activity.this.category = category;
            return this;
        }

        public Builder setState(State state) {
            Activity.this.state = state;
            return this;
        }

        public Activity build() {
            return Activity.this;
        }
    }
}
