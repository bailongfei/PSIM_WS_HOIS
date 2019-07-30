package com.pojo;

public class BasicDisplayStatusInfo {
    private String displayTypeId;
    private String displayTypeStatus;

    public String getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(String displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public String getDisplayTypeStatus() {
        return displayTypeStatus;
    }

    public void setDisplayTypeStatus(String displayTypeStatus) {
        this.displayTypeStatus = displayTypeStatus;
    }

    public BasicDisplayStatusInfo(String displayTypeId, String displayTypeStatus) {
        this.displayTypeId = displayTypeId;
        this.displayTypeStatus = displayTypeStatus;
    }

    public BasicDisplayStatusInfo() {
        super();
    }

    @Override
    public String toString() {
        return this.displayTypeStatus;
    }
}
