package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.familiar.app.data.model.Rule;

import java.util.List;

@Dao
public interface RuleDao {
    @Insert
    long insertRule(Rule rule);

    @Update
    void updateRule(Rule rule);

    @Query("SELECT * FROM rules ORDER BY createdAt DESC")
    List<Rule> getAllRules();

    @Query("SELECT * FROM rules WHERE status = 'AGREED' OR status = 'ACTIVE'")
    List<Rule> getAgreedRules();

    @Query("SELECT * FROM rules WHERE ruleCategory = :category")
    List<Rule> getRulesByCategory(String category);
}
