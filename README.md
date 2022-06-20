# RenderLib-Minecraft-Development
It can be quite annoying to set-up RenderLib in a specific Minecraft version. This is why i chose to make this repository.
This repository holds ready to use Minecraft modding development kit's with pre-installed versions of RenderLib.

# What is RenderLib?
[RenderLib](https://gitlab.com/FriedrichS/renderlib/-/tree/polishing_fixes) is a small lightweight rendering engine written 
in pure Java and is highly optimized. You can join RenderLib's official Discord server [here](https://discord.gg/44RENR4).

# Installation
Integrate the files from src/ into your project. Add these configurations to your build.gradle:

```
plugins {
    id "com.github.johnrengelman.shadow" version "5.2.0"
}

dependencies {
    implementation 'ga.beycraft:RenderLib:1.0.0-pre6'
    shade 'ga.beycraft:RenderLib:1.0.0-pre6'
}

repositories {
    repositories {
        maven { url 'https://maven.explodingcreeper.me/releases' }
        maven {
            url "https://www.cursemaven.com"
        }
    }
}

configurations {
    shade
    compile.extendsFrom shade
}

shadowJar {
    configurations = [project.configurations.shade]
    classifier ''
    finalizedBy 'reobfShadowJar'
}

reobf {
    shadowJar {
        dependsOn tasks.createMcpToSrg
        mappings = tasks.createMcpToSrg.outputs.files.singleFile
    }
}
```
