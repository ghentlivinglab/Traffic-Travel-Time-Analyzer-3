package be.ugent.tiwi;

import be.ugent.tiwi.controller.ScheduleController;
import be.ugent.tiwi.dal.MetingRepository;
import be.ugent.tiwi.dal.ProviderRepository;
import be.ugent.tiwi.dal.TrajectRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/json")
public class JsonController {

    private ServletContext context;

    @Autowired
    public JsonController(ServletContext context){
        this.context = context;
    }

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
        return new Gson().toJson( mr.getMetingenByProvider(id));
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

    @RequestMapping(value = "/vertragingen/{id}/{start}/{end}", method = RequestMethod.GET)
    public @ResponseBody
    String getJsonMetingenProviderStartAndEndInterval(@PathVariable("id") int id, @PathVariable("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
                                                      @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end, ModelMap model)
    {
        MetingRepository mr = new MetingRepository();
        return new Gson().toJson( mr.getVertragingen(new ProviderRepository().getProvider(id),start,end));
    }

    @RequestMapping(value = "/admin/status", method = RequestMethod.GET)
    public @ResponseBody
    String getServerStatus(ModelMap model)
    {
        ScheduleController scheduler = (ScheduleController) context.getAttribute("scraperScheduler");
        return new Gson().toJson(scheduler.isStarted());
    }

    @RequestMapping(value = "/admin/status", method = RequestMethod.POST)
    public @ResponseBody
    String setServerStatus(@RequestParam(value="start") boolean start, ModelMap model) {
        ScheduleController scheduler = (ScheduleController) context.getAttribute("scraperScheduler");
        if(start)
            try {
                scheduler.start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else
            scheduler.stop();
        return new Gson().toJson(scheduler.isStarted());
    }
}
