package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updatePlayerProfile(Player updatedPlayer) {
        playerRepository.save(updatedPlayer);
    }

    public void registerPlayer(Player player) {
        UserDetails newUser = User.builder()
                .username(player.getEmail())
                .password(passwordEncoder.encode(player.getPassword()))
                .disabled(true)
                .authorities("USER")
                .build();
        jdbcUserDetailsManager.createUser(newUser);

        player.setApproved(false);
        playerRepository.save(player);
    }

    public void approvePlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow();
        player.setApproved(true);
        playerRepository.save(player);
        jdbcUserDetailsManager.updateUser(
                User.builder()
                        .username(player.getEmail())
                        .password(getUserPassword(player.getEmail()))
                        .disabled(false)
                        .authorities("USER")
                        .build()
        );
    }

    public void rejectPlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow();
        String email = player.getEmail();
        playerRepository.delete(player);
        jdbcUserDetailsManager.deleteUser(email);
    }

    public void removePlayer(Integer id) {
        rejectPlayer(id);
    }

    private String getUserPassword(String email) {
        return jdbcUserDetailsManager.loadUserByUsername(email).getPassword();
    }

    public void updatePlayerEmail(String oldEmail, String newEmail, String loggedInAdminEmail) {
        if (oldEmail.equals(newEmail)) return;

        if (oldEmail.equals(loggedInAdminEmail)) {
            throw new IllegalStateException("Je kan je eigen e-mailadres niet aanpassen als admin.");
        }

        String encodedPassword = getUserPassword(oldEmail);

        jdbcUserDetailsManager.deleteUser(oldEmail);

        jdbcUserDetailsManager.createUser(
                User.builder()
                        .username(newEmail)
                        .password(encodedPassword)
                        .disabled(false)
                        .authorities("USER")
                        .build()
        );
    }

    public List<Player> getPaidPlayers(PadelDay padelDay) {
        return padelDay.getSignedUpPlayers().stream()
                .filter(player -> player.getPayedPadelDays().contains(padelDay))
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }

    public List<Player> getUnpaidPlayers(PadelDay padelDay) {
        return padelDay.getSignedUpPlayers().stream()
                .filter(player -> !player.getPayedPadelDays().contains(padelDay))
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }
}
