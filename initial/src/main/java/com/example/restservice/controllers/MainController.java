package com.example.restservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.restservice.models.Level;
import com.example.restservice.repositories.LevelRepository;
import com.example.restservice.models.Game;
import com.example.restservice.repositories.GameRepository;
import com.example.restservice.models.Gender;
import com.example.restservice.repositories.GenderRepository;
import com.example.restservice.models.Gamer;
import com.example.restservice.repositories.GamerRepository;
import com.example.restservice.models.Geography;
import com.example.restservice.repositories.GeographyRepository;
import com.example.restservice.models.GamerLevel;
import com.example.restservice.repositories.GamerLevelRepository;
import com.example.restservice.models.Credit;
import com.example.restservice.repositories.CreditRepository;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import javax.validation.Valid;

@RestController
public class MainController {

	@Autowired
	private LevelRepository levelRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private GenderRepository genderRepo;

	@Autowired
	private GamerRepository gamerRepo;

	@Autowired
	private GeographyRepository geographyRepo;

	@Autowired
	private GamerLevelRepository gamerLevelRepo;

	@Autowired
	private CreditRepository creditRepo;

	@GetMapping("/levels")
	public ArrayList<Level> getAllLevels() {
		return levelRepo.getAll();
	}

	@GetMapping("/games")
	public ArrayList<Game> getAllGames() {
		return gameRepo.getAll();
	}

	@GetMapping("/genders")
	public ArrayList<Gender> getAllGenders() {
		return genderRepo.getAll();
	}

	@GetMapping("/gamers")
	public ArrayList<Gamer> getAllGamers() {
		return gamerRepo.getAll();
	}

	@GetMapping("/gamer/{id}")
	public ResponseEntity<?> getGamerById(@PathVariable String id) {
		Optional<Gamer> gamer = gamerRepo.getGamerById(Long.parseLong(id));

		if (gamer.isPresent()) {
			return ResponseEntity.ok().body(gamer);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/gamer/{gamerId}/gamerlevel")
	public ResponseEntity<?> getGamerLevelByGamerId(@PathVariable String gamerId) {
		ArrayList<GamerLevel> result = gamerLevelRepo.getGamerLevelByGamerId(Long.parseLong(gamerId));

		if (result != null) {
			return ResponseEntity.ok().body(result);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/geography")
	public ArrayList<Geography> getAllGeography() {
		return geographyRepo.getAll();
	}

	@GetMapping("/geography/{id}")
	public ResponseEntity<?> getGeographyById(@PathVariable String id) {
		Optional<Geography> result = geographyRepo.getGeographyById(Long.parseLong(id));

		if (result.isPresent()) {
			return ResponseEntity.ok().body(result);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/gamerlevels")
	public ArrayList<GamerLevel> getAllGamerLevels() {
		
		return gamerLevelRepo.getAll();
	}

	@GetMapping("/gamerlevel/{id}")
	public ResponseEntity<?> getGamerLevelById(@PathVariable String id) {
		Optional<GamerLevel> result = gamerLevelRepo.getGamerLevelById(Long.parseLong(id));

		if (result.isPresent()) {
			return ResponseEntity.ok().body(result);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/gamer/{id}/make-match/{gameId}")
	public ResponseEntity<?> makeMatchByGamerAndGameId(@PathVariable String id, @PathVariable String gameId) {
		Gamer matched = gamerRepo.makeMatchByGamerAndGameId(Long.parseLong(id), Long.parseLong(gameId));

		if (matched == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(matched);
	}

	@GetMapping("/max-credit/{levelId}")
	public ResponseEntity<?> getGamerWithMaxCreditForEachGame(@PathVariable String levelId) {
		ArrayList<Gamer> gamers = gamerRepo.getGamerWithMaxCreditForEachGame(Long.parseLong(levelId));

		if (gamers == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(gamers);
	}

	@GetMapping("/credits-summed/{levelId}/{gameId}")
	public ResponseEntity<?> getGamersWithCreditsSummedPerLevelPerGame(@PathVariable String levelId, @PathVariable String gameId) {
		HashMap<Integer, Gamer> hmap = gamerRepo.getGamersWithCreditsSummedPerLevelPerGame(Long.parseLong(levelId), Long.parseLong(gameId));

		if (hmap == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(hmap);
	}

	@GetMapping("/user-credits/{userId}/{gameId}/{levelId}")
	public ResponseEntity<?> getUserCreditsByUserIdAndGameIdAndLevelId(@PathVariable String userId, @PathVariable String gameId, @PathVariable String levelId) {
		ArrayList<Integer> userCredits = gamerRepo.getUserCreditsByUserIdAndGameIdAndLevelId(Long.parseLong(userId), Long.parseLong(gameId), Long.parseLong(levelId));

		if (userCredits == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(userCredits);
	}

	@PostMapping(path = "/gamer",
        consumes = "application/json", 
        produces = "application/json")
	public ResponseEntity<?> createGamer(@Valid @RequestBody Gamer gamer) {
		try {
			Gamer persistedGamer = gamerRepo.save(gamer);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(persistedGamer.getId())
				.toUri();

			return ResponseEntity.created(uri).body(persistedGamer);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	@PostMapping(path = "/gamerlevel",
        consumes = "application/json", 
        produces = "application/json")
	public ResponseEntity<?> createGamerLevel(@Valid @RequestBody GamerLevel gamerLevel) {
		try {
			GamerLevel persistedGamerLevel = gamerLevelRepo.save(gamerLevel);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(persistedGamerLevel.getId())
				.toUri();

			return ResponseEntity.created(uri).body(persistedGamerLevel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	@PostMapping(path = "/credit",
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<?> giveCreditToUser(@Valid @RequestBody Credit credit) {
		try {
			Credit persistedCredit = creditRepo.save(credit);

			return ResponseEntity.ok().body(persistedCredit);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	@PostMapping(path = "/geography",
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<?> createGeography(@Valid @RequestBody Geography geography) {
		try {
			Geography persistedGeography = geographyRepo.save(geography);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(persistedGeography.getId())
				.toUri();

			return ResponseEntity.created(uri).body(persistedGeography);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	@PutMapping(path = "/gamer/{id}",
        consumes = "application/json", 
        produces = "application/json")
	public ResponseEntity<?> updateGamer(@PathVariable String id, @Valid @RequestBody Gamer gamer) {
		Optional<Gamer> optionalGamer = gamerRepo.getGamerById(Long.parseLong(id));

		if (!optionalGamer.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		try {
			Gamer updatedGamer = gamerRepo.updateGamer(optionalGamer.get(), gamer);

			return ResponseEntity.accepted().body(updatedGamer);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

}