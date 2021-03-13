package com.dongmyungahn.android.javajokeslib;

import java.util.Random;

public class Jokes {

    /*
        Jokes Sources
        https://answersafrica.com/best-funny-short-jokes.html

     */
    private static final String[] jokesList = {
            "There were two peanuts walking down a dark alley, one was assaulted.",
            "What do you call a sleepwalking nun… A roamin’ Catholic.",
            "How do you make holy water? You boil the hell out of it.",
            "What did the 0 say to the 8? Nice belt!",
            "Why did the orange stop? Because, it ran outta juice.",
            "What’s brown and sounds like a bell? Dung!",
            "Knock knock. Who’s there? Interrupting Cow. Interrupting Cow who- MOOOOOOO!",
            "Why did the storm trooper buy an iphone? He couldn’t find the Droid he was looking for.",
            "Knock knock…who’s there? I eat mop. I eat mop who? Ooooo gross! (now do you get the earlier one?)",
            "Why is six afraid of seven? Because seven ate nine."
    };

    public String getJoke()
    {
        return jokesList[new Random().nextInt(jokesList.length)];
    }


}
