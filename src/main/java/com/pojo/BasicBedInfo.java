package com.pojo;

public class BasicBedInfo {
    private String placeID;    //房价ID
    private String placeNO;     //房间序号
    private String bedID;
    private String bedName;
    private String bedStatus;
    private String puerpaerName;
    private String displayTypeStatus;//显示状态
    private String customType;//自定义状态
    private String displayStatus;//要显示那个状态(0显示自定义状态,1显示选中状态)
    private String puerpaerStatus;//是否在院

    public String getBedID() {
        return bedID;
    }

    public void setBedID(String bedID) {
        this.bedID = bedID;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(String bedStatus) {
        this.bedStatus = bedStatus;
    }

    public String getPuerpaerName() {
        return puerpaerName;
    }

    public void setPuerpaerName(String puerpaerName) {
        this.puerpaerName = puerpaerName;
    }

    public String getDisplayTypeStatus() {
        return displayTypeStatus;
    }

    public void setDisplayTypeStatus(String displayTypeStatus) {
        this.displayTypeStatus = displayTypeStatus;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getPuerpaerStatus() {
        return puerpaerStatus;
    }

    public void setPuerpaerStatus(String puerpaerStatus) {
        this.puerpaerStatus = puerpaerStatus;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceNO() {
        return placeNO;
    }

    public void setPlaceNO(String placeNO) {
        this.placeNO = placeNO;
    }

    public BasicBedInfo(String placeID, String placeNO, String bedID, String bedName, String bedStatus, String puerpaerName, String displayTypeStatus, String customType, String displayStatus, String puerpaerStatus) {
        this.placeID = placeID;
        this.placeNO = placeNO;
        this.bedID = bedID;
        this.bedName = bedName;
        this.bedStatus = bedStatus;
        this.puerpaerName = puerpaerName;
        this.displayTypeStatus = displayTypeStatus;
        this.customType = customType;
        this.displayStatus = displayStatus;
        this.puerpaerStatus = puerpaerStatus;
    }

    public BasicBedInfo() {
        super();
    }
}
