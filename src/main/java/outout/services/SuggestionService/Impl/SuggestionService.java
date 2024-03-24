package outout.services.SuggestionService.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import outout.model.Suggestion;
import outout.repository.SuggestionRepository.ISuggestionRepository;
import outout.services.SuggestionService.Exception.SuggestionException;
import outout.services.SuggestionService.ISuggestionService;
import outout.view.RestaurantSuggestion;
import outout.view.RestaurantSuggestions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("suggestionService")
public class SuggestionService implements ISuggestionService {

    @Autowired
    private ISuggestionRepository suggestionRepository;

    @Override
    public void suggestRestaurant(RestaurantSuggestion suggestion, String username, Date date) throws SuggestionException {
        List<Suggestion> restaurantSuggestions = suggestionRepository.getSuggestionsByDate(date);
        List<Suggestion> userSuggestions = suggestionRepository.GetSuggestionsByUserAndDate(username, date);
        if(restaurantSuggestions.isEmpty() && userSuggestions.size() < 2) {
            Suggestion newSuggestion = new Suggestion();
            newSuggestion.setSuggestedBy(username);
            newSuggestion.setSuggestion(suggestion.getRestaurant());
            newSuggestion.setSuggestedDate(new java.sql.Date(date.getTime()));
            suggestionRepository.createSuggestion(newSuggestion);
        }
        else{
            throw new SuggestionException("Suggestions requirements not met - 2 suggestion per user and 1 suggestion per restaurant per day.");
        }
    }

    @Override
    public RestaurantSuggestions getSuggestionsByDate(Date date) {
        List<Suggestion> retrievedSuggestions = suggestionRepository.getSuggestionsByDate(date);
        List<RestaurantSuggestion> suggestions = new ArrayList<>();
        retrievedSuggestions.forEach(value -> {
            RestaurantSuggestion restaurantSuggestion = new RestaurantSuggestion();
            restaurantSuggestion.setRestaurant(value.getSuggestion());
            suggestions.add(restaurantSuggestion);
        });

        return new RestaurantSuggestions(suggestions);



    }
}
