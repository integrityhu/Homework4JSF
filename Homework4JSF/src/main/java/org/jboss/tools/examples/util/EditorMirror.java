package org.jboss.tools.examples.util;

import org.jboss.tools.examples.annotation.EditοrFieldInfo;

import javax.faces.component.UIComponent;

import org.jboss.tools.examples.annotation.EditorInfo;

public class EditorMirror {

    @EditorInfo(fields={@EditοrFieldInfo()})
    public UIComponent parent;
    
}
