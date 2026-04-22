package ioann.uwu.aeronautics_additions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

public class FloatConfigValue implements Supplier<Float> {

    private final ModConfigSpec.ConfigValue<String> stringConfigValue;
    private final float defaultValue;

    public FloatConfigValue(String name, float defaultValue, ModConfigSpec.Builder modConfigBuilder) {
        this.defaultValue = defaultValue;
        this.stringConfigValue = modConfigBuilder.define(name, String.valueOf(defaultValue));
    }

    @Override
    public Float get() {
        String currentString = stringConfigValue.get().trim();

        float currentValue;
        try {
            currentValue = Float.parseFloat(currentString);
        } catch (NumberFormatException e) {
            stringConfigValue.set(String.valueOf(this.defaultValue));
            return this.defaultValue;
        }

        return currentValue;
    }
}
