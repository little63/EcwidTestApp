package ru.panov.greendao;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "ru.panov.testapp.db");
        Entity product = schema.addEntity("Product");
        product.addIdProperty();
        product.addStringProperty("tittle");
        product.addFloatProperty("price");
        product.addIntProperty("count");
        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }
}
