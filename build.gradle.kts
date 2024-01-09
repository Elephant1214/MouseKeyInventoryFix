plugins {
    id("java")
    id("gg.essential.loom") version "1.3.+" apply false
}

allprojects {
    group = "me.elephant1214.mousekeyinventoryfix"
    version = "1.2.0"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
        options.release.set(8)
    }
}

subprojects {
    apply(plugin = "java")

    java {
        withSourcesJar()
    }

    tasks.withType<Jar> {
        from(rootProject.file("LICENSE")) {
            rename { "LICENSE_MouseKeyInventoryFix.txt" }
        }

        duplicatesStrategy = DuplicatesStrategy.WARN
    }
}

gradle.projectsEvaluated {
    subprojects.mapNotNull { it.tasks.findByPath("remapJar") }.reduce { t1, t2 ->
        t2.mustRunAfter(t1)
        t2
    }
}
