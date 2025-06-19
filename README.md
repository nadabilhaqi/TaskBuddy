# TaskBuddy – Manajemen Tugas Personal (CLI-Based)

## 📌 Deskripsi Singkat

**TaskBuddy** adalah aplikasi berbasis Command Line Interface (CLI) yang dirancang untuk membantu pengguna dalam mengelola tugas pribadi secara terstruktur. Aplikasi ini mengimplementasikan berbagai konsep Struktur Data dan Pemrograman Berorientasi Objek (OOP) yang telah dipelajari dalam praktikum Pemrograman Lanjut.

## 🧠 Fitur Utama

### ✅ Manajemen Daftar Tugas
- **Struktur tugas Tree**  
  Setiap tugas dapat memiliki subtugas, disusun dalam bentuk pohon (Tree) berdasarkan kategori dan hierarki.
  
- **Log Aktivitas (Double Linked List)**  
  Setiap perubahan (login, tambah, ubah, hapus tugas) dicatat dalam log riwayat menggunakan double linked list.
  
- **Antrian Pengguna (Queue)**  
  Simulasi multi-user dengan sistem antrian (queue) untuk bergiliran mengakses sistem.

- **Sorting & Searching**
  - Sorting daftar tugas berdasarkan **prioritas**, **deadline**, **nama**, atau **status** menggunakan *Bubble Sort*.
  - Pencarian tugas berdasarkan **nama/deskripsi**, **prioritas**, **status**, dan bisa juga mencari **task yang terlambat** menggunakan *Linear Search*.

## 🧑‍🤝‍🧑 Anggota Kelompok

| No | Nama Anggota | NIM | Tanggung Jawab |
|----|--------------|-----|----------------|
| 1 | Nada Musyaffa Bilhaqi      | 245150400111035 | Struktur tugas (Tree) |
| 2 | Muhammad Rifa Aqilla      | 245150407111047 | Log perubahan (Double Linked List) & Antrian user (Queue)|
| 3 | Zaky Ahmady Santoso      | 245150407111048 | Sorting dan Searching & Manajemen pengguna dan peran |
| 4 | A. Muh. Abduh Dzaky | 245150407111039 | Menu CLI & logika utama |

## 🚀 Cara Menjalankan Aplikasi

1. **Persyaratan**:
   - Java Development Kit (JDK) versi 8 atau lebih tinggi.
   - Code editor untuk menjalankan aplikasi CLI.

2. **Langkah-langkah**:

   - Klon repositori:
     ```bash
     git clone https://github.com/nadabilhaqi/TaskBuddy.git
     ```

   
   - Jalankan aplikasi:
     ```bash
     java TaskBuddyApp.java
     ```

   - Login menggunakan akun default, misalnya:
     - `admin / admin123`
     - `user1 / pass123`
     - `guest / guest`


   > Setelah login, ikuti instruksi yang ditampilkan di Command Line Interface.
