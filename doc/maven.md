mvn clean package -DskipTests

or

mvn clean package -Dmaven.test.skip=true

Difference:

    -DskipTests: Skips the execution of tests but still compiles them.
    -Dmaven.test.skip=true: Skips both compilation and execution of tests.