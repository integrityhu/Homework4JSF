package org.jboss.tools.examples.util;

import org.jboss.tools.examples.annotation.QueryFieldInfo;
import org.jboss.tools.examples.annotation.QueryInfo;

public class QueryMirror {

    @QueryInfo(fields={@QueryFieldInfo()})
    public String query;
    
}
