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

package com.abiquo.api.resources.cloud;

import static com.abiquo.api.common.UriTestResolver.resolveVirtualMachinesURI;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Random;

import javax.ws.rs.core.Response.Status;

import org.apache.wink.client.ClientResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.abiquo.api.resources.AbstractJpaGeneratorIT;
import com.abiquo.server.core.cloud.NodeVirtualImage;
import com.abiquo.server.core.cloud.VirtualAppliance;
import com.abiquo.server.core.cloud.VirtualDatacenter;
import com.abiquo.server.core.cloud.VirtualMachine;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.abiquo.server.core.enterprise.Enterprise;
import com.abiquo.server.core.infrastructure.Datacenter;

public class VirtualMachinesResourceIT extends AbstractJpaGeneratorIT
{

    protected Enterprise ent;

    protected Datacenter datacenter;

    protected VirtualDatacenter vdc;

    protected VirtualAppliance vapp;

    @BeforeMethod
    public void setUp()
    {
        ent = enterpriseGenerator.createUniqueInstance();
        datacenter = datacenterGenerator.createUniqueInstance();
        vdc = vdcGenerator.createInstance(datacenter, ent);
        vapp = vappGenerator.createInstance(vdc);
    }

    @AfterMethod
    public void tearDown()
    {
        tearDown("ip_pool_management", "rasd_management", "nodevirtualimage", "virtualmachine",
            "virtualimage", "virtualapp", "virtualdatacenter", "vlan_network", 
            "network_configuration", "dhcp_service", "remote_service", "hypervisor",
            "physicalmachine", "rack", "datacenter", "network", "user", "role", "enterprise");
    }

    /**
     * Create a virtual appliance. Insert tow virtual machines in the virtual appliance and check it.
     * Check also an 'empty' virtual appliance result
     */
    @Test
    public void getVirtualMachinesTest()
    {
        // Create a virtual machine
        VirtualMachine vm = vmGenerator.createInstance(ent);
        VirtualMachine vm2 = vmGenerator.createInstance(ent);
        
        VirtualAppliance vapp2 = vappGenerator.createInstance(vdc);

        // Asociate it to the created virtual appliance
        NodeVirtualImage nvi = nodeVirtualImageGenerator.createInstance(vapp, vm);
        NodeVirtualImage nvi2 = nodeVirtualImageGenerator.createInstance(vapp, vm2);

        setup(ent, datacenter, vdc, vapp, vapp2, vm.getUser().getRole(), vm2.getUser().getRole(), vm.getUser(), vm2.getUser(), vm
            .getVirtualImage(), vm2.getVirtualImage(), vm, vm2, nvi, nvi2);

        // Check for vapp
        ClientResponse response = get(resolveVirtualMachinesURI(vdc.getId(), vapp.getId()));
        assertEquals(response.getStatusCode(), Status.OK.getStatusCode());
        VirtualMachinesDto vms = response.getEntity(VirtualMachinesDto.class);
        assertNotNull(vms);
        assertNotNull(vms.getCollection());
        assertEquals(vms.getCollection().size(), 2);
        
        // Check for vapp2
        response = get(resolveVirtualMachinesURI(vdc.getId(), vapp2.getId()));
        assertEquals(response.getStatusCode(), Status.OK.getStatusCode());
        vms = response.getEntity(VirtualMachinesDto.class);
        assertNotNull(vms);
        assertNotNull(vms.getCollection());
        assertEquals(vms.getCollection().size(), 0);

    }
    
    /**
     * Check the virtual machines of invalid vitual appliance id.
     * 
     * Server response should return a 404 NOT FOUND status code
     */
    @Test
    public void getVirtualMachinesRaises404WhenInvalidVirtualApplianceId()
    {
        setup(ent, datacenter, vdc, vapp);
        
        ClientResponse response = get(resolveVirtualMachinesURI(vdc.getId(), new Random().nextInt()));
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }
    
    /**
     * Check the virtual machines list of an invalid virtualdatacenter 
     * for a valid virtual appliance id.
     * 
     * Server response should return a 404 NOT FOUND status code
     */
    @Test
    public void getVirtualMachinesRaises404WhenInvalidVirtualDatacenterId()
    {
        setup(ent, datacenter, vdc, vapp);
        
        ClientResponse response = get(resolveVirtualMachinesURI(new Random().nextInt(), vapp.getId()));
        assertEquals(response.getStatusCode(), Status.NOT_FOUND.getStatusCode());
    }

}
