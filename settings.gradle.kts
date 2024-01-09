rootProject.name = "MouseKeyInventoryFix"

pluginManagement {
    repositories {
        maven("https://repo.essential.gg/repository/maven-public/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
}

include("fabric")
include("forge-latest")
include("forge-116")
