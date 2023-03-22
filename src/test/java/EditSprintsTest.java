import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class EditSprintsTest extends Simulation {

    private static final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    private static final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");

    private ScenarioBuilder scn = scenario("Get boards and edit Sprints")
            .exec(
                    http("GET " + "/rest/agile/1.0/board")
                            .get("/rest/agile/1.0/board")
                            .queryParam("startAt", ThreadLocalRandom.current().nextInt(0, 2) * 50)
                            .asJson()
                            .check(jsonPath("$.values[*].id").findAll().saveAs("ids"))
            ).exec(
                    session -> {
                        List<String> ids = session.getList("ids");
                        String randomId = ids.get(new Random().nextInt(ids.size()));
                        Session newSession = session.set("id", randomId);
                        return newSession;
                    })
            .exec(
                    http("GET " + "rest/agile/1.0/sprint/{id}")
                            .get("/rest/agile/1.0/sprint/" + "#{id}")
                            .asJson()
            )
            .exec(
                    http("POST " + "/rest/agile/1.0/sprint/{id}")
                            .post("/rest/agile/1.0/sprint/" + "#{id}")
//                           .body(StringBody("{ \"goal\": \"new goal for Sprint #{id} @"+ System.currentTimeMillis() + "  \" }"))
                            .body(StringBody(session -> "{ \"goal\": \"new goal for Sprint " + session.getString("id") + " at " + System.currentTimeMillis() + "\" }"))
                            .asJson()
            );


    {
        setUp(scn.injectOpen(atOnceUsers(2))).protocols(httpProtocol);
    }

}
