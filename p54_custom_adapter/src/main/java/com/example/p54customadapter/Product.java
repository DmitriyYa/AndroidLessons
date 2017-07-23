package com.example.p54customadapter;

/**
 * Created by dima on 24.06.17.
 */
// Класс описывающий товар.
public class Product {
    String name;
    int price;
    int image;
    boolean box;

    public Product(String _name, int _price, int _image, boolean _box) {
        this.name = _name;
        this.price = _price;
        this.image = _image;
        this.box = _box;
    }
}
