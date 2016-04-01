package be.ugent.tiwi;

import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import be.ugent.tiwi.domein.Meting;
import com.google.gson.Gson;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

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

    @RequestMapping(value = "/metingen/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonMetingen(@PathVariable("id") int id, ModelMap model)
    {
        MetingRepository mr = new MetingRepository();
        return new Gson().toJson( mr.getMetingenFromTrajectByProvider(id));
    }

    @RequestMapping(value = "/metingen/{id}/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonMetingenStartAndEndInterval(@PathVariable("id") int id, @PathVariable("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
                                              @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end, ModelMap model)
    {
        MetingRepository mr = new MetingRepository();
        return new Gson().toJson( mr.getMetingenFromTraject(id,start,end));
    }

    @RequestMapping(value = "/vertragingen/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonMetingenStartAndEndInterval(@PathVariable("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
                                              @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end, ModelMap model)
    {
        MetingRepository mr = new MetingRepository();
        return new Gson().toJson( mr.getVertragingen(start,end));
    }
}
