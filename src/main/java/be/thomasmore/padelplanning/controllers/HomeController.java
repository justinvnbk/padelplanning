package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {
    private final FieldRepository fieldRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final PadelDayRepository padelDayRepository;
    private final TeamRepository teamRepository;

    public HomeController(FieldRepository fieldRepository, PlayerRepository playerRepository, MatchRepository matchRepository, PadelDayRepository padelDayRepository, TeamRepository teamRepository) {
        this.fieldRepository = fieldRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.padelDayRepository = padelDayRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping({"/","/home"})
    public String home(Model model){
        int matchAmount = 6;
        LocalTime startTime = LocalTime.of(14,0,0);
        int matchDurationInMinutes = 40;
        Collection<Field> fields = fieldRepository.getAvailable();
        List<Player> signedUpPlayers = playerRepository.getAll();

        Collection<Match> matches = new ArrayList<>();
        int playerIndex = 0; //om de volgende speler in de lijst te nemen
        for (int i = 0; i < matchAmount; i++) {
            Match match = new Match();

            List<Team> teams = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                Team team = new Team();
                Collection<Player> players = new ArrayList<>();
                if(playerIndex >= (matchAmount/fields.size())*4){
                    playerIndex = 0;
                }
                players.add(signedUpPlayers.get(playerIndex));
                playerIndex++;
                players.add(signedUpPlayers.get(playerIndex));
                playerIndex++;
                team.setAveragePRanking(players.stream().mapToDouble(Player::getpRanking).average().getAsDouble());
                team.setPlayers(players);
                teamRepository.save(team);
                teams.add(team);
            }
            match.setTeams(teams);

            double team1PRanking = teams.get(0).getAveragePRanking();
            double team2PRanking = teams.get(1).getAveragePRanking();
            match.setpRankingDifference(team1PRanking - team2PRanking);

            if(i%4 == 0){//4 = matchAmount/availableFields
                startTime = startTime.plusMinutes(matchDurationInMinutes);
            }
            match.setTimeSlot(startTime);
            matchRepository.save(match);
            matches.add(match);
        }

        PadelDay padelDay = new PadelDay();
        padelDay.setDate(LocalDateTime.now());
        padelDay.setNumberOfMatches(matchAmount);
        padelDay.setMatches(matches);
        padelDay.setFields(fields);

        padelDayRepository.save(padelDay);
        model.addAttribute("padelDay", padelDay);
        return "home";
    }
}
