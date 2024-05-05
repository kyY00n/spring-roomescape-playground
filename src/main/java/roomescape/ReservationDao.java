package roomescape;

import java.util.List;
import java.util.Map;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    public static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            rs.getString("time")
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public void insert(Reservation reservation) {
        Map<String, String> reservationMap = Map.of("name", reservation.getName(), "date", reservation.getDate(), "time",
                reservation.getTime());
        Number key = simpleJdbcInsert.executeAndReturnKey(reservationMap);
        reservation.setId(key.longValue());
    }

    public void delete(Long id) {
        Reservation reservation = selectById(id);
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservation.getId());
    }

    public List<Reservation> selectAll() {
        return jdbcTemplate.query("SELECT * FROM reservation",
                RESERVATION_ROW_MAPPER);
    }

    public void update(Reservation reservation) {
        jdbcTemplate.update("UPDATE reservation SET name = ?, date = ?, time = ? WHERE id = ?",
                reservation.getName(), reservation.getDate(), reservation.getTime(), reservation.getId());
    }

    public Reservation selectById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM reservation WHERE id = ?",
                    RESERVATION_ROW_MAPPER, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException();
        }
    }


}
