package com.fera.kuiz.feat_userAuth.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface daoUserRegistration : InterfaceUserRegistration {
    @Insert(onConflict = REPLACE)
    override suspend fun insertUserRegistration(userRegistration: TblUserRegistration): Long

    @Update(onConflict = REPLACE)
    override suspend fun updateUserRegistration(userRegistration: TblUserRegistration)

    @Query("delete from tblUserRegistration where pkUserRegistrationId = :pkUserRegistrationId")
    override suspend fun deleteUserRegistration(pkUserRegistrationId: Long)
}