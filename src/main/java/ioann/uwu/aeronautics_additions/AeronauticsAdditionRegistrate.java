package ioann.uwu.aeronautics_additions;

import com.simibubi.create.foundation.data.CreateRegistrate;

public class AeronauticsAdditionRegistrate extends CreateRegistrate {

    protected AeronauticsAdditionRegistrate() {
        super(AeronauticsAdditions.MOD_ID);
    }

    public static AeronauticsAdditionRegistrate create() {
        AeronauticsAdditionRegistrate registrate = new AeronauticsAdditionRegistrate();
        registrate.defaultCreativeTab();
        return new AeronauticsAdditionRegistrate();
    }
}
