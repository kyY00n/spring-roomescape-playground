package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RoomescapeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String admin() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

}
