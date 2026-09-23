package com.jouona.usage;

import com.jouona.example.Animal;

public class UsageExamples {

    void example1() {

        // ====== unsafe builder ======

        // With the unsafe builder, you can build without any fields being set.
        // This can quickly lead to runtime errors.
        Animal.unsafeBuilder()
                .build();

        // You could also still build an Animal without the required field "age" being set.
        // In fact, there is no concept of "required" and "optional" fields.
        Animal.unsafeBuilder()
                .withName("tom")
                .withRace("cat")
                .build();


        // ====== safe builder ======

        // Using the safe builder in both these cases, you get a compile-time error as shown here.
        Animal.safeBuilder()
                .build();

        // And here again.
        Animal.safeBuilder()
                .withName("tom")
                .withRace("cat")
                .build();

        // The safe builder, notably, enforces the order of the individual building steps.
        // See how this is invalid:
        Animal.safeBuilder()
                .withAge(10)
                .withRace("cat")
                .withName("tom")
                .build();
        // but this is not valid, even though all required fields have been set. This behavior is a bit clunky, but it
        // can also be useful by having all building steps in the same order at all times (note that this is not true
        // for optional fields that are not under any ordering rules, after the "FinalStep" has been reached).
        Animal.safeBuilder()
                .withName("tom")
                .withRace("cat")
                .withAge(10)
                .build();

        // We can skip the optional steps, of course
        Animal.safeBuilder()
                .withAge(10)
                .withRace("cat")
                .build();


        // ====== safe builder 2 ======

        // This is also an implementation that some people may prefer syntactically. Here the required steps are more
        // explicitly marked as explicit.
        Animal.safeBuilder2()
                .withRequiredFields(10, "cat")
                .build();

        // setting optional fields
        Animal.safeBuilder2()
                .withRequiredFields(10, "cat")
                .withName("tom")
                .build();
    }
}
