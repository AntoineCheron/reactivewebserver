package fr.cheron.antoine.reactivewebserver;

import com.typesafe.config.ConfigFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Config {

  public static final com.typesafe.config.Config configuration = ConfigFactory.defaultApplication().resolve();

  public static final String HOST = configuration.getString("http.host");
  public static final int PORT = configuration.getInt("http.port");

  public static final Scheduler APPLICATION_SCHEDULER = Schedulers.elastic();

}
