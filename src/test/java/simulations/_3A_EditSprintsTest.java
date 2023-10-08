package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _3A_EditSprintsTest extends BaseSimulation {

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
                    http("GET /rest/agile/1.0/board")
                            .get("/rest/agile/1.0/board")
                            .check(jsonPath("$.total").find().saveAs(TOTAL_NO_BOARDS))
            )
            .exec(
                    session -> session.set(START_AT, ThreadLocalRandom.current().nextInt(session.getInt(TOTAL_NO_BOARDS))))

            .exec(
                    http("GET /rest/agile/1.0/board")
                            .get("/rest/agile/1.0/board")
                            .queryParam(START_AT, session -> session.getInt(START_AT))
                            .check(jsonPath("$.values[*].id").findAll().saveAs(BOARD_IDS))
            )
            .exec(
                    session -> {
                        // TODO Get random BoardId from session variable BoardIds
                        Session newSession = session; // TODO It's only a stub
                        return newSession;
                    }
            )
            .exec(
                    // TODO Send Get all sprints for board request and extract all Sprint IDs
                    // TODO GET /rest/agile/1.0/board/{id}/sprint
                    // TODO save all SprintIds into session variable sprintIds
            )
            .exec(
                    // TODO Extract random SprintID from session variable sprintIds into session variable sprintId
            )
            .exec(
                    // TODO GET /rest/agile/1.0/sprint/{id}
            )
            .exec(
                    // TODO Update sprint by sending Post Sprint request
                    // TODO POST /rest/agile/1.0/sprint/{id}
                    // TODO with body: { "goal": "New goal for Sprint {id} changed at {current time}" }
            );


    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}