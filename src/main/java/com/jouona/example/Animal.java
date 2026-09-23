package com.jouona.example;

import java.util.Objects;
import java.util.Optional;

public final class Animal {

    /**
     * {@code age} is a required field because all animals intrinsically have an age.
     */
    private final int age;

    /**
     * {@code race} is a required field too. The same reason as above.
     */
    private final String race;

    /**
     * {@code name} is <b>not</b> a required field, because not all animals have a name.
     * <p>
     * It does not have to be set when building an {@code Animal} instance.
     */
    private final Optional<String> name;

    public static AnimalSafeBuilder.AgeStep safeBuilder() {
        return AnimalSafeBuilder.start();
    }

    public static AnimalSafeBuilder2.RequiredStep safeBuilder2() {
        return AnimalSafeBuilder2.start();
    }

    public static AnimalUnsafeBuilder unsafeBuilder() {
        return AnimalUnsafeBuilder.start();
    }

    static Animal of(int age, String race) {
        Objects.requireNonNull(race);
        System.out.println("The animal squeaks sadly. It was not given a name.");
        return new Animal(age, race, Optional.empty());
    }

    static Animal of(int age, String race, String name) {
        Objects.requireNonNull(race);
        Objects.requireNonNull(name);
        System.out.printf("%s squeaks happily. It was given a name.", name);
        return new Animal(age, race, Optional.of(name));
    }

    private Animal(int age, String race, Optional<String> name) {
        this.age = age;
        this.race = race;
        this.name = name;
    }
}
