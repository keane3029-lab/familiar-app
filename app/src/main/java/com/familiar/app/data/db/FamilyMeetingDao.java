package com.familiar.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.familiar.app.data.model.FamilyAgreement;

import java.util.List;

@Dao
public interface FamilyMeetingDao {
    @Insert
    long insertAgreement(FamilyAgreement agreement);

    @Update
    void updateAgreement(FamilyAgreement agreement);

    @Query("SELECT * FROM family_agreements ORDER BY agreedAt DESC")
    List<FamilyAgreement> getAllAgreements();

    @Query("SELECT * FROM family_agreements WHERE id = :id")
    FamilyAgreement getAgreementById(long id);
}
