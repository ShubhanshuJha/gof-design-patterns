import java.util.List;

/**
 * Abstract Factory pattern demonstrated with compatible UI product families.
 */
public final class AbstractFactoryPattern {
    private AbstractFactoryPattern() {
    }

    public interface Button {
        String familyName();

        String render();
    }

    public interface Checkbox {
        String familyName();

        String render();
    }

    public interface UiFactory {
        Button createButton();

        Checkbox createCheckbox();
    }

    public static final class LightButton implements Button {
        @Override
        public String familyName() {
            return "light";
        }

        @Override
        public String render() {
            return "Light button";
        }
    }

    public static final class LightCheckbox implements Checkbox {
        @Override
        public String familyName() {
            return "light";
        }

        @Override
        public String render() {
            return "Light checkbox";
        }
    }

    public static final class DarkButton implements Button {
        @Override
        public String familyName() {
            return "dark";
        }

        @Override
        public String render() {
            return "Dark button";
        }
    }

    public static final class DarkCheckbox implements Checkbox {
        @Override
        public String familyName() {
            return "dark";
        }

        @Override
        public String render() {
            return "Dark checkbox";
        }
    }

    public static final class LightUiFactory implements UiFactory {
        @Override
        public Button createButton() {
            return new LightButton();
        }

        @Override
        public Checkbox createCheckbox() {
            return new LightCheckbox();
        }
    }

    public static final class DarkUiFactory implements UiFactory {
        @Override
        public Button createButton() {
            return new DarkButton();
        }

        @Override
        public Checkbox createCheckbox() {
            return new DarkCheckbox();
        }
    }

    public static final class Application {
        private final UiFactory factory;

        public Application(UiFactory factory) {
            this.factory = factory;
        }

        public String render() {
            Button button = factory.createButton();
            Checkbox checkbox = factory.createCheckbox();

            if (!button.familyName().equals(checkbox.familyName())) {
                throw new IllegalStateException(
                        "Products must belong to the same family"
                );
            }

            return button.render() + " | " + checkbox.render();
        }
    }

    public static void main(String[] args) {
        for (UiFactory factory : List.of(
                new LightUiFactory(),
                new DarkUiFactory()
        )) {
            System.out.println(new Application(factory).render());
        }
    }
}

