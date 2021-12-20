import java.util.ListResourceBundle;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App_eo extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hello", "Hello!"},
                {"bye", new String[]{"\u011dis!", "\u011dis revido!", "Estis agrable vidi vin!"}},
                {"answer.yes", new String[]{"jes"}},
                {"answer.no", new String[]{"ne"}},
                {"animal.name", (UnaryOperator<String>) name -> name},
                {"animal.question", (UnaryOperator<String>) animal ->
                        "Ĉu \u011di estas " + animal + "?"},
                {"game.pattern", Pattern.compile("(?i)\u011di (povas|estas|havas|lo\u011das) (.+)")},
                {"game.printFacts", (BiFunction<String, Boolean, String>) (s, positive) -> {
                    if (s.equals("povas")) {
                        return (positive ? "La %s povas %s.\nLa %s ne povas %s." : "La %s ne povas %s.\n" + "La %s povas %s.")
                                + "\nMi povas distingi ĉi tiujn bestojn per la demando:\n - Povas %s?";
                    } else if (s.equals("estas")) {
                        return (positive ? "La %s estas %s.\nLa %s ne estas %s." : "La %s ne estas %s.\n" + "La %s estas %s.")
                                + "\nMi povas distingi ĉi tiujn bestojn per la demando:\n - Estas %s?";
                    } else if (s.equals("logas")) {
                        return (positive ? "La %s lo\u011das %s.\nLa %s ne lo\u011das %s." : "La %s ne lo\u011das %s.\n" + "La %s lo\u011das %s.")
                                + "\nMi povas distingi ĉi tiujn bestojn per la demando:\n - Lo\u011das %s?";
                    } else {
                        return (positive ? "La %s havas %s.\nLa %s ne havas %s." : "La %s ne havas %s.\n" + "La %s havas %s.")
                                + "\nMi povas distingi ĉi tiujn bestojn per la demando:\n - Havas %s?";
                    }
                }},
                {"process.facts", (BiFunction<Matcher, Boolean, String>) (matcher, positive) -> {
                    return positive ? "\u011di " + matcher.group(1) + " " + matcher.group(2) + "\n" : "\u011di ne " + matcher.group(1) + " " + matcher.group(2) + "\n";
                }},
                {"ask.question", (Consumer<String>) s -> {
                    System.out.println(s + "?");
                }},
                {"ask.again", new String[]{"Bonvolu enigi jes aŭ ne.",
                        "Amuze, mi ankoraŭ ne komprenas, ĉu jes aŭ ne?",
                        "Let’s try again: yes or no?",
                        "Pardonu, devas esti jes aŭ ne.",
                        "Ni provu denove: ĉu jes aŭ ne?",
                        "Pardonu, ĉu mi rajtas demandi vin denove: ĉu tio estis jes aŭ ne?"}},
                {"animal.wantLearn", "Mi volas lerni pri bestoj."},
                {"animal.askFavorite", "Kiun beston vi plej \u015datas?"},
                {"animal.learnedMuch", "Mi lernis tiom multe pri bestoj!"},
                {"animal.prompt", "Enigu la nomon de besto:"},
                {"tree.search.nofacts", "Neniuj faktoj pri la "},
                {"tree.search.facts", "Faktoj pri la "},
                {"tree.stats.title", "La statistiko de la Scio-Arbo"},
                {"tree.stats.empty", "The tree is emtpy"},
                {"tree.stats.root", "radika nodo "},
                {"tree.stats.nodes", "totala nombro de nodoj "},
                {"tree.stats.animals", "totala nombro de bestoj "},
                {"tree.stats.statements", "totala nombro de deklaroj "},
                {"tree.stats.height", "alteco de la arbo "},
                {"tree.stats.minimum", " minimuma profundo "},
                {"tree.stats.average", "avera\u011da profundo "},
                {"menu.property.title", "Kion vi volas fari:"},
                {"menu.property.play", "1. Ludi la divenludon"},
                {"menu.property.list", "2. Listo de ĉiuj bestoj"},
                {"menu.property.search", "3. Serĉi beston"},
                {"menu.property.statistics", "4. Kalkuli statistikojn"},
                {"menu.property.print", "5. Printi la Sciarbon"},
                {"menu.property.exit", "0. Eliri"},
                {"game.think", "Vi pensu pri besto, kaj mi divenos \u011din."},
                {"game.enter", "Premu enen kiam vi pretas."},
                {"game.again", "Ĉu vi volas provi denove?"},
                {"game.giveUp", "Mi rezignas. Kiun beston vi havas en la kapo?"},
                {"game.isCorrect", "Ĉu la aserto \u011dustas por la "},
                {"greeting.morning", "Bonan matenon! "},
                {"greeting.afternoon", "Bonan tagon! "},
                {"greeting.evening", "Bonan vesperon! "},
                {"welcome", "Bonvenon al la sperta sistemo de la besto!"},
                {"game.learned", "Mi lernis la jenajn faktojn pri bestoj:"},
                {"game.distinguish", "Mi povas distingi ĉi tiujn bestojn per la demando:"},
                {"statement.prompt", "Indiku fakton, kiu distingas %s de %s.\n" +
                        "La frazo devas esti de la formato: '\u011di ...'."},
        };
    }
}
