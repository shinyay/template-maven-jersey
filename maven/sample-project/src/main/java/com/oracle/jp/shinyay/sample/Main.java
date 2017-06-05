package com.oracle.jp.shinyay.sample;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * Main class.
 *
 */
public class Main {
    public static final String BASE_URI;
    public static final String PROTOCOL;
    public static String HOST;
    public static final String HOST_LOCALHOST = "localhost";
    public static final String HOST_DEFAULT_ROOT = "0.0.0.0";
    public static final Optional<String> PORT;
    public static final Optional<String> APP_HOME;

    static{
        PROTOCOL = "http://";
        HOST = HOST_LOCALHOST;
        PORT = Optional.ofNullable(System.getenv("PORT"));
        APP_HOME = Optional.ofNullable(System.getenv("APP_HOME"));
        if(APP_HOME.isPresent()){
            HOST = HOST_DEFAULT_ROOT;
        }
        BASE_URI = PROTOCOL
                + HOST
                + ":"
                + PORT.orElseGet(() -> "8080")
                + "/";
    }

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("com.oracle.jp.shinyay.rest.resource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}

