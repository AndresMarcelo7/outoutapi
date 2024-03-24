package outout.repository.SuggestionRepository;


import outout.model.Suggestion;

import java.util.Date;
import java.util.List;

public interface ISuggestionRepository {

    void createSuggestion(Suggestion suggestion);


    List<Suggestion> GetSuggestionsByUserAndDate(String username, Date date);


    List<Suggestion> getSuggestionsByDate(Date date);
}
