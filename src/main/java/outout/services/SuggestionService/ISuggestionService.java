package outout.services.SuggestionService;

import outout.model.Suggestion;
import outout.services.SuggestionService.Exception.SuggestionException;
import outout.view.RestaurantSuggestion;
import outout.view.RestaurantSuggestions;

import java.util.Date;
import java.util.List;

public interface ISuggestionService {

    public void suggestRestaurant(RestaurantSuggestion suggestion,String username ,Date date) throws SuggestionException;

    public RestaurantSuggestions getSuggestionsByDate(Date date);
}
