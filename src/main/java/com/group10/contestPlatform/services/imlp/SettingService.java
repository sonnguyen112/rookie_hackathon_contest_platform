package com.group10.contestPlatform.services.imlp;

import com.group10.contestPlatform.entities.EmailSettingBag;
import com.group10.contestPlatform.entities.Setting;
import com.group10.contestPlatform.entities.SettingCategory;
import com.group10.contestPlatform.repositories.SettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SettingService implements ISettingService{
    @Autowired
     SettingRepository settingRepo;


    @Override
    public EmailSettingBag getEmailSettings() {
        try {
            List<Setting> settings = settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
            settings.addAll(settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES));
            return new EmailSettingBag(settings);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving email settings", e);
        }
    }







}

