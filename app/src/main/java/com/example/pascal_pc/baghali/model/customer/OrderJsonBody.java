package com.example.pascal_pc.baghali.model.customer;

import java.util.List;

public class OrderJsonBody {

    private List<Order> line_items;
    private List<Coupon> coupon_lines;
    private Billing billing;
    private int customer_id;

    public OrderJsonBody(List<Order> line_items, Billing billing, int customer_id) {
        this.line_items = line_items;
        this.billing = billing;
        this.customer_id = customer_id;
    }

    public OrderJsonBody(List<Order> line_items, List<Coupon> coupon_lines, Billing billing, int customer_id) {
        this.line_items = line_items;
        this.coupon_lines = coupon_lines;
        this.billing = billing;
        this.customer_id = customer_id;
    }
}
