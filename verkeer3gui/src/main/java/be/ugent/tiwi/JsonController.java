package be.ugent.tiwi;

import be.ugent.tiwi.dal.TrajectRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/json")
public class JsonController {
    @RequestMapping(value = "/trajecten/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonTrajecten(@PathVariable("id") int id, ModelMap model)
    {
        TrajectRepository tr = new TrajectRepository();

        return new Gson().toJson(tr.getWaypoints(id));
    }
}
