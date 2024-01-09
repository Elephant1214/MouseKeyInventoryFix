plugins {
    id("gg.essential.loom")
}

loom {
    runConfigs {
        named("client") {
            ideConfigGenerated(true)
        }
    }
    forge {
        mixinConfig("mousekeyinventoryfix.mixins.json")
    }
    @Suppress("UnstableApiUsage")
    mixin.defaultRefmapName.set("mousekeyinventoryfix.refmap.json")
}

dependencies {
    minecraft("com.mojang:minecraft:1.16.5")
    mappings(loom.officialMojangMappings())
    forge("net.minecraftforge:forge:1.16.5-36.2.34")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand("version" to project.version)
        }
    }

    jar {
        manifest.attributes(
            "ModSide" to "CLIENT",
            "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
            "TweakOrder" to "0",
            "MixinConfigs" to "mousekeyinventoryfix.mixins.json",
        )
    }
}

base {
    archivesName = "${rootProject.name}-Forge-1.16"
}
