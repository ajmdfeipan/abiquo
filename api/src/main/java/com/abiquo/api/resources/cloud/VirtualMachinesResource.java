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

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.wink.common.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.abiquo.api.resources.AbstractResource;
import com.abiquo.api.services.cloud.HypervisorService;
import com.abiquo.api.services.cloud.VirtualApplianceService;
import com.abiquo.api.services.cloud.VirtualMachineService;
import com.abiquo.api.transformer.ModelTransformer;
import com.abiquo.api.util.IRESTBuilder;
import com.abiquo.server.core.cloud.Hypervisor;
import com.abiquo.server.core.cloud.VirtualAppliance;
import com.abiquo.server.core.cloud.VirtualMachine;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachinesDto;
import com.abiquo.server.core.infrastructure.Machine;
import com.abiquo.server.core.infrastructure.Rack;

@Parent(VirtualApplianceResource.class)
@Path(VirtualMachinesResource.VIRTUAL_MACHINES_PATH)
@Controller
public class VirtualMachinesResource extends AbstractResource
{
    public static final String VIRTUAL_MACHINES_PATH = "virtualmachines";

    @Autowired
    VirtualMachineService service;

    @Autowired
    VirtualApplianceService vappService;

    @Autowired
    HypervisorService hypervisorService;

    @GET
    public VirtualMachinesDto getVirtualMachines(
        @PathParam(VirtualDatacenterResource.VIRTUAL_DATACENTER) Integer vdcId,
        @PathParam(VirtualApplianceResource.VIRTUAL_APPLIANCE) Integer vappId,
        @Context IRESTBuilder restBuilder) throws Exception
    {
        VirtualAppliance vapp = vappService.getVirtualAppliance(vdcId, vappId);

        List<VirtualMachine> all = service.findByVirtualAppliance(vapp);
        VirtualMachinesDto vappsDto = new VirtualMachinesDto();

        if (all != null && !all.isEmpty())
        {
            for (VirtualMachine v : all)
            {
                vappsDto.add(createCloudTransferObject(v, vapp.getVirtualDatacenter().getId(), vapp
                    .getId(), restBuilder));
            }
        }

        return vappsDto;
    }

    public static VirtualMachinesDto createAdminTransferObjects(Collection<VirtualMachine> vms,
        IRESTBuilder restBuilder) throws Exception
    {
        VirtualMachinesDto machines = new VirtualMachinesDto();
        for (VirtualMachine vm : vms)
        {
            VirtualMachineDto vmDto =
                ModelTransformer.transportFromPersistence(VirtualMachineDto.class, vm);

            Hypervisor hypervisor = vm.getHypervisor();
            Machine machine = (hypervisor == null) ? null : hypervisor.getMachine();
            Rack rack = (machine == null) ? null : machine.getRack();

            vmDto.addLinks(restBuilder.buildVirtualMachineAdminLinks((rack == null) ? null : rack
                .getDatacenter().getId(), (rack == null) ? null : rack.getId(), (machine == null)
                ? null : machine.getId(), vm.getEnterprise().getId(), vm.getUser().getId()));

            machines.add(vmDto);
        }

        return machines;
    }

    /**
     * Converts to the transfer object for the VirtualMachine POJO when the request is from the
     * /cloud URI
     * 
     * @param v virtual machine
     * @param vdcId identifier of the virtual datacenter
     * @param vappId identifier of the virtual appliance
     * @param restBuilder {@link IRESTBuilder} object injected by context.
     * @return the generate {@link VirtualMachineDto} object.
     * @throws Exception
     */
    public static VirtualMachineDto createCloudTransferObject(VirtualMachine v, Integer vdcId,
        Integer vappId, IRESTBuilder restBuilder) throws Exception
    {
        VirtualMachineDto vmDto =
            ModelTransformer.transportFromPersistence(VirtualMachineDto.class, v);
        vmDto.addLinks(restBuilder.buildVirtualMachineCloudLinks(vdcId, vappId, v.getId()));

        return vmDto;
    }
}
