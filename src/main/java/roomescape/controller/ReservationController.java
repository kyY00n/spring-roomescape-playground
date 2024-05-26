package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.NotFoundReservationException;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final TimeDao timeDao;
    private final ReservationDao reservationDao;

    public ReservationController(final ReservationDao reservationDao, final TimeDao timeDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<Reservation> readAllReservations() {
        return reservationDao.selectAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest request) {
        Time time = timeDao.selectBy(request.time()).orElseThrow(IllegalArgumentException::new);
        Reservation newReservation = new Reservation(request.name(), request.date(), time);
        reservationDao.insert(newReservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.selectBy(id).orElseThrow(() -> new NotFoundReservationException(id));
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }

}
