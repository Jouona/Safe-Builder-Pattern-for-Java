package com.jouona.example;

/**
 * Compile-time safe Builder-Pattern that introduces the concept of <i>steps</i> in its implementation.
 * <p>
 * This additional complexity is, however, not being transferred to client code that uses the safe builder pattern.
 */
public final class AnimalSafeBuilder {

    /**
     * first step
     */
    public interface AgeStep {
        RaceStep withAge(int age);
    }

    /**
     * second step
     */
    public interface RaceStep {
        FinalStep withRace(String race);
    }

    /**
     * final step
     * <p>
     * At this step you can either build the {@link Animal} or you can perform more optional building steps:
     * <ul>
     *   <li>{@code name}</li>
     * </ul>
     */
    public interface FinalStep {
        FinalStep withName(String name);

        Animal build();
    }

    /**
     * Starts the builder with its first step - the age step.
     */
    static AgeStep start() {
        return new Builder();
    }

    private static final class Builder implements AgeStep, RaceStep, FinalStep {

        private int age;
        private String race;
        private String name;

        @Override
        public RaceStep withAge(int age) {
            this.age = age;
            return this;
        }

        @Override
        public FinalStep withRace(String race) {
            this.race = race;
            return this;
        }

        @Override
        public FinalStep withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Animal build() {

            // The Animal-class handles this if-case well on its own via its AllArgsConstructor, but we include it here to show that there
            // might be additional complexity involved during building when optional fields are not set.
            if (name == null) {
                return Animal.of(this.age, this.race);
            }

            return Animal.of(this.age, this.race, this.name);
        }
    }
}
