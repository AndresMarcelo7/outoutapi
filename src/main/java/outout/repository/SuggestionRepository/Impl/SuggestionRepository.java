package outout.repository.SuggestionRepository.Impl;

import org.springframework.stereotype.Component;
import outout.model.Suggestion;
import outout.repository.SuggestionRepository.ISuggestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.Date;
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
    public List<Suggestion> GetSuggestionsByUserAndDate(String username, java.util.Date date) {
        //convert to sqlDate
        Date sqlDate = new Date(date.getTime());
        Query suggestionsByUserQuery = entityManager.createQuery("select s from Suggestion s where s.suggestedDate = :suggestedDate and s.suggestedBy = :username");
        suggestionsByUserQuery.setParameter("suggestedDate", sqlDate);
        suggestionsByUserQuery.setParameter("username", username);
        return suggestionsByUserQuery.getResultList();
    }

    @Override
    public List<Suggestion> getSuggestionsByDate(java.util.Date date) {
        Date sqlDate = new Date(date.getTime());
        Query SuggestionsByDateQuery = entityManager.createQuery("select s from Suggestion s where s.suggestedDate = :suggestedDate");
        SuggestionsByDateQuery.setParameter("suggestedDate", sqlDate);
        return SuggestionsByDateQuery.getResultList();
    }
}
