package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingCRUD;
import be.ugent.tiwi.dal.TrajectCRUD;
import be.ugent.tiwi.domein.Meting;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/json")
public class JsonController {
    @RequestMapping(value = "/trajecten", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonTrajecten(ModelMap model)
    {
        TrajectCRUD tc = new TrajectCRUD();

        return new Gson().toJson(tc.getTrajecten());
    }
}
