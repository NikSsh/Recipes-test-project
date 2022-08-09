package recipes.model.businessLayer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.persistenceLayer.UsersRepository;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of the UserService interface,
 * It is responsible for processing of actions related to User model
 */
@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantLock.readLock();
    private final Lock writeLock = reentrantLock.writeLock();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * @param user represents object of the User class that is used to be checked and saved in DB
     * @throws ResponseStatusException with parameter HttpStatus.BAD_REQUEST if username already exists
     */
    public void register (User user) {
        synchronized (readLock) {
            if (usersRepository.existsById(user.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        synchronized (writeLock) {
            usersRepository.save(user);
        }
    }
}
