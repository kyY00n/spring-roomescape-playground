package roomescape;

public class Time {

    private Long id;
    private String time;

    public Time(final Long id, final String time) {
        this.id = id;
        this.time = time;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }

    public String getTime() {
        return time;
    }

}
