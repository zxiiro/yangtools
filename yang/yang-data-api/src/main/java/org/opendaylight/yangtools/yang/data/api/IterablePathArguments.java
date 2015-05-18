/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.data.api;

import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;

/*
 * This base class provides binary backwards compatibility for Helium, which
 * used an Iterable instead of collection.
 *
 * @deprecated remove this class in Beryllium
 */
@Deprecated
abstract class IterablePathArguments {
    public abstract Iterable<PathArgument> getPathArguments();
    public abstract Iterable<PathArgument> getReversePathArguments();
}
