package com.pojo;

public class BasicPuerpaerInfo {
    private String PlaceNO;    //产室序号
    private String PlaceFlag;  //特殊标符
    private String BedID;         //床位id
    private String BedName;    //床位号
    private String BedStatus;  //床位状态：1可用，0不可用
    private String PuerpaerName; //产妇姓名
    private String DisplayTypeStatus; //产妇试产进度状态
    private String CustomType;      //自定义显示状态
    private String CustomBroadcast; //自定义呼叫信息
    private String DisplayStatus;      //1、0

    public String getPlaceNO() {
        return PlaceNO;
    }

    public void setPlaceNO(String placeNO) {
        PlaceNO = placeNO;
    }

    public String getPlaceFlag() {
        return PlaceFlag;
    }

    public void setPlaceFlag(String placeFlag) {
        PlaceFlag = placeFlag;
    }

    public String getBedID() {
        return BedID;
    }

    public void setBedID(String bedID) {
        BedID = bedID;
    }

    public String getBedName() {
        return BedName;
    }

    public void setBedName(String bedName) {
        BedName = bedName;
    }

    public String getBedStatus() {
        return BedStatus;
    }

    public void setBedStatus(String bedStatus) {
        BedStatus = bedStatus;
    }

    public String getPuerpaerName() {
        return PuerpaerName;
    }

    public void setPuerpaerName(String puerpaerName) {
        PuerpaerName = puerpaerName;
    }

    public String getDisplayTypeStatus() {
        return DisplayTypeStatus;
    }

    public void setDisplayTypeStatus(String displayTypeStatus) {
        DisplayTypeStatus = displayTypeStatus;
    }

    public String getCustomType() {
        return CustomType;
    }

    public void setCustomType(String customType) {
        CustomType = customType;
    }

    public String getCustomBroadcast() {
        return CustomBroadcast;
    }

    public void setCustomBroadcast(String customBroadcast) {
        CustomBroadcast = customBroadcast;
    }

    public String getDisplayStatus() {
        return DisplayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        DisplayStatus = displayStatus;
    }

    public BasicPuerpaerInfo(String placeNO, String placeFlag, String bedID, String bedName, String bedStatus, String puerpaerName, String displayTypeStatus, String customType, String customBroadcast, String displayStatus) {
        PlaceNO = placeNO;
        PlaceFlag = placeFlag;
        BedID = bedID;
        BedName = bedName;
        BedStatus = bedStatus;
        PuerpaerName = puerpaerName;
        DisplayTypeStatus = displayTypeStatus;
        CustomType = customType;
        CustomBroadcast = customBroadcast;
        DisplayStatus = displayStatus;
    }

    public BasicPuerpaerInfo() {
        super();
    }
}
