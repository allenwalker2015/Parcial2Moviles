package com.alphadev.gamesnews.menu;

public class MenuModel {

    public String menuName;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean hasChildren, boolean isGroup) {
        this.menuName = menuName;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
    }
}