package be.thomasmore.padelplanning.controllers;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        List<Field> fields = fieldRepository.getAvailable();//Ik heb ook deze veranderd naar List en ook in de field repository vernaderd naar List -Gab
        List<Player> signedUpPlayers = playerRepository.getAll();
        Collections.shuffle(signedUpPlayers);//Deze toegevoegd voor de shuffle van de players

        List<Match> matches = new ArrayList<>();
        int playerIndex = 0; //om de volgende speler in de lijst te nemen
        for (int i = 0; i < matchAmount; i++) {
            Match match = new Match();

            List<Team> teams = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                Team team = new Team();
                Collection<Player> players = new ArrayList<>();
//                if(playerIndex >= (matchAmount/fields.size())*4){
//                    playerIndex = 0;
//                }
                players.add(signedUpPlayers.get(playerIndex % signedUpPlayers.size()));//Ik heb deze veranderd voor een betere shuffle
                playerIndex++;
                players.add(signedUpPlayers.get(playerIndex % signedUpPlayers.size()));//Deze ook is veranderd -Gab
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

        //Ik heb deze stuk toegevoegd om de patches in de fields te bewaren anders waren er geen matches per field
        int matchesPerField = matchAmount / fields.size();
        for (int i = 0; i < fields.size(); i++) {
            List<Match> fieldMatches = new ArrayList<>(matches.subList(i * matchesPerField, (i + 1) * matchesPerField));
            fields.get(i).setMatches(fieldMatches);
            fieldRepository.save(fields.get(i));
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
