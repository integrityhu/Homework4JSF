package org.jboss.tools.examples.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.el.ELException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.tools.examples.model.SystemUser;

@Stateless
public class ServiceBean {

    @Inject
    private EntityManager em;

    public void mergeSystemUser(SystemUser systemUser) throws ELException {
        em.merge(systemUser);
    }

    public List<SystemUser> getUserList() {
        Query q = em.createQuery("from SystemUser");
        return q.getResultList();
    }

}
