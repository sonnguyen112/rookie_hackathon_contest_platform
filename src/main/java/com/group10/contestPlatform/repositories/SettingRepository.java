package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.Setting;
import com.group10.contestPlatform.entities.SettingCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingRepository extends CrudRepository<Setting, String> {
    public List<Setting> findByCategory(SettingCategory category);

    @Query("SELECT s FROM Setting s WHERE s.category = ?1 OR s.category = ?2")
    public List<Setting> findByTwoCategories(SettingCategory catOne, SettingCategory catTwo);

    public Setting findByKey(String key);
}
