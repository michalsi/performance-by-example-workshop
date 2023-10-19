package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CrudTest extends Simulation {

    //ToDo: setup authentication
    private String baseUrl = "https://api.trello.com";

    ScenarioBuilder scenarioBuilder = scenario("Create new board")
        .exec(
                http("get boards")
                        .get("/1/members/me/boards")
                        .check(status().is(200)));

    {
        setUp(
                scenarioBuilder.injectOpen(atOnceUsers(1))
        ).protocols(http.baseUrl(baseUrl));
    }
}
