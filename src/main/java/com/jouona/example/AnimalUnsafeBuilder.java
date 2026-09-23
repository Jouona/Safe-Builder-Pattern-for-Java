package com.jouona.example;

/**
 * Classic builder pattern. Similar to how Lombok would generate it.
 * <p>
 * Note that Lombok would use {@code Optional<String>} as the type of {@code name}. I decided against an Optional here
 * because I believe that this way the builder showcases how builders can quickly become more complex with their logic.
 */
public final class AnimalUnsafeBuilder {

    int age;
    String race;
    String name;

    static AnimalUnsafeBuilder start() {
        return new AnimalUnsafeBuilder();
    }

    public AnimalUnsafeBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public AnimalUnsafeBuilder withRace(String race) {
        this.race = race;
        return this;
    }

    public AnimalUnsafeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Animal build() {

        // The Animal-class handles this if-case well on its own via its AllArgsConstructor, but we include it here to show that there
        // might be additional complexity involved during building when optional fields are not set.
        if (name == null) {
            return Animal.of(this.age, this.race);
        }

        return Animal.of(this.age, this.race, this.name);
    }
}
