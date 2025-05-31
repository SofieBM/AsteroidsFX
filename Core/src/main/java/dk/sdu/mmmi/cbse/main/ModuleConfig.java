package dk.sdu.mmmi.cbse.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "dk.sdu.mmmi.cbse.main",
        "dk.sdu.mmmi.cbse.playersystem",
        "dk.sdu.mmmi.cbse",
        "dk.sdu.mmmi.cbse.asteroid",
        "dk.sdu.mmmi.cbse.bulletsystem",
        "dk.sdu.mmmi.cbse.collisionsystem",
        "dk.sdu.mmmi.cbse.scoreclient"
})
public class ModuleConfig {}
