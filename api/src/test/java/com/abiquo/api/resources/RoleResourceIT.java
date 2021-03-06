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

package com.abiquo.api.resources;

import static com.abiquo.api.common.Assert.assertLinkExist;
import static com.abiquo.api.common.UriTestResolver.resolveRoleURI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientWebException;
import org.apache.wink.client.Resource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.abiquo.server.core.enterprise.Role;
import com.abiquo.server.core.enterprise.RoleDto;

public class RoleResourceIT extends AbstractJpaGeneratorIT
{

    @Test
    public void getRoleDoesntExist() throws ClientWebException
    {
        Role role = roleGenerator.createUniqueInstance();
        setup(role);

        Resource resource = client.resource(resolveRoleURI(12345));

        ClientResponse response = resource.accept(MediaType.APPLICATION_XML).get();
        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void getRole() throws Exception
    {
        Role role = roleGenerator.createUniqueInstance();
        setup(role);

        Resource resource = client.resource(resolveRoleURI(role.getId()));

        ClientResponse response = resource.accept(MediaType.APPLICATION_XML).get();

        RoleDto dto = response.getEntity(RoleDto.class);

        assertNotNull(dto);
    }

    @Test
    public void roleContainCorrectLinks() throws ClientWebException
    {
        Role role = roleGenerator.createUniqueInstance();
        setup(role);

        String href = resolveRoleURI(role.getId());
        Resource resource = client.resource(href);

        RoleDto dto = resource.accept(MediaType.APPLICATION_XML).get(RoleDto.class);

        assertNotNull(dto.getLinks());

        assertLinkExist(dto, href, "edit");
    }
}
