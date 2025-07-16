Android Enrollment Application (Khai Project)
Overview
The Android Enrollment Application (Khai Project) is a mobile application designed to streamline the student enrollment process for educational institutions. This app provides a user-friendly interface for students to register, submit required documents, and track their enrollment status. Administrators can manage applications, review submissions, and communicate with applicants. Built with modern Android development practices, this project aims to simplify and automate the enrollment workflow.
Features

User Registration and Authentication: Secure sign-up and login for students and administrators using email/password or third-party services (e.g., Google Sign-In).
Enrollment Form Submission: Allows students to fill out and submit enrollment forms with personal details, academic records, and supporting documents.
Document Upload: Supports uploading of required documents (e.g., transcripts, ID proofs) in formats like PDF and JPEG.
Application Status Tracking: Real-time updates on application status (e.g., pending, under review, approved, rejected).
Admin Dashboard: Interface for administrators to review applications, approve/reject submissions, and manage user data.
Notifications: Push notifications to inform users about application updates or deadlines.
Multi-language Support: Supports multiple languages to cater to diverse users.
Offline Capability: Basic offline functionality to save draft forms and sync when online.

Tech Stack

Programming Language: Kotlin/Java
Framework: Android SDK
Architecture: Model-View-ViewModel (MVVM) / Model-View-Presenter (MVP)
Database: 
Local: Room Database for offline storage
Remote: Firebase Realtime Database / Firestore (assumed based on common practices)


Backend Services: Firebase for authentication, storage, and push notifications
Libraries:
Retrofit/OkHttp for API calls
Glide/Picasso for image handling
Jetpack Compose (if used) or XML for UI
Dagger/Hilt for dependency injection


Version Control: Git
IDE: Android Studio

Prerequisites
Before setting up the project, ensure you have the following:

Android Studio: Version 4.2 or higher
JDK: Version 11 or higher
Android SDK: API Level 21 (Lollipop) or higher
Firebase Account: For backend services like authentication and database
Git: For cloning the repository
Gradle: Version 7.0 or higher (managed by Android Studio)

Installation and Setup
Follow these steps to set up the project locally:

Clone the Repository:
git clone https://github.com/abdurrahmankhairii/Android_Enrollment_Application_Khai_Project.git
cd Android_Enrollment_Application_Khai_Project


Set Up Firebase:

Create a Firebase project at console.firebase.google.com.
Add an Android app to your Firebase project and download the google-services.json file.
Place the google-services.json file in the app/ directory of the project.
Enable Firebase Authentication, Firestore/Realtime Database, and Storage in the Firebase Console.
Enable AppCheck to prevent API abuse (optional but recommended).


Configure Gradle:

Open the project in Android Studio.
Sync the project with Gradle to download dependencies.
Ensure the following lines are in your build.gradle (project level):buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.3.15'
    }
}


And in build.gradle (app level):apply plugin: 'com.google.gms.google-services'




Optional Gradle Properties:

To customize configurations (e.g., font rendering), add properties to ~/.gradle/gradle.properties:org.gradle.jvmargs=-Xmx4g
android.useAndroidX=true




Build and Run:

Connect an Android device or start an emulator.
Build the project using Build > Make Project.
Run the app using Run > Run 'app'.



Usage

For Students:

Register or log in using your credentials.
Complete the enrollment form with personal and academic details.
Upload required documents via the document upload feature.
Track your application status in the app’s dashboard.
Receive notifications for updates or additional requirements.


For Administrators:

Log in with admin credentials.
Access the admin dashboard to view and manage applications.
Approve or reject applications and provide feedback.
Monitor enrollment statistics and generate reports (if implemented).



Project Structure
Android_Enrollment_Application_Khai_Project/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/enrollmentapp/
│   │   │   │   ├── activities/      # UI screens (Activities/Fragments)
│   │   │   │   ├── adapters/       # RecyclerView adapters
│   │   │   │   ├── models/         # Data models
│   │   │   │   ├── viewmodels/     # ViewModels for MVVM
│   │   │   │   ├── repository/     # Data handling (API, database)
│   │   │   ├── res/
│   │   │   │   ├── layout/        # XML layouts
│   │   │   │   ├── values/        # Strings, colors, dimensions
│   │   │   ├── AndroidManifest.xml
│   ├── google-services.json       # Firebase configuration
├── gradle/
├── build.gradle                   # Project-level Gradle
├── app/build.gradle               # App-level Gradle
├── README.md                      # This file

Contributing
We welcome contributions to improve the project! To contribute:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Make your changes and commit (git commit -m "Add your feature").
Push to the branch (git push origin feature/your-feature).
Open a Pull Request with a detailed description of your changes.

Please ensure your code follows the project’s coding standards and includes appropriate tests.
License
This project is licensed under the MIT License. See the LICENSE file for details.
Acknowledgements

Thanks to the Android development community for resources and libraries.
Special thanks to Firebase for providing robust backend services.
Contributors: [List contributors or leave blank if none specified].

Contact
For issues, suggestions, or questions, please open an issue on the GitHub repository or contact the project maintainer at abdurrahmankhairi17@gmail.com
