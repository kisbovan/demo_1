package com.example.restservice.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;

import java.util.Random;
import java.util.ArrayList;

import com.example.restservice.models.Gamer;
import com.example.restservice.models.Gender;
import com.example.restservice.models.Geography;
import com.example.restservice.models.Game;
import com.example.restservice.models.Level;
import com.example.restservice.models.Credit;
import com.example.restservice.models.GamerLevel;
import com.example.restservice.repositories.GamerRepository;
import com.example.restservice.repositories.GamerLevelRepository;

import com.example.restservice.repositories.GenderRepository;
import com.example.restservice.repositories.GeographyRepository;
import com.example.restservice.repositories.GameRepository;
import com.example.restservice.repositories.LevelRepository;
import com.example.restservice.repositories.CreditRepository;

@Component
public class GamersSeeder implements CommandLineRunner {

	@Autowired
	GamerRepository gamerRepo;

	@Autowired
	GamerLevelRepository gamerLevelRepo;

	@Autowired
	GenderRepository genderRepo;

	@Autowired
	GeographyRepository geographyRepo;

	@Autowired
	GameRepository gameRepo;

	@Autowired
	LevelRepository levelRepo;

	@Autowired
	CreditRepository creditRepo;

	@Override
	public void run(String... args) throws Exception {

		// Let's generate random gamers' data for testing
		seedGamersData(100);

		//Generate data so that each gamer has a random game with random level
		seedGamerLevelsData();

		// Let's also generate some credit for the gamers
		seedCredit();
	}

	private void seedCredit() {
		ArrayList<Gamer> gamers = gamerRepo.getAll();
		ArrayList<Game> games = gameRepo.getAll();

		for (Gamer gamer : gamers) {
			for (Game game: games) {
				Random r = new Random();
				Credit credit = new Credit(gamer.getId(), game.getId(), r.nextInt(100) + 1);
				creditRepo.save(credit);
			}
		}
	}

	private void seedGamerLevelsData() {
		ArrayList<Gamer> gamers = gamerRepo.getAll();

		for (Gamer gamer : gamers) {
			Long levelId = getRandomLevelId();
			Long gameId = getRandomGameId();

			GamerLevel gamerLevel = new GamerLevel(gamer.getId(), levelId, gameId);
			gamerLevelRepo.save(gamerLevel);
		}
	}

	private void seedGamersData(int noOfGamers) {
		Faker faker = new Faker();

		if (gamerRepo.count() == 0) {
			int counter = 0;

			while (counter < noOfGamers) {
				String name = faker.name().fullName();
				Long genderId = getRandomGenderId();
				String nickname = faker.name().username();
				Long geographyId = getRandomGeographyId();

				Gamer gamer = new Gamer(name, genderId, nickname, geographyId);
				gamerRepo.save(gamer);

				counter++;
			}
		}
	}

	private Long getRandomGenderId() {
		Random random = new Random();
		ArrayList<Gender> genders = genderRepo.getAll();
		
		return genders.get(random.nextInt(genders.size())).getId();
	}

	private Long getRandomGeographyId() {
		Random random = new Random();
		ArrayList<Geography> geographies = geographyRepo.getAll();

		return geographies.get(random.nextInt(geographies.size())).getId();
	}

	private Long getRandomLevelId() {
		Random random = new Random();
		ArrayList<Level> levels = levelRepo.getAll();

		return levels.get(random.nextInt(levels.size())).getId();
	}

	private Long getRandomGameId() {
		Random random = new Random();
		ArrayList<Game> games = gameRepo.getAll();

		return games.get(random.nextInt(games.size())).getId();
	}
}