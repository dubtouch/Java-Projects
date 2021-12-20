import java.util.ListResourceBundle;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.BiFunction;


public class App extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hello", "Hello!"},
                {"bye", new String[]{"Bye!", "Bye, bye!", "See you later!", "See you soon!", "Have a nice one!"}},
                {"answer.yes", new String[]{"y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct", "indeed", "you bet", "exactly", "you said it"}},
                {"answer.no", new String[]{"i don't think so", "yeah no", "n", "no", "no way", "nah", "nope", "negative", "I don't think so, yeah no"}},
                {"animal.name", (UnaryOperator<String>) name -> {
                    if (name.matches("(a|an) .*")) return name;
                    if (name.matches("[aeiou].*")) {
                        return "an " + name;
                    } else {
                        return "a " + name;
                    }
                }},
                {"animal.question", (UnaryOperator<String>) animal -> "Is it " + animal + "?"},
                {"game.pattern", Pattern.compile("(?i)It (can|has|is) (.+)")},
                {"game.printFacts", (BiFunction<String, Boolean, String>) (s, positive) -> {
                    if (s.equals("can")) {
                        return (positive ? "The %s can %s.\nThe %s can't %s." : "The %s can't %s.\n" + "The %s can %s.")
                                + "\nI can distinguish these animals by asking the question:\n - Can it %s?";
                    } else if (s.equals("is")) {
                        return (positive ? "The %s is %s.\nThe %s isn't %s." : "The %s isn't %s.\n" + "The %s is %s.")
                                + "\nI can distinguish these animals by asking the question:\n - Is it %s?";
                    } else {
                        return (positive ? "The %s has %s.\nThe %s doesn't have %s." : "The %s doesn't %s.\n" + "The %s has %s.")
                                + "\nI can distinguish these animals by asking the question:\n - Does it have %s?";
                    }
                }},
                {"process.facts", (BiFunction<Matcher, Boolean, String>) (matcher, positive) -> {
                    if (matcher.group(1).equals("can")) {
                        return positive ? " - It can " + matcher.group(2) + "\n" : " - It can't " + matcher.group(2) + "\n";
                    } else if (matcher.group(1).equals("is")) {
                        return positive ? " - It is " + matcher.group(2) + "\n" : " - It isn't " + matcher.group(2) + "\n";
                    } else {
                        return positive ? " - It has " + matcher.group(2) + "\n" : " - It doesn't have " + matcher.group(2) + "\n";
                    }
                }},
                {"ask.question", (Consumer<String>) s -> {
                    if (s.matches(".+ can .+")) {
                        System.out.println("Can it" + s.substring(6) + "?");
                    } else if (s.matches(".+ is .+")) {
                        System.out.println("Is it" + s.substring(5) + "?");
                    } else {
                        System.out.println("Does it have" + s.substring(6) + "?");
                    }
                }},
                {"ask.again", new String[]{"Come on, yes or no?",
                        "Please enter yes or no.",
                        "Funny, I still don’t understand, is it yes or no?",
                        "Sorry, it must be yes or no.",
                        "Let’s try again: yes or no?",
                        "I’m not sure I caught you: was it yes or no?",
                        "Oh, it’s too complicated for me: just say me yes or no.",
                        "I’m filling a bit intrigued: just say yes or no.",
                        "I am a bit confused, give me a simple answer: yes or no",
                        "Oh, no, don’t try to confuse me: say yes or no.",
                        "Could you please simply say yes or no?",
                        "Sorry, may I ask you again: was it yes or no?"}
                },
                {"animal.wantLearn", "I want to learn about animals."},
                {"animal.askFavorite", "Which animal do you like most?"},
                {"animal.learnedMuch", "I’ve learned so much about animals!"},
                {"animal.prompt", "Enter the animal:"},
                {"tree.search.nofacts", "No facts about the "},
                {"tree.search.facts", "Facts about the "},
                {"tree.stats.title", "The Knowledge Tree stats"},
                {"tree.stats.empty", "The tree is emtpy"},
                {"tree.stats.root", "root node "},
                {"tree.stats.nodes", "total number of nodes "},
                {"tree.stats.animals", "total number of animals "},
                {"tree.stats.statements", "total number of statements "},
                {"tree.stats.height", "height of the tree "},
                {"tree.stats.minimum", "minimum depth "},
                {"tree.stats.average", "average depth "},
                {"menu.property.title", "What do you want to do:"},
                {"menu.property.play", "1. Play the guessing game"},
                {"menu.property.list", "2. List of all animals"},
                {"menu.property.search", "3. Search for an animal"},
                {"menu.property.statistics", "4. Calculate statistics"},
                {"menu.property.print", "5. Print the Knowledge Tree"},
                {"menu.property.exit", "0. Exit"},
                {"game.think", "You think of an animal, and I guess it."},
                {"game.enter", "Press enter when you’re ready."},
                {"game.again", "Would you like to play again?"},
                {"game.giveUp", "I give up. What animal do you have in mind?"},
                {"game.isCorrect", "Is the statement correct for "},
                {"greeting.morning", "Good morning! "},
                {"greeting.afternoon", "Good afternoon! "},
                {"greeting.evening", "Good evening! "},
                {"welcome", "Welcome to the animal’s expert system!"},
                {"game.learned", "I have learned the following facts about animals:"},
                {"statement.prompt", "Specify a fact that distinguishes %s from %s.\n" +
                        "The sentence should be of the format: 'It can/has/is ...'."},
        };
    }
}