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

package com.abiquo.server.core.infrastructure.storage;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.abiquo.server.core.common.GenericEntityGenerator;
import com.softwarementors.commons.test.SeedGenerator;
import com.softwarementors.commons.testng.AssertEx;

public class StoragePoolGenerator extends GenericEntityGenerator<StoragePool>
{

    TierGenerator tierGenerator;

    StorageDeviceGenerator deviceGenerator;

    public StoragePoolGenerator(SeedGenerator seed)
    {
        super(seed);

        tierGenerator = new TierGenerator(seed);

        deviceGenerator = new StorageDeviceGenerator(seed);

    }

    @Override
    public void assertAllPropertiesEqual(StoragePool obj1, StoragePool obj2)
    {
        AssertEx.assertPropertiesEqualSilent(obj1, obj2, StoragePool.NAME_PROPERTY);
    }

    @Override
    public StoragePool createUniqueInstance()
    {
        StoragePool storagePool = new StoragePool();

        Tier tier = tierGenerator.createUniqueInstance();
        storagePool.setTier(tier);

        StorageDevice device = deviceGenerator.createUniqueInstance();
        storagePool.setDevice(device);

        storagePool.setIdStorage(UUID.randomUUID().toString());
        storagePool.setName("LoPutoStorage" + String.valueOf(new Random().nextInt()));
        storagePool.setAvailableSizeInMb(1000L);
        storagePool.setTotalSizeInMb(1000L);
        storagePool.setUsedSizeInMb(0L);

        return storagePool;
    }

    public StoragePool createInstanceIntoDevice(StorageDevice device)
    {
        StoragePool storagePool = new StoragePool();

        Tier tier = tierGenerator.createInstance(device.getDatacenter(), "Default Tier");

        storagePool.setTier(tier);
        storagePool.setDevice(device);
        storagePool.setIdStorage(UUID.randomUUID().toString());
        storagePool.setName("LoPutoStorage" + String.valueOf(new Random().nextInt()));
        storagePool.setAvailableSizeInMb(1000L);
        storagePool.setTotalSizeInMb(1000L);
        storagePool.setUsedSizeInMb(0L);

        return storagePool;
    }
    
    public StoragePool createInstanceIntoTier(Tier tier)
    {
        StoragePool storagePool = new StoragePool();

        StorageDevice device = deviceGenerator.createDeviceForInstance(tier.getDatacenter());

        storagePool.setTier(tier);
        storagePool.setDevice(device);
        storagePool.setIdStorage(UUID.randomUUID().toString());
        storagePool.setName("LoPutoStorage" + String.valueOf(new Random().nextInt()));
        storagePool.setAvailableSizeInMb(1000L);
        storagePool.setTotalSizeInMb(1000L);
        storagePool.setUsedSizeInMb(0L);

        return storagePool;
    }

    @Override
    public void addAuxiliaryEntitiesToPersist(StoragePool entity, List<Object> entitiesToPersist)
    {
        super.addAuxiliaryEntitiesToPersist(entity, entitiesToPersist);

        Tier tier = entity.getTier();
        tierGenerator.addAuxiliaryEntitiesToPersist(tier, entitiesToPersist);
        entitiesToPersist.add(tier);

        StorageDevice device = entity.getDevice();
        deviceGenerator.addAuxiliaryEntitiesToPersist(device, entitiesToPersist);
        entitiesToPersist.add(device);

    }

}
