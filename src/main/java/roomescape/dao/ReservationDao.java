package roomescape.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    public List<Reservation> selectAll() {
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

    public Optional<Reservation> selectBy(Long id) {
        try {
            Reservation reservation = jdbcTemplate.queryForObject("""
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
            return Optional.of(reservation);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }


}
