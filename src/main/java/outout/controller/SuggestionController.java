package outout.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import outout.model.Suggestion;
import outout.services.SuggestionService.ISuggestionService;
import outout.view.RestaurantSuggestion;
import outout.view.RestaurantSuggestions;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/suggestion")
public class SuggestionController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ISuggestionService suggestionService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<Void> suggest(@RequestBody RestaurantSuggestion r, Principal p) {
        try {
            suggestionService.suggestRestaurant(r, p.getName(), DateTime.now().toDate());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<RestaurantSuggestions> suggestionsForToday() {
        RestaurantSuggestions todaySuggestions = suggestionService.getSuggestionsByDate(DateTime.now().toDate());
        return new ResponseEntity<>(todaySuggestions, HttpStatus.OK);
    }


}
