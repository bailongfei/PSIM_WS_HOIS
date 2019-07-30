package com.pojo;

/**
 * @Package: com.info.entity
 * @ClassName: Item
 * @Description: TODO
 * @Author: LaoShiRen
 * @CreateDate: 2019-05-14 10:58
 * @Version: 1.0
 */
public class Item {
    private String itemID;
    private String itemName;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Item(){

    }

    public Item(String itemID, String itemName){
        setItemID(itemID);
        setItemName(itemName);
    }

    @Override
    public String toString() {
        return ""+itemName;
    }
}
