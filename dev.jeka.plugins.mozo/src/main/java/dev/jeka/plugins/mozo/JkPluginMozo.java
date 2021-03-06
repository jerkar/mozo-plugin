package dev.jeka.plugins.mozo;

import dev.jeka.core.api.depmanagement.JkDependencySet;
import dev.jeka.core.api.java.project.JkJavaProject;
import dev.jeka.core.tool.JkCommandSet;
import dev.jeka.core.tool.JkPlugin;
import dev.jeka.core.tool.builtins.java.JkPluginJava;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class JkPluginMozo extends JkPlugin {

    protected JkPluginMozo(JkCommandSet commandSet) {
        super(commandSet);
    }

    @Override
    protected void activate() {
        JkPluginJava java = this.getCommandSet().getPlugin(JkPluginJava.class);
        JkJavaProject javaProject = java.getProject();
        javaProject
            .getJarProduction()
                .getDependencyManagement()
                    .addDependencies(fetchMozoDependencies());
    }

    private JkDependencySet fetchMozoDependencies() {
        // TODO
        // May be better to use directly Mozo Java API rather than cli.
        JkJavaProject otherModule = JkJavaProject.of().setBaseDir(Paths.get("../otherModule"));
        List<Path> moduleDeps = new LinkedList<>(); // fulfill this list using Mozo
        return JkDependencySet.of()
                .andFiles(moduleDeps)
                .and(otherModule.toDependency());
    }
}
