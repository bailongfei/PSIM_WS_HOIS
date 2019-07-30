package com.pojo;

public class BasicPlaceInfo {private String PlaceNO;//房间号
    private String PlaceName;//房间地址
    private String PlaceType;//房间类型 产房
    private String PlaceFlag;//标识
    private String PlaceNote;//备注信息
    private String PlaceStatus;//状态
    private String availablebedCount;//总床位
    private String placeNumber;//可用床位


    public String getPlaceNO() {
        return PlaceNO;
    }

    public void setPlaceNO(String placeNO) {
        PlaceNO = placeNO;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getPlaceType() {
        return PlaceType;
    }

    public void setPlaceType(String placeType) {
        PlaceType = placeType;
    }

    public String getPlaceFlag() {
        return PlaceFlag;
    }

    public void setPlaceFlag(String placeFlag) {
        PlaceFlag = placeFlag;
    }

    public String getPlaceNote() {
        return PlaceNote;
    }

    public void setPlaceNote(String placeNote) {
        PlaceNote = placeNote;
    }

    public String getPlaceStatus() {
        return PlaceStatus;
    }

    public void setPlaceStatus(String placeStatus) {
        PlaceStatus = placeStatus;
    }

    public String getAvailablebedCount() {
        return availablebedCount;
    }

    public void setAvailablebedCount(String availablebedCount) {
        this.availablebedCount = availablebedCount;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public BasicPlaceInfo(String placeNO, String placeName, String placeType, String placeFlag, String placeNote, String placeStatus, String availablebedCount, String placeNumber) {
        PlaceNO = placeNO;
        PlaceName = placeName;
        PlaceType = placeType;
        PlaceFlag = placeFlag;
        PlaceNote = placeNote;
        PlaceStatus = placeStatus;
        this.availablebedCount = availablebedCount;
        this.placeNumber = placeNumber;
    }

    public BasicPlaceInfo() {
        super();
    }
}
