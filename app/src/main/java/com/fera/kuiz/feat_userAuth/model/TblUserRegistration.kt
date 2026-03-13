package com.fera.kuiz.feat_userAuth.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fera.kuiz.common.util.DatabaseProperties

@Entity(tableName = DatabaseProperties.TBL_USER_REGISTRATION)
data class TblUserRegistration(
    @PrimaryKey(autoGenerate = false)
    val pkUserRegistrationId: Long,
    val dateRegistered: Long
)
