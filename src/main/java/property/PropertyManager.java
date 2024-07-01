package property;

import java.util.ResourceBundle;

public class PropertyManager {
    private static final String CONFIG_PATH = "application";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG_PATH);

    private PropertyManager() {
    }

    public static int getPropertyByPredicate(PropertyKey propertyKey) {
        final int propertyValue;

        try {
            propertyValue = Integer.parseInt(resourceBundle.getString(propertyKey.getKey()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Property must be a Integer");
        }

        if (!propertyKey.getValidationCondition().test(propertyValue)) {
            throw new IllegalArgumentException("Property validation exception: " + propertyKey);
        }

        return propertyValue;
    }
}