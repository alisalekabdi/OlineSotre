package com.example.pascal_pc.baghali.dataBase;


import com.example.pascal_pc.baghali.model.dataBaseModel.CartDao;
import com.example.pascal_pc.baghali.model.dataBaseModel.DaoSession;
import com.example.pascal_pc.baghali.utils.App;
import com.example.pascal_pc.baghali.model.dataBaseModel.Cart;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


public class CartLab {

    private static CartLab ourInstance;
    private DaoSession daoSession = App.getApp().getDaoSession();
    private CartDao mCartDao = daoSession.getCartDao();

    private CartLab() {

    }

    public static CartLab getInstance() {
        if (ourInstance == null) {
            ourInstance = new CartLab();
        }
        return ourInstance;
    }

    public void addCart(Cart cart) {
        mCartDao.insert(cart);
    }

    public void dltCart(Cart cart) {
        mCartDao.delete(cart);
    }

    public List<Cart> getCarts() {
        return mCartDao.loadAll();
    }

    public List<Cart> checkCart(Integer id) {
        QueryBuilder<Cart> qb = mCartDao.queryBuilder();
        return qb.where(CartDao.Properties.MProductId.eq(id)).list();

    }

}
