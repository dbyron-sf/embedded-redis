package redis.embedded.ports;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import redis.embedded.PortProvider;
import redis.embedded.exceptions.RedisBuildingException;

public class PredefinedPortProvider implements PortProvider {
  private final List<Integer> ports = new LinkedList<Integer>();
  private final Iterator<Integer> current;

  public PredefinedPortProvider(Collection<Integer> ports) {
    this.ports.addAll(ports);
    this.current = this.ports.iterator();
  }

  @Override
  public synchronized int next() {
    if (!current.hasNext()) {
      throw new RedisBuildingException("Run out of Redis ports!");
    }
    return current.next();
  }
}
