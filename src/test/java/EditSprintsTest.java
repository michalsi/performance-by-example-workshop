import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.Proxy;
import static io.gatling.javaapi.http.HttpDsl.http;
import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class EditSprintsTest extends Simulation {

    final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .proxy(Proxy("localhost", 8000))
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");

    String totalNoBoards = "totalNoBoards";
    ScenarioBuilder scn = scenario("Get boards and edit Sprints")
            .exec(
                    http("GET " + "/rest/agile/1.0/board")
                            .get("/rest/agile/1.0/board")
                            .check(jsonPath("$.total").find().saveAs(totalNoBoards))
            )
            .exec(
                    session -> session.set("startAt", ThreadLocalRandom.current().nextInt(session.getInt(totalNoBoards)))
            )
            .exec(
                    http("GET " + "/rest/agile/1.0/board")
                            .get("/rest/agile/1.0/board")
                            .queryParam("startAt", session -> session.getInt("startAt"))
                            .check(jsonPath("$.values[*].id").findAll().saveAs("BoardIds"))
            )
            .exec(
                    session -> {
                        List<String> ids = session.getList("BoardIds");
                        String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
                        Session newSession = session.set("BoardId", randomId);
                        return newSession;
                    }
            )
            .exec(
                    http("GET " + "rest/agile/1.0/board/{id}/sprint")
                            .get("/rest/agile/1.0/board/" + "#{BoardId}" + "/sprint")
                            .check(jsonPath("$.values[*].id").findAll().saveAs("SprintIds"))
            )
            .exec(
                    session -> {
                        List<String> ids = session.getList("SprintIds");
                        String randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
                        return session.set("SprintId", randomId);
                    })
            .exec(
                    http("GET " + "/rest/agile/1.0/sprint/{id}")
                            .get("/rest/agile/1.0/sprint/" + "#{sprintId}")
            )
            .exec(
                    http("POST " + "/rest/agile/1.0/sprint/{id}")
                            .post("/rest/agile/1.0/sprint/" + "#{SprintId}")
                            .body(StringBody(session -> "{ \"goal\": \"New goal for Sprint " +
                                    session.getString("SprintId") + " changed at " + System.currentTimeMillis() + "\" }"))
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);

    }
}