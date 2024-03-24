package outout.repository.SuggestionRepository.Impl;

import org.springframework.stereotype.Component;
import outout.model.Suggestion;
import outout.repository.SuggestionRepository.ISuggestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;

@Component("suggestionRepository")
public class SuggestionRepository implements ISuggestionRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void createSuggestion(Suggestion suggestion) {
        entityManager.persist(suggestion); 
    }

    @Override
    public List<Suggestion> GetSuggestionsByUserAndDate(String username, Date date) {
        Query suggestionsByUserQuery = entityManager.createQuery("select s from Suggestion s where trunc(s.suggestedDate) = trunc(:suggestedDate) " +
                "and s.suggestedBy = :username");
        suggestionsByUserQuery.setParameter("suggestedDate", date);
        suggestionsByUserQuery.setParameter("username", username);
        return suggestionsByUserQuery.getResultList();
    }

    @Override
    public List<Suggestion> getSuggestionsByDate(Date date) {
        Query SuggestionsByDateQuery = entityManager.createQuery("select s from Suggestion s where trunc(s.suggestedDate) = trunc(:suggestedDate)");
        SuggestionsByDateQuery.setParameter("suggestedDate", date);
        return SuggestionsByDateQuery.getResultList();
    }
}
