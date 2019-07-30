package com.pojo;

public class PlaceInfo {
    private String PlaceNO;//房间号
    private String placeNumber;//可用床位

    public String getPlaceNO() {
        return PlaceNO;
    }

    public void setPlaceNO(String placeNO) {
        PlaceNO = placeNO;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "PlaceNO='" + PlaceNO + '\'' +
                ", placeNumber='" + placeNumber + '\'' +
                '}';
    }

    public PlaceInfo(String placeNO, String placeNumber) {
        PlaceNO = placeNO;
        this.placeNumber = placeNumber;
    }

    public PlaceInfo() {
        super();
    }
}
