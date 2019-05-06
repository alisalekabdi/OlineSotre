package com.example.pascal_pc.baghali.model.customer;

import java.util.List;

public class OrderJsonBody {

private Billing billing;
    private List<Order> line_items;
    private int customer_id;

    public OrderJsonBody(Billing billing, List<Order> line_items, int customer_id) {
        this.billing = billing;
       this.line_items = line_items;

        this.customer_id = customer_id;
    }
}
