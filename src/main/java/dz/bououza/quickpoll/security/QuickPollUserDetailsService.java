package dz.bououza.quickpoll.security;

import dz.bououza.quickpoll.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class QuickPollUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public QuickPollUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format("User with username %s doesn't exist",username)));
    }
}
