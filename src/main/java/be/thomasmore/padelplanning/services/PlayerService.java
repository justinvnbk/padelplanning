package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerRepository playerRepository, JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return jdbcUserDetailsManager.userExists(email);
    }

    public void registerPlayer(Player player) {
        UserDetails newUser = User.builder()
                .username(player.getEmail())
                .password(passwordEncoder.encode(player.getPassword()))
                .disabled(true)
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(newUser);

        player.setApproved(false);
        playerRepository.save(player);
    }
}
