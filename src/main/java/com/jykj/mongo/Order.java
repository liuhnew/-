package com.jykj.mongo;

public class Order {
    public static int ASC=1;
    public static int DESC=-1;
    private int order;

    public Order(int order)
    {
        this.order = order;
    }

    public int getOrder()
    {
        return this.order;
    }
}
