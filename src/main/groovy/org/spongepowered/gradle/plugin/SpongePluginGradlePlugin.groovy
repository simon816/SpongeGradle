/*
 * This file is part of SpongeGradle, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.gradle.plugin

import static org.gradle.api.JavaVersion.VERSION_1_8
import static org.spongepowered.gradle.plugin.SpongeExtension.EXTENSION_NAME

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention

class SpongePluginGradlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {
            plugins.apply('java')
            plugins.apply('eclipse')
            plugins.apply('idea')

            convention.getPlugin(JavaPluginConvention).with {
                sourceCompatibility = VERSION_1_8
                targetCompatibility = VERSION_1_8
            }

            repositories {
                mavenCentral()
                maven {
                    name = 'sponge'
                    url = 'http://repo.spongepowered.org/maven'
                }
            }

            // IntelliJ IDEA resource fix
            idea.module.inheritOutputDirs = true

            def extension = extensions.create(EXTENSION_NAME, SpongePluginExtension, project, project.name)

            plugins.apply(SpongePluginGradlePluginBase)

            extension.plugin {
                id = project.name.toLowerCase(Locale.ENGLISH)
                meta {
                    name = project.name
                    version = {project.version}
                    description = {project.description}
                }
            }
        }
    }

}
