package fr.cheron.antoine.reactivewebserver;

import com.typesafe.config.ConfigFactory;

public class Config {

  public static final com.typesafe.config.Config configuration = ConfigFactory.defaultApplication().resolve();

  public static final String HOST = configuration.getString("http.host");
  public static final int PORT = configuration.getInt("http.port");

}
