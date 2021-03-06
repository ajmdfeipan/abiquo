/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.abiquo.abiserver.persistence.dao.user.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.abiquo.abiserver.business.hibernate.pojohb.user.UserHB;
import com.abiquo.abiserver.persistence.dao.user.UserDAO;
import com.abiquo.abiserver.persistence.hibernate.HibernateDAO;
import com.abiquo.abiserver.persistence.hibernate.HibernateDAOFactory;

/**
 * * Class that implements the extra DAO functions for the
 * {@link com.abiquo.abiserver.persistence.dao.user.UserDAO} interface
 * 
 * @author jdevesa@abiquo.com
 */
public class UserDAOHibernate extends HibernateDAO<UserHB, Integer> implements UserDAO
{

    /** Named queries */
    private static final String GET_USER_BY_USER_NAME = "GET_USER_BY_USER_NAME";

    private static final String GET_USERS_BY_PRIVILEGE = "GET_USERS_BY_PRIVILEGE";

    private static final String GET_USERS_BY_ROLE_DESC = "GET_USERS_BY_ROLE_DESC";

    /*
     * (non-Javadoc)
     * @see com.abiquo.abiserver.persistence.dao.user.UserDAO#getUserByUserName(java.lang.String)
     */
    @Override
    public UserHB getUserByUserName(String username)
    {
        UserHB requestedUser = new UserHB();

        Session session = HibernateDAOFactory.getSessionFactory().getCurrentSession();
        Query userQuery = session.getNamedQuery(GET_USER_BY_USER_NAME);
        userQuery.setString("username", username);
        requestedUser = (UserHB) userQuery.uniqueResult();

        return requestedUser;
    }

    @Override
    public String getEmailByUserName(String username)
    {
        return getUserByUserName(username).getEmail();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserHB> getUsersByUserPrivileges(String privileges, Integer enterprise)
    {
        List<UserHB> requestedUser = new ArrayList<UserHB>();

        Session session = HibernateDAOFactory.getSessionFactory().getCurrentSession();
        Query userQuery = session.getNamedQuery(GET_USERS_BY_PRIVILEGE);
        userQuery.setString("security", privileges);
        userQuery.setInteger("enterprise", enterprise);
        requestedUser = userQuery.list();

        return requestedUser;
    }

    public UserHB findUserHBByName(String name)
    {
        return (UserHB) getSession().createCriteria(UserHB.class)
            .add(Restrictions.eq("user", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserHB> getUsersByUserRol(String role, Integer enterprise)
    {
        List<UserHB> requestedUser = new ArrayList<UserHB>();

        Session session = HibernateDAOFactory.getSessionFactory().getCurrentSession();
        Query userQuery = session.getNamedQuery(GET_USERS_BY_ROLE_DESC);
        userQuery.setString("roleDescription", role);
        userQuery.setInteger("enterprise", enterprise);
        requestedUser = userQuery.list();

        return requestedUser;

    }
}
