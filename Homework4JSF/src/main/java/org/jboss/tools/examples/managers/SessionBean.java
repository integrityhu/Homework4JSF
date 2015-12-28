package org.jboss.tools.examples.managers;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;

import org.jboss.tools.examples.model.SystemUser;
import org.jboss.tools.examples.services.ServiceBean;
import org.jboss.tools.examples.util.Utils4EntityEditorUI;
import org.jboss.tools.examples.util.Utils4EntityTableUI;

/*
 * http://www.tutorialspoint.com/jsf/jsf_composite_components.htm
 * http://www.tutorialspoint.com/jsf/jsf_customconvertor_tag.htm
 * http://www.mkyong.com/jsf2/jsf-2-datatable-example/
 * RenderKit rk = fc.getRenderKit();
 * 
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {

	private static final long serialVersionUID = 2193274342943234563L;

	@Inject
	private ServiceBean service;

	private SystemUser value = new SystemUser();

	public SystemUser getValue() {
		return value;
	}

	public void setValue(SystemUser user) {
		this.value = user;
	}

	public void initValue() {
		if (value.getUserid() != null) {
			value = new SystemUser();
		}
		createForm();
	}

	public List<SystemUser> getUserList() {
		return service.getUserList();
	}

	public void save() {
		try {
			service.mergeSystemUser(value);
			createTable();
			value = new SystemUser();
		} catch (EJBException e) {
			Throwable ex = e.getCause();
			if (ex instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> msg = ((ConstraintViolationException) ex).getConstraintViolations();
				msg.forEach(c -> {
					StringBuffer validationExcMsg = new StringBuffer();
					ConstraintDescriptor<?> desc = c.getConstraintDescriptor();
					System.out.println(desc.getAttributes().get("message"));
					String temp = c.getMessageTemplate();
					System.out.println(temp);
					validationExcMsg.append(c.getPropertyPath()).append(":").append(c.getMessage());
					FacesMessage message = new FacesMessage(validationExcMsg.toString());
					FacesContext.getCurrentInstance().addMessage(null, message);
				});
			} else {
				FacesMessage message = new FacesMessage("Failed: " + e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}

	private void createTable() {
		FacesContext fc = FacesContext.getCurrentInstance();		
		UIViewRoot root = fc.getViewRoot();
		UIComponent table = root.findComponent(":userTable");
		if (table != null && table.getChildCount() == 0) {
			Utils4EntityTableUI.addFieldsToTable(table, Utils4EntityTableUI.getTableFieldsFromEntityInfo(fc, "u", SystemUser.class));
		}
	}

	public void createForm() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIViewRoot root = fc.getViewRoot();
		UIComponent form = root.findComponent("user");
		UIComponent grid = root.findComponent(":user:grid");
		if (form != null && grid != null && grid.getChildCount() == 0) {
			Utils4EntityEditorUI.addFieldsToEditor(grid, Utils4EntityEditorUI.getEditorFieldsFromEntityInfo(fc, SystemUser.class));
		}

	}
}
