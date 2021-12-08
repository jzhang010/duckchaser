# Computer Vision in Robotics - User Manual
**The User Manual is best viewed on https://github.com/jzhang010/duckchaser/edit/cs50-final-project/README.md for picture instruction
This robot project uses computer vision to recognize location of objects, which informs autonomous robot decisions. 

## Software Setup 
### Setting up Android Studio 
1. Download Android Studio (https://developer.android.com/studio)
2. Click on "Android Studio" on the top tool bar and go to Preferences->Appearance and Behavior->System Settings->Android SDK and make sure that Android 8.0-10.0 are all checked under SDK platforms. Apply any changes if necessary. <img width="981" alt="Screen Shot 2021-12-07 at 12 14 05 PM" src="https://user-images.githubusercontent.com/29489739/145075476-fdac9d9c-af0c-402d-94d9-f3f67dad6e59.png">
3. Click on "File" on the top tool bar and select "Open...". Navigate to the duckchaser project and select the entire folder to be opened in Android Studio. 
<img width="833" alt="Screen Shot 2021-12-07 at 4 51 53 PM" src="https://user-images.githubusercontent.com/29489739/145111798-87747a21-673a-4293-95ea-83ba0babdb59.png">
4. Click on "Build" on the top tool bar and click on "Rebuild Project". Look for "BUILD SUCCESSFUL" on the bottom log. <img width="892" alt="Screen Shot 2021-12-07 at 12 24 13 PM" src="https://user-images.githubusercontent.com/29489739/145076747-967543ba-2ae3-4aaa-adf7-eb63fb6a63b5.png">

Problem solving: 
* If the robot project fails to build, try opening up build.gradle (Project) under Gradle sScripts and clicking the lightbulb on line 9 if it appears and clicking "Try Again"
* Make sure the computer is connnected to wifi
* Make sure that Instant Run is disabled in by going to Android Studio Preferences 

You can also refer to https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/android-studio-guide.pdf for further questions. 

## Robot Setup 
### Materials Needed 
* Robot!
* Gamepad controller
* Battery
* Android Phone
* Measuring Tape 
* USB to USB C cable 

### Robot 
Note (Robot Orientation): The front of the robot is where the camera is and the back of the robot is where the tail is. 
1. Place battery vertically into foam holder 
2. Connect the battery wire with the battery wire coming out of the control hub 
3. Flip the switch on the left side of the robot toward the back 
The robot should now be turned on! Looking for a blue light on the control hub and wait for it to turn green 
Tips: Make sure that the wires are securely and correctly connected and that the battery is relatively snug in the foam holder  
![IMG_20211206_221346](https://user-images.githubusercontent.com/29489739/145090087-6daf8f65-e913-412b-b37f-1aa017c97f3e.jpg)

## Pushing code onto the robot  
1. Connect the computer to the robot using the USB to USB C cable by plugging the USB C side into the control hub and the USB side into the computer. 
2. Make sure the robot is powered on and the green light is on the control hub
3. Press the play button on top of the tool bar in the window to push the code onto the robot <img width="1433" alt="Screen Shot 2021-12-07 at 12 32 21 PM" src="https://user-images.githubusercontent.com/29489739/145078159-6b655d8d-7049-437a-af63-7892b674f4d0.png">
4. Wait for the light on the control hub to turn back green again

## Running robot code
Open up "FTC Driver Station" on the Android Phone 

### Human Drive 
1. Plug in the gamepad into the phone
2. Press the "start" and "A" button at the same, and a gamepad icon should show up above User 1 on the top right
3. Select TeleOp Op Mode by clicking the right white down arrow
4. Select the "Human Drive" program 
5. Press INIT and then the play button to runthe program! 
You can view logs of the robot motors in the telemetry, or the black region on the bottom of the screen. 

Controller mappings:
* Left joystick forward/backward: The robot will drive forward/backwards 
* Left joystick right/left: The robot will strafe right/left 
* Right joystick right/left: The robot will turn right/left 
* X button: Sets the tail to the left position
* B button: Sets the tail to the right position
* A button: Sets the tail to the left position

### Auto Drive to Duck 
1. Select the Autonomous Op Mode by clicking the left white down arrow
2. Select the "Auto Drive To Duck" program 
#### 3a. Press INIT
In the initialization state, we can view the camera stream and adjust the camera position as needed. To view the camera stream, press on the three dots on toward the right side of the screen and select "Camera Stream". We can now see what the camera is seeing! You do need to keep tapping the screen to get the most recent images. Notice that there are three blue boxes outlined on the camera stream, those are the regions that code is looking for the duck in. Place the duck 16" from the front of the robot and in line with the center of the duck. Move the camera up and down by changing the angle of the L shape that it is attached to or slide it side or side as necessary to align the duck with the center blue box and make sure that the telemetry is outputting that the duck is in the center, as the center blue box should turn red or green based on its confidence. Slide the duck left and right to make sure that the camera is also recognizing the duck in the correctly in the right and left positions and adjust the camera and/or duck location as necessary. The tail should also move in accordance to what position the code thinks the duck is, so please play around with the different duck positions! Try to keep it the duck 16" away from the robot so the robot doesn't run over the duck when it starts driving autonomously. 

![IMG_20211207_130103](https://user-images.githubusercontent.com/29489739/145089740-5dbb045c-b74c-48fe-9528-faaa54234a36.jpg)
#### 3a. Run the program
Once you have placed the duck in one of the three possible regions that the robot camera is analyzing for a duck, making sure that telemetry correctly outputs the position the code thinks the duck is at, you can now run the program! Since we opened up the camera stream, we need to stop the program first by pressing the stop button and reinitialize but without opening the camera stream. Wait for the telemetry to output the correct position of the duck, and then hit the start button. The robot should drive to the duck! Feel free to play around with the different duck positions, but make sure to repeat the initialziation process in properly setting up the duck and robot. 

## Project Video
See our robot in action! 
https://www.youtube.com/watch?v=M0E-NeL26WA

