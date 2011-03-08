package com.abiquo.server.core.infrastructure.storage;

import javax.xml.bind.annotation.XmlRootElement;

import com.abiquo.model.transport.SingleResourceTransportDto;

@XmlRootElement(name = "storagePoolWithDevice")
public class StoragePoolWithDeviceDto extends SingleResourceTransportDto
{
    
    private static final long serialVersionUID = 1L;
    public static final String MEDIA_TYPE = "application/storagepoolwithdevicedto+xml";
    
    private String idStorage;

    public String getIdStorage()
    {
        return idStorage;
    }

    public void setIdStorage(String idStorage)
    {
        this.idStorage = idStorage;
    }
    
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private long totalSizeInMb;

    public long getTotalSizeInMb()
    {
        return totalSizeInMb;
    }

    public void setTotalSizeInMb(long totalSizeInMb)
    {
        this.totalSizeInMb = totalSizeInMb;
    }

    private long usedSizeInMb;

    public long getUsedSizeInMb()
    {
        return usedSizeInMb;
    }

    public void setUsedSizeInMb(long usedSizeInMb)
    {
        this.usedSizeInMb = usedSizeInMb;
    }

    private long availableSizeInMb;

    public long getAvailableSizeInMb()
    {
        return availableSizeInMb;
    }

    public void setAvailableSizeInMb(long availableSizeInMb)
    {
        this.availableSizeInMb = availableSizeInMb;
    }

    private boolean enabled;

    public boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    private StorageDeviceDto device;
    
    public void setDevice(StorageDeviceDto device)
    {
        this.device = device;
    }

    public StorageDeviceDto getDevice()
    {
        return device;
    }


}