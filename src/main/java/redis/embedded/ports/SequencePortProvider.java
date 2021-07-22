package redis.embedded.ports;

import java.util.concurrent.atomic.AtomicInteger;
import redis.embedded.PortProvider;

public class SequencePortProvider implements PortProvider {
  private AtomicInteger currentPort = new AtomicInteger(26379);

  public SequencePortProvider() {}

  public SequencePortProvider(int currentPort) {
    this.currentPort.set(currentPort);
  }

  public void setCurrentPort(int port) {
    currentPort.set(port);
  }

  @Override
  public int next() {
    return currentPort.getAndIncrement();
  }
}
