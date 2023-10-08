package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _1B_GetProjectsTest extends BaseSimulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");

    ScenarioBuilder scn = scenario("Get all projects test")
            .exec(
                    http("GET /rest/api/2/project")
                            .get("/rest/api/2/status")
                            .asJson()
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
