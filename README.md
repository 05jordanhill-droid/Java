# Overview

This program takes the Number Guessing Game with choosing the minimal and maximal possibilities and then cranks the options further. In addition to the classic game, you can run an algorithm to pinpoint the random number systematically, displaying what the number was and how many guesses it took to find it. Furthermore, you can graph this out by providing a range to the options. The range is iterated to find the hardest number to pinpoint in each iteration (enter in 100 > 1 through 1 is the range ran, then 1 through 2, then 1 through 3, etc... 1 through 100).

The purpose for writing this software is satiating my desire to find patterns. When making the auto number guesser I found that with a varying amount of guesses required to find the answer, that was data that I could chart. Charting this data led me to finding a relationship between the number of guesses and the maximal value when looking at each increase of the value from a base 2 perspective. This is likely due to how the algorithm pinpoints its guesses.

{Provide a link to your YouTube demonstration. It should be a 4-5 minute demo of the software running and a walkthrough of the code. Focus should be on sharing what you learned about the language syntax.}

[Software Demo Video](http://youtube.link.goes.here)

# Development Environment

The primary tool that I used to help build this software was VSCode.
I used the assistance of ChatGPT to help understand syntax of Java as I transitioned over from C#. As I learned Java, I made Support functions to help integrate myself further in the language.

I used Java for this software along with a huge variety of libraries due to the nature of Java not having nearly anything native to it (As can be seen at the top of many of the program files).

# Useful Websites

- [Java Tutorial—W3Schools](https://www.w3schools.com/java/default.asp)
- [Java Reference—Oracle](https://docs.oracle.com/en/java/javase/index.html)
- [ChatGPT - Java Questions](https://chatgpt.com/share/6ac15fe3-2338-83e8-9466-10ad1f545dac)

# Future Work

- Incorporate a better method of clearing the terminal.
- Incorporate safeguards against ranges too high to run.
- Incorporate more methods of analyzing the data.