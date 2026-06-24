package com.devsu.e2e;

import com.intuit.karate.junit5.Karate;

class E2ETest {

    @Karate.Test
    Karate banco() {
        return Karate.run("classpath:features/banco.feature");
    }
}
