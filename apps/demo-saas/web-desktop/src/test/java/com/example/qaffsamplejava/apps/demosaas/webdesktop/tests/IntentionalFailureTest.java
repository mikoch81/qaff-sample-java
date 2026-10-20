package com.example.qaffsamplejava.apps.demosaas.webdesktop.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Phase 112 live check: deliberately red run to verify the failure notification. Removed right after. */
class IntentionalFailureTest {
    @Test
    void failsOnPurpose() {
        Assertions.fail("Intentional failure for the Phase 112 notification check");
    }
}
