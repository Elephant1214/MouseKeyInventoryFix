plugins {
    id("gg.essential.loom")
}

loom {
    runConfigs {
        named("client") {
            ideConfigGenerated(true)
        }
    }
    @Suppress("UnstableApiUsage")
    mixin {
        add(sourceSets.main.get(), "mousekeyinventoryfix.mixins.json")
        defaultRefmapName.set("mousekeyinventoryfix-refmap.json")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.16.5")
    mappings("net.fabricmc:yarn:1.16.5+build.10:v2")
    modImplementation("net.fabricmc:fabric-loader:0.14.6")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}

base {
    archivesName = "${rootProject.name}-Fabric"
}
