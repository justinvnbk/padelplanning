package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.MatchRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
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
    private final PadelDayRepository padelDayRepository;

    public CreatePadelDayService(MatchRepository matchRepository, TeamRepository teamRepository, PadelDayRepository padelDayRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.padelDayRepository = padelDayRepository;
    }

    //This Service is used to create a new PadelDay object, all related objects (matches and teams) will also
    //be created and added to the database.
    public PadelDay newPadelDay(int timeSlots, List<Field> availableFields, LocalTime startTime, int matchDurationInMinutes, List<Player> signedUpPlayers) {
        PadelDay padelDay = new PadelDay();
        padelDay.setDate(LocalDateTime.now());
        padelDay.setMatches(newMatches(timeSlots, availableFields.size(), startTime, matchDurationInMinutes, signedUpPlayers));
        padelDay.setNumberOfMatches(padelDay.getMatches().size());
        padelDay.setFields(availableFields);
        padelDayRepository.save(padelDay);
        return padelDay;
    }

    private List<Match> newMatches(int timeSlots, int availableFieldCount, LocalTime startTime, int matchDurationInMinutes, List<Player> signedUpPlayers) {
        List<Match> matches = new ArrayList<>();
        startTime = startTime.minusMinutes(matchDurationInMinutes); //Prevents the first matches to start matchDurationInMinutes after the startTime.
        int playerIndex = 0; //Used to take the next player out of the list, currently causes each player to be planned in order

        //Creates a match for every timeslot*fields
        for (int i = 0; i < timeSlots*availableFieldCount; i++) {
            Match match = new Match();

            //to create the matches, new teams have to be created
            match.setTeams(newTeams(playerIndex, signedUpPlayers));

            double team1PRanking = match.getTeams().get(0).getAveragePRanking();
            double team2PRanking = match.getTeams().get(1).getAveragePRanking();
            match.setpRankingDifference(team1PRanking - team2PRanking);

            //After we created enough teams for one timeslot, the next matches start at a different time
            if(i%(timeSlots) == 0){
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
