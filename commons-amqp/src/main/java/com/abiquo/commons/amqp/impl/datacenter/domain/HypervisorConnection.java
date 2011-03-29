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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.02 at 03:37:41 PM CET 
//

package com.abiquo.commons.amqp.impl.datacenter.domain;

import java.net.URL;

public class HypervisorConnection
{
    protected HypervisorType hypervisorType;

    protected String ip;

    protected String loginUser;

    protected String loginPassword;

    public String getIp()
    {
        return ip;
    }

    public void setIp(String value)
    {
        this.ip = value;
    }

    public String getLoginUser()
    {
        return loginUser;
    }

    public void setLoginUser(String value)
    {
        this.loginUser = value;
    }

    public String getLoginPassword()
    {
        return loginPassword;
    }

    public void setLoginPassword(String value)
    {
        this.loginPassword = value;
    }

    public HypervisorType getHypervisorType()
    {
        return hypervisorType;
    }

    public void setHypervisorType(HypervisorType hypervisorType)
    {
        this.hypervisorType = hypervisorType;
    }

    // TODO duplicated
    public enum HypervisorType
    {
        /** Virtual Box */
        VBOX(8889),

        /** KVM */
        KVM(8889),

        /** XEN */
        XEN_3(8889),

        /** ESXi */
        VMX_04(443),

        /** Hyper V */
        HYPERV_301(5985),

        /** Xen Server */
        XENSERVER(9363);

        public final int defaultPort;

        private HypervisorType(int port)
        {
            this.defaultPort = port;
        }
        
        public String getConnectionURI(String ip)
        {
            switch (this)
            {
                case VMX_04:
                    //sdkUrl = new URL("https://" + ip + "/sdk");
                    return String.format("https://%s:%d/sdk", ip, defaultPort);
             
                default:
                    return "NOT IMPLEMENTED FOR OTHER HYPERVISORS";
            }
        }
        

    }
}
