package roomescape;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Test
    void insert() {
        //given
        Reservation reservation = new Reservation("가영", "2024-04-28", "12:00");

        //when
        reservationDao.insert(reservation);

        //then
        assertThat(reservation.getId()).isNotNull();
    }

}
