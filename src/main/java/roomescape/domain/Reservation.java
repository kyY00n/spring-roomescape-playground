package roomescape.domain;

import static org.springframework.util.ObjectUtils.isEmpty;

public class Reservation {

    private Long id;
    private final String name;
    private final String date;
    private final Time time;

    public Reservation(final Long id, final String name, final String date, final Time time) {
        this.id = id;
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final String date, final Time time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final String date, final Time time) {
        if (isEmpty(name) || isEmpty(date) || isEmpty(time)) {
            throw new IllegalArgumentException("예약 정보를 모두 입력해주세요.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
