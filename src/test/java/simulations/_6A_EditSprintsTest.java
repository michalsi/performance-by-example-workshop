package simulations;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _6A_EditSprintsTest extends BaseSimulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
//            .proxy(Proxy("localhost", 8000))
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");


    public static final String TOTAL_NO_BOARDS = "totalNoBoards";
    public static final String START_AT = "startAt";
    public static final String BOARD_IDS = "boardIds";
    public static final String BOARD_ID = "BoardId";
    public static final String SPRINT_IDS = "sprintIds";

    public static final String SPRINT_ID = "sprintId";

    ScenarioBuilder scn = scenario("Get boards and edit Sprints")
            .exec(
                    getTotalBoards()
            )
            // TODO: Add IF condition to check if TotalBoards > 0
            // docs: https://gatling.io/docs/gatling/reference/current/core/scenario/#conditional-statements
            .exec(
                    setBoardsStartAtInSession()
            )
            .exec(
                    getRandomBoards()
            )
            .exec(
                    setRandomBoardIdInSession()
            )
            .exec(
                    getRandomSprintFromBoard()
            )
            .exec(
                    setRandomSprintIdInSession()
            )
            .exec(
                    getRandomSprint()
            )
            .exec(
                    postUpdateSprintGoal()
            );

    private ChainBuilder getTotalBoards() {
        return exec(
                http("GET /rest/agile/1.0/board")
                        .get("/rest/agile/1.0/board")
                        .check(jsonPath("$.total").find().saveAs(TOTAL_NO_BOARDS))
        );
    }

    private static Function<Session, Session> setBoardsStartAtInSession() {
        return session -> session.set(START_AT, ThreadLocalRandom.current().nextInt(session.getInt(TOTAL_NO_BOARDS)));
    }

    private static HttpRequestActionBuilder getRandomBoards() {
        return http("GET /rest/agile/1.0/board")
                .get("/rest/agile/1.0/board")
                .queryParam(START_AT, session -> session.getInt(START_AT))
                .check(jsonPath("$.values[*].id").findAll().saveAs(BOARD_IDS));
    }

    private static Function<Session, Session> setRandomBoardIdInSession() {
        return session -> {
            List<String> ids = session.getList(BOARD_IDS);
            String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            Session newSession = session.set(BOARD_ID, randomId);
            return newSession;
        };
    }

    private static HttpRequestActionBuilder getRandomSprintFromBoard() {
        return http("GET rest/agile/1.0/board/{id}/sprint")
                .get("/rest/agile/1.0" +
                        "/board/" + "#{" + BOARD_ID + "}" + "/sprint")
                .check(jsonPath("$.values[*].id").findAll().saveAs(SPRINT_IDS)
                );
    }

    private static Function<Session, Session> setRandomSprintIdInSession() {
        return session -> {
            List<String> ids = session.getList(SPRINT_IDS);
            String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            return session.set(SPRINT_ID, randomId);
        };
    }

    private static HttpRequestActionBuilder getRandomSprint() {
        return http("GET " + "/rest/agile/1.0/sprint/{id}")
                .get("/rest/agile/1.0/sprint/" + "#{" + SPRINT_ID + "}");
    }

    private static HttpRequestActionBuilder postUpdateSprintGoal() {
        return http("POST " + "/rest/agile/1.0/sprint/{id}")
                .post("/rest/agile/1.0/sprint/" + "#{" + SPRINT_ID + "}")
                .body(StringBody(session -> "{ \"goal\": \"New goal for Sprint " +
                        session.getString(SPRINT_ID) + " changed at " + System.currentTimeMillis() + "\" }"));
    }


    {
        setUp(scn.injectOpen(
                incrementUsersPerSec(5.0)
                        .times(5)
                        .eachLevelLasting(10)
                        .separatedByRampsLasting(10)
                        .startingFrom(10) // Double
        )).protocols(httpProtocol);
    }
}