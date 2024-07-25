package com.project.test.enums;


public enum Message {
	  SUCCESS("Berhasil!"),
	  NOTFOUND("Data Tidak Ditemukan!"),
	  ERROR("Error in services!"),
	  CREATED("Data Berhasil Ditambahkan!"),
	  UPDATED("Data Berhasil Diubah!"),
	  USED("Username Telah Digunakan!"),
	  DELETED("Data Berhasil dihapus!"),
	  BADREQUEST("Bad Request!"),
	  BADDATE("{data} Tanggal {date} belum tersedia!");

	  private final String value;

	  private Message(String value) {
	    this.value = value;
	  }

	  public String getValue() {
	    return value;
	  }
}
