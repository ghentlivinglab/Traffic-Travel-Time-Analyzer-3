package be.ugent.tiwi;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Traject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jelle on 10.03.16.
 */
@Controller
@RequestMapping("/traject")
public class TrajectController {
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String wijzigTraject(@PathVariable("id") int id, ModelMap model)
    {
        DatabaseController databaseController = new DatabaseController();
        Traject traject = databaseController.haalTraject(id);
        model.addAttribute("traject",traject);
        return "traject/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public ModelAndView slaTrajectWijzigingenOp(@PathVariable("id") int id, @ModelAttribute Traject traject, Model model)
    {
        DatabaseController databaseController = new DatabaseController();
        databaseController.wijzigTraject(traject);
        ModelAndView modelAndView =  new ModelAndView("redirect:/");
        modelAndView.addObject("message" , "Wijzigen van "+traject.getNaam()+" geslaagd!");
        return modelAndView;
    }
}