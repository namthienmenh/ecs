package com.nguyenct.ecs;

public class Component1
{
    private int attr1;
    private String attr2;

    public Component1(int attr1, String attr2) {
        this.attr1 = attr1;
        this.attr2 = attr2;
    }

    public int getAttr1() {
        return attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr1(int attr1) {
        this.attr1 = attr1;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }
}
