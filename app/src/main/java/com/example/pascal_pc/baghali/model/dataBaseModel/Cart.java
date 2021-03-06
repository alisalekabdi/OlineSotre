package com.example.pascal_pc.baghali.model.dataBaseModel;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;


@Entity()
public class Cart {

    @Id(autoincrement = true)
    private Long mId;

    @Unique
    private Integer mProductId;
    private int mProductCount;
    private Float mPrice;
    private String mName;
    private String mImgPath;
    @Generated(hash = 55606297)
    public Cart(Long mId, Integer mProductId, int mProductCount, Float mPrice,
            String mName, String mImgPath) {
        this.mId = mId;
        this.mProductId = mProductId;
        this.mProductCount = mProductCount;
        this.mPrice = mPrice;
        this.mName = mName;
        this.mImgPath = mImgPath;
    }
    @Generated(hash = 1029823171)
    public Cart() {
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public Integer getMProductId() {
        return this.mProductId;
    }
    public void setMProductId(Integer mProductId) {
        this.mProductId = mProductId;
    }
    public int getMProductCount() {
        return this.mProductCount;
    }
    public void setMProductCount(int mProductCount) {
        this.mProductCount = mProductCount;
    }
    public Float getMPrice() {
        return this.mPrice;
    }
    public void setMPrice(Float mPrice) {
        this.mPrice = mPrice;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }
    public String getMImgPath() {
        return this.mImgPath;
    }
    public void setMImgPath(String mImgPath) {
        this.mImgPath = mImgPath;
    }

}
