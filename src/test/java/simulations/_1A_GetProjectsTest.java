package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class _1A_GetProjectsTest extends BaseSimulation {

    // TODO Set up HTTP Protocol common configuration
    // docs: https://gatling.io/docs/gatling/reference/current/http/protocol/
    HttpProtocolBuilder httpProtocol;

    // TODO define test scenario
    // docs https://gatling.io/docs/gatling/reference/current/core/scenario/
    ScenarioBuilder scn = scenario("Get all projects test")
            // exec method is used to execute an action
            // https://gatling.io/docs/gatling/reference/current/core/scenario/#exec
            .exec(
                    // TODO add HTTP requests https://gatling.io/docs/gatling/reference/current/http/request/
                    // GET /rest/api/2/project
            );

    // TODO Define Injection profile
    // https://gatling.io/docs/gatling/reference/current/core/simulation/#setup
    {
//        setUp( TODO: scenario injection & protocols)

    }
}
