package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.MatchRepository;
import be.thomasmore.padelplanning.repositories.PlayerRepository;
import be.thomasmore.padelplanning.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CreatePadelDayService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public CreatePadelDayService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public PadelDay newPadelDay(int matchCountPerTimeSlot, List<Field> fields, LocalTime startTime, int matchDurationInMinutes, List<Player> signedUpPlayers) {
        PadelDay padelDay = new PadelDay();
        padelDay.setDate(LocalDateTime.now());
        padelDay.setMatches(newMatches(matchCountPerTimeSlot, fields.size(), startTime, matchDurationInMinutes, signedUpPlayers));
        padelDay.setNumberOfMatches(padelDay.getMatches().size());
        padelDay.setFields(fields);

        return padelDay;
    }

    private List<Match> newMatches(int matchCountPerTimeSlot, int availableFieldCount, LocalTime startTime, int matchDurationInMinutes, List<Player> signedUpPlayers) {
        List<Match> matches = new ArrayList<>();
        startTime = startTime.minusMinutes(matchDurationInMinutes);
        int playerIndex = 0; //om de volgende speler in de lijst te nemen, zorgt nu dat alle spelers in volgorde worden ingepland.
        for (int i = 0; i < matchCountPerTimeSlot*availableFieldCount; i++) {
            Match match = new Match();

            match.setTeams(newTeams(playerIndex, signedUpPlayers));

            double team1PRanking = match.getTeams().get(0).getAveragePRanking();
            double team2PRanking = match.getTeams().get(1).getAveragePRanking();
            match.setpRankingDifference(team1PRanking - team2PRanking);

            if(i%(matchCountPerTimeSlot) == 0){
                startTime = startTime.plusMinutes(matchDurationInMinutes);
            }
            match.setTimeSlot(startTime);
            matchRepository.save(match);
            matches.add(match);
        }
        return matches;
    }

    private List<Team> newTeams(int playerIndex, List<Player> signedUpPlayers) {
        List<Team> teams = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            Team team = new Team();
            Collection<Player> players = new ArrayList<>();
            if(playerIndex >= signedUpPlayers.size()){
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
        return teams;
    }
}
