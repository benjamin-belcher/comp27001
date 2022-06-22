package com.example.workernodes;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// Custom class to create a number field
public class NumberField  extends TextField {
    public NumberField() {
        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle( KeyEvent t ) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    System.out.println("The char you entered is not a number");
                    t.consume();
                }
            }
        });
    }
}

