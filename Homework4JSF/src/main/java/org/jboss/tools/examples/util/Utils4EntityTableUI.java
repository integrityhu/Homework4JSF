package org.jboss.tools.examples.util;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.jboss.tools.examples.annotation.EntityInfo;

public class Utils4EntityTableUI {

    public static Map<Integer, UIComponent> getTableFieldsFromEntityInfo(FacesContext fc, String var, Class<?> clazz) {
        ELContext el = fc.getELContext();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        Map<Integer, UIComponent> weightMap = new TreeMap<Integer, UIComponent>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EntityInfo.class)) {                
                UIComponent column = createTableColumn(el, elFactory, var, field);
                EntityInfo entityInfo = field.getAnnotation(EntityInfo.class);
                weightMap.put(new Integer(entityInfo.weight()), column);
            }
        }
        return weightMap;
    }

    private static UIComponent createTableColumn(ELContext el, ExpressionFactory elFactory, String var, Field field) {
        HtmlColumn column = new HtmlColumn();
        column.setId(field.getName() + "_column");
        ValueExpression valueExp = elFactory.createValueExpression(el, "#" + "{"+var+"." + field.getName() + "}", String.class);
        UIOutput header = new HtmlOutputText();
        EntityInfo entityInfo = field.getAnnotation(EntityInfo.class);
        String info = entityInfo.info();
        header.setValue(info);
        header.setParent(column);
        column.getFacets().put("header", header);
        HtmlOutputText content = new HtmlOutputText(); 
        content.setParent(column);
        content.setValueExpression("value",valueExp);
        column.getChildren().add(content);
        return column;
    }

    public static void addFieldsToTable(UIComponent parent, Map<Integer, UIComponent> weightHashMap) {
        if (weightHashMap.size() > 0) {
            Iterator<Entry<Integer, UIComponent>> iter = weightHashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<Integer, UIComponent> item = iter.next();
                addToTable(parent, item.getValue());
            }
        }        
    }

    private static void addToTable(UIComponent parent, UIComponent column) {
        column.setParent(parent);
        parent.getChildren().add(column);        
    }
}
