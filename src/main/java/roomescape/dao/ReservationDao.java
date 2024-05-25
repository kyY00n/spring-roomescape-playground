package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDao {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            new Time(rs.getLong("time_id"), rs.getString("time_value"))
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public ReservationDao(JdbcTemplate jdbcTemplate, TimeDao timeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public void insert(Reservation reservation) {
        Map<String, String> reservationMap = Map.of("name", reservation.getName(), "date", reservation.getDate(), "time_id",
                reservation.getTime().getId().toString());
        Number key = simpleJdbcInsert.executeAndReturnKey(reservationMap);
        reservation.setId(key.longValue());
    }

    public void delete(Long id) {
        Reservation reservation = findById(id);
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservation.getId());
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                                  SELECT
                                  r.id as reservation_id,
                                  r.name,
                                  r.date,
                                  t.id as time_id,
                                  t.time as time_value
                              FROM reservation as r inner join time as t on r.time_id = t.id
                        """,
                RESERVATION_ROW_MAPPER);
    }

    public void update(Reservation reservation) {
        jdbcTemplate.update("UPDATE reservation SET name = ?, date = ?, time = ? WHERE id = ?",
                reservation.getName(), reservation.getDate(), reservation.getTime(), reservation.getId());
    }

    public Reservation findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("""
                                      SELECT
                                      r.id as reservation_id,
                                      r.name,
                                      r.date,
                                      t.id as time_id,
                                      t.time as time_value
                                  FROM reservation as r inner join time as t on r.time_id = t.id
                                  WHERE r.id = ?
                            """,
                    RESERVATION_ROW_MAPPER, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException();
        }
    }


}
