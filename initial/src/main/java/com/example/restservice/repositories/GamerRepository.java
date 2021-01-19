package com.example.restservice.repositories;

import org.springframework.stereotype.Service;
import com.example.restservice.repositories.interfaces.GamerRepositoryInterface;
import com.example.restservice.repositories.GamerLevelRepository;
import com.example.restservice.repositories.GameRepository;
import com.example.restservice.repositories.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.restservice.models.Credit;
import com.example.restservice.models.Game;
import com.example.restservice.models.Gamer;
import com.example.restservice.models.GamerLevel;
import java.lang.Iterable;
import java.util.ArrayList;

import java.util.Optional;
import java.util.Random;
import java.util.HashMap;

@Service
public class GamerRepository {

    @Autowired
    private GamerRepositoryInterface gamerRepo;

    @Autowired
    private GamerLevelRepository gamerLevelRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CreditRepository creditRepo;

    public ArrayList<Gamer> getAll() {
        Iterable<Gamer> results = gamerRepo.findAll();
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();

        results.forEach(e -> gamers.add(e));

        return gamers;
    }

    public Gamer save(Gamer gamer) {
        return gamerRepo.save(gamer);
    }

    public long count() {
        return gamerRepo.count();
    }

    public Optional<Gamer> getGamerById(long id) {
        return gamerRepo.findById(id);
    }

    public Gamer updateGamer(Gamer gamerToUpdate, Gamer gamerFromUpdate) {
        gamerToUpdate.setName(gamerFromUpdate.getName());
        gamerToUpdate.setGenderId(gamerFromUpdate.getGenderId());
        gamerToUpdate.setNickname(gamerFromUpdate.getNickname());
        gamerToUpdate.setGeographyId(gamerFromUpdate.getGeographyId());

        return gamerRepo.save(gamerToUpdate);
    }

    public Gamer makeMatchByGamerAndGameId(long id, long gameId) {
        Optional<Gamer> currentGamer = gamerRepo.findById(id);

        if (!currentGamer.isPresent()) {
            return null;
        }

        //Let's fetch the gamer's level for that particular game
        GamerLevel currentGamerLevel = gamerLevelRepo.getGamerLevelByGamerAndGameId(id, gameId);

        if (currentGamerLevel == null) {
            return null;
        }

        //Let's fetch all gamer levels which match the required criteria
        ArrayList<GamerLevel> gamerLevels = gamerLevelRepo.findByGameIdAndLevelId(gameId, currentGamerLevel.getLevelId());
        ArrayList<Gamer> gamersMatched = new ArrayList<Gamer>();

        //Then let's filter out the user itself, and if the geography matches, we add them to the possible candidates
        for (GamerLevel gamerLevel : gamerLevels) {
            if (gamerLevel.getGamerId() == id) {
                continue;
            }

            Gamer matchedGamer = gamerRepo.findById(gamerLevel.getGamerId()).get();

            if (matchedGamer.getGeographyId() == currentGamer.get().getGeographyId()) {
                gamersMatched.add(matchedGamer);
            }
        }

        // Let's make sure to return only a single gamer, even if there are multiple matches
        if (gamersMatched.size() == 0) {
            return null;
        } else if (gamersMatched.size() == 1) {
            return gamersMatched.get(0);
        } else {
            Random random = new Random();

            return gamersMatched.get(random.nextInt(gamersMatched.size()));
        }
    }

    //Here we look for the top scorers for each game based on level
    public ArrayList<Gamer> getGamerWithMaxCreditForEachGame(long levelId) {
        ArrayList<GamerLevel> gamerLevels = gamerLevelRepo.getGamerLevelByLevelId(levelId);
        ArrayList<Credit> allCredit = creditRepo.getAll();
        ArrayList<Game> allGames = gameRepo.getAll();
        ArrayList<Gamer> gamersWithMaxCredit = new ArrayList<Gamer>();

        if (gamerLevels.size() == 0) {
            return null;
        }

        for (Game game : allGames) {
            int highestCredit = 0;
            long userIdWithHighestCredit = 0;
            ArrayList<GamerLevel> gamerLevelsForGame = new ArrayList<GamerLevel>();

            //Let's fetch all gamer levels for each game
            for (GamerLevel gamerLevel : gamerLevels) {
                if (game.getId() == gamerLevel.getGameId()) {
                    gamerLevelsForGame.add(gamerLevel);
                }
            }

            for (GamerLevel gamerLevel : gamerLevelsForGame) {
                int currentCredit = 0;

                //Then let's calculate who has the highest credit for each game
                for (Credit credit : allCredit) {
                    if (gamerLevel.getGamerId() == credit.getGamerId() &&
                        gamerLevel.getGameId() == credit.getGameId()) {
                            currentCredit += credit.getCredit();
                        }
                }

                if (currentCredit > highestCredit) {
                    highestCredit = currentCredit;
                    userIdWithHighestCredit = gamerLevel.getGamerId();
                }
            }

            if (userIdWithHighestCredit == 0) {
                continue;
            }

            gamersWithMaxCredit.add(gamerRepo.findById(userIdWithHighestCredit).get());
        }

        return gamersWithMaxCredit;
    }

    // Based on level and game, here we sum up the credits for each user and return them in a 
    // key-value pair: key being the summed up score, and the value being the Gamer object
    public HashMap<Integer, Gamer> getGamersWithCreditsSummedPerLevelPerGame(long levelId, long gameId) {
        ArrayList<GamerLevel> gamerLevels = gamerLevelRepo.getGamerLevelByLevelId(levelId);
        Optional<Game> optionalGame = gameRepo.getGameByGameId(gameId);
        ArrayList<Credit> allCredit = creditRepo.getAll();

        if (!optionalGame.isPresent()) {
            return null;
        }

        if (gamerLevels.size() == 0) {
            return null;
        }

        Game game = optionalGame.get();
        HashMap<Integer, Gamer> hmap = new HashMap<Integer, Gamer>();

        for (GamerLevel gamerLevel : gamerLevels) {
            int highestCredit = 0;

            for (Credit credit : allCredit) {
                if (gamerLevel.getGamerId() == credit.getGamerId() &&
                    gamerLevel.getGameId() == credit.getGameId() &&
                    gamerLevel.getGameId() == gameId) {
                        highestCredit += credit.getCredit();
                    }
            }

            hmap.put(highestCredit, gamerRepo.findById(gamerLevel.getGamerId()).get());
        }

        return hmap;
    }

    //Here let's fetch all the credits based on a user per game per level,
    //then also add the sum in the end.
    public ArrayList<Integer> getUserCreditsByUserIdAndGameIdAndLevelId(long userId, long gameId, long levelId) {
        Optional<GamerLevel> optionalGamerLevel = gamerLevelRepo.findByGamerIdAndGameIdAndLevelId(userId, gameId, levelId);
        int creditsSummed = 0;
        ArrayList<Integer> userCreditsToReturn = new ArrayList<Integer>();

        if (!optionalGamerLevel.isPresent()) {
            return null;
        }

        GamerLevel gamerLevel = optionalGamerLevel.get();
        ArrayList<Credit> userCredits = creditRepo.findByGamerIdAndGameId(userId, gameId);

        for (Credit credit : userCredits) {
            userCreditsToReturn.add(credit.getCredit());
            creditsSummed += credit.getCredit();
        }

        userCreditsToReturn.add(creditsSummed);

        return userCreditsToReturn;
    }
}