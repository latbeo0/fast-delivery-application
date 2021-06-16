package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.model.Slider;

import java.util.List;

@Dao
public interface SliderDao {
    @Insert
    void insetAll(Slider... sliders);

    @Update
    void updateAll(Slider... sliders);

    @Delete
    void deleteAll(Slider... sliders);

    @Query("SELECT * FROM slider")
    List<Slider> getAll();

    @Query("SELECT * FROM slider WHERE id = :id")
    Slider getMenuItemById(Long id);
}
