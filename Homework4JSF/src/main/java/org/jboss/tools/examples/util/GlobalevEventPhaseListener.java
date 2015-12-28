package org.jboss.tools.examples.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import  javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

public class GlobalevEventPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 6976716893196065905L;
    
    private static final Logger log = Logger.getLogger("handler.CONSOLE");

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void beforePhase(final PhaseEvent event) {
        log.info(event.getPhaseId());
		final FacesContext facesContext = event.getFacesContext();
		final HttpSession httpSession = JSFUtil.getHttpSession(facesContext);

		if (httpSession != null) {
			final List globalEvents = getGlobalEvents(httpSession);
			globalEvents.stream().forEach(e -> {
			    log.info(e);
			});
		}
	}


	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private synchronized List getGlobalEvents(final HttpSession httpSession) {
		final List events = (List) httpSession.getAttribute(GlobalevHttpSessionController.EVENT_ATTRIBUTE_NAME);
		final List result = new ArrayList();

		if (events != null) {
			result.addAll(events);
			events.clear();
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void afterPhase(final PhaseEvent event) {
        final FacesContext facesContext = event.getFacesContext();
        final HttpSession httpSession = JSFUtil.getHttpSession(facesContext);

        if (httpSession != null) {            
            final List globalEvents = getGlobalEvents(httpSession);
            globalEvents.stream().forEach(e -> {
                System.out.println(e);
            });
        }
	}
}
