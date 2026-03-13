package com.fera.hymn.utils.enumeration

enum class EnumDataInfo(val info: Int) {
    UNKNOWN(-1),
    NONE(0),

    //NOTE: DataStatus
    NEW(1),
    UPLOADED(2),
    DOWNLOADED(3),
    UPDATED(4),
    DELETED(5),
    DOWNLOADED_TABLE(6),
    DOWNLOADED_FILE(7),
    UPLOADED_TABLE(8),
    UPLOADED_FILE(9),
    DOWNLOADED_TABLE_FILE(10),
    UPLOADED_TABLE_FILE(11),
    NO_UPLOAD_DOWNLOAD(12),

    //NOTE: Data Storage Status
    DATA_AS_BYTES_SQLITE(20),
    DATA_AS_PATH_ASSET(21),
    DATA_AS_PATH_RAW(22),
    DATA_AS_PATH_DIR_CACHE(23),
    DATA_AS_PATH_DIR_FILE(24),

    //NOTE: Encryption-Decryption Status
    ENCRYPTED(40),
    DECRYPTED(41),
    NO_ENCRYPTION(42),
    NO_DECRYPTION(43),

    //NOTE: Zip
    ZIPPED(50),
    UNZIPPED(51),


    SENT(60),
    DELIVERED(61),
    NOT_DELIVERED(62),
    SEEN(63),
    NOT_SEEN(64),
    SENDING(65),
    FAILED(66),
    RECALLED(67)
}