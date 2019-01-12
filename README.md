# Wireless-Module-Ad-Hoc
Peripheral Designed for Mobile Phones to Support Ad Hoc Network


# Introduction
There is a great need on a wide range of mobile Ad Hoc network when emergency occurs, such as earthquake and tsunami. This project aims to design and implement a peripheral for mobile phones to support the multimedia communication without the support of telecommunication infrastructure. 


# System Architecture
![Hardware architecture](/Users/apple/Desktop/Desktop/Sync/github/Software architecture.png)

1. Wireless Data Transmission Module (WDTM)
Wireless data transmission modules are connected to the mobiles through bluetooth. WDTM are served 
to transmit and receive the data

2. Bluetooth Module
Bluetooth modules are used to connect the WDTM

3. Android Application
The Android Application has the function of login, transmit of text, image, get current location, share
location information with group members.


# Software Design


1. Activities
Activities include login, logout, send text, image and etc...

2. Bluetooth
Call bluetooth thread to achieve the function of search, connect to the bluetooth

3. Map Api
Call Baidu Map Api to get the real-time locaiton

4. Data Processing
Encode text, image at the transmitting end and decode them at the receiving end.


# Usage
Clone the code, import to the Android Studio and build the gradle.

