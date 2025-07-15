package org.adeniuobesu.resumegenerator.core.validation;

import java.util.Map;

import org.adeniuobesu.resumegenerator.core.models.ContactMethod;
import org.adeniuobesu.resumegenerator.core.models.ContactType;

public final class ContactMethodValidator {
    private static final Map<ContactType, String> VALIDATION_RULES = Map.of(
        ContactType.EMAIL, "EMAIL",
        ContactType.PHONE, "PHONE",
        ContactType.LINKEDIN, "URL",
        ContactType.GITHUB, "URL",
        ContactType.PORTFOLIO, "URL"
    );

    private static final Map<ContactType, String> FIELD_NAMES = Map.of(
        ContactType.EMAIL, "Email address",
        ContactType.PHONE, "Phone number",
        ContactType.LINKEDIN, "LinkedIn URL",
        ContactType.GITHUB, "GitHub URL",
        ContactType.PORTFOLIO, "Portfolio URL",
        ContactType.CITY, "City",
        ContactType.COUNTRY, "Country"
    );

    private static final Map<ContactType, Integer> MAX_LENGTHS = Map.of(
        ContactType.CITY, 50,
        ContactType.COUNTRY, 50
    );

    public static void validate(ContactMethod contact) {
        ValidationUtils.requireNonNull(contact, "Contact method");
        ValidationUtils.requireNonNull(contact.type(), "Contact type");
        
        final String fieldName = FIELD_NAMES.getOrDefault(
            contact.type(), 
            contact.type().name()
        );

        // Special handling for CITY/COUNTRY
        if (contact.type() == ContactType.CITY || contact.type() == ContactType.COUNTRY) {
            ValidationUtils.validateString(
                contact.value(), 
                fieldName, 
                2, 
                MAX_LENGTHS.get(contact.type())
            );
            return;
        }

        // Standard validation for other types
        ValidationUtils.validateString(contact.value(), fieldName, 3, 200);
        
        if (VALIDATION_RULES.containsKey(contact.type())) {
            ValidationUtils.validatePattern(
                contact.value(),
                fieldName,
                VALIDATION_RULES.get(contact.type())
            );
        }
    }
}
