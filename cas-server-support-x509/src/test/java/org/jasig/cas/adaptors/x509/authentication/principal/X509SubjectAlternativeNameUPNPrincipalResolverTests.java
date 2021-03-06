/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.adaptors.x509.authentication.principal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Unit test for {@link org.jasig.cas.adaptors.x509.authentication.principal.X509SubjectAlternativeNameUPNPrincipalResolver}.
 *
 * @author Dmitriy Kopylenko
 */
@RunWith(Parameterized.class)
public class X509SubjectAlternativeNameUPNPrincipalResolverTests {

    private X509Certificate certificate;
    private final X509SubjectAlternativeNameUPNPrincipalResolver resolver;
    private final String expected;

    /**
     * Creates a new test instance with the given parameters.
     *
     * @param certPath
     * @param expectedResult
     */
    public X509SubjectAlternativeNameUPNPrincipalResolverTests(
            final String certPath,
            final String expectedResult) {

        this.resolver = new X509SubjectAlternativeNameUPNPrincipalResolver();
        try {
            this.certificate = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(
                    new FileInputStream(getClass().getResource(certPath).getPath()));
        } catch (final Exception e) {
            Assert.fail(String.format("Error parsing certificate %s: %s", certPath, e.getMessage()));
        }
        this.expected = expectedResult;
    }

    /**
     * Gets the unit test parameters.
     *
     * @return Test parameter data.
     */
    @Parameters
    public static Collection<Object[]> getTestParameters() {
        final Collection<Object[]> params = new ArrayList<Object[]>();

        params.add(new Object[] {
                "/x509-san-upn-resolver.crt",
                "test-user@some-company-domain"
        });
        return params;
    }

    @Test
    public void testResolvePrincipalInternal() {
        Assert.assertEquals(this.expected, this.resolver.resolvePrincipalInternal(this.certificate));
    }

}
