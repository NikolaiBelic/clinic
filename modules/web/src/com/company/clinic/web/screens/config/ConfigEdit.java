package com.company.clinic.web.screens.config;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.core.entity.Config;

@UiController("sys$Config.edit")
@UiDescriptor("config-edit.xml")
@EditedEntityContainer("configDc")
@LoadDataBeforeShow
public class ConfigEdit extends StandardEditor<Config> {
}