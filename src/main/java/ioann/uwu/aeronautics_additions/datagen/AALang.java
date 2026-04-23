package ioann.uwu.aeronautics_additions.datagen;

import com.simibubi.create.foundation.data.CreateRegistrate;

public class AALang {

    public static void initLang(CreateRegistrate registrate) {
        registrate.addRawLang("aeronautics_additions.configuration.title", "Aeronautics Additions");
        registrate.addRawLang("aeronautics_additions.configuration.section.aeronautics.additions.common.toml.title", "Aeronautics Additions Common Configuration");
        registrate.addRawLang("aeronautics_additions.configuration.section.aeronautics.additions.common.toml", "Aeronautics Additions Common Configuration");

        registrate.addRawLang("aeronautics_additions.configuration.turbine_airflow", "Turbine Airflow");
        registrate.addRawLang("aeronautics_additions.configuration.turbine_airflow.tooltip",
                """
                Turbine Area Effect length

                Default is 0.5 which is 5 times that of andesite propeller (0.1)""");

        registrate.addRawLang("aeronautics_additions.configuration.turbine_radius", "Turbine Radius");
        registrate.addRawLang("aeronautics_additions.configuration.turbine_radius.tooltip",
                """
                Turbine Area Effect radius

                Default is 2.0 which is 2 times that of andesite propeller (1.0)""");

        registrate.addRawLang("aeronautics_additions.configuration.turbine_thrust", "Turbine Thrust");
        registrate.addRawLang("aeronautics_additions.configuration.turbine_thrust.tooltip",
                """
                Turbine Thrust affects simulated contraptions
                
                Default is 4.0 which is 4 times that of andesite propeller (1.0)""");
    }
}
