# Facebook Android Application

## Description
This Android application serves as a client for a social media platform. It allows users to register, log in, view their feed, create and delete posts, manage friends, and more.

## Features
1. **User authentication**: Registering new users and logging into existing accounts.
2. **Feed display**: Showing posts from friends and new posts.
3. **Post management**: Creating, editing, and deleting posts.
4. **Comment management**: Users can view comments on posts, and participate in discussions by adding their own comments to posts.
5. **Friends management**: Viewing friends list, sending friend requests, accepting friend requests, and removing friends.
6. **Profile management**: Viewing and updating user profile.
7. **Account deletion**: Deleting user accounts.
8. **Like/Dislike posts**: Interact with posts by liking or disliking them.
9. **Dark/Light mode**: Toggle between dark and light themes for better user experience.

## Setup Instructions
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Ensure that the necessary dependencies are installed.
4. Run the application on an Android emulator.

## How to Run
1. In Android Studio, select your device from the list of available devices.
2. Click on the "Run" button to build and install the app on the device.
3. Once installed, open the app on the device and follow the on-screen instructions to register or log in.



## Instructions for Changing Server URL if needed
**Note:** By default, the server URL is set to `http://10.0.2.2:8080/api/` and may not require modification unless you are connecting to a different server.

   - Navigate and open `res` > `values` > `strings.xml` file in a text editor or within Android Studio.
   - Find the line `<string name="BaseUrl">http://10.0.2.2:8080/api/</string>`.
   - Change the URL within the `<string>` tags to the desired server URL. For example:
     ```xml
     <string name="BaseUrl">http://your-new-server-url.com/api/</string>
     ```
   - Save the `strings.xml` file after making the modifications.
   - rebuild the App to ensure that the changes are applied.


## Screenshots
<!-- Add screenshots of your app to illustrate its functionality -->
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/afd61af6-af3c-4221-b05d-0142c8327502" alt="Screenshot 1" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/4e53f6b7-fefe-4f43-a800-33d879bef428" alt="Screenshot 2" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/2086aadb-d286-4ef2-8fe1-98fb6457cd85" alt="Screenshot 3" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/c44ed4e4-2ef9-40e1-ac6c-fa8940e6874e" alt="Screenshot 8" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/c94b130f-9da1-4c07-b0d4-0d07202818cc" alt="Screenshot 4" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/b895f974-98d9-4cd3-ab2c-e712449d0208" alt="Screenshot 5" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/47ae69dc-853e-42bb-825d-ada098d46691" alt="Screenshot 6" width="300">
<img src="https://github.com/RoeeHashai/FacebookASP-AndroidApp-FE/assets/155381822/2a542d5b-505d-414a-b5a9-a115f1fe7a7d" alt="Screenshot 7" width="300">

## Limitations
1. This app requires an active server connection.
2. It supports only the features provided by the API.
3. No sensitive data should be stored on the device or sent to the server.

## Conclusion
Please ensure to follow the setup instructions carefully and use the application responsibly. For any issues or feedback, please contact the project team.
