import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class BasicUserJourneyTest extends Simulation {

    final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .authorizationHeader("Bearer " + TOKEN)
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate");

    ScenarioBuilder scn = scenario("Get all projects test")
            .exec(
                    http("GET " + "/rest/api/2/project")
                            .get("/rest/api/2/status")
                            .asJson()
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
