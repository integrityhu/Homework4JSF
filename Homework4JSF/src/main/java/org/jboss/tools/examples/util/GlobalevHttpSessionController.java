package org.jboss.tools.examples.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;

/**
 * Management of HTTP sessions and global event dispatching.
 * 
 * @author dahm
 */
@ApplicationScoped
public class GlobalevHttpSessionController {
  public static final String EVENT_ATTRIBUTE_NAME = "HttpSessionControllerEvent";

  private final List<HttpSession> _httpSessions = new ArrayList<HttpSession>();

  public List<HttpSession> getHttpSessions() {
    return new ArrayList<HttpSession>(_httpSessions);
  }

  public void addSession(final HttpSession httpSession) {
    assert httpSession != null : "httpSession != null";
    _httpSessions.add(httpSession);
  }

  public void removeSession(final HttpSession httpSession) {
    assert httpSession != null : "httpSession != null";
    _httpSessions.remove(httpSession);
  }
}
