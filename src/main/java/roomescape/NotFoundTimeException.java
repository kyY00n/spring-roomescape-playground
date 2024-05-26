package roomescape;

public class NotFoundTimeException extends RuntimeException {

    public NotFoundTimeException(Long id) {
        super("해당 id의 Time 을 찾을 수 없습니다. (id: " + id + ")");
    }

}
