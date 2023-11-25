package buysellmoto.core.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NamingConventionEnum {

    CAMEL("always starts out lowercase with each word delimited by a capital letter (like personOne, textUtil, thingsToDo)"),
    SNAKE("means that we delimit words with an underscore (like person_one, text_util, things_to_do)"),
    PASCAL("is similar to camel case, but the first letter is always capitalized (like PersonOne, TextUtil, ThingsToDo)");

    private final String noun;

}
