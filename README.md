# Link Shortener

## Description
Welcome to **Link Shortener**, a personal project built with Maven, Java, Spring Boot, and MySQL.

This app receives a URL and returns a shortened version, which is the backend URL plus an ID that identifies the original URL.
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
You can test it at the following link:  
[https://sf.up.railway.app/shortener](http://sf.up.railway.app/shortener)

## How to Use
### 1. Using Postman
You can interact with the project by sending requests via Postman.
Create a POST request and send the following JSON body:

```json
{
    "url": "(your url)"
}
```
![Postman 1](Postman1.PNG)


### 2. Api Response
The API will return a shortened URL that you can copy and paste into your browser.
This shortened link will redirect you to the original URL.

![Postman 2](Postman2.PNG)

### 3. Example of use

![example](use_example.gif)