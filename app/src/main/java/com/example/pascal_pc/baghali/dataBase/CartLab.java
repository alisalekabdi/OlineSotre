package com.example.pascal_pc.baghali.dataBase;


import com.example.pascal_pc.baghali.model.dataBaseModel.CartDao;
import com.example.pascal_pc.baghali.model.dataBaseModel.DaoSession;
import com.example.pascal_pc.baghali.utils.App;
import com.example.pascal_pc.baghali.model.dataBaseModel.Cart;

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

    public Cart checkCart(Integer id) {
        List<Cart> carts = mCartDao.queryBuilder()
                .where(CartDao.Properties.MProductId.eq(id)).list();
        for (Cart cart : carts) {
            return cart;
        }
        return null;
    }

    public void updateProductCount(Cart cart){
        mCartDao.update(cart);
    }
    public  void deleteCarts(){
        mCartDao.deleteAll();
    }
    public Float getTotalCost(){
        Float totalCost= Float.valueOf(0);
        List<Cart> cartList=mCartDao.queryBuilder()
                .list();
        for (Cart cart:cartList) {
            totalCost+=cart.getMPrice()*cart.getMProductCount();
        }
        return totalCost;
    }
}
