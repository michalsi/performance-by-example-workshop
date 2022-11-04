
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class BasicUserJourneyTest extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://10.108.14.189:8080")
            .disableFollowRedirect()
            .disableAutoReferer()
            .acceptHeader("*/*")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("pl,en-US;q=0.7,en;q=0.3")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:106.0) Gecko/20100101 Firefox/106.0");

    private Map<CharSequence, String> headers_0 = Map.ofEntries(
            Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Logout!default.jspa?atl_token=WS1J-VZE9-5PSK-PEYR_0b9cc556930b01717ad5089cfe2b6c074e2ddd51_lin"),
            Map.entry("Upgrade-Insecure-Requests", "1")
    );

    private Map<CharSequence, String> headers_1 = Map.ofEntries(
            Map.entry("Content-Type", "application/json"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/login.jsp")
    );

    private Map<CharSequence, String> headers_4 = Map.ofEntries(
            Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/login.jsp"),
            Map.entry("Upgrade-Insecure-Requests", "1")
    );

    private Map<CharSequence, String> headers_5 = Map.ofEntries(
            Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"),
            Map.entry("Referer", "http://10.108.14.189:8080/login.jsp"),
            Map.entry("Upgrade-Insecure-Requests", "1")
    );

    private Map<CharSequence, String> headers_6 = Map.ofEntries(
            Map.entry("Content-Type", "application/json"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa")
    );

    private Map<CharSequence, String> headers_7 = Map.ofEntries(
            Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_8 = Map.ofEntries(
            Map.entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa"),
            Map.entry("Upgrade-Insecure-Requests", "1")
    );

    private Map<CharSequence, String> headers_9 = Map.ofEntries(
            Map.entry("Accept", "application/json, text/javascript, */*; q=0.01"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_10 = Map.ofEntries(
            Map.entry("Accept", "application/json, text/javascript, */*; q=0.01"),
            Map.entry("Content-Type", "application/json; charset=utf-8"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_12 = Map.ofEntries(
            Map.entry("Accept", "application/json, text/javascript, */*; q=0.01"),
            Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
            Map.entry("Referer", "http://10.108.14.189:8080/plugins/servlet/gadgets/ifr?container=atlassian&mid=10003&country=US&lang=en&view=default&view-params=%7B%22writable%22%3A%22false%22%7D&st=atlassian%3Avsog3hAAeRqzbjFqlzYaG0HiMaDZ%2BsEAnq645G8o5x9qUfj4419pVHZLIyanAFH1WUeNCjltLNYN5%2FqxrUFj5CjCY5WUMIq5zgkDZ%2FvI0Syu1dNSKwr0TzEr24Ycu0qg3kneqasWm258VYvn3gDH7oL4fjhNb2pQKfxPFwnJshl9aCNGwtQ00cNCJ%2FS%2B3m1%2BaIo%2BB6oODOCFQosHmBRZQR1srtI%2BoTnipTQtusA5rYpgFzmpE2A0hRO0TClXWybx7NQnWzQkyrSpqou%2FVqQ3TdN4cjBCRhX%2FOPVFm%2BPgWoKTXMbr3O%2FL12zTI7egFEEWiibBfA%3D%3D&up_isConfigured=true&up_isReallyConfigured=false&up_title=Your+Company+Jira&up_titleRequired=true&up_numofentries=5&up_refresh=false&up_maxProviderLabelCharacters=50&up_rules=&up_renderingContext=&up_keys=__all_projects__&up_itemKeys=&up_username=&url=http%3A%2F%2F10.108.14.189%3A8080%2Frest%2Fgadgets%2F1.0%2Fg%2Fcom.atlassian.streams.streams-jira-plugin%3Aactivitystream-gadget%2Fgadgets%2Factivitystream-gadget.xml&libs=auth-refresh"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_13 = Map.ofEntries(
            Map.entry("Content-Type", "application/json"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/plugins/servlet/gadgets/ifr?container=atlassian&mid=10003&country=US&lang=en&view=default&view-params=%7B%22writable%22%3A%22false%22%7D&st=atlassian%3Avsog3hAAeRqzbjFqlzYaG0HiMaDZ%2BsEAnq645G8o5x9qUfj4419pVHZLIyanAFH1WUeNCjltLNYN5%2FqxrUFj5CjCY5WUMIq5zgkDZ%2FvI0Syu1dNSKwr0TzEr24Ycu0qg3kneqasWm258VYvn3gDH7oL4fjhNb2pQKfxPFwnJshl9aCNGwtQ00cNCJ%2FS%2B3m1%2BaIo%2BB6oODOCFQosHmBRZQR1srtI%2BoTnipTQtusA5rYpgFzmpE2A0hRO0TClXWybx7NQnWzQkyrSpqou%2FVqQ3TdN4cjBCRhX%2FOPVFm%2BPgWoKTXMbr3O%2FL12zTI7egFEEWiibBfA%3D%3D&up_isConfigured=true&up_isReallyConfigured=false&up_title=Your+Company+Jira&up_titleRequired=true&up_numofentries=5&up_refresh=false&up_maxProviderLabelCharacters=50&up_rules=&up_renderingContext=&up_keys=__all_projects__&up_itemKeys=&up_username=&url=http%3A%2F%2F10.108.14.189%3A8080%2Frest%2Fgadgets%2F1.0%2Fg%2Fcom.atlassian.streams.streams-jira-plugin%3Aactivitystream-gadget%2Fgadgets%2Factivitystream-gadget.xml&libs=auth-refresh")
    );

    private Map<CharSequence, String> headers_14 = Map.ofEntries(
            Map.entry("Accept", "application/xml, text/xml, */*; q=0.01"),
            Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
            Map.entry("Referer", "http://10.108.14.189:8080/plugins/servlet/gadgets/ifr?container=atlassian&mid=10003&country=US&lang=en&view=default&view-params=%7B%22writable%22%3A%22false%22%7D&st=atlassian%3Avsog3hAAeRqzbjFqlzYaG0HiMaDZ%2BsEAnq645G8o5x9qUfj4419pVHZLIyanAFH1WUeNCjltLNYN5%2FqxrUFj5CjCY5WUMIq5zgkDZ%2FvI0Syu1dNSKwr0TzEr24Ycu0qg3kneqasWm258VYvn3gDH7oL4fjhNb2pQKfxPFwnJshl9aCNGwtQ00cNCJ%2FS%2B3m1%2BaIo%2BB6oODOCFQosHmBRZQR1srtI%2BoTnipTQtusA5rYpgFzmpE2A0hRO0TClXWybx7NQnWzQkyrSpqou%2FVqQ3TdN4cjBCRhX%2FOPVFm%2BPgWoKTXMbr3O%2FL12zTI7egFEEWiibBfA%3D%3D&up_isConfigured=true&up_isReallyConfigured=false&up_title=Your+Company+Jira&up_titleRequired=true&up_numofentries=5&up_refresh=false&up_maxProviderLabelCharacters=50&up_rules=&up_renderingContext=&up_keys=__all_projects__&up_itemKeys=&up_username=&url=http%3A%2F%2F10.108.14.189%3A8080%2Frest%2Fgadgets%2F1.0%2Fg%2Fcom.atlassian.streams.streams-jira-plugin%3Aactivitystream-gadget%2Fgadgets%2Factivitystream-gadget.xml&libs=auth-refresh"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_15 = Map.of("Referer", "http://10.108.14.189:8080/secure/Dashboard.jspa");

    private Map<CharSequence, String> headers_16 = Map.ofEntries(
            Map.entry("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01"),
            Map.entry("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
            Map.entry("Referer", "http://10.108.14.189:8080/plugins/servlet/gadgets/ifr?container=atlassian&mid=10003&country=US&lang=en&view=default&view-params=%7B%22writable%22%3A%22false%22%7D&st=atlassian%3Avsog3hAAeRqzbjFqlzYaG0HiMaDZ%2BsEAnq645G8o5x9qUfj4419pVHZLIyanAFH1WUeNCjltLNYN5%2FqxrUFj5CjCY5WUMIq5zgkDZ%2FvI0Syu1dNSKwr0TzEr24Ycu0qg3kneqasWm258VYvn3gDH7oL4fjhNb2pQKfxPFwnJshl9aCNGwtQ00cNCJ%2FS%2B3m1%2BaIo%2BB6oODOCFQosHmBRZQR1srtI%2BoTnipTQtusA5rYpgFzmpE2A0hRO0TClXWybx7NQnWzQkyrSpqou%2FVqQ3TdN4cjBCRhX%2FOPVFm%2BPgWoKTXMbr3O%2FL12zTI7egFEEWiibBfA%3D%3D&up_isConfigured=true&up_isReallyConfigured=false&up_title=Your+Company+Jira&up_titleRequired=true&up_numofentries=5&up_refresh=false&up_maxProviderLabelCharacters=50&up_rules=&up_renderingContext=&up_keys=__all_projects__&up_itemKeys=&up_username=&url=http%3A%2F%2F10.108.14.189%3A8080%2Frest%2Fgadgets%2F1.0%2Fg%2Fcom.atlassian.streams.streams-jira-plugin%3Aactivitystream-gadget%2Fgadgets%2Factivitystream-gadget.xml&libs=auth-refresh"),
            Map.entry("X-Requested-With", "XMLHttpRequest")
    );

    private Map<CharSequence, String> headers_24 = Map.ofEntries(
            Map.entry("Content-Type", "application/json"),
            Map.entry("Origin", "http://10.108.14.189:8080"),
            Map.entry("Referer", "http://10.108.14.189:8080/secure/Logout!default.jspa?atl_token=WS1J-VZE9-5PSK-PEYR_0b0189467b3a2a9fcf27615993d10ef6e411c57b_lin")
    );


    private ScenarioBuilder scn = scenario("BasicUserJourneyTest")
            .exec(
                    http("request_0")
                            .get("/login.jsp")
                            .headers(headers_0)
            )
            .pause(Duration.ofMillis(110))
            .exec(
                    http("request_1")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_1)
                            .body(RawFileBody("basicuserjourneytest/0001_request.json"))
            )
            .exec(
                    http("request_2")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_1)
                            .body(RawFileBody("basicuserjourneytest/0002_request.json"))
            )
            .pause(Duration.ofMillis(344))
            .exec(
                    http("request_3")
                            .post("/rest/analytics/1.0/publish/bulk")
                            .headers(headers_1)
                            .body(RawFileBody("basicuserjourneytest/0003_request.json"))
            )
            .pause(7)
            .exec(
                    http("request_4")
                            .post("/login.jsp")
                            .headers(headers_4)
                            .formParam("os_username", "admin")
                            .formParam("os_password", "uEHr,FwKIJ,.zEMa5Vy8zfI6")
                            .formParam("os_destination", "")
                            .formParam("user_role", "")
                            .formParam("atl_token", "")
                            .formParam("login", "Log In")
                            .check(status().is(302))
            )
            .exec(
                    http("request_5")
                            .get("/")
                            .headers(headers_5)
            )
            .pause(Duration.ofMillis(161))
            .exec(
                    http("request_6")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_6)
                            .body(RawFileBody("basicuserjourneytest/0006_request.json"))
            )
            .pause(Duration.ofMillis(129))
            .exec(
                    http("request_7")
                            .post("/plugins/servlet/gadgets/dashboard-diagnostics")
                            .headers(headers_7)
                            .formParam("uri", "http://10.108.14.189:8080/secure/Dashboard.jspa")
            )
            .exec(
                    http("request_8")
                            .get("/plugins/servlet/gadgets/ifr?container=atlassian&mid=10003&country=US&lang=en&view=default&view-params=%7B%22writable%22%3A%22false%22%7D&st=atlassian%3Avsog3hAAeRqzbjFqlzYaG0HiMaDZ%2BsEAnq645G8o5x9qUfj4419pVHZLIyanAFH1WUeNCjltLNYN5%2FqxrUFj5CjCY5WUMIq5zgkDZ%2FvI0Syu1dNSKwr0TzEr24Ycu0qg3kneqasWm258VYvn3gDH7oL4fjhNb2pQKfxPFwnJshl9aCNGwtQ00cNCJ%2FS%2B3m1%2BaIo%2BB6oODOCFQosHmBRZQR1srtI%2BoTnipTQtusA5rYpgFzmpE2A0hRO0TClXWybx7NQnWzQkyrSpqou%2FVqQ3TdN4cjBCRhX%2FOPVFm%2BPgWoKTXMbr3O%2FL12zTI7egFEEWiibBfA%3D%3D&up_isConfigured=true&up_isReallyConfigured=false&up_title=Your+Company+Jira&up_titleRequired=true&up_numofentries=5&up_refresh=false&up_maxProviderLabelCharacters=50&up_rules=&up_renderingContext=&up_keys=__all_projects__&up_itemKeys=&up_username=&url=http%3A%2F%2F10.108.14.189%3A8080%2Frest%2Fgadgets%2F1.0%2Fg%2Fcom.atlassian.streams.streams-jira-plugin%3Aactivitystream-gadget%2Fgadgets%2Factivitystream-gadget.xml&libs=auth-refresh")
                            .headers(headers_8)
            )
            .exec(
                    http("request_9")
                            .get("/rest/troubleshooting/1.0/check/admin?_=1667571778719")
                            .headers(headers_9)
            )
            .exec(
                    http("request_10")
                            .get("/rest/gadget/1.0/issueTable/jql?num=10&tableContext=jira.table.cols.dashboard&addDefault=true&enableSorting=true&paging=true&showActions=true&jql=assignee+%3D+currentUser()+AND+resolution+%3D+unresolved+ORDER+BY+priority+DESC%2C+created+ASC&sortBy=&startIndex=0&_=1667571778718")
                            .headers(headers_10)
            )
            .exec(
                    http("request_11")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_6)
                            .body(RawFileBody("basicuserjourneytest/0011_request.json"))
            )
            .exec(
                    http("request_12")
                            .get("/rest/activity-stream/1.0/preferences?_=1667571779272")
                            .headers(headers_12)
            )
            .exec(
                    http("request_13")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_13)
                            .body(RawFileBody("basicuserjourneytest/0013_request.json"))
            )
            .exec(
                    http("request_14")
                            .get("/plugins/servlet/streams?maxResults=5&relativeLinks=true&_=1667571779273")
                            .headers(headers_14)
            )
            .exec(
                    http("request_15")
                            .get("/rest/internal/1.0/licensebanner/markup")
                            .headers(headers_15)
            )
            .exec(
                    http("request_16")
                            .get("/s/1oxy4p/920000/1dlckms/9.1.1/_/download/resources/com.atlassian.streams.streams-jira-plugin:date-en-US/date.js?callback=ActivityStreams.loadDateJs&_=1667571779274")
                            .headers(headers_16)
            )
            .exec(
                    http("request_17")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_13)
                            .body(RawFileBody("basicuserjourneytest/0017_request.json"))
            )
            .exec(
                    http("request_18")
                            .post("/rest/analytics/1.0/publish/bulk")
                            .headers(headers_6)
                            .body(RawFileBody("basicuserjourneytest/0018_request.json"))
            )
            .pause(Duration.ofMillis(130))
            .exec(
                    http("request_19")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_6)
                            .body(RawFileBody("basicuserjourneytest/0019_request.json"))
            )
            .exec(
                    http("request_20")
                            .get("/s/a249aa4f9adf2699986c7815e0900573-CDN/1oxy4p/920000/1dlckms/0aa7c52edd9fbc1b0da79744914cb7d4/_/download/contextbatch/js/com.atlassian.jira.plugins.jira-quicksearch-plugin:5,-_super,-jira.global,-atl.dashboard,-com.atlassian.jira.plugins.jira-development-integration-plugin:0,-com.atlassian.jira.jira-header-plugin:newsletter-signup-tip/batch.js?locale=en-US")
                            .headers(headers_15)
            )
            .exec(
                    http("request_21")
                            .post("/rest/analytics/1.0/publish/bulk")
                            .headers(headers_13)
                            .body(RawFileBody("basicuserjourneytest/0021_request.json"))
            )
            .pause(3)
            .exec(
                    http("request_22")
                            .get("/logout?atl_token=WS1J-VZE9-5PSK-PEYR_0b0189467b3a2a9fcf27615993d10ef6e411c57b_lin")
                            .headers(headers_8)
                            .check(status().is(302))
            )
            .exec(
                    http("request_23")
                            .get("/secure/Logout!default.jspa?atl_token=WS1J-VZE9-5PSK-PEYR_0b0189467b3a2a9fcf27615993d10ef6e411c57b_lin")
                            .headers(headers_8)
            )
            .pause(Duration.ofMillis(102))
            .exec(
                    http("request_24")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_24)
                            .body(RawFileBody("basicuserjourneytest/0024_request.json"))
            )
            .exec(
                    http("request_25")
                            .post("/rest/wrm/2.0/resources")
                            .headers(headers_24)
                            .body(RawFileBody("basicuserjourneytest/0025_request.json"))
            )
            .pause(Duration.ofMillis(396))
            .exec(
                    http("request_26")
                            .post("/rest/analytics/1.0/publish/bulk")
                            .headers(headers_24)
                            .body(RawFileBody("basicuserjourneytest/0026_request.json"))
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
