import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.Proxy;
import static io.gatling.javaapi.http.HttpDsl.http;
import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class EditSprintsTest extends Simulation {

    final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
//            .proxy(Proxy("localhost", 8000))
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");
    String totalNoBoards = "totalNoBoards";
    ScenarioBuilder scn = scenario("Get boards and edit Sprints")
            .exec(
                    getTotalBoards())
            .doIf(session -> session.getInt("totalNoBoards") > 0).then(
                    exec(
                            setBoardsStartAtInSession(totalNoBoards))
                            .exec(
                                    getRandomBoards())
                            .exec(
                                    getRandomBoardId())
                            .exec(
                                    getSprintIds())
                            .exec(
                                    setRandomSprintIdInSession())
                            .exec(
                                    getRandomSprint())
                            .exec(
                                    updateSprintGoal())
            );

    private ChainBuilder getTotalBoards() {
        return exec(
                http("GET " + "/rest/agile/1.0/board")
                        .get("/rest/agile/1.0/board")
                        .check(jsonPath("$.total").find().saveAs(totalNoBoards))
        );
    }

    private static Function<Session, Session> setBoardsStartAtInSession(String totalNoBoards) {
        return session -> session.set("startAt", ThreadLocalRandom.current().nextInt(session.getInt(totalNoBoards)));
    }

    private ChainBuilder getRandomBoards() {
        return exec(http("GET " + "/rest/agile/1.0/board")
                .get("/rest/agile/1.0/board")
                .queryParam("startAt", session -> session.getInt("startAt"))
                .check(jsonPath("$.values[*].id").findAll().saveAs("boardIds")));
    }

    private Function<Session, Session> getRandomBoardId() {
        return session -> {
            List<String> ids = session.getList("boardIds");
            String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            Session newSession = session.set("BoardId", randomId);
            return newSession;
        };
    }

    private HttpRequestActionBuilder getSprintIds() {
        return http("GET " + "rest/agile/1.0/board/{id}/sprint")
                .get("/rest/agile/1.0" +
                        "/board/" + "#{BoardId}" + "/sprint")
                .check(jsonPath("$.values[*].id").findAll().saveAs("sprintIds"));
    }

    private Function<Session, Session> setRandomSprintIdInSession() {
        return session -> {
            List<String> ids = session.getList("sprintIds");
            String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            return session.set("sprintId", randomId);
        };
    }

    private HttpRequestActionBuilder getRandomSprint() {
        return http("GET " + "/rest/agile/1.0/sprint/{id}")
                .get("/rest/agile/1.0/sprint/" + "#{sprintId}");
    }

    private HttpRequestActionBuilder updateSprintGoal() {
        return http("POST " + "/rest/agile/1.0/sprint/{id}")
                .post("/rest/agile/1.0/sprint/" + "#{sprintId}")
                .body(StringBody(session -> "{ \"goal\": \"New goal for Sprint " +
                        session.getString("sprintId") + " changed at " + System.currentTimeMillis() + "\" }"));
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