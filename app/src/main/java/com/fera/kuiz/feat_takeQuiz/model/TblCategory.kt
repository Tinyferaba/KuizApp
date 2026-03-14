package com.fera.kuiz.feat_takeQuiz.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fera.hymn.utils.enumeration.EnumDataInfo
import com.fera.kuiz.common.util.DatabaseProperties

@Entity(tableName = DatabaseProperties.TBL_CATEGORY)
data class TblCategory(
    @PrimaryKey(autoGenerate = false)
    val pkCategoryId: Long,
    val title: String?,
    val description: String?,
    val note: String?,
    val iconFilePath: String?,
    val noteFilePath: String?,
    val totalQuestions: Int,
    val totalCorrectAnswers: Int,
    val totalWrongAnswers: Int,
    val totalQAnswered: Int,
    val lastQuestionTakenId: Long,
    val lastQuestionTakenNo: Int,
    val qStartTakenDate: Long,
    val dataStatus: Int
) : Parcelable {

    constructor() : this(
        pkCategoryId = 0L,
        title = null,
        description = null,
        note = null,
        iconFilePath = null,
        noteFilePath = null,
        totalQuestions = 0,
        totalCorrectAnswers = 0,
        totalWrongAnswers = 0,
        totalQAnswered = 0,
        lastQuestionTakenId = 0L,
        lastQuestionTakenNo = 0,
        qStartTakenDate = 0,
        dataStatus = EnumDataInfo.NONE.info
    )

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TblCategory> {
        override fun createFromParcel(parcel: Parcel): TblCategory {
            return TblCategory(parcel)
        }

        override fun newArray(size: Int): Array<TblCategory?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcelDest: Parcel, flags: Int) {
        parcelDest.writeLong(pkCategoryId)
        parcelDest.writeString(title)
        parcelDest.writeString(description)
        parcelDest.writeString(note)
        parcelDest.writeString(iconFilePath)
        parcelDest.writeString(noteFilePath)
        parcelDest.writeInt(totalQuestions)
        parcelDest.writeInt(totalWrongAnswers)
        parcelDest.writeInt(totalQAnswered)
        parcelDest.writeLong(lastQuestionTakenId)
        parcelDest.writeInt(lastQuestionTakenNo)
        parcelDest.writeLong(qStartTakenDate)
        parcelDest.writeInt(dataStatus)
    }
}
