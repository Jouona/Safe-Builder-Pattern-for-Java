package com.jouona.example;

/**
 * Second variant of a compile-time safe builder that makes required fields more explicit syntactically.
 */
public final class AnimalSafeBuilder2 {

    /**
     * required step
     */
    public interface RequiredStep {
        FinalStep withRequiredFields(int age, String race);
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
    static RequiredStep start() {
        return new Builder();
    }

    private static final class Builder implements RequiredStep, FinalStep {

        private int age;
        private String race;
        private String name;

        @Override
        public FinalStep withRequiredFields(int age, String race) {
            this.age = age;
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
