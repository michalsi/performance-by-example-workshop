import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class BasicUserJourneyTest extends Simulation {
    private static final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    private static final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");

    HttpProtocolBuilder httpProtocol = http
                .baseUrl(BASE_URL)
                .authorizationHeader("Bearer "+ TOKEN)
                .acceptHeader("*/*")
                .contentTypeHeader("application/json")
                .acceptEncodingHeader("gzip, deflate");

    private ScenarioBuilder scn = scenario("BasicUserJourneyTest")
            .exec(
                    http("GET " +  "/rest/api/2/status")
                            .get("/rest/api/2/status")
                            .asJson()
            );


    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
