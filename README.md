# Link Shortener

## Description
Welcome to **Link Shortener**, a personal project built with Maven, Java, Spring Boot, and MySQL.

This app receives a URL and returns an ID that represents the original URL in the DB.
This ID is a combination of six numbers and letters.

### Can collisions occur between different URLs?

Collisions are possible. This means that, for two or more URLs, this API might generate the same ID.

### How to avoid collisions?

First of all, If you have 36 possible characters (numbers from 0 to 9 and letters from A to Z) 
and you combine them into a sequence of 6 positions, the total number of possible combinations is 36⁶, 
which equals 2,176,782,336 different IDs.
Despite this large number of combinations, collisions can still occur. 
In such cases, when a new URL generates an ID that already exists in the database, 
the app searches for the generated ID and, if it already exists, adds new random symbols (letters and numbers) 
until a unique ID is created.


> **Note:** This project is for practice purposes only.

## Try it out
You can test it at the front-end web page:  
[https://sfshortlink.vercel.app](https://sfshortlink.vercel.app/)

You can see the front-end code [here](https://github.com/SebastianFurnier/Link-shortener-front)

## For comments you can contact me here:
[portfolio](https://sebastianfurnier.vercel.app/)