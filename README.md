# Wireless-Module-Ad-Hoc
Peripheral Designed for Mobile Phones to Support Ad Hoc Network


# Introduction
There is a great need on a wide range of mobile Ad Hoc network when emergency occurs, such as earthquake and tsunami. This project aims to design and implement a peripheral for mobile phones to support the multimedia communication without the support of telecommunication infrastructure. 


# System Architecture
![Hardware architecture](https://github.com/easy41/Images/blob/master/Hardware%20architecture.png)

1. Wireless Data Transmission Module (WDTM)
Taking power consumption and price into consideration, this project adopted FT-52 as the wireless data transmission module. FT-52 is half-duplex and low power consumption. Its maximum transmit power is 200mW and the transmit distance is around 1 km in open areas. 


2. Bluetooth Module
The Bluetooth module is closely connected to FT-52 based on serial communication. It enables the wireless communication between FT-52 and mobile devices such as smart phones.

3. Mobile device
The mobile device was equipped with an Android application to implement the originally designed Ad Hoc protocol (low power consumption AODV) and multi-media communication functions.


# Software Design
![Software architecture](https://github.com/easy41/Images/blob/master/Software%20architecture.png)

The software part was implemented as an Android application. To distinguish received data, the system assigns tags to the data blocks. The algorithm is able to pack and unpack the transmitting data with tags according to their types.

1. Text and image transmission
Activities include login, logout, send text, image and etc...

2. Location sharing
Adopting the Baidu Map API, it is able to show the location information intuitively.

3. Relay
The peripheral can also serve as a relay by broadcasting the message received to the peripherals around automatically.


# Usage
Clone the code, import to the Android Studio and build the gradle.

