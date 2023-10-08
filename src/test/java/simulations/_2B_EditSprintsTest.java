package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _2B_EditSprintsTest extends BaseSimulation{
    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
//            .proxy(Proxy("localhost", 8000))
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");

    public static final String TOTAL_NO_BOARDS = "totalNoBoards";
    public static final String BOARD_IDS = "BoardIds";
    public static final String START_AT = "startAt";

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
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
