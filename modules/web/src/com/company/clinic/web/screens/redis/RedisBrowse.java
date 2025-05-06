package com.company.clinic.web.screens.redis;

import com.company.clinic.entity.redis.Redis;
import com.company.clinic.service.redis.RedisService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

@UiController("clinic_RedisBrowse")
@UiDescriptor("RedisBrowse.xml")
public class RedisBrowse extends Screen {

    @Inject
    private Metadata metadata;

    @Inject
    private RedisService redisService;

    @Inject
    private GroupTable<Redis> redisTable;

    @Inject
    private CollectionContainer<Redis> redisDc;
    @Inject
    private Button btnRemove;
    @Inject
    private Button btnRemoveAll;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        loadRedisKeys();
    }

    @Subscribe
    public void onInit(InitEvent event) {
        btnRemove.setEnabled(false);

        btnRemove.addClickListener(e -> {
            Set<Redis> keys = redisTable.getSelected();
            for (Redis key : keys) {
                redisService.delete(key.getKey());
            }
            redisDc.getMutableItems().clear();
            loadRedisKeys();
        });

        redisTable.addSelectionListener(e -> {
            btnRemove.setEnabled(!redisTable.getSelected().isEmpty());
        });

        btnRemoveAll.addClickListener(e -> {
            Set<String> keys = redisService.getKeys("*");
            for (String key : keys) {
                redisService.delete(key);
            }
            System.out.println("boton apretado");
            redisDc.getMutableItems().clear();
        });
    }

    private void loadRedisKeys() {
            Set<String> keys = redisService.getKeys("*");

            for (String key : keys) {
                Redis redis = metadata.create(Redis.class);
                redis.setKey(key);
                redisDc.getMutableItems().add(redis);
            }
    }
}