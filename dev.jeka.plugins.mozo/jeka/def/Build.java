import dev.jeka.core.api.depmanagement.JkDependencySet;
import dev.jeka.core.api.java.JkJavaVersion;
import dev.jeka.core.api.java.testing.JkTestProcessor;
import dev.jeka.core.api.java.testing.JkTestSelection;
import dev.jeka.core.api.system.JkLocator;
import dev.jeka.core.api.tooling.JkGitWrapper;
import dev.jeka.core.tool.JkCommandSet;
import dev.jeka.core.tool.JkInit;
import dev.jeka.core.tool.builtins.java.JkPluginJava;

import static dev.jeka.core.api.depmanagement.JkScope.PROVIDED;
import static dev.jeka.core.api.depmanagement.JkScope.TEST;

class Build extends JkCommandSet {

    final JkPluginJava java = getPlugin(JkPluginJava.class);

    /*
     * Configures plugins to be bound to this command class. When this method is called, option
     * fields have already been injected from command line.
     */
    @Override
    protected void setup() {
        java.getProject()
            .getJarProduction()
                .getDependencyManagement()
                .addDependencies(JkDependencySet.of()
                        .andFile(JkLocator.getJekaJarPath(), PROVIDED)
                   // add Mozo dependency here (should be published on Maven central "martinaguero:mozo:0.31" ?)
                        .and("org.junit.jupiter:junit-jupiter:5.6.2", TEST)).__
                .getCompilation()
                    .setJavaVersion(JkJavaVersion.V8).__.__

            // Publication is only necessary if your project is being deployed on a binary repository.
            // Many projects as jee war jar, springboot application, tools, Graphical application
            // does not need this section at all.  In this case you can remove this section.
            .getPublication()
                .setModuleId("dev.jeka:mozo-plugin")
                .setVersion(JkGitWrapper.of(getBaseDir()).getVersionFromTags());  // Version inferred from Git
    }

    public void cleanPack() {
        clean(); java.pack();
    }

    public static void main(String[] args) {
        JkInit.instanceOf(Build.class, args).cleanPack();
    }

}