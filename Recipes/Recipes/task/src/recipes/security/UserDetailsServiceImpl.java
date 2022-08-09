package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.model.businessLayer.user.User;
import recipes.model.persistenceLayer.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    /**
     * @param username the username identifying the user whose data is required.
     * @return UserDetails that is implemented by UserDetailsImpl and will represent the user found in DB
     * @throws UsernameNotFoundException with parameter String msg if the user with providen username was not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Not found " + username));

        return new UserDetailsImpl(user);
    }
}
