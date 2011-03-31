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

package com.abiquo.commons.amqp.impl.datacenter.domain.dto;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.impl.datacenter.domain.operations.BasicDatacenterOp;
import com.abiquo.commons.amqp.util.JSONUtils;

public class DatacenterJobDto implements Queuable
{
    protected BasicDatacenterOp operation;

    public BasicDatacenterOp getOperation()
    {
        return operation;
    }

    public void setOperation(BasicDatacenterOp operation)
    {
        this.operation = operation;
    }

    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    public static DatacenterJobDto fromByteArray(byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, DatacenterJobDto.class);
    }
}