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

package com.abiquo.server.core.infrastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;

import com.abiquo.model.enumerator.RemoteServiceType;
import com.abiquo.server.core.common.DefaultEntityBase;
import com.abiquo.server.core.enterprise.DatacenterLimits;
import com.abiquo.server.core.infrastructure.Machine.State;
import com.abiquo.server.core.infrastructure.network.Network;
import com.softwarementors.commons.bean.CaseInsensitiveStringPropertyComparator;
import com.softwarementors.validation.constraints.LeadingOrTrailingWhitespace;
import com.softwarementors.validation.constraints.Required;

@Entity
@Table(name = Datacenter.TABLE_NAME, uniqueConstraints = {})
@org.hibernate.annotations.Table(appliesTo = Datacenter.TABLE_NAME, indexes = {})
public class Datacenter extends DefaultEntityBase // DefaultEntityWithLimits
{

    // ****************************** JPA support *******************************
    public static final String TABLE_NAME = "datacenter";

    // DO NOT ACCESS: present due to needs of infrastructure support. *NEVER* call from business
    // code
    protected Datacenter()
    {
        // Just for JPA support
    }

    private final static String ID_COLUMN = "idDatacenter";

    @Id
    @Column(name = ID_COLUMN, nullable = false)
    @GeneratedValue
    private Integer id;

    @Override
    public Integer getId()
    {
        return this.id;
    }

    // ******************************* Properties *******************************
    public final static String LOCATION_PROPERTY = "location";

    private final static boolean LOCATION_REQUIRED = true;

    final static int LOCATION_LENGTH_MIN = 1;

    final static int LOCATION_LENGTH_MAX = 100;

    private final static boolean LOCATION_LEADING_OR_TRAILING_WHITESPACES_ALLOWED = false;

    private final static String LOCATION_COLUMN = "situation";

    @Column(name = LOCATION_COLUMN, nullable = !LOCATION_REQUIRED, length = LOCATION_LENGTH_MAX)
    private String location;

    @Required(value = LOCATION_REQUIRED)
    @Length(min = LOCATION_LENGTH_MIN, max = LOCATION_LENGTH_MAX)
    @LeadingOrTrailingWhitespace(allowed = LOCATION_LEADING_OR_TRAILING_WHITESPACES_ALLOWED)
    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public final static String NAME_PROPERTY = "name";

    private final static boolean NAME_REQUIRED = true;

    final static int NAME_LENGTH_MIN = 1;

    final static int NAME_LENGTH_MAX = 20;

    private final static boolean NAME_LEADING_OR_TRAILING_WHITESPACES_ALLOWED = false;

    private final static String NAME_COLUMN = "Name";

    @Column(name = NAME_COLUMN, nullable = !NAME_REQUIRED, length = NAME_LENGTH_MAX)
    private String name;

    @Required(value = NAME_REQUIRED)
    @Length(min = NAME_LENGTH_MIN, max = NAME_LENGTH_MAX)
    @LeadingOrTrailingWhitespace(allowed = NAME_LEADING_OR_TRAILING_WHITESPACES_ALLOWED)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    // ****************************** Associations ******************************

    public final static String NETWORK_PROPERTY = "network";

    private final static boolean NETWORK_REQUIRED = false;

    private final static String NETWORK_ID_COLUMN = "network_id";

    @JoinColumn(name = NETWORK_ID_COLUMN)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ForeignKey(name = "FK_" + TABLE_NAME + "_network")
    private Network network;

    @Required(value = NETWORK_REQUIRED)
    public Network getNetwork()
    {
        return this.network;
    }

    public void setNetwork(Network network)
    {
        this.network = network;
    }

    // *************************** Mandatory constructors ***********************
    public Datacenter(String name, String location)
    {
        super();
        setName(name);
        setLocation(location);
    }

    // *************************** Business methods ***********************
    public Rack createRack(String name, Integer vlanIdMin, Integer vlanIdMax,
        Integer vlanPerVdcExpected, Integer nrsq)
    {
        return new Rack(name, this, vlanIdMin, vlanIdMax, vlanPerVdcExpected, nrsq);
    }

    public Machine createMachine(String name, String description, int ramInMb, int realRamInMb,
        int currentRamInUseInMb, long hardDiskInMb, long realHardDiskInMb,
        long currentHardDiskInUse, int realCpuThreads, int realCpuCores, int virtualCpusPerThread,
        int currentCpusInUse, State state, String virtualSwitch)
    {
        return new Machine(this,
            name,
            description,
            ramInMb,
            realRamInMb,
            currentRamInUseInMb,
            hardDiskInMb,
            realHardDiskInMb,
            currentHardDiskInUse,
            realCpuThreads,
            realCpuCores,
            virtualCpusPerThread,
            currentCpusInUse,
            state,
            virtualSwitch);
    }

    public RemoteService createRemoteService(RemoteServiceType type, String uri, int status)
    {
        return new RemoteService(this, type, uri, status);
    }

    // ********************************** Others ********************************
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static final Comparator<Datacenter> ORDER_BY_NAME =
        new CaseInsensitiveStringPropertyComparator<Datacenter>(Datacenter.NAME_PROPERTY);

    @OneToMany(targetEntity = DatacenterLimits.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "datacenter")
    private List<DatacenterLimits> limitsByEnterprise = new ArrayList<DatacenterLimits>();
}
