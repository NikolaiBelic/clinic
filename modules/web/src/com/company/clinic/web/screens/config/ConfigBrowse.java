package com.company.clinic.web.screens.config;

import com.company.clinic.entity.Cita;
import com.company.clinic.service.CitaService;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.core.entity.Config;

import javax.inject.Inject;
import java.util.List;

@UiController("sys$Config.browse")
@UiDescriptor("config-browse.xml")
@LookupComponent("configsTable")
@LoadDataBeforeShow
public class ConfigBrowse extends StandardLookup<Config> {
}