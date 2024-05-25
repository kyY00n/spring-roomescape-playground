package roomescape.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;

@Component
public class TimeDao {

    public static final RowMapper<Time> TIME_ROW_MAPPER = (rs, rowNum) -> new Time(rs.getLong("id"),
            rs.getString("time"));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Time addTime(final Time time) {
        Long id = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(time)).longValue();
        time.setId(id);
        return time;
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM time", TIME_ROW_MAPPER);
    }

    public Optional<Time> findById(final Long id) {
        try {
            Time time = jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?", TIME_ROW_MAPPER, id);
            return Optional.of(time);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(final Long id) {
        findById(id).orElseThrow(IllegalArgumentException::new);
        jdbcTemplate.update("DELETE from time WHERE id = ?", id);
    }

}
