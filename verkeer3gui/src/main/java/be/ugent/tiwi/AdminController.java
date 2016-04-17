package be.ugent.tiwi;

import be.ugent.tiwi.dal.TrajectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
