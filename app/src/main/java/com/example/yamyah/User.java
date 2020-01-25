package com.example.yamyah;

public class User {
    String id,id1,email,name,type,shopid;

    public String getId() {
        return id;
    }

    public String getId1() {
        return id1;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getShopid() {
        return shopid;
    }

    public User(String id, String id1, String name, String email, String type, String shopid) {
        this.id = id;
        this.id1 = id1;
        this.email = email;
        this.name = name;
        this.type = type;
        this.shopid = shopid;
    }
}
