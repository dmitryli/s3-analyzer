package me.dimalimonov.app.model;

import lombok.ToString;

import java.text.DecimalFormat;

@ToString
public class WBucket {
    @ToString.Exclude
    private final int KB = 1024;
    @ToString.Exclude
    private final long MB = KB * KB;
    @ToString.Exclude
    private final long GB = KB * MB;
    @ToString.Exclude
    private final long TB = KB * GB;


    private String name;
    private long size;
    private int objectCount;
    private String lastModified;
    private String providerId;
    private String providerName;

    public String getDisplaySize() {
        double displaySize = Double.valueOf("" + size);

        String units = "bytes";

        // calculate TB
        if (size > TB) {
            displaySize = displaySize / TB;
            units = "TB";
        } else
            // calculate GB
            if (size > GB) {
                displaySize = displaySize / GB;
                units = "GB";
            } else
                // calculate MB
                if (size > MB) {
                    displaySize = displaySize / MB;
                    units = "MB";
                } else
                    // calculate KiB
                    if (size > KB) {
                        displaySize = displaySize / KB;
                        units = "KB";
                    }


        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(displaySize) + " " + units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
