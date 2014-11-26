/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.jaxen;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import org.jaxen.NamespaceContext;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.common.QNameModule;

final class ConverterNamespaceContext extends Converter<String, QNameModule> implements NamespaceContext {
    private final Converter<String, QNameModule> delegate;

    ConverterNamespaceContext(final Converter<String, QNameModule> convertor) {
        this.delegate = Preconditions.checkNotNull(convertor);
    }

    @Override
    protected QNameModule doForward(final String a) {
        return delegate.convert(a);
    }

    @Override
    protected String doBackward(final QNameModule b) {
        return delegate.reverse().convert(b);
    }

    @Override
    public String translateNamespacePrefixToUri(final String prefix) {
        return convert(prefix).getNamespace().toString();
    }

    String jaxenQName(final QName qname) {
        return reverse().convert(qname.getModule()) + ':' + qname.getLocalName();
    }
}
