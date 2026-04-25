package be.thomasmore.padelplanning.services;

import be.thomasmore.padelplanning.entities.*;
import be.thomasmore.padelplanning.repositories.MatchRepository;
import be.thomasmore.padelplanning.repositories.PadelDayRepository;
import be.thomasmore.padelplanning.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
        //Voor nu maakt dit de mees 'faire' teams geordend in p-ranking
        signedUpPlayers = signedUpPlayers.stream().sorted(Comparator.comparing(Player::getpRanking)).toList();
        padelDay.setMatches(newMatches(timeSlots, availableFields, startTime, matchDurationInMinutes, signedUpPlayers));
        padelDay.setNumberOfMatches(padelDay.getMatches().size());
        padelDay.setFields(availableFields);
        padelDayRepository.save(padelDay);
        return padelDay;
    }

    private List<Match> newMatches(int timeSlots, List<Field> availableFields, LocalTime startTime, int matchDurationInMinutes, List<Player> signedUpPlayers) {
        List<Match> matches = new ArrayList<>();
        //Creates a match for every timeslot
        for (int i = 0; i < timeSlots; i++) {
            //creating a new List of players so we can remove players from it as we assign them
            List<Player> playersToAssign = new ArrayList<>(signedUpPlayers);
            //creating new list of teams so we can have different teams per timeslot
            List<Team> teamsToAssign = newTeams(playersToAssign,availableFields);

            for (Field availableField : availableFields) {
                Match match = new Match();
                match.setField(availableField);

                //we add 2 teams to the match and remove them from the team pool
                match.setTeams(List.of(teamsToAssign.get(0), teamsToAssign.get(1)));
                teamsToAssign.remove(0);
                teamsToAssign.remove(0);

                double team1PRanking = match.getTeams().get(0).getAveragePRanking();
                double team2PRanking = match.getTeams().get(1).getAveragePRanking();
                match.setpRankingDifference(Math.abs(team1PRanking - team2PRanking));

                match.setTimeSlot(startTime);
                matchRepository.save(match);
                matches.add(match);
            }
            //After we created enough teams for one timeslot, the next matches start at a different time
            startTime = startTime.plusMinutes(matchDurationInMinutes);
        }
        return matches;
    }

    private List<Team> newTeams(List<Player> playersToAssign, List<Field> availableFields) {
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < availableFields.size(); i++) {
            for (int j = 0; j < 2; j++) {
                Team team = new Team();
                //Add the players to the team and remove them from the to be assigned list
                team.setPlayers(List.of(playersToAssign.get(0), playersToAssign.get(1)));
                playersToAssign.remove(0);
                playersToAssign.remove(0);
                team.setAveragePRanking(team.getPlayers().stream().mapToDouble(Player::getpRanking).average().getAsDouble());
                teamRepository.save(team);
                teams.add(team);
            }
        }
        return teams;
    }
}
