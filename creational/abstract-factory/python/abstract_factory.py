"""Abstract Factory pattern demonstrated with compatible UI product families."""

from __future__ import annotations

from abc import ABC, abstractmethod


class Button(ABC):
    """Abstract product type A."""

    @property
    @abstractmethod
    def family_name(self) -> str:
        """Return the product family name."""

    @abstractmethod
    def render(self) -> str:
        """Render the button."""


class Checkbox(ABC):
    """Abstract product type B."""

    @property
    @abstractmethod
    def family_name(self) -> str:
        """Return the product family name."""

    @abstractmethod
    def render(self) -> str:
        """Render the checkbox."""


class UiFactory(ABC):
    """Abstract Factory for a compatible family of UI products."""

    @abstractmethod
    def create_button(self) -> Button:
        """Create a button from this factory's family."""

    @abstractmethod
    def create_checkbox(self) -> Checkbox:
        """Create a checkbox from this factory's family."""


class LightButton(Button):
    @property
    def family_name(self) -> str:
        return "light"

    def render(self) -> str:
        return "Light button"


class LightCheckbox(Checkbox):
    @property
    def family_name(self) -> str:
        return "light"

    def render(self) -> str:
        return "Light checkbox"


class DarkButton(Button):
    @property
    def family_name(self) -> str:
        return "dark"

    def render(self) -> str:
        return "Dark button"


class DarkCheckbox(Checkbox):
    @property
    def family_name(self) -> str:
        return "dark"

    def render(self) -> str:
        return "Dark checkbox"


class LightUiFactory(UiFactory):
    """Concrete Factory for the light product family."""

    def create_button(self) -> Button:
        return LightButton()

    def create_checkbox(self) -> Checkbox:
        return LightCheckbox()


class DarkUiFactory(UiFactory):
    """Concrete Factory for the dark product family."""

    def create_button(self) -> Button:
        return DarkButton()

    def create_checkbox(self) -> Checkbox:
        return DarkCheckbox()


class Application:
    """Client that uses only abstract factory and product contracts."""

    def __init__(self, factory: UiFactory) -> None:
        self._factory = factory

    def render(self) -> str:
        button = self._factory.create_button()
        checkbox = self._factory.create_checkbox()

        if button.family_name != checkbox.family_name:
            raise ValueError("Products must belong to the same family")

        return f"{button.render()} | {checkbox.render()}"


if __name__ == "__main__":
    for factory in (LightUiFactory(), DarkUiFactory()):
        print(Application(factory).render())

