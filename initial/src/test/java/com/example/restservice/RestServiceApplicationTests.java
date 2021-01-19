package com.example.restservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import org.springframework.util.Assert;

import com.github.javafaker.Faker;
import java.util.Random;
import java.util.ArrayList;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class RestServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)  
	public void testGetAllInitiallyLoadedLevels() throws Exception {
		this.mockMvc.perform(get("/levels"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].name", is("noob")))
			.andExpect(jsonPath("$[1].name", is("pro")))
			.andExpect(jsonPath("$[2].name", is("invincible")));
	}

	@Test
	@Order(2)
	public void testGetAllInitiallyLoadedGames() throws Exception {
		this.mockMvc.perform(get("/games"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(5)))
			.andExpect(jsonPath("$[0].name", is("Fortnite")))
			.andExpect(jsonPath("$[1].name", is("Call of Duty Warzone")))
			.andExpect(jsonPath("$[2].name", is("Dota")))
			.andExpect(jsonPath("$[3].name", is("Among Us")))
			.andExpect(jsonPath("$[4].name", is("Fall Guys")));
	}

	@Test
	@Order(3)
	public void testGetAllInitiallyLoadedGenders() throws Exception {
		this.mockMvc.perform(get("/genders"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].name", is("Male")))
			.andExpect(jsonPath("$[1].name", is("Female")))
			.andExpect(jsonPath("$[2].name", is("Other")));
	}

	@Test
	@Order(4)
	public void testGetAllInitiallyLoadedGamers() throws Exception {
		this.mockMvc.perform(get("/gamers"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(100)));
	}

	@Test
	@Order(5)
	public void testGetAllInitiallyLoadedGeography() throws Exception {
		this.mockMvc.perform(get("/geography"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].name", is("Europe")))
			.andExpect(jsonPath("$[1].name", is("Asia")))
			.andExpect(jsonPath("$[2].name", is("America")));
	}

	@Test
	@Order(6)
	public void testGetAllInitiallyLoadedGamerLevels() throws Exception {
		this.mockMvc.perform(get("/gamerlevels"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(100)));
	}

	@Test
	@Order(7)
	public void testMakeMatch() throws Exception {
		int geoId = createGeography("Antarctica");

		Faker faker = new Faker();
		Random r = new Random();

		String name = faker.name().fullName();
		int genderId = r.nextInt(3) + 1;
		String nickname = faker.name().username();
		int gamerId1 = createGamer(name, genderId, nickname, geoId);

		String name2 = faker.name().fullName();
		int genderId2 = r.nextInt(3) + 1;
		String nickname2 = faker.name().username();
		int gamerId2 = createGamer(name2, genderId2, nickname2, geoId);

		String name3 = faker.name().fullName();
		int genderId3 = r.nextInt(3) + 1;
		String nickname3 = faker.name().username();
		int gamerId3 = createGamer(name3, genderId3, nickname3, geoId);

		String name4 = faker.name().fullName();
		int genderId4 = r.nextInt(3) + 1;
		String nickname4 = faker.name().username();
		int gamerId4 = createGamer(name4, genderId4, nickname4, geoId);

		int levelId1 = 1;
		int levelId2 = 2;
		int gameId = 1;
		createGamerLevel(gamerId1, levelId1, gameId);
		createGamerLevel(gamerId2, levelId2, gameId);
		createGamerLevel(gamerId3, levelId1, gameId);
		createGamerLevel(gamerId4, levelId2, gameId);

		MvcResult result = this.mockMvc.perform(get(String.format("/gamer/%d/make-match/%d", gamerId1, gameId)))
			.andExpect(status().isOk())
			.andReturn();

		JSONObject json = new JSONObject(result.getResponse().getContentAsString());
		Assert.isTrue(gamerId3 == Integer.parseInt(json.get("id").toString()));

		MvcResult result2 = this.mockMvc.perform(get(String.format("/gamer/%d/make-match/%d", gamerId2, gameId)))
			.andExpect(status().isOk())
			.andReturn();

		JSONObject json2 = new JSONObject(result2.getResponse().getContentAsString());
		Assert.isTrue(gamerId4 == Integer.parseInt(json2.get("id").toString()));
	}

	@Test
	@Order(8)
	public void testGetUsersWithMaxCreditForEachGameBasedOnLevel() throws Exception {
		Faker faker = new Faker();
		Random r = new Random();

		ArrayList<Integer> gamerIds = new ArrayList<Integer>();

		for (int i = 0; i < 5; i++) {
			String name = faker.name().fullName();
			int genderId = r.nextInt(3) + 1;
			String nickname = faker.name().username();
			int geographyId = r.nextInt(3) + 1;
			gamerIds.add(createGamer(name, genderId, nickname, geographyId));
		}

		int levelId = r.nextInt(3) + 1;
		int gameId = 1;

		for (int i : gamerIds) {
			createGamerLevel(i, levelId, gameId);
			giveCredit(i, gameId, 1000);
			gameId++;
		}

		this.mockMvc.perform(get("/max-credit/" + levelId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(5)))
			.andExpect(jsonPath("$[0].id", is(gamerIds.get(0))))
			.andExpect(jsonPath("$[1].id", is(gamerIds.get(1))))
			.andExpect(jsonPath("$[2].id", is(gamerIds.get(2))))
			.andExpect(jsonPath("$[3].id", is(gamerIds.get(3))))
			.andExpect(jsonPath("$[4].id", is(gamerIds.get(4))));
	}

	@Test
	@Order(9)
	public void testGetAllUserCreditsBasedOnGameAndLevel() throws Exception {
		Faker faker = new Faker();
		Random r = new Random();

		String name = faker.name().fullName();
		int genderId = r.nextInt(3) + 1;
		String nickname = faker.name().username();
		int geographyId = r.nextInt(3) + 1;
		int gamerId = createGamer(name, genderId, nickname, geographyId);

		int levelId = r.nextInt(3) + 1;
		int gameId = r.nextInt(5) + 1;
		createGamerLevel(gamerId, levelId, gameId);

		int credit1 = r.nextInt(100) + 1;
		giveCredit(gamerId, gameId, credit1);

		int credit2 = r.nextInt(100) + 1;
		giveCredit(gamerId, gameId, credit2);

		int credit3 = r.nextInt(100) + 1;
		giveCredit(gamerId, gameId, credit3);

		this.mockMvc.perform(get(String.format("/user-credits/%d/%d/%d", gamerId, gameId, levelId)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)))
			.andExpect(jsonPath("$[0]", is(credit1)))
			.andExpect(jsonPath("$[1]", is(credit2)))
			.andExpect(jsonPath("$[2]", is(credit3)))
			.andExpect(jsonPath("$[3]", is(credit1 + credit2 + credit3)));
	}

	@Test
	@Order(10)
	public void testGetAllGamerLevelsForGamer() throws Exception {
		Faker faker = new Faker();
		Random r = new Random();

		String name = faker.name().fullName();
		int genderId = r.nextInt(3) + 1;
		String nickname = faker.name().username();
		int geographyId = r.nextInt(3) + 1;
		int gamerId = createGamer(name, genderId, nickname, geographyId);

		int levelId1 = 1;
		int gameId1 = 1;
		createGamerLevel(gamerId, levelId1, gameId1);

		int levelId2 = 2;
		int gameId2 = 2;
		createGamerLevel(gamerId, levelId2, gameId2);

		int levelId3 = 3;
		int gameId3 = 3;
		createGamerLevel(gamerId, levelId3, gameId3);

		this.mockMvc.perform(get("/gamer/" + gamerId + "/gamerlevel"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].levelId", is(levelId1)))
			.andExpect(jsonPath("$[0].gameId", is(gameId1)))
			.andExpect(jsonPath("$[1].levelId", is(levelId2)))
			.andExpect(jsonPath("$[1].gameId", is(gameId2)))
			.andExpect(jsonPath("$[2].levelId", is(levelId3)))
			.andExpect(jsonPath("$[2].gameId", is(gameId3)));
	}

	@Test
	@Order(11)
	public void testCreateNewGamerLevel() throws Exception {
		Random r = new Random();

		int gamerId = r.nextInt(100) + 1;
		int levelId = r.nextInt(3) + 1;
		int gameId = r.nextInt(5) + 1;
		int newGamerLevelId = createGamerLevel(gamerId, levelId, gameId);

		MvcResult result2 = this.mockMvc.perform(get("/gamerlevel/" + newGamerLevelId))
			.andExpect(status().isOk())
			.andReturn();

		JSONObject gamerLevel = new JSONObject(result2.getResponse().getContentAsString());

		Assert.isTrue(gamerId == Integer.parseInt(gamerLevel.get("gamerId").toString()));
		Assert.isTrue(levelId == Integer.parseInt(gamerLevel.get("levelId").toString()));
		Assert.isTrue(gameId == Integer.parseInt(gamerLevel.get("gameId").toString()));
	}

	@Test
	@Order(12)
	public void testCreateNewGamer() throws Exception {
		Faker faker = new Faker();
		Random r = new Random();

		String name = faker.name().fullName();
		int genderId = r.nextInt(3) + 1;
		String nickname = faker.name().username();
		int geographyId = r.nextInt(3) + 1;
		int newGamerId = createGamer(name, genderId, nickname, geographyId);

		MvcResult result = this.mockMvc.perform(get("/gamer/" + newGamerId))
			.andExpect(status().isOk())
			.andReturn();

		JSONObject newGamer = new JSONObject(result.getResponse().getContentAsString());

		String newName = newGamer.get("name").toString();
		int newGenderId = Integer.parseInt(newGamer.get("genderId").toString());
		String newNickname = newGamer.get("nickname").toString();
		int newGeographyId = Integer.parseInt(newGamer.get("geographyId").toString());

		Assert.isTrue(name.equals(newName), "Names do not match!");
		Assert.isTrue(genderId == newGenderId, "Gender Ids do not match!");
		Assert.isTrue(nickname.equals(newNickname), "Nicknames do not match!");
		Assert.isTrue(geographyId == newGeographyId, "Geography Ids do not match!");
	}

	private int createGamer(String name, int genderId, String nickname, int geographyId) throws Exception {
		String gamerPayload = String.format("{\"name\":\"%s\", \"genderId\":%d, \"nickname\":\"%s\", \"geographyId\":%d}", 
			name, genderId, nickname, geographyId);

		MvcResult initialResult = this.mockMvc.perform(post("/gamer")
			.content(gamerPayload)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		JSONObject initialNewGamer = new JSONObject(initialResult.getResponse().getContentAsString());

		return Integer.parseInt(initialNewGamer.get("id").toString());
	}

	private int createGamerLevel(int gamerId, int levelId, int gameId) throws Exception {
		String gamerLevelPayload = String.format("{\"gamerId\":%d, \"levelId\":%d, \"gameId\":%d}",
			gamerId, levelId, gameId);

		MvcResult result = this.mockMvc.perform(post("/gamerlevel")
			.content(gamerLevelPayload)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		JSONObject newGamerLevel = new JSONObject(result.getResponse().getContentAsString());

		return Integer.parseInt(newGamerLevel.get("id").toString());
	}

	private void giveCredit(int gamerId, int gameId, int credit) throws Exception {
		String creditPayload = String.format("{\"gamerId\":%d, \"gameId\":%d, \"credit\":%d}",
			gamerId, gameId, credit);

		this.mockMvc.perform(post("/credit")
			.content(creditPayload)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}

	private int createGeography(String name) throws Exception {
		String payload = String.format("{\"name\": \"%s\"}", name);

		MvcResult result = this.mockMvc.perform(post("/geography")
			.content(payload)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		JSONObject newGeography = new JSONObject(result.getResponse().getContentAsString());

		return Integer.parseInt(newGeography.get("id").toString());
	}
}
