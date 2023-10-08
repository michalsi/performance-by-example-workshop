package simulations;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _2A_EditSprintsTest extends BaseSimulation {
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
                    //  TODO  "GET /rest/agile/1.0/board"
                    //  use jsonPath and save response "$.total" into totalNoBoards session var
                    //  doc: https://gatling.io/docs/gatling/reference/current/core/check/
            )
            .exec(
                    // TODO set up 'startAt' value for the subsequent 'random' query for boards
                    // Use session API
                    // doc: https://gatling.io/docs/gatling/reference/current/core/session/session_api/#setting-attributes
            )
            .exec(
                    //  TODO  "GET /rest/agile/1.0/board" with .queryParam("startAt")
                    // extract all returned BoardIds
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
