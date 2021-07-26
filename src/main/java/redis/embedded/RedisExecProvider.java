package redis.embedded;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import redis.embedded.util.Architecture;
import redis.embedded.util.JarUtil;
import redis.embedded.util.OS;
import redis.embedded.util.OsArchitecture;
import redis.embedded.util.Preconditions;

public class RedisExecProvider {

  private final Map<OsArchitecture, String> executables = new HashMap<OsArchitecture, String>();

  public static RedisExecProvider defaultProvider() {
    return new RedisExecProvider();
  }

  private RedisExecProvider() {
    initExecutables();
  }

  private void initExecutables() {
    // TODO: Include windows binaries
    //        executables.put(OsArchitecture.WINDOWS_x86, "redis-server-4.0.10.exe");
    //        executables.put(OsArchitecture.WINDOWS_x86_64, "redis-server-4.0.10.exe");

    executables.put(OsArchitecture.UNIX_x86, "redis-server-4.0.10-32");
    executables.put(OsArchitecture.UNIX_x86_64, "redis-server-4.0.10");

    executables.put(OsArchitecture.MAC_OS_X_x86, "redis-server-4.0.10.app");
    executables.put(OsArchitecture.MAC_OS_X_x86_64, "redis-server-4.0.10.app");
  }

  public RedisExecProvider override(OS os, String executable) {
    Preconditions.checkNotNull(executable);
    for (Architecture arch : Architecture.values()) {
      override(os, arch, executable);
    }
    return this;
  }

  public RedisExecProvider override(OS os, Architecture arch, String executable) {
    Preconditions.checkNotNull(executable);
    executables.put(new OsArchitecture(os, arch), executable);
    return this;
  }

  public File get() throws IOException {
    OsArchitecture osArch = OsArchitecture.detect();
    String executablePath = executables.get(osArch);
    return fileExists(executablePath)
        ? new File(executablePath)
        : JarUtil.extractExecutableFromJar(executablePath);
  }

  private boolean fileExists(String executablePath) {
    return new File(executablePath).exists();
  }
}
