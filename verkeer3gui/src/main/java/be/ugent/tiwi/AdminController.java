package be.ugent.tiwi;

import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Waypoint;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model) {
        TrajectRepository trajectRepository = new TrajectRepository();
        model.addAttribute("trajecten", trajectRepository.getTrajectenMetWayPoints());
        return "admin/index";
    }
}
