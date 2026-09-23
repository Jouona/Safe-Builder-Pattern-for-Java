# Safe Builder Pattern for Java

## Contents
- [Introduction](#Introduction)
- [Safer Builder Pattern](#Safer-Builder-Pattern)
- [Problems](#Problems)
  - [Lombok](#lombok)
  - [Building Order](#building-order-problem)
- [Benefits](#Benefits)
  - [Building Order](#building-order-benefit)
  - [*<u>**Compile-Time Safety**</u>*](#compile-time-safety)
- [Examples](#Examples)

## Introduction

In Java, the builder pattern is used extensively to create objects.

Its main feat arguably being its easy-to-read syntax.

It offers other feats too, such as immutability and encapsulation, but those can also be achieved with other patterns
(or even constructors).

In my opinion, it is quite a big problem that the builder pattern does not offer any safety involved with required 
fields. <br/>
The builder pattern by default will implicitly *consider all building steps optional*, which could very well be a
strength of the pattern if it wasn't consequently used to create objects that also **require** some building steps.

Clearly, we need a safer builder pattern that can *enforce required fields*.

## Safer Builder Pattern

A safer builder pattern can be achieved by defining an interface for each **building step** in a Builder class.

There will then be a private-scoped inner **builder** class that implements all the interfaces.

The outer class will provide a start-method that returns the interface defined for the first building step.

The interfaces will effectively "pass" the builder to the next step (or rather the next interface), allowing for a safer 
construction. This way, you will only allow to call build on the very last step.

The last step is special, as it will not only define a build method but it also defines all **optional steps**.

```Java
public class ItemBuilder {

    public interface FirstStep {
        SecondStep withRequiredValue1(String requiredValue1);
    }

    public interface SecondStep {
        FinalStep withRequiredValue2(String requiredValue2);
    }

    public interface FinalStep {
        FinalStep withOptionalValue1(String optionalValue1);

        FinalStep withOptionalValue2(String optionalValue2);

        Item build();
    }

    private static class Builder implements FirstStep, SecondStep, FinalStep {

        String requiredValue1;
        String requiredValue2;
        String optionalValue1 = "";
        String optionalValue2 = "";

        public SecondStep withRequiredValue1(String requiredValue1) {
            this.requiredValue1 = requiredValue1;
            return this;
        }

        public FinalStep withRequiredValue2(String requiredValue2) {
            this.requiredValue2 = requiredValue2;
            return this;
        }

        public FinalStep withOptionalValue1(String optionalValue1) {
            this.optionalValue1 = optionalValue1;
            return this;
        }

        public FinalStep withOptionalValue2(String optionalValue2) {
            this.optionalValue2 = optionalValue2;
            return this;
        }

        @Override
        public Item build() {
            return new Item(requiredValue1, requiredValue2, optionalValue1, optionalValue2);
        }
    }
}
```

## Problems

### Lombok

Lombok does not implement this safe builder. Therefore, you would currently have to implement them yourself. This
arguably does not take as much time as expected once you get used to the safe builder pattern, but it for sure takes
more time than annotating your classes with Lombok's `@Builder` annotation.

Implementing builders yourself can possibly open your mind to new ways of thinking about object construction, though. 
You can possibly offload some construction logic to the builders that would otherwise live in the class itself, which 
can be quite useful in practice.

In theory, however, this safe-builder pattern **could be automated**.

### Building order <a name="building-order-problem"></a>

This safer implementation of the builder pattern introduces an order for building objects. This can break existing
projects.

Though, arguably, it is best to have an enforced order for ease of use during development. An explicit order can help
avoid confusion, especially when creating many objects at once.

## Benefits

### Building order <a name="building-order-benefit"></a>

Building order is also a potential benefit. An explicit order can help avoid confusion, especially when creating many 
objects at once, where you might be more vulnerable to mixing up some values without an explicit order.

See how the *order of the builder methods is not enforced* in the example below. It is quite hard to see that the
order was messed up two times which caused animal's races to be set to their names and the other way around.

```Java
import com.jouona.example.Animal;
import com.jouona.example.AnimalUnsafeBuilder;

void main() {
    Animal.unsafeBuilder()
            .withRace("cat")
            .withName("tom")
            .withAge(10);
    Animal.unsafeBuilder()
            .withRace("elephant")
            .withName("benjamin")
            .withAge(10);
    Animal.unsafeBuilder()
            .withRace("mouse")
            .withName("jerry")
            .withAge(10);
    Animal.unsafeBuilder()
            .withRace("cat")
            .withName("garfield")
            .withAge(10);
    Animal.unsafeBuilder()
            .withName("dog")
            .withRace("snoopy")
            .withAge(10);
    Animal.unsafeBuilder()
            .withRace("duck")
            .withName("donald")
            .withAge(10);
}
```

### Compile-Time Safety

This is arguably the biggest benefit of using a safe builder.

It is enforced at compile-time that all required fields are set. This catches many errors upfront where you would 
otherwise be throwing exceptions at runtime or even unwillingly crashing your application.

## Examples

See [Animal Safe Builder](src/main/java/com/jouona/example/AnimalSafeBuilder.java) for the safe builder implementation 
similar to that shown above.

See [Animal Safe Builder 2](src/main/java/com/jouona/example/AnimalSafeBuilder2.java) for a different implementation
that makes required fields more explicit. Some people might prefer this.

See [Animal Unsafe Builder](src/main/java/com/jouona/example/AnimalUnsafeBuilder.java) for an unsafe builder 
implementation.

See [how all these builders differ in use](src/main/java/com/jouona/usage/UsageExamples.java). You can play around
with different usages here by yourself to see their compile-time safety. <br/>
If you actually want to run some code, you can use the [empty Main class](src/main/java/com/jouona/Main.java).
