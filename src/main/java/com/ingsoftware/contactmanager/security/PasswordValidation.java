package com.ingsoftware.contactmanager.security;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidation {

    public static PasswordValidator validator = new PasswordValidator(
            new LengthRule(8, 16),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Alphabetical, 1),
            new WhitespaceRule()
    );

    public RuleResult validatePassword(String password) {
        final char[] charArrayPassword = password.toCharArray();
        return validator.validate(new PasswordData(new String(charArrayPassword)));
    }
}
