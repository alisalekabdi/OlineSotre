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
    @Generated(hash = 1183783017)
    public Cart(Long mId, Integer mProductId, int mProductCount, Float mPrice) {
        this.mId = mId;
        this.mProductId = mProductId;
        this.mProductCount = mProductCount;
        this.mPrice = mPrice;
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

}
