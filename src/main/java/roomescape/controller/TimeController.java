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
import roomescape.NotFoundTimeException;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeDao timeDao;

    public TimeController(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping
    public ResponseEntity<List<Time>> readTimes() {
        return ResponseEntity.ok(timeDao.selectAll());
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        Time createdTime = timeDao.insert(time);
        return ResponseEntity.created(URI.create("/times/" + createdTime.getId())).body(createdTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTIme(@PathVariable Long id) {
        timeDao.selectBy(id).orElseThrow(() -> new NotFoundTimeException(id));
        timeDao.deleteBy(id);
        return ResponseEntity.noContent().build();
    }

}
