package simulations;

import io.gatling.javaapi.core.Simulation;

import static utils.SystemHelper.getPropertyIfNotEmptyOrThrow;

public class BaseSimulation extends Simulation {

    final String BASE_URL = getPropertyIfNotEmptyOrThrow("url", "No base url passed");
    final String TOKEN = getPropertyIfNotEmptyOrThrow("token", "No Authorisation Token passed");
}
