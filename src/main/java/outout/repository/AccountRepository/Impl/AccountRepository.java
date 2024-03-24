package outout.repository.AccountRepository.Impl;
import org.springframework.stereotype.Component;
import outout.model.User;
import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;
import outout.repository.AccountRepository.IAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Component("accountRepository")
public class AccountRepository implements IAccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createAccount(User user) throws AccountRepositoryException {
        User retreivedUser = getAccount(user.getUsername());
        if (retreivedUser != null) {
            throw new AccountRepositoryException("User already exists");
        }
        entityManager.persist(user);

    }
    @Override
    public User getAccount(String username) {
        Query q = entityManager.createQuery("select u from User u where u.username = :username");
        q.setParameter("username", username);
        q.setMaxResults(1);
        List<User> uList = q.getResultList();
        return uList.isEmpty() ? null : uList.get(0);
    }
}
