package com.acme.frontify.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleModuleResource;
import info.magnolia.module.delta.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is optional and lets you manage the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class FrontifyModuleVersionHandler extends DefaultModuleVersionHandler {
    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {

        final List<Task> tasks = new ArrayList<>();


        tasks.add(new BootstrapSingleModuleResource("config.modules.ui-admincentral.config.appLauncherLayout.groups.edit.apps.xml"));
        tasks.add(new BootstrapSingleModuleResource("config.modules.acme-frontify.apps.xml"));
        tasks.add(new BootstrapSingleModuleResource("config.modules.ui-framework.fieldTypes.frontifyField.xml"));

        return tasks;
    }

    @Override
    protected List<Task> getStartupTasks(InstallContext installContext) {

        final List<Task> tasks = new ArrayList<>();

        tasks.addAll(getExtraInstallTasks(installContext));

        return tasks;
    }
}
