package com.example.application.views.main;
public class Factory_Provider {
    public static Abstract_Factory getFactory() {
        return new Assignment_Factory();
    }
}
