package com.rpgpack.classes;

import com.rpgpack.core.PlayerData;

public class ClassManager {

    private ClassManager() {}

    public static void applyClass(PlayerData data, ClassType type) {
        if (type == ClassType.NONE) return;

        data.setSelectedClass(type.name());
        data.setStr(type.getBaseStr());
        data.setVit(type.getBaseVit());
        data.setEnd(type.getBaseEnd());
        data.setAgi(type.getBaseAgi());
        data.setDex(type.getBaseDex());
        data.setInt(type.getBaseInt());
        data.setWis(type.getBaseWis());
        data.setLuk(type.getBaseLuk());

        var stats = com.rpgpack.core.StatCalculator.calculate(data);
        data.setCurrentHp(stats.maxHp);
        data.setCurrentMana(stats.maxMana);
        data.setCurrentStamina(stats.maxStamina);
    }
}
