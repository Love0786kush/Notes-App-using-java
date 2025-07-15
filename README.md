# 📒 Note-App-With-Java

A lightweight and clean Notes App developed using **Java** and **Room Database**, with a structure inspired by **MVVM architecture**. Designed to manage notes efficiently with basic CRUD operations and a real-time search feature.

---

## ✨ Features

- ✅ Add new notes
- ✏️ Edit existing notes
- 🗑️ Delete notes with confirmation
- 🔍 Live search filtering
- 🧾 View notes in RecyclerView
- 🧠 Data persistence using Room Database

---

## 🧱 Tech Stack

| Component         | Technology Used         |
|------------------|--------------------------|
| Language          | Java                     |
| Database          | Room Persistence Library |
| UI Architecture   | LiveData + ViewModel     |
| UI Elements       | RecyclerView, Fragments, ViewBinding |
| Navigation        | Android Jetpack Navigation |

---

## 📂 Project Structure

com.example.notesapp/
├── adapter/ # RecyclerView adapter (NoteAdapter)
├── database/ # Room DB + DAO (NoteDatabase, NoteDao)
├── fragment/ # Fragments (Home, Add, Edit)
├── model/ # Note.java model
├── repository/ # Repository (NoteRepository.java)
├── viewModel/ # NoteViewModel.java and Factory
└── MainActivity.java # Main activity that hosts NavHostFragment

yaml
Copy
Edit

---

## 🧑‍💻 How It Works

1. **Room** handles local database storage.
2. **NoteViewModel** fetches and manages data using the Repository.
3. **LiveData** updates the UI whenever data changes.
4. **Fragments** represent different screens (Home, Add Note, Edit Note).
5. **RecyclerView** displays the list of notes.
6. **Navigation Component** is used for smooth screen transitions.

---



🙋‍♂️ Author
Developed by Lovekush Prajapat
📧 Email: lovekushprajapat0786@gmail.com

